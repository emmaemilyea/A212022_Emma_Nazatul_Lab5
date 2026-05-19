package com.example.a212022_emma_nazatul_lab5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.a212022_emma_nazatul_lab5.ui.theme.A212022_Emma_Nazatul_Lab5Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            A212022_Emma_Nazatul_Lab5Theme() {
                GrocerlystApp()
            }
        }
    }
}
