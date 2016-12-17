package com.meetup.centennial.centennialmeetup;

/**
 * Created by Satt-3 on 12/14/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
    static final String KEY_ID = "centennialId";
    static final String KEY_FNAME = "firstName";
    static final String KEY_LNAME = "lastName";
    //static final String KEY_ADDRESS="Address";
    static final String KEY_EMAIL="emailid";
    static final String KEY_DOB="dateofbirth";
    static final String KEY_HOBBY="hobby";
    static final String KEY_PASS="password";
    static final String KEY_PROGRAM="program";
    //static final String KEY_COO = "CountryOfOrigin";
    static final String KEY_DEPT="department";
    static final String KEY_CAMPUS = "campus";
    static final String KEY_POS = "position";
    static final String TAG = "DBAdapter";

    static final String DATABASE_TABLE = "User";
    static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "Prototype.db";
    //static final String DATABASE_TABLE = "contacts";


    //table names
    private static final String tables[]={"User"};
    //corresponding create table queries
//    private static final String tableCreatorString[] ={"CREATE TABLE Users (UserId INTEGER  , FirstName TEXT, LastName TEXT,Address TEXT,Email TEXT," +
    //          "DOB TEXT,Hobby TEXT,Hobby TEXT,CountryOfOrigin TEXT,Campus TEXT,Usertype TEXT)"};

    private static final String tableCreatorString[] ={"CREATE TABLE User (centennialid INTEGER PRIMARY KEY ,firstname TEXT," +
            " lastname TEXT, emailid TEXT, dateofbirth TEXT,hobby TEXT,password TEXT,program TEXT,department TEXT,campus TEXT,position TEXT)"};

    final Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);

    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        ///-********************CREATING TABLE---***********//////////////////////
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            for (int i=0;i<tables.length;i++)
                db.execSQL("DROP TABLE IF EXISTS " + tables[i]);
            for (int i=0;i<tables.length;i++)
                db.execSQL(tableCreatorString[i]);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }
    }

    //---opens the database---
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }

    //---insert a user into the database--- //PREVIOUS CODE
    /*public long insertUser(String uid, String fname,String lname,String address,String email,String dob,String hobby,String program,String coo,String campus,String utype)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_USERID, uid);
        initialValues.put(KEY_FNAME, fname);
        initialValues.put(KEY_LNAME,lname);
        initialValues.put(KEY_ADDRESS,address);
        initialValues.put(KEY_EMAIL,email);
        initialValues.put(KEY_DOB ,dob);
        initialValues.put(KEY_HOBBY,hobby);
        initialValues.put(KEY_PROGRAM,program);
        initialValues.put(KEY_COO,coo);
        initialValues.put(KEY_CAMPUS,campus);
        initialValues.put(KEY_UTYPE,utype);
        return db.insert("Users", null, initialValues);
    }*/

    //NEW DBADAPTER FROM FINAL COMMITEED CODE

    public boolean insertUser(Integer id,String fname, String lname,String email,String dob,String hobby,String pass,String program, String dept, String campus,String pos)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ID,id);
        initialValues.put(KEY_FNAME,fname);
        initialValues.put(KEY_LNAME,lname);
        initialValues.put(KEY_EMAIL,email);
        initialValues.put(KEY_DOB,dob);
        initialValues.put(KEY_HOBBY,hobby);
        initialValues.put(KEY_PASS,pass);
        initialValues.put(KEY_PROGRAM,program);
        initialValues.put(KEY_DEPT,dept);
        initialValues.put(KEY_CAMPUS,campus);
        initialValues.put(KEY_POS,pos);
        db.insert("User",null,initialValues);
        return true;

    }

    //---retrieves all the contacts---
   /* public Cursor getAllUsers()
    {
        return db.query("Patient", new String[] {KEY_PATIENTID, KEY_FNAME,KEY_LNAME,KEY_DEPT,KEY_DOCTORID,KEY_ROOM
                }, null, null, null, null, null);
    }*/

    //---retrieves all the contacts---



    //GET BY ID NOT NEEDED BY SEARCH MODULE
    /* public Cursor getById(Integer uid)
    {
        return db.query("Users", new String[] {KEY_ID,KEY_FNAME,KEY_LNAME,KEY_EMAIL,KEY_DOB,KEY_HOBBY,KEY_PROGRAM,KEY_CAMPUS,
        }, KEY_ID + "=" + uid, null, null, null, null);
    }*/



    public Cursor searchfunction(String fname,String arg2,String columnname)
    {    String selectQuery=null;

        if(arg2==null || columnname=="default" )
        {
            selectQuery    =  "SELECT  rowid as " +
                    "_id"+ "," +
                    "centennialid" + "," +
                    "firstname" + "," +
                    "lastname" + "," +
                    "emailid" +","+
                    "dateofbirth" +","+
                    "hobby" +","+
                    "password" +","+
                    "program" +","+
                    "department" +","+
                    "campus" +","+
                    "position "+
                    " FROM " + "User"
                    + " Where firstname = '"+fname+"'";
        }
        else {
            selectQuery = "SELECT  rowid as " +
                    "_id"+ "," +
                    " centennialid" + "," +
                    "firstname" + "," +
                    "lastname" + "," +
                    "emailid" +","+
                    "dateofbirth" +","+
                    "hobby" +","+
                    "password" +","+
                    "program" +","+
                    "department" +","+
                    "campus" +","+
                    "position "+
                    " FROM " + "User"
                    + " Where firstname = '" + fname + "'" + "AND  " + columnname + "=" + "'" + arg2 + "'";
        }
        SQLiteDatabase db=DBHelper.getWritableDatabase();
        Cursor cursor= db.rawQuery(selectQuery,null);
        return  cursor;
    }








    /*************************NOT NEEDED CODE*/////////////////////////////////





   /* public Cursor getByName(String fname)
    {
        String selectQuery =  "SELECT  rowid as " +
                "_id"+ "," +
                "UserId" + "," +
                "FirstName" + "," +
                "LastName" + "," +
                "Address" +","+
                "Email" +","+
                "DOB" +","+
                "Hobby" +","+
                "CountryOfOrigin" +","+
                "Campus" +","+
                "Hobby" +","+
                "Usertype "+
                " FROM " + "Users"
                + " Where FirstName = '"+fname+"'";
        SQLiteDatabase db=DBHelper.getWritableDatabase();
        Cursor cursor= db.rawQuery(selectQuery,null);
        return  cursor;
  //return db.query("Users", new String[] {KEY_USERID,KEY_FNAME,KEY_LNAME,KEY_ADDRESS,KEY_EMAIL,KEY_DOB,KEY_HOBBY,KEY_PROGRAM,KEY_COO,KEY_CAMPUS,KEY_UTYPE
    //    }, KEY_FNAME + "='" + fname+"'", null, null, null, null);
    }*/




  /*  public Cursor getByNameandOtherArg(String fname,String arg2,String columnname)
    {
        String selectQuery =  "SELECT  rowid as " +
                "_id"+ "," +
                "UserId" + "," +
                "FirstName" + "," +
                "LastName" + "," +
                "Address" +","+
                "Email" +","+
                "DOB" +","+
                "Hobby" +","+
                "CountryOfOrigin" +","+
                "Campus" +","+
                "Hobby" +","+
                "Usertype "+
                " FROM " + "Users"
                + " Where FirstName = '"+fname+"'"+ "AND "+columnname+"="+"'"+arg2 +"'";
        SQLiteDatabase db=DBHelper.getWritableDatabase();
        Cursor cursor= db.rawQuery(selectQuery,null);
        return  cursor;

    }
*/






    /*********************************NOT NEEDED CODE************************/////////////



    /* public Cursor getByNameWhileTyping(String fname)
    {

        return db.query("Users", new String[] {KEY_USERID,KEY_FNAME,KEY_LNAME,KEY_ADDRESS,KEY_EMAIL,KEY_DOB,KEY_HOBBY,KEY_PROGRAM,KEY_COO,KEY_CAMPUS,KEY_UTYPE
        }, KEY_FNAME + "=" + fname+"%", null, null, null, null);
    }*/


    //---retrieves a particular contact---
   /* public Cursor getContact(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                KEY_NAME, KEY_EMAIL}, KEY_ROWID + "=" + rowId, null,
                null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }*/
    //---updates a contact---
   /* public boolean updateContact(long rowId, String name, String email)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_NAME, name);
        args.put(KEY_EMAIL, email);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }*/

}

