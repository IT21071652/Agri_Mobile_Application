package com.example.mad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_admin_homepage.*

class AdminHomepage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_homepage)

        imageView5.setOnClickListener{
            startActivity(Intent(this,cropreadadmin::class.java))
        }

        imageView8.setOnClickListener{
            startActivity(Intent(this,ArticleAdminMain::class.java))
        }

        imageView6.setOnClickListener{
            startActivity(Intent(this,productreadadmin::class.java))
        }
    }


}