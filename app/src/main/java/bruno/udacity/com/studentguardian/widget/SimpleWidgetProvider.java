package bruno.udacity.com.studentguardian.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import bruno.udacity.com.studentguardian.R;

/**
 * Created by Bruno on 29/10/2016.
 */
public class SimpleWidgetProvider extends AppWidgetProvider {

    public static String UPDATE_ACTION = "actionUpdate";

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        final int count = appWidgetIds.length;

        for(int i = 0; i < count; i++){
            int widgetId = appWidgetIds[i];

            // Tell the AppWidgetManager to perform an update on the current app widget
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
            appWidgetManager.updateAppWidget(widgetId, views);
        }
    }

    public void onReceive(Context context, Intent intent){
        String action = intent.getAction();
        Bundle extras = intent.getExtras();

        String studentName = extras.getString("studentName");
        double lowestGrade = extras.getDouble("lowestGrade");
        String noteTitle = extras.getString("noteTitle");
        String noteDescription = extras.getString("noteDescription");
        String date = extras.getString("date");

        if(action != null && action.equals(UPDATE_ACTION)){
            final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName name = new ComponentName(context, SimpleWidgetProvider.class);
            int[] appWidgetId = AppWidgetManager.getInstance(context).getAppWidgetIds(name);
            final int count = appWidgetId.length;
            if(count < 1){
                return;
            }
            else{
                int id = appWidgetId[count-1];
                updateWidget(context, appWidgetManager, id, studentName, lowestGrade, noteTitle, noteDescription, date);
            }
        }
        else{
            super.onReceive(context, intent);
        }
    }

    static void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, String studentName,
                             double lowestGrade, String noteTitle, String noteDescription, String date){

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        views.setTextViewText(R.id.tv_student_name, studentName);
        views.setTextViewText(R.id.tv_lowest_grade, String.valueOf(lowestGrade));
        views.setTextViewText(R.id.tv_note_title, noteTitle);
        views.setTextViewText(R.id.tv_note_description, noteDescription);
        views.setTextViewText(R.id.tv_note_date, date);
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

}
