package com.deepakkumarinc.chatgpt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.deepakkumarinc.chatgpt.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.chatWithbot.setOnClickListener{
            startActivity(Intent(this,ChatActivity::class.java))
        }
        binding.generateImage.setOnClickListener{
            startActivity(Intent(this,ImageGenerateActivity::class.java))
        }
    }
}