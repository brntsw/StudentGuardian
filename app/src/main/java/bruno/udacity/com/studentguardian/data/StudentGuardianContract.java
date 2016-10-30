package bruno.udacity.com.studentguardian.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by BPardini on 24/10/2016.
 */

public class StudentGuardianContract {

    public static final String CONTENT_AUTHORITY = "bruno.udacity.com.studentguardian";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_USER = "user";
    public static final String PATH_STUDENT = "student";
    public static final String PATH_SUBJECT = "subject";
    public static final String PATH_EVALUATION = "evaluation";
    public static final String PATH_TYPE_EVALUATION = "type_evaluation";
    public static final String PATH_ABSENCE = "absence";
    public static final String PATH_CONTENT_CLASS = "content_class";
    public static final String PATH_NOTE = "note";

    public static final class UserEntry implements BaseColumns{
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;

        // Table name
        public static final String TABLE_NAME = "user";

        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PROFILE = "profile";
        public static final String COLUMN_DATE_BIRTH = "date_birth";
        public static final String COLUMN_LOGGED = "logged";

        public static Uri buildUserUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class StudentEntry implements BaseColumns{
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_STUDENT).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STUDENT;

        // Table name
        public static final String TABLE_NAME = "student";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DATE_BIRTH = "date_birth";
        public static final String COLUMN_ACTIVE = "active";

        public static Uri buildStudentUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class SubjectEntry implements BaseColumns{
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SUBJECT).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SUBJECT;

        // Table name
        public static final String TABLE_NAME = "subject";

        public static final String COLUMN_CODE = "code";
        public static final String COLUMN_NAME = "name";

        public static Uri buildSubjectUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class EvaluationEntry implements BaseColumns{
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_EVALUATION).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EVALUATION;

        // Table name
        public static final String TABLE_NAME = "evaluation";

        public static final String COLUMN_CODE = "code";
        public static final String COLUMN_CODE_SUBJECT = "code_subject";
        public static final String COLUMN_TYPE_EVALUATION = "type_evaluation";
        public static final String COLUMN_NAME = "code_type_evaluation";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_GRADE = "grade";

        public static Uri buildEvaluationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class TypeEvaluationEntry implements BaseColumns{
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TYPE_EVALUATION).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TYPE_EVALUATION;

        // Table name
        public static final String TABLE_NAME = "type_evaluation";

        public static final String COLUMN_CODE = "code";
        public static final String COLUMN_TYPE = "type";

        public static Uri buildTypeEvaluationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class AbsenceEntry implements BaseColumns{
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ABSENCE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ABSENCE;

        // Table name
        public static final String TABLE_NAME = "absence";

        public static final String COLUMN_CODE_SUBJECT = "code_subject";
        public static final String COLUMN_ABSENCES = "count_absences";

        public static Uri buildAbsenceUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class ContentClassEntry implements BaseColumns{
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CONTENT_CLASS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CONTENT_CLASS;

        // Table name
        public static final String TABLE_NAME = "content_class";

        public static final String COLUMN_CODE = "code";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_DATE = "date";

        public static Uri buildContentClassUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class NoteEntry implements BaseColumns{
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_NOTE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NOTE;

        // Table name
        public static final String TABLE_NAME = "note";

        public static final String COLUMN_CODE = "code";
        public static final String COLUMN_CODE_SUBJECT = "code_subject";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_EVIDENCE_IMAGE = "path_evidence_image";
        public static final String COLUMN_GRAVITY = "gravity";

        public static Uri buildNoteUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

}
