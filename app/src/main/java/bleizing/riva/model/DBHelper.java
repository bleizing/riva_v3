package bleizing.riva.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String NAMA_DB = "Riva.db";
    private static final int VERSION = 1;

    private static final String TABLE_GDS = "Gds";

    private static final String KOLOM_ID = "id";
    private static final String KOLOM_ANGKA = "angka";
    private static final String KOLOM_CATATAN = "catatan";
    private static final String KOLOM_TIME = "time";

    public DBHelper(Context context) {
        super(context, NAMA_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create = "CREATE TABLE " + TABLE_GDS + " (" + KOLOM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KOLOM_ANGKA + " TEXT, " + KOLOM_CATATAN + " TEXT, " + KOLOM_TIME + " REAL)";
        sqLiteDatabase.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_GDS);
        onCreate(sqLiteDatabase);
    }

    public boolean inputGds(GulaDarah gulaDarah) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KOLOM_ANGKA, gulaDarah.getAngka());
        values.put(KOLOM_CATATAN, gulaDarah.getCatatan());
        values.put(KOLOM_TIME, gulaDarah.getTime());

        long success = sqLiteDatabase.insert(TABLE_GDS, null, values);
//        if (success != -1) {
//            return true;
//        } else {
//            return false;
//        }

        return success != -1;
    }

    public boolean editGds(GulaDarah gulaDarah) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KOLOM_ANGKA, gulaDarah.getAngka());
        values.put(KOLOM_CATATAN, gulaDarah.getCatatan());
        values.put(KOLOM_TIME, gulaDarah.getTime());

        long success = sqLiteDatabase.update(TABLE_GDS, values, KOLOM_ID + "=?", new String[] {String.valueOf(gulaDarah.getId())});

        return success != -1;
    }

    public GulaDarah getGdsByTime(GulaDarah gulaDarah) {
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        String select="SELECT * FROM " + TABLE_GDS + " WHERE " + KOLOM_TIME + "=" + gulaDarah.getTime();
        Cursor cursor=sqLiteDatabase.rawQuery(select,null);

        if (cursor.moveToNext()) {
            gulaDarah.setId(cursor.getInt(cursor.getColumnIndex(KOLOM_ID)));
        } else {
            gulaDarah = null;
        }

        return gulaDarah;
    }

    public ArrayList<GulaDarah> getAllGds() {
        ArrayList<GulaDarah> gulaDarahArrayList = new ArrayList<>();
        GulaDarah gulaDarah;
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String select = "SELECT * FROM " + TABLE_GDS;

        Cursor cursor = sqLiteDatabase.rawQuery(select, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(KOLOM_ID));
                String angka = cursor.getString(cursor.getColumnIndex(KOLOM_ANGKA));
                String catatan = cursor.getString(cursor.getColumnIndex(KOLOM_CATATAN));
                long time = cursor.getLong(cursor.getColumnIndex(KOLOM_TIME));

                gulaDarah = new GulaDarah(id, angka, catatan, time);
//                Log.d("DBHelper", "gulaDarahId = " + gulaDarah.getId() + " & gulaDarahAngka = " + gulaDarah.getAngka());
                gulaDarahArrayList.add(gulaDarah);
            } while (cursor.moveToNext());
        }

        return gulaDarahArrayList;
    }

    public ArrayList<GulaDarah> getGdsByLimit(String limit) {
        ArrayList<GulaDarah> gulaDarahArrayList = new ArrayList<>();
        GulaDarah gulaDarah;
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String select = "SELECT * FROM " + TABLE_GDS + " ORDER BY " + KOLOM_TIME + " ASC LIMIT 0," + limit;

        Cursor cursor = sqLiteDatabase.rawQuery(select, null);

//        if (cursor.moveToFirst()) {
//            do {
//                int id = cursor.getInt(cursor.getColumnIndex(KOLOM_ID));
//                String angka = cursor.getString(cursor.getColumnIndex(KOLOM_ANGKA));
//                String catatan = cursor.getString(cursor.getColumnIndex(KOLOM_CATATAN));
//                long time = cursor.getLong(cursor.getColumnIndex(KOLOM_TIME));
//
//                gulaDarah = new GulaDarah(id, angka, catatan, time);
//                Log.d("DBHelper", "getGdsByLimit -> limit = " + limit);
//                Log.d("DBHelper", "gulaDarahId = " + gulaDarah.getId() + " & gulaDarahAngka = " + gulaDarah.getAngka());
//                gulaDarahArrayList.add(gulaDarah);
//            } while (cursor.moveToNext());
//        }

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(KOLOM_ID));
            String angka = cursor.getString(cursor.getColumnIndex(KOLOM_ANGKA));
            String catatan = cursor.getString(cursor.getColumnIndex(KOLOM_CATATAN));
            long time = cursor.getLong(cursor.getColumnIndex(KOLOM_TIME));

            gulaDarah = new GulaDarah(id, angka, catatan, time);
//            Log.d("DBHelper", "getGdsByLimit -> limit = " + limit);
//            Log.d("DBHelper", "gulaDarahId = " + gulaDarah.getId() + " & gulaDarahAngka = " + gulaDarah.getAngka());
            gulaDarahArrayList.add(gulaDarah);
        }

        return gulaDarahArrayList;
    }

    public ArrayList<GulaDarah> getGdsByRangeTime(String timeStart, String timeEnd) {
        ArrayList<GulaDarah> gulaDarahArrayList = new ArrayList<>();
        GulaDarah gulaDarah;
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String select = "SELECT * FROM " + TABLE_GDS + " WHERE " + KOLOM_TIME + " >= " + timeStart + " AND " + KOLOM_TIME + " <= " + timeEnd + " ORDER BY " + KOLOM_TIME + " ASC";

        Cursor cursor = sqLiteDatabase.rawQuery(select, null);

//        if (cursor.moveToFirst()) {
//            do {
//                int id = cursor.getInt(cursor.getColumnIndex(KOLOM_ID));
//                String angka = cursor.getString(cursor.getColumnIndex(KOLOM_ANGKA));
//                String catatan = cursor.getString(cursor.getColumnIndex(KOLOM_CATATAN));
//                long time = cursor.getLong(cursor.getColumnIndex(KOLOM_TIME));
//
//                gulaDarah = new GulaDarah(id, angka, catatan, time);
//                Log.d("DBHelper", "getGdsByRangeTime -> timeStart = " + timeStart + " & timeEnd = " + timeEnd);
//                Log.d("DBHelper", "gulaDarahId = " + gulaDarah.getId() + " & gulaDarahAngka = " + gulaDarah.getAngka());
//                gulaDarahArrayList.add(gulaDarah);
//            } while (cursor.moveToNext());
//        }

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(KOLOM_ID));
            String angka = cursor.getString(cursor.getColumnIndex(KOLOM_ANGKA));
            String catatan = cursor.getString(cursor.getColumnIndex(KOLOM_CATATAN));
            long time = cursor.getLong(cursor.getColumnIndex(KOLOM_TIME));

            gulaDarah = new GulaDarah(id, angka, catatan, time);
//            Log.d("DBHelper", "getGdsByRangeTime -> timeStart = " + timeStart + " & timeEnd = " + timeEnd);
//            Log.d("DBHelper", "gulaDarahId = " + gulaDarah.getId() + " & gulaDarahAngka = " + gulaDarah.getAngka());
            gulaDarahArrayList.add(gulaDarah);
        }

        return gulaDarahArrayList;
    }

    public void clearTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GDS, null, null);
        db.close();
    }
}
