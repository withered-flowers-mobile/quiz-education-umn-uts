package id.ac.umn.quiz1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by aldo_ on 20/03/2018.
 */

public class MemberContract {
    public static class MemberEntry implements BaseColumns{
        public static final String TABLE_NAME = "member";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_POSITION = "position";
    }

    public static final String SQL_CREATE_MEMBER = String.format(
            "CREATE TABLE %s(%s, %s, %s)",
            MemberEntry.TABLE_NAME,
            String.format("%s INTEGER PRIMARY KEY AUTOINCREMENT", MemberEntry._ID),
            String.format("%s VARCHAR(100)", MemberEntry.COLUMN_NAME_NAME),
            String.format("%s VARCHAR(100)", MemberEntry.COLUMN_NAME_POSITION)
    );
    public static final String SQL_DELETE_MEMBER = String.format(
            "DROP TABLE IF EXISTS %s",
            MemberEntry.TABLE_NAME
    );
    private MemberContract(){}

    public static class MemberDbHelper extends SQLiteOpenHelper{
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "member.db";
        public MemberDbHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(SQL_CREATE_MEMBER);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1){
            db.execSQL(SQL_DELETE_MEMBER);
            this.onCreate(db);
        }
        @Override
        public void onDowngrade(SQLiteDatabase db, int i, int i1){
            onUpgrade(db,i,i1);
        }
    }
}
