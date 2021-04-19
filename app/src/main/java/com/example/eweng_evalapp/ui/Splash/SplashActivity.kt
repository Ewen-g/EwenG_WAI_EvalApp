package com.example.eweng_evalapp.ui.Splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.eweng_evalapp.R
import com.example.eweng_evalapp.databinding.ActivitySplashBinding
import com.example.eweng_evalapp.ui.Main.MainActivity


class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding // <-- Référence à notre ViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val worldAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        val backgroundAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_background_animation)

        binding.imgWave.startAnimation(backgroundAnim)
        binding.imgWorld.startAnimation(worldAnim)


        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(MainActivity.getStartIntent(this))
            finish()    //pour killer l'activity (evite de faire retour et de tomber sur le splashActivity)
        }, 2500)
    }


}