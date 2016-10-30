package bruno.udacity.com.studentguardian.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Bruno on 29/10/2016.
 */
public class Utils {

    public static void saveImageToExternalStorage(Context context, Bitmap capturedBitmap, int codeNote){
        String path = Environment.getExternalStorageDirectory().toString();
        OutputStream fOutputStream = null;
        File file = new File(path + "/NotesImages/", codeNote + ".jpg"); //The name is the code number . format
        if (!file.exists()) {
            file.mkdirs();
        }

        try {
            fOutputStream = new FileOutputStream(file);

            capturedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOutputStream);

            fOutputStream.flush();
            fOutputStream.close();

            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap getBitmapFromUri(Context context, Uri uri) throws IOException{
        ParcelFileDescriptor parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
        assert parcelFileDescriptor != null;
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();

        return image;
    }

}
