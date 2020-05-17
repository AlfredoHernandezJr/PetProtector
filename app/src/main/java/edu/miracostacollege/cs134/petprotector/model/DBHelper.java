package edu.miracostacollege.cs134.petprotector.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    //TASK 1: DEFINE THE DATABASE VERSION, NAME AND TABLE NAME
    public static final String DATABASE_NAME = "PetProtector";
    private static final String DATABASE_TABLE = "Pets";
    private static final int DATABASE_VERSION = 1;


    //TASK 2: DEFINE THE FIELDS (COLUMN NAMES) FOR THE TABLE
    private static final String KEY_FIELD_ID = "_id";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_DETAILS = "details";
    private static final String FIELD_PHONE = "phone";
    private static final String FIELD_IMAGE_URI = "image_uri";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String table = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE + "("
                + KEY_FIELD_ID + " INTEGER PRIMARY KEY, "
                + FIELD_NAME + " TEXT, "
                + FIELD_DETAILS + " TEXT, "
                + FIELD_PHONE + " TEXT, "
                + FIELD_IMAGE_URI + " TEXT" + ")";
        database.execSQL(table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database,
                          int oldVersion,
                          int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(database);
    }

    //********** DATABASE OPERATIONS:  ADD, UPDATE, EDIT, DELETE

    /**
     * Adds a new Pet to the Db
     * @param pet current pet
     */
    public void addPet(Pet pet) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        //ADD KEY-VALUE PAIR INFORMATION FOR THE PET NAME
        values.put(FIELD_NAME, pet.getName());

        //ADD KEY-VALUE PAIR INFORMATION FOR THE PET DETAILS
        values.put(FIELD_DETAILS, pet.getDetails());

        //ADD KEY-VALUE PAIR INFORMATION FOR THE PET PHONE
        values.put(FIELD_PHONE, pet.getPhone());

        //ADD KEY-VALUE PAIR INFORMATION FOR THE PET URI
        // values.put(FIELD_IMAGE_URI, pet.getImageURI());

        // INSERT THE ROW IN THE TABLE
        long id = db.insert(DATABASE_TABLE, null, values);

        // UPDATE THE GAME WITH THE NEWLY ASSIGNED ID FROM THE DATABASE

        // CLOSE THE DATABASE CONNECTION
        db.close();
    }

    /**
     * Returns all of the Pets in the DB
     * @return List of pets
     */
    public List<Pet> getAllPets() {
        List<Pet> petList = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        // A cursor is the results of a database query (what gets returned)
        Cursor cursor = database.query(
                DATABASE_TABLE,
                new String[]{KEY_FIELD_ID, FIELD_NAME, FIELD_DETAILS, FIELD_PHONE, FIELD_IMAGE_URI},
                null,
                null,
                null, null, null, null);

        //COLLECT EACH ROW IN THE TABLE
        if (cursor.moveToFirst()) {
            if(cursor.getString(4) != null)
            {
                do {
                    Pet pet =
                            new Pet(cursor.getLong(0),
                                    cursor.getString(1),
                                    cursor.getString(2),
                                    cursor.getString(3),
                                    Uri.parse(cursor.getString(4)));
                    petList.add(pet);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        database.close();
        return petList;
    }
}