package com.example.mad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mad.databinding.ActivityMainUserBinding

class MainUser : AppCompatActivity() {
    private lateinit var binding: ActivityMainUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newsepagebtn.setOnClickListener{
            startActivity(Intent(this,NewsUserRead::class.java))
        }
        binding.articlepagebtn.setOnClickListener{
            startActivity(Intent(this,ArticleUserRead::class.java))
        }
    }
}