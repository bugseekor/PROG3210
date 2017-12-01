package ca.on.conestogac.assignment1;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by spark1435 on 10/19/2017.
 * editted for third commit
 */

public class AllDB {
    public static final String DB_NAME = "allDB.db";
    public static final int DB_VERSION = 1;
    public static final String UNIT_TABLE = "unit";
    public static final String UNIT_ID = "_id";
    public static final int UNIT_ID_COL = 0;
    public static final String UNIT_NAME = "unit_name";
    public static final int UNIT_NAME_COL = 1;
    public static final String CREATE_UNIT_TABLE =
            "CREATE TABLE " + UNIT_TABLE + " (" +
                    UNIT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    UNIT_NAME + "TEXT NOT NULL);";
    public static final String DROP_UNIT_TABLE =
            "DROP TABLE IF EXISTS " + UNIT_TABLE;

    public static final String EFFICIENCY_TABLE = "efficiency";
    public static final String EFFICIENCY_ID = "_id";
    public static final int EFFICIENCY_ID_COL = 0;
    public static final String EFFICIENCY_UNITTYPE = "unitType";
    public static final int EFFICIENCY_UNITTYPE_COL = 1;
    public static final String EFFICIENCY_FE = "fe";
    public static final int EFFICIENCY_FE_COL = 2;
    public static final String CREATE_EFFICIENCY_TABLE =
            "CREATE TABLE " + EFFICIENCY_TABLE + " (" +
                    EFFICIENCY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    EFFICIENCY_UNITTYPE + " INTEGER NOT NULL, " +
                    EFFICIENCY_FE + " REAL NOT NULL);";
    public static final String DROP_EFFICIENCY_TABLE =
            "DROP TABLE IF EXISTS " + EFFICIENCY_TABLE;

    public static final String CAR_TABLE = "car";
    public static final String CAR_ID = "_id";
    public static final int CAR_ID_COL = 0;
    public static final String CAR_NAME = "name";
    public static final int CAR_NAME_COL = 1;
    public static final String CAR_YEAR = "year";
    public static final int CAR_YEAR_COL = 2;
    public static final String CAR_ENGINE = "engine";
    public static final int CAR_ENGINE_COL = 3;
    public static final String CREATE_CAR_TABLE =
            "CREATE TABLE " + CAR_TABLE + " (" +
                    CAR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CAR_NAME + " TEXT, " +
                    CAR_YEAR + " TEXT, " +
                    CAR_ENGINE + " TEXT);";
    public static final String DROP_CAR_TABLE =
            "DROP TABLE IF EXISTS " + CAR_TABLE;

    private static class DBHelper extends SQLiteOpenHelper{
        public DBHelper(Context context, String name,
                        CursorFactory factory, int version){
            super(context, name, factory, version);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_UNIT_TABLE);
            db.execSQL(CREATE_EFFICIENCY_TABLE);
            db.execSQL(CREATE_CAR_TABLE);
            //insert default lists
            db.execSQL("INSERT INTO unit VALUES (1, 'km/l')");
            db.execSQL("INSERT INTO unit VALUES (2, 'l/100km')");
            db.execSQL("INSERT INTO unit VALUES (3, 'mpg')");

            db.execSQL("INSERT INTO efficiency VALUES (null, 1, 10.2)");
            db.execSQL("INSERT INTO efficiency VALUES (null, 2, 9.4)");
            db.execSQL("INSERT INTO efficiency VALUES (null, 3, 28)");

            db.execSQL("INSERT INTO car VALUES (null, 'Camry', '2017', '3.5')");
            db.execSQL("INSERT INTO car VALUES (null, 'Accord', '2016', '3.5')");
            db.execSQL("INSERT INTO car VALUES (null, 'Sonata', '2015', '2.4')");
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d("Unit", "Upgrading db from version " +
                oldVersion + "to " + newVersion);
            db.execSQL(AllDB.DROP_UNIT_TABLE);
            db.execSQL(AllDB.DROP_EFFICIENCY_TABLE);
            db.execSQL(AllDB.DROP_CAR_TABLE);
            onCreate(db);
        }
    }
    private SQLiteDatabase db;
    private DBHelper dbHelper;
    //constructor
    public AllDB(Context context){
        dbHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
    }
    private void openReadableDB(){
        db = dbHelper.getReadableDatabase();
    }
    private void openWriteableDB(){
        db = dbHelper.getWritableDatabase();
    }
    private void closeDB(){
        if (db != null)
            db.close();
    }
    public ArrayList<Unit> getUnits(){
        this.openReadableDB();
        Cursor cursor = db.query(UNIT_TABLE, null, null, null, null, null, null);
        ArrayList<Unit> units = new ArrayList<Unit>();
        while (cursor.moveToNext()){
            units.add(getUnitFromCursor(cursor));
        }
        if (cursor != null)
            cursor.close();
        this.closeDB();
        return units;
    }
    private static Unit getUnitFromCursor(Cursor cursor){
        if (cursor == null || cursor.getCount() == 0){
            return null;
        } else {
            try {
                Unit unit = new Unit(
                        cursor.getInt(UNIT_ID_COL),
                        cursor.getString(UNIT_NAME_COL));
                return unit;
            } catch (Exception e) {
                return null;
            }
        }
    }
    public ArrayList<Efficiency> getEfficiencies(){
        this.openReadableDB();
        Cursor cursor = db.query(EFFICIENCY_TABLE, null, null, null, null, null, null);
        ArrayList<Efficiency> efficiencies = new ArrayList<Efficiency>();
        while (cursor.moveToNext()){
            efficiencies.add(getEfficiencyFromCursor(cursor));
        }
        if (cursor != null)
            cursor.close();
        this.closeDB();
        return efficiencies;
    }
    private static Efficiency getEfficiencyFromCursor(Cursor cursor){
        if (cursor == null || cursor.getCount() == 0){
            return null;
        } else {
            try {
                Efficiency ef = new Efficiency(
                        cursor.getInt(EFFICIENCY_ID_COL),
                        cursor.getInt(EFFICIENCY_UNITTYPE_COL),
                        cursor.getFloat(EFFICIENCY_FE_COL));
                return ef;
            } catch (Exception e) {
                return null;
            }
        }
    }
    public long insertEfficiency(Efficiency ef){
        ContentValues cv = new ContentValues();
        cv.put(EFFICIENCY_ID, ef.getId());
        cv.put(EFFICIENCY_UNITTYPE, ef.getUnitType());
        cv.put(EFFICIENCY_FE, ef.getFe());
        this.openWriteableDB();
        long rowID = db.insert(EFFICIENCY_TABLE, null, cv);
        this.closeDB();
        return rowID;
    }
    public int deleteAllEfficiency(){
        this.openWriteableDB();
        int rowCount = db.delete(EFFICIENCY_TABLE, null, null);
        this.closeDB();
        return rowCount;
    }
    public ArrayList<Car> getCars(){
        this.openReadableDB();
        Cursor cursor = db.query(CAR_TABLE, null, null, null, null, null, null);
        ArrayList<Car> cars = new ArrayList<Car>();
        while (cursor.moveToNext()){
            cars.add(getCarFromCursor(cursor));
        }
        if (cursor != null)
            cursor.close();
        this.closeDB();
        return cars;
    }
    private static Car getCarFromCursor(Cursor cursor){
        if (cursor == null || cursor.getCount() == 0){
            return null;
        } else {
            try {
                Car car = new Car(
                        cursor.getInt(CAR_ID_COL),
                        cursor.getString(CAR_NAME_COL),
                        cursor.getString(CAR_YEAR_COL),
                        cursor.getString(CAR_ENGINE_COL));
                return car;
            } catch (Exception e) {
                return null;
            }
        }
    }
    public long insertCar(Car car){
        ContentValues cv = new ContentValues();
        cv.put(CAR_NAME, car.getName());
        cv.put(CAR_YEAR, car.getYear());
        cv.put(CAR_ENGINE, car.getEngine());
        this.openWriteableDB();
        long rowID = db.insert(CAR_TABLE, null, cv);
        this.closeDB();
        return rowID;
    }
    public int deleteAllCar(){
        this.openWriteableDB();
        int rowCount = db.delete(CAR_TABLE, null, null);
        this.closeDB();
        return rowCount;
    }
}
