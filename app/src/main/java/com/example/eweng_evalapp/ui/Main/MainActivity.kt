package com.example.eweng_evalapp.ui.Main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eweng_evalapp.ui.Settings.RVparametresActivity
import com.example.eweng_evalapp.databinding.ActivityMainBinding
import com.example.eweng_evalapp.ui.Historique.RVhistoriqueActivity
import com.example.eweng_evalapp.ui.Location.LocationActivity


class MainActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    private lateinit var binding: ActivityMainBinding // <-- Référence à notre ViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btLocalisation.setOnClickListener {
            startActivity(LocationActivity.getStartIntent(this))
        }

        binding.btHistorique.setOnClickListener {
            startActivity(RVhistoriqueActivity.getStartIntent(this))
        }

        binding.imgSetting.setOnClickListener {
            startActivity(RVparametresActivity.getStartIntent(this)
            )
        }


    }
}