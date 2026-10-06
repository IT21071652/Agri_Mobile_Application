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
import com.example.mad.databinding.ActivityProductreadadminBinding

class productreadadmin : AppCompatActivity() {
    private lateinit var binding: ActivityProductreadadminBinding
    lateinit var recyclerView: RecyclerView
    lateinit var btnadd: Button

    var Adapter:ProductsAdapter ?= null
    var DbHelp:DbHelperProduct ?=null

    var articleList:List<ProductModel> = ArrayList<ProductModel>()
    var linierlayoutManager: LinearLayoutManager?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductreadadminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerView = binding.recyclerView
        DbHelp = DbHelperProduct(this)
        val db = DbHelperProduct(this)

        fetchlist()

        var deleteBtn = binding.deletebtn

        var addArticlebtn = binding.addArticle

        addArticlebtn.setOnClickListener{
            startActivity(Intent(this,AddProduct::class.java))


        }

        deleteBtn.setOnClickListener{
            var id = binding.editNumber.text.toString()
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

        binding.updatebtn.setOnClickListener{
            var id = binding.editNumber.text.toString()
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