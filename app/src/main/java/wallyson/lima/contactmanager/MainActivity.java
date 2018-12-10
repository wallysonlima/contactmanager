package wallyson.lima.contactmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import Data.DatabaseHandler;
import Model.Contact;

public class MainActivity extends AppCompatActivity {


    // Test
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler db = new DatabaseHandler(this);

        // Insert contacts
        Log.d("Insert: ", "Insertin...");
        db.addContact(new Contact("Paul", "2995-9186"));
        db.addContact(new Contact("Wallyson", "98156-9942"));
        db.addContact(new Contact("Jessica", "3994-4432"));
        db.addContact(new Contact("Bia Carvalho", "98156-9942"));

        // Read them back
        Log.d("Reading: ", " Reading all contacts...");
        List<Contact> contactList = db.getAllContacts();

        // Get 1 contact
        Contact oneContact = db.getContact(1);
        oneContact.setName("Wally");
        oneContact.setPhoneNumber("0000-0000");
        // Updated contact
        int newContact = db.updateContact(oneContact);

        Log.d("One Contact: ", "Name: " + oneContact.getName() + " Phone: " + oneContact.getPhoneNumber());

        for ( Contact c: contactList) {
            String log = "ID: " + c.getId() + ", Name: " + c.getName() + ", Phone: " + c.getPhoneNumber() + "\n";
            Log.d("Name: ", log);
        }
    }
}
