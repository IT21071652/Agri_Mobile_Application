package com.example.mad.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mad.Model.CropModel

class DbHelperCrop(context: Context): SQLiteOpenHelper(context, DB_NAME,null, DB_VERSION) {

    companion object {

        private val DB_NAME = "CropManagement"
        private val DB_VERSION = 1


        //Crops table
        private val TABLE_NAME6 = "crop"
        private val CROP_ID = "cropId"
        private val CROP_NAME = "cropName"
        private val CROP_REGION = "cropRegion"
        private val CROP_PRICE = "marketPrice"
        private val CROP_IMAGE = "cropImage"
    }

    override fun onCreate(p0: SQLiteDatabase?) {

        val CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME6 ($CROP_ID INTEGER PRIMARY KEY,$CROP_NAME TEXT,$CROP_REGION TEXT,$CROP_PRICE TEXT,$CROP_IMAGE TEXT)"
        p0?.execSQL(CREATE_TABLE);
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTs $TABLE_NAME6"
        p0?.execSQL(DROP_TABLE)
        onCreate(p0)
    }

    //crops Functions

    fun getAllCrops(): List<CropModel> {
        val CropsList = ArrayList<CropModel>()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME6"
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {

                    val crops = CropModel()

                    crops.id = cursor.getInt(0)
                    crops.cropName = cursor.getString(1)
                    crops.cropRegion = cursor.getString(2)
                    crops.cropPrice = cursor.getString(3)
                    crops.cropImage = cursor.getString(4)

                    CropsList.add(crops)

                } while (cursor.moveToNext())
            }
        }

        cursor.close()
        return CropsList


    }

    fun addCrop(tip: CropModel): Boolean {

        val db = this.writableDatabase
        val values = ContentValues();
        values.put(CROP_NAME, tip.cropName)
        values.put(CROP_REGION, tip.cropRegion)
        values.put(CROP_PRICE, tip.cropPrice)
        values.put(CROP_IMAGE, tip.cropImage)

        val success = db.insert(TABLE_NAME6, null, values)
        db.close()

        return (Integer.parseInt("$success") != -1)
    }


    fun getCrop(_id: Int): CropModel {

        val crops = CropModel()
        val db = writableDatabase
        val selectQueary = "SELECT * FROM $TABLE_NAME6 WHERE $CROP_ID=$_id"
        val cursor: Cursor = db.rawQuery(selectQueary, null)


        cursor?.moveToFirst()

        crops.id = cursor.getInt(0)
        crops.cropName = cursor.getString(1)
        crops.cropRegion = cursor.getString(2)
        crops.cropPrice = cursor.getString(3)


        cursor.close()

        return crops;

    }

    fun deleteCrop(_id: Int): Boolean {

        val db = this.writableDatabase

        val success = db.delete(TABLE_NAME6, CROP_ID + "=?", arrayOf(_id.toString())).toLong()

        db.close()
        return Integer.parseInt("$success") != -1;

    }

    fun updateCrop(tip: CropModel): Boolean {

        val db = this.writableDatabase
        val values = ContentValues()
        values.put(CROP_NAME, tip.cropName)
        values.put(CROP_REGION, tip.cropRegion)
        values.put(CROP_PRICE, tip.cropPrice)

        val success =
            db.update(TABLE_NAME6, values, CROP_ID + "=?", arrayOf(tip.id.toString())).toLong()

        db.close()
        return Integer.parseInt("$success") != -1
    }
}