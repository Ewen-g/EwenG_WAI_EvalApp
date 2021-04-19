package com.example.eweng_evalapp.ui.Settings

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eweng_evalapp.R
import com.example.eweng_evalapp.databinding.ActivityRVparametresBinding
import com.example.eweng_evalapp.ui.Settings.Adapter.ParametresAdapter
import com.example.eweng_evalapp.ui.Settings.Item.SettingsItem

class RVparametresActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, RVparametresActivity::class.java)
        }
    }

    private lateinit var binding: ActivityRVparametresBinding // <-- Référence à notre ViewBinding

    //redefinition de la navigation par la toolbar hautes
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRVparametresBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Pour modifier la ToolBar avec un bouton retour
        supportActionBar?.apply {
            setTitle("Paramètres")
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }


        //Declaration de chacun des items qui seront present dans le RV des parametre
        val items = arrayOf(

            SettingsItem(
                "Paramètre de WAI",
                R.drawable.ic_settings
            ) {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.parse("package:" + "com.example.eweng_evalapp")
                startActivity(intent)

            },


            SettingsItem(
                "Paramètre localisation",
                R.drawable.ic_settings
            ) {
                val targetIntent = Intent().apply {
                    action = Settings.ACTION_LOCATION_SOURCE_SETTINGS
                }
                startActivity(targetIntent);
            },


            SettingsItem(
                "Aller à l'ESEO",
                R.drawable.ic_location
            ) {
                val gmmIntentUri =
                    Uri.parse("geo:0,0?q=ESEO, Angers, France")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            },


            SettingsItem(
                "Site de l'ESEO",
                R.drawable.ic_web
            ) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://eseo.fr/")
                    )
                );
            },


            SettingsItem(
                "Contact",
                R.drawable.ic_mail
            ) {
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("mailto: ewen.guillou@reseau.eseo.fr")
                intent.putExtra(Intent.EXTRA_SUBJECT, "Demande sur <..sujet..>")
                startActivity(intent)
            }
        )

        //
        binding.rvLayout.layoutManager = LinearLayoutManager(this)
        binding.rvLayout.adapter = ParametresAdapter(items)
    }
}
