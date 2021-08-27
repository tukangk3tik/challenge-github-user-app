package com.dicoding.picodiploma.githubuserapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log.d
import com.dicoding.picodiploma.githubuserapp.db.FavoritDatabase
import com.dicoding.picodiploma.githubuserapp.ui.home.MainActivity
import com.dicoding.picodiploma.githubuserapp.utils.alarm.AlarmReceiver
import com.dicoding.picodiploma.githubuserapp.utils.alarm.AlarmStateManager

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        FavoritDatabase.getInstance(applicationContext)

        // Move to mainActivity after splash
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)


    }
}
