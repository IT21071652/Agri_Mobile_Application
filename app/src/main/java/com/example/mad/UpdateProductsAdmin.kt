package com.example.mad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mad.Database.DbHelperProduct
import com.example.mad.Model.ProductModel
import kotlinx.android.synthetic.main.activity_update_products_admin.*

class UpdateProductsAdmin : AppCompatActivity() {

    var product: ProductModel = ProductModel();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_products_admin)
//fetch data
        val value = intent.getStringExtra("id")
        val id = value!!.toInt()
        val db = DbHelperProduct(this)

        product = db.getProduct(id)

        productTypeEdit.setText(product.productName)
        productregionEdit.setText(product.productRegion)
        productpriceedit.setText(product.productPrice)


        //update
        productUpdate.setOnClickListener {

            var title = productTypeEdit.text.toString()
            var date = productregionEdit.text.toString()
            var discription = productpriceedit.text.toString()


            product = ProductModel(id, title, date, discription)

            var success = db.updateProduct(product)

            if (success == true) {
                Toast.makeText(this, "Update Succesfully", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, productreadadmin::class.java))
            } else {
                Toast.makeText(this, "Update Unsuccesfully", Toast.LENGTH_LONG).show()
            }

        }

    }
}