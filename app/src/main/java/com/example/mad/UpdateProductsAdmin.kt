package com.example.mad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mad.Database.DbHelperProduct
import com.example.mad.Model.ProductModel
import com.example.mad.databinding.ActivityUpdateProductsAdminBinding

class UpdateProductsAdmin : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateProductsAdminBinding
    var product: ProductModel = ProductModel();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateProductsAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
//fetch data
        val value = intent.getStringExtra("id")
        val id = value!!.toInt()
        val db = DbHelperProduct(this)

        product = db.getProduct(id)

        binding.productTypeEdit.setText(product.productName)
        binding.productregionEdit.setText(product.productRegion)
        binding.productpriceedit.setText(product.productPrice)


        //update
        binding.productUpdate.setOnClickListener {

            var title = binding.productTypeEdit.text.toString()
            var date = binding.productregionEdit.text.toString()
            var discription = binding.productpriceedit.text.toString()


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