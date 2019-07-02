package org.meicode.sqlinandroid;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private TextView textView;
    private SQLiteDatabase database;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        textView = (TextView) findViewById(R.id.textView);

        DatabaseAsyncTask databaseAsyncTask = new DatabaseAsyncTask();
        databaseAsyncTask.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        cursor.close();
        database.close();

    }

    private class DatabaseAsyncTask extends AsyncTask<Void, Void, String> {

        private SQLiteDatabaseHelper databaseHelper;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            databaseHelper = new SQLiteDatabaseHelper(MainActivity.this);
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                database = databaseHelper.getReadableDatabase();
//                databaseHelper.insert(database, "students", "Sarah", "Sarah@meiCode.org");
//                databaseHelper.delete(database, "students", "Sarah");
                databaseHelper.updateName(database, "students", "Brad", "Tom");
//                Cursor cursor = database.query("students", null, null,
//                        null,
//                        null, null, "_ID");

//                Cursor cursor = databaseHelper.read(database, "students");

                String sqlStatement = "SELECT * FROM students";

                cursor = database.rawQuery(sqlStatement, null);

                String returnString = "";
                if (cursor.moveToFirst()) {

                    for (int i=0; i<cursor.getCount(); i++) {
                        for (int j=0; j<cursor.getColumnCount(); j++) {
                            returnString += cursor.getColumnName(j) + ": " + cursor.getString(j) + "\n";
                        }
                        returnString += "**********\n";
                        cursor.moveToNext();
                    }

                    return returnString;
                }

//                SimpleCursorAdapter adapter = new SimpleCursorAdapter(
//                        MainActivity.this,
//                        android.R.layout.simple_list_item_1,
//                        cursor,
//                        new String[] {"name"},
//                        new int[] {android.R.id.text1},
//                        0
//                );
//                listView.setAdapter(adapter);
            }catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            textView.setText(s);
        }
    }
}
