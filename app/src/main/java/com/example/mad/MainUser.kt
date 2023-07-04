package com.example.mad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_user.*

class MainUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_user)

        newsepagebtn.setOnClickListener{
            startActivity(Intent(this,NewsUserRead::class.java))
        }
        articlepagebtn.setOnClickListener{
            startActivity(Intent(this,ArticleUserRead::class.java))
        }
    }
}