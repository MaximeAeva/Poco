package fr.maximeaeva.pocketscoins

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper


class MovementRepository(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "PoCoDatabaseTest"
        private val TABLE_MOV = "tableMovements"
        private val KEY_ID = "id"
        private val KEY_DESCRIPTION = "description"
        private val KEY_MODULE = "module"
        private val KEY_ADDON = "addon"
        private val KEY_VALUE = "value"
        private val KEY_DATE = "date"
        private val KEY_DEL = "del"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        //creating table with fields
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_MOV + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_DESCRIPTION + " VARCHAR(255),"
                + KEY_MODULE + " INTEGER,"
                + KEY_ADDON + " BOOLEAN,"
                + KEY_VALUE + " REAL,"
                + KEY_DATE + " DATE DEFAULT (datetime('now','localtime')), "
                + KEY_DEL + " BOOLEAN DEFAULT 0" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_MOV)
        onCreate(db)
    }


    //method to insert data
    fun addMovement(mov: Movement):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_DESCRIPTION, mov.description)
        contentValues.put(KEY_MODULE, mov.module)
        contentValues.put(KEY_ADDON, mov.add)
        contentValues.put(KEY_VALUE, mov.value)
        // Inserting Row
        val success = db.insert(TABLE_MOV, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    //method to read data
    fun viewMovement():List<Movement>{
        val movList:ArrayList<Movement> = ArrayList<Movement>()
        val selectQuery = "SELECT * FROM $TABLE_MOV"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var description: String
        var module: Int
        var add: Boolean
        var value: Double
        var date: String
        var del: Boolean

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                description = cursor.getString(cursor.getColumnIndex("description"))
                module = cursor.getInt(cursor.getColumnIndex("module"))
                add = cursor.getInt(cursor.getColumnIndex("addon"))>0
                value = cursor.getDouble(cursor.getColumnIndex("value"))
                date = cursor.getString(cursor.getColumnIndex("date"))
                del = cursor.getInt(cursor.getColumnIndex("del"))>0
                val mov= Movement( id,
                    description,
                    module,
                    add,
                    value,
                    date,
                    del)
                movList.add(mov)
            } while (cursor.moveToNext())
        }
        return movList
    }
    //method to update data
    fun updateMovement(mov: Movement):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, mov.id)
        contentValues.put(KEY_DESCRIPTION, mov.description)
        contentValues.put(KEY_MODULE, mov.module)
        contentValues.put(KEY_ADDON, mov.add)
        contentValues.put(KEY_VALUE, mov.value)
        contentValues.put(KEY_DATE, mov.date)
        contentValues.put(KEY_DEL, mov.del)

        // Updating Row
        val success = db.update(TABLE_MOV, contentValues,"id="+mov.id,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    //method to delete data
    private fun deleteMovement(id: Int):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, id)
        // Deleting Row
        val success = db.delete(TABLE_MOV, "id=$id",null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    //Delete each Movement with status pending
    fun cleanMovement(): Boolean{
        val movList:ArrayList<Movement> = ArrayList<Movement>()
        val selectQuery = "SELECT $KEY_ID FROM $TABLE_MOV WHERE $KEY_DEL"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return false
        }
        var id: Int
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                deleteMovement(id)
            } while (cursor.moveToNext())
        }
        return true
    }

    //method to get the sum
    fun bilan():Double{
        val valQuery = "SELECT " +
                "(SELECT SUM($KEY_VALUE) FROM $TABLE_MOV WHERE $KEY_ADDON AND NOT($KEY_DEL)) - " +
                "(SELECT SUM($KEY_VALUE) FROM $TABLE_MOV WHERE NOT($KEY_ADDON) AND NOT($KEY_DEL)) as res"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        var res = 0.0
        try{
            cursor = db.rawQuery(valQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(valQuery)
            return 0.0
        }

        if (cursor.moveToFirst()) {
            res = cursor.getDouble(cursor.getColumnIndex("res"))
        }
        db.close()
        return res
    }
}