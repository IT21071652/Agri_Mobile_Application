package com.example.mad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mad.databinding.ActivityArticleAdminMainBinding

class ArticleAdminMain : AppCompatActivity() {
    private lateinit var binding: ActivityArticleAdminMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleAdminMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.articlepagebtn.setOnClickListener{
            startActivity(Intent(this,ArtcleReadAdmin::class.java))
        }

       binding.newsepagebtn.setOnClickListener{
            startActivity(Intent(this,NewsReadAdmin::class.java))
        }
    }
}