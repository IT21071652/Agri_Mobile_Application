package com.example.mad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mad.Adapter.ProductsAdapter
import com.example.mad.Database.DbHelperProduct
import com.example.mad.Model.ProductModel
import kotlinx.android.synthetic.main.activity_productreadadmin.*

class productreadadmin : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var btnadd: Button

    var Adapter:ProductsAdapter ?= null
    var DbHelp:DbHelperProduct ?=null

    var articleList:List<ProductModel> = ArrayList<ProductModel>()
    var linierlayoutManager: LinearLayoutManager?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productreadadmin)
        recyclerView = findViewById(R.id.recyclerView)
        DbHelp = DbHelperProduct(this)
        val db = DbHelperProduct(this)

        fetchlist()

        var deleteBtn=findViewById<Button>(R.id.deletebtn)

        var addArticlebtn=findViewById<Button>(R.id.addArticle)

        addArticlebtn.setOnClickListener{
            startActivity(Intent(this,AddProduct::class.java))


        }

        deleteBtn.setOnClickListener{
            var id = editNumber.text.toString()
            println(id)

            val iD = id.toInt()//Casting
            val success = db.deleteCrop(iD)

            println(iD)

            if (success == true){
                Toast.makeText(this,"Delete Successfully",Toast.LENGTH_LONG).show()
                startActivity(Intent(this,productreadadmin::class.java))
            }else{
                Toast.makeText(this,"Delete Unsuccessfully",Toast.LENGTH_LONG).show()
            }
        }

        updatebtn.setOnClickListener{
            var id = editNumber.text.toString()
            println(id)
            val intent = Intent(this, UpdateProductsAdmin::class.java)
            intent.putExtra("id", id)//bind the Id value and send update page
            startActivity(intent)
        }

    }
    private fun fetchlist(){

        articleList=DbHelp!!.getAllCrops()
        Adapter= ProductsAdapter(articleList,applicationContext);
        linierlayoutManager= LinearLayoutManager(applicationContext);
        recyclerView.layoutManager = LinearLayoutManager(this);
        recyclerView.adapter=Adapter
        Adapter!!.notifyDataSetChanged()

    }
}