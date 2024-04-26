package com.decoded.imagepoem

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                URI_COL + " TEXT," +
                NAME_COL + " TEXT," +
                PATH_COL + " TEXT," +
                CONTENT_COL + " TEXT" + ")")

        // we are calling sqlite
        // method for executing our query
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    // This method is for adding data in our database
    fun addImage(uri : String, name : String, path : String, content : String ): Int {

        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        values.put(URI_COL, uri)
        values.put(NAME_COL, name)
        values.put(PATH_COL, path)
        values.put(CONTENT_COL, content)

        // here we are creating a
        // writable variable of
        // our database as we want to
        // insert value in our database
        val db = this.writableDatabase

        // all values are inserted into database
        val newRecordId = db.insert(TABLE_NAME, null, values)

        // at last we are
        // closing our database
        db.close()

        return newRecordId.toInt()
    }

    // below method is to get
    // all data from our database
    fun getName(): Cursor? {

        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase

        // below code returns a cursor to
        // read data from the database
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)

    }

    fun getImage(imageId: String): Cursor? {
        val db = this.readableDatabase

        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE id = " + imageId + " LIMIT 1", null)
    }

    fun getImageRecordById(id: Int): Image? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_NAME,  // Table name
            null,                   // Select all columns
            ID_COL + "=?",  // Selection criteria (where id = ?)
            arrayOf(id.toString()),   // Selection arguments (id value as string)
            null,                   // No grouping
            null,                   // No filtering by row groups
            null                    // No sorting
        )
        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_COL))
            val uri = cursor.getString(cursor.getColumnIndexOrThrow(URI_COL))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(NAME_COL))
            val path = cursor.getString(cursor.getColumnIndexOrThrow(PATH_COL))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(CONTENT_COL))
            cursor.close()
            return Image(id, uri, name, path, content)
        } else {
            cursor.close()
            return null
        }
    }

    fun updateImageContent(image: Image, newContent: String): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(CONTENT_COL, newContent)
        val rowUpdated = db.update(
            TABLE_NAME,  // Table name
            contentValues,           // Values to update
            ID_COL + "=?",  // Selection criteria
            arrayOf(image.id.toString())    // Selection arguments (id value)
        )
        db.close()
        return rowUpdated
    }

    companion object{
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "imagepoem"

        // below is the variable for database version
        private val DATABASE_VERSION = 1

        // below is the variable for table name
        val TABLE_NAME = "image"

        // below is the variable for id column
        val ID_COL = "id"

        // below is the variable for image_uri column
        val URI_COL = "uri"

        // below is the variable for image_name column
        val NAME_COL = "name"

        // below is the variable for image_path column
        val PATH_COL = "path"

        // below is the variable for content column
        val CONTENT_COL = "content"
    }
}
