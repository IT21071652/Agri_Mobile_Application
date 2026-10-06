package com.example.mad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mad.Adapter.ArticleAdminAdapter
import com.example.mad.Database.DbHelperArticles
import com.example.mad.Model.ArticlesModal
import com.example.mad.databinding.ActivityArtcleReadAdminBinding

class ArtcleReadAdmin : AppCompatActivity() {

    private lateinit var binding: ActivityArtcleReadAdminBinding
    lateinit var recyclerView: RecyclerView
    lateinit var btnadd: Button

    var Adapter:ArticleAdminAdapter ?= null
    var DbHelp:DbHelperArticles ?=null

    var articleList:List<ArticlesModal> = ArrayList<ArticlesModal>()
    var linierlayoutManager: LinearLayoutManager?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtcleReadAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerView = binding.recyclerView
        DbHelp = DbHelperArticles(this)
        val db = DbHelperArticles(this)

        fetchlist()

        var deleteBtn = binding.deletebtn

        var addArticlebtn = binding.addArticle

        addArticlebtn.setOnClickListener{
            startActivity(Intent(this,AddArticlesAdmin::class.java))


        }

        deleteBtn.setOnClickListener{
            var id = binding.editNumber.text.toString()
            println(id)

            val iD = id.toInt()//Casting
            val success = db.deleteArticle(iD)

            println(iD)

            if (success == true){
                Toast.makeText(this,"Delete Successfully",Toast.LENGTH_LONG).show()
                startActivity(Intent(this,ArtcleReadAdmin::class.java))
            }else{
                Toast.makeText(this,"Delete Unsuccessfully",Toast.LENGTH_LONG).show()
            }
        }

        binding.updatebtn.setOnClickListener{
            var id = binding.editNumber.text.toString()
            println(id)
            val intent = Intent(this, UpdateArticlesAdmin::class.java)
            intent.putExtra("id", id)//bind the Id value and send update page
            startActivity(intent)
        }



    }
    private fun fetchlist(){

        articleList=DbHelp!!.getAllArtcles()
        Adapter= ArticleAdminAdapter(articleList,applicationContext);
        linierlayoutManager= LinearLayoutManager(applicationContext);
        recyclerView.layoutManager = LinearLayoutManager(this);
        recyclerView.adapter=Adapter
        Adapter!!.notifyDataSetChanged()

    }
}