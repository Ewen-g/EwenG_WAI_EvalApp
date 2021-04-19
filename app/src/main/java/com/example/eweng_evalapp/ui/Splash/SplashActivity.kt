package com.example.eweng_evalapp.ui.Splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import com.example.eweng_evalapp.R
import com.example.eweng_evalapp.databinding.ActivitySplashBinding
import com.example.eweng_evalapp.ui.Main.MainActivity
import com.example.eweng_evalapp.ui.Splash.SplashActivity
import java.util.*

class SplashActivity : AppCompatActivity() {
//    companion object {
//        fun getStartIntent(context: Context): Intent {
//            return Intent(context, SplashActivity::class.java)
//        }
//    }
    private lateinit var binding: ActivitySplashBinding // <-- Référence à notre ViewBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val worldAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        val backgroundAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_background_animation)
        val textAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        binding.imgWave.startAnimation(backgroundAnim)
        binding.imgWorld.startAnimation(worldAnim)


        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(MainActivity.getStartIntent(this))
            finish()    //pour killer l'activity (evite de faire retour et de tomber sur le splashActivity)
        }, 2500)
    }


}