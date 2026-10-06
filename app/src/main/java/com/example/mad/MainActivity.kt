package com.example.mad

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mad.ui.AgriApp
import com.example.mad.ui.AgriTheme

class MainActivity : AppCompatActivity() {

    private val dashboardViewModel: DashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AgriTheme {
                AgriApp(viewModel = dashboardViewModel)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        dashboardViewModel.refresh()
    }
}
