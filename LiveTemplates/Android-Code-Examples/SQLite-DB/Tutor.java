///////////////////////////////////////////////////////////////////////////////////////////////////////////
1. ---------------------------------------------------------------- create Contact class-----------------------

public class Contact {
    int _id;
    String _name;
    String _phone_number;
     
    public Contact(){ } // default constructor
    
    public Contact(int id, String name, String _phone_number){
        this._id = id;
        this._name = name;
        this._phone_number = _phone_number;
    }
     
    public Contact(String name, String _phone_number){
        this._name = name;
        this._phone_number = _phone_number;
    }
    
    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id = id;
    }
     
    public String getName(){
        return this._name;
    }
     
    public void setName(String name){
        this._name = name;
    }
     
    public String getPhoneNumber(){
        return this._phone_number;
    }
     
    public void setPhoneNumber(String phone_number){
        this._phone_number = phone_number;
    }
}







////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
2. -----------------------write our own class to handle all database (Create, Read, Update and Delete)----------------------

// create DatabaseHandler.java class, you need to override two methods: 
// onCreate(): These is where we need to write create table statements. This is called when database is created. 
// onUpgrage(): This method is called when database is upgraded like modifying the table structure, adding constraints to database etc.


public class DatabaseHandler extends SQLiteOpenHelper {
 
    private static final int DATABASE_VERSION = 1;
 

    private static final String DATABASE_NAME = "contactsManager";
 
    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
 
        // Create tables again
        onCreate(db);
    }





/////////////////////////////////////////////////////////////////////////////////////////////////////////////
3. ------------------- /*Now we need to write methods for handling all database read and write operations. 
Here we are implementing following methods for our contacts table. */


// Adding new contact
public void addContact(Contact contact) {}
 
// Getting single contact
public Contact getContact(int id) {}
 
// Getting All Contacts
public List<Contact> getAllContacts() {}
 
// Getting contacts Count
public int getContactsCount() {}
// Updating single contact
public int updateContact(Contact contact) {}
 
// Deleting single contact
public void deleteContact(Contact contact) {}









    // Adding new contact
    public void addContact(Contact contact) {
    SQLiteDatabase db = this.getWritableDatabase();
 
    ContentValues values = new ContentValues();
    values.put(KEY_NAME, contact.getName()); // Contact Name
    values.put(KEY_PH_NO, contact.getPhoneNumber()); // Contact Phone Number
 
    // Inserting Row
    db.insert(TABLE_CONTACTS, null, values);
    db.close(); // Closing database connection
}









// will read single contact row. It accepts id as parameter and will return the matched row from the database.
public Contact getContact(int id) {
    SQLiteDatabase db = this.getReadableDatabase();
 
    Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
            KEY_NAME, KEY_PH_NO }, KEY_ID + "=?",
            new String[] { String.valueOf(id) }, null, null, null, null);
    if (cursor != null)
        cursor.moveToFirst();
 
    Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
            cursor.getString(1), cursor.getString(2));
    // return contact
    return contact;
}








// return all contacts from database in array list format of Contact class type. You need to write a for loop to go through each contact.

public List<Contact> getAllContacts() {
    List<Contact> contactList = new ArrayList<Contact>();
    // Select All Query
    String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
 
    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);
 
    // looping through all rows and adding to list
    if (cursor.moveToFirst()) {
        do {
            Contact contact = new Contact();
            contact.setID(Integer.parseInt(cursor.getString(0)));
            contact.setName(cursor.getString(1));
            contact.setPhoneNumber(cursor.getString(2));
            // Adding contact to list
            contactList.add(contact);
        } while (cursor.moveToNext());
    }
 
    // return contact list
    return contactList;
}








// return total number of contacts in SQLite database.
public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }








// update single contact in database. This method accepts Contact class object as parameter.
public int updateContact(Contact contact) {
    SQLiteDatabase db = this.getWritableDatabase();
 
    ContentValues values = new ContentValues();
    values.put(KEY_NAME, contact.getName());
    values.put(KEY_PH_NO, contact.getPhoneNumber());
 
    // updating row
    return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
            new String[] { String.valueOf(contact.getID()) });
}













// delete single contact from database.
public void deleteContact(Contact contact) {
    SQLiteDatabase db = this.getWritableDatabase();
    db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
            new String[] { String.valueOf(contact.getID()) });
    db.close();
}












///////////////////////////////////////////////////////////////////////////////////////////////////////
4. ------------------------------------------------------------------------------------ USE

public class AndroidSQLiteTutorialActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
         
        DatabaseHandler db = new DatabaseHandler(this);
         
      
        // Inserting Contacts
        db.addContact(new Contact("abc", "243255"));        
        db.addContact(new Contact("def", "2325255"));
        db.addContact(new Contact("ghi", "4564758"));
        db.addContact(new Contact("xyz", "23453678"));
         
        // Reading all contacts
        List<Contact> contacts = db.getAllContacts();       
         
        for (Contact cn : contacts) {
            String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber();
                // Writing Contacts to log
        Log.d("Name: ", log);
    }
    }
}
