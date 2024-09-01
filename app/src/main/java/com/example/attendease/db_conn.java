package com.example.attendease;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class db_conn extends SQLiteOpenHelper
{
    public final static String db_name = "AttendEase_DB";
    public final static int db_version = 1;
    public final static String TABLE_NAME = "Teachers";
    public final static String ID_COL = "id";
    public final static String NAME_COL = "Name";
    public final static String USERNAME_COL = "Username";
    public final static String EMAIL_COL = "Email";
    public final static String PASSWORD_COL = "Password";

    public db_conn (Context context)
    {
        super(context, db_name, null, db_version);
    }

    public void onCreate (SQLiteDatabase db)
    {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT, "
                + USERNAME_COL +  " TEXT, "
                + EMAIL_COL + " TEXT, "
                + PASSWORD_COL + " TEXT);";
        db.execSQL(CREATE_TABLE);
    }

    public boolean signup (String Name, String Username, String Email, String Password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COL, Name);
        values.put(USERNAME_COL, Username);
        values.put(EMAIL_COL, Email);
        values.put(PASSWORD_COL, Password);
        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result != -1;
    }

    public Cursor userExists ()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] cols = {
                USERNAME_COL
        };

        return db.query(
                TABLE_NAME,   // The table to query
                cols,         // The columns to return
                null,         // The columns for the WHERE clause
                null,         // The values for the WHERE clause
                null,         // Group the rows
                null,         // Filter by row groups
                null          // The sort order
        );
    }

    public Cursor login()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] cols = {
                USERNAME_COL,
                PASSWORD_COL
        };

        return db.query(
                TABLE_NAME,   // The table to query
                cols,         // The columns to return
                null,         // The columns for the WHERE clause
                null,         // The values for the WHERE clause
                null,         // Group the rows
                null,         // Filter by row groups
                null          // The sort order
        );
    }

    public boolean addClass (String CLASS_NAME, int size)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            StringBuilder createTable = new StringBuilder("CREATE TABLE ")
                    .append(CLASS_NAME)
                    .append(" (")
                    .append(ID_COL)
                    .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                    .append("date TEXT, ");

            for (int i = 1; i <= size; i++)
            {
                createTable.append("rollno_")
                        .append(i)
                        .append(" INTEGER");
                if (i < size)
                {
                    createTable.append(", ");
                }
            }

            createTable.append(");");

            db.execSQL(createTable.toString());

            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeClass (String CLASS_NAME)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            String deleteTable = "DROP TABLE IF EXISTS " + CLASS_NAME + ";";
            db.execSQL(deleteTable);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean classExists (String CLASS_NAME)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", CLASS_NAME});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int count = cursor.getInt(0);
                cursor.close();
                return count > 0;
            }
            cursor.close();
        }
        return false;
    }

    public int getTableCount ()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(name) FROM sqlite_master WHERE type='table'";
        Cursor cursor = null;
        int tableCount = 0;

        try
        {
            cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst())
            {
                tableCount = cursor.getInt(0);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
            db.close();
        }

        return tableCount;
    }

    public int getColumnCount (String CLASS_NAME)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "PRAGMA table_info(" + CLASS_NAME + ")";
        Cursor cursor = null;
        int columnCount = 0;
        try
        {
            cursor = db.rawQuery(query, null);
            if (cursor != null)
            {
                columnCount = cursor.getCount();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
            db.close();
        }
        return columnCount - 2;
    }

    public Cursor getClasses ()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT name FROM sqlite_master WHERE type='table'";

        return db.rawQuery(query, null);
    }

    public boolean addStudent (String CLASS_NAME, int elements) {
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            String query = "PRAGMA table_info(" + CLASS_NAME + ")";
            Cursor cursor = db.rawQuery(query, null);
            int size = cursor.getCount();
            cursor.close();
            for (int i = size - 1; i < size + elements - 1; i++)
            {
                String createTable = "ALTER TABLE " +
                        CLASS_NAME +
                        " ADD COLUMN rollno_" + i + " INTEGER;";
                db.execSQL(createTable);
            }
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeStudent (String CLASS_NAME, int elements) {
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            String query = "PRAGMA table_info(" + CLASS_NAME + ")";
            Cursor cursor = db.rawQuery(query, null);
            int size = cursor.getCount();
            cursor.close();
            for (int i = size - 2; i > size - elements - 2; i--)
            {
                String createTable = "ALTER TABLE " +
                        CLASS_NAME +
                        " DROP COLUMN rollno_" + i + ";";
                db.execSQL(createTable);
            }
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean writeAttendance (String CLASS_NAME, String date, String[] rollNos)
    {
        try
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put("date", date);
            for (int i = 1; i <= rollNos.length; i++)
            {
                contentValues.put("rollno_" + i, rollNos[i - 1]);
            }
            long result = db.insert(CLASS_NAME, null, contentValues);
            if (result == -1)
            {
                throw new SQLException("Failed to insert row into " + CLASS_NAME);
            }
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkIfDateExists(String CLASS_NAME, String date)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT 1 FROM " + CLASS_NAME + " WHERE date = ?";
        Cursor cursor = db.rawQuery(query, new String[]{date});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

    public Cursor returnAttendance(String CLASS_NAME, String Date)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = "date = ?";
        String[] selectionArgs = { Date };

        return db.query(
                CLASS_NAME,   // The table to query
                null,         // The columns to return
                selection,         // The columns for the WHERE clause
                selectionArgs,         // The values for the WHERE clause
                null,         // Group the rows
                null,         // Filter by row groups
                null          // The sort order
        );
    }

    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
