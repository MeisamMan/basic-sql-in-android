package org.meicode.sqlinandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "university";

    public SQLiteDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCommand = "CREATE TABLE students (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT);";
        db.execSQL(sqlCommand);

        String sqlInsert = "INSERT INTO students (name, email) VALUES (\"Meisam\", \"Meisam@meiCode.org\")";
        db.execSQL(sqlInsert);

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", "Brad");
        contentValues.put("email", "Brad@meiCode.org");
        db.insert("students", null, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sqlStatement = "ALTER TABLE students ADD COLUMN grade DOUBLE";
        db.execSQL(sqlStatement);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);

    }

    public void insert (SQLiteDatabase db, String tableName, String name, String email) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("email", email);
        db.insert(tableName, null, contentValues);
    }

    public Cursor read (SQLiteDatabase db, String tableName) {
        Cursor cursor = db.query(tableName, null, null, null, null, null, null);
        return cursor;
    }

    public void updateName (SQLiteDatabase db, String tableName, String oldName, String newName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", newName);
        db.update(tableName, contentValues, "name=?", new String[] {oldName});
    }

    public void delete (SQLiteDatabase db, String tableName, String name) {
        db.delete(tableName, "name=?", new String[] {name});
    }
}
