package com.example.mad.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mad.Model.ProductModel

class DbHelperProduct(context: Context): SQLiteOpenHelper(context, DB_NAME,null, DB_VERSION) {

    companion object {

        private val DB_NAME = "ProductManagement"
        private val DB_VERSION = 1


        //Crops table
        private val TABLE_NAME6 = "product"
        private val PRODUCT_ID = "productId"
        private val PRODUCT_NAME = "productName"
        private val PRODUCT_REGION = "productRegion"
        private val PRODUCT_PRICE = "marketPrice"
        private val PRODUCT_IMAGE = "productImage"
    }

    override fun onCreate(p0: SQLiteDatabase?) {

        val CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME6 ($PRODUCT_ID INTEGER PRIMARY KEY,$PRODUCT_NAME TEXT,$PRODUCT_REGION TEXT,$PRODUCT_PRICE TEXT,$PRODUCT_IMAGE TEXT)"
        p0?.execSQL(CREATE_TABLE);
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTs $TABLE_NAME6"
        p0?.execSQL(DROP_TABLE)
        onCreate(p0)
    }

    //crops Functions

    fun getAllCrops(): List<ProductModel> {
        val ProductsList = ArrayList<ProductModel>()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME6"
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {

                    val products = ProductModel()

                    products.id = cursor.getInt(0)
                    products.productName = cursor.getString(1)
                    products.productRegion = cursor.getString(2)
                    products.productPrice = cursor.getString(3)
                    products.productImage = cursor.getString(4)

                    ProductsList.add(products)

                } while (cursor.moveToNext())
            }
        }

        cursor.close()
        return ProductsList


    }

    fun addProduct(tip: ProductModel): Boolean {

        val db = this.writableDatabase
        val values = ContentValues();
        values.put(PRODUCT_NAME, tip.productName)
        values.put(PRODUCT_REGION, tip.productRegion)
        values.put(PRODUCT_PRICE, tip.productPrice)
        values.put(PRODUCT_IMAGE, tip.productImage)

        val success = db.insert(TABLE_NAME6, null, values)
        db.close()

        return (Integer.parseInt("$success") != -1)
    }


    fun getProduct(_id: Int): ProductModel {

        val products = ProductModel()
        val db = writableDatabase
        val selectQueary = "SELECT * FROM $TABLE_NAME6 WHERE $PRODUCT_ID=$_id"
        val cursor: Cursor = db.rawQuery(selectQueary, null)


        cursor?.moveToFirst()

        products.id = cursor.getInt(0)
        products.productName = cursor.getString(1)
        products.productRegion = cursor.getString(2)
        products.productPrice = cursor.getString(3)


        cursor.close()

        return products;

    }

    fun deleteCrop(_id: Int): Boolean {

        val db = this.writableDatabase

        val success = db.delete(TABLE_NAME6, PRODUCT_ID + "=?", arrayOf(_id.toString())).toLong()

        db.close()
        return Integer.parseInt("$success") != -1;

    }

    fun updateProduct(tip: ProductModel): Boolean {

        val db = this.writableDatabase
        val values = ContentValues()
        values.put(PRODUCT_NAME, tip.productName)
        values.put(PRODUCT_REGION, tip.productRegion)
        values.put(PRODUCT_PRICE, tip.productPrice)

        val success =
            db.update(TABLE_NAME6, values, PRODUCT_ID + "=?", arrayOf(tip.id.toString())).toLong()

        db.close()
        return Integer.parseInt("$success") != -1
    }
}