package Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import Model.Contact;
import Utils.Util;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL -- Structure Query Language
        String CREATE_CONTACT_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "(" +
                Util.KEY_ID + " INTEGER PRIMARY KEY," + Util.KEY_NAME + " TEXT," +
                Util.KEY_PHONE_NUMBER + " TEXT" + ")";

        db.execSQL(CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Util.TABLE_NAME);

        // Create table again
        onCreate(db);
    }

    // Add Contact
    public void addContact(Contact contact) {
        SQLiteDatabase db =  this.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(Util.KEY_NAME, contact.getName());
        value.put(Util.KEY_PHONE_NUMBER, contact.getPhoneNumber());

        // Insert to row
        db.insert(Util.TABLE_NAME, null, value);
        db.close();
    }

    // Get Contact
    public Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{ Util.KEY_ID, Util.KEY_NAME,
                Util.KEY_PHONE_NUMBER}, Util.KEY_ID + "=?", new String[] {String.valueOf(id)}, null, null, null, null);

        if ( cursor != null ) {
            cursor.moveToFirst();

            Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getString(2));

            return contact;
        }

        return null;
    }

    // Get All Contacts
    public List<Contact> getAllContacts() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Contact> contactList = new ArrayList<>();

        // Select all contacts
        String selectAll = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        // Loop through our contacts
        if ( cursor.moveToFirst() ) {
            do {
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));

                // add contact object to our contact list
                contactList.add(contact);
            } while(cursor.moveToNext());
        }

        return contactList;
    }

    // UpdateContact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME, contact.getName());
        values.put(Util.KEY_PHONE_NUMBER, contact.getPhoneNumber());

        // update row
        return db.update(Util.TABLE_NAME, values, Util.KEY_ID + "=?",
                new String[] {String.valueOf(contact.getId())});
    }
}
