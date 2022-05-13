package com.example.to_dolist

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        val sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", "empty")
        when(token){
            "empty" -> { // 로그인이 되어 있지 않은 경우
                startActivity(Intent(this, ToDoLoginActivity::class.java))
            }
            else -> { //로그인이 되어있는 경우
                startActivity(Intent(this, ToDoActivity::class.java))
            }
        }
    }
}