package com.example.mad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mad.databinding.ActivityAdminHomepageBinding

class AdminHomepage : AppCompatActivity() {
    private lateinit var binding: ActivityAdminHomepageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView5.setOnClickListener{
            startActivity(Intent(this,cropreadadmin::class.java))
        }

        binding.imageView8.setOnClickListener{
            startActivity(Intent(this,ArticleAdminMain::class.java))
        }

        binding.imageView6.setOnClickListener{
            startActivity(Intent(this,productreadadmin::class.java))
        }
    }


}