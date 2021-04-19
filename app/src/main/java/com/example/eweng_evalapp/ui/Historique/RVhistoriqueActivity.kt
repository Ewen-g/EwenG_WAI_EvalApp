package com.example.eweng_evalapp.ui.Historique
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ewen1.ui.Settings.Adapter.HistoriqueAdapter
import com.example.ewen1.ui.Settings.Item.HistoriqueItem
import com.example.eweng_evalapp.databinding.ActivityRVhistoriqueBinding
import com.example.eweng_evalapp.ui.LocalPreferences
import com.example.eweng_evalapp.ui.Main.MainActivity
import com.google.gson.Gson


class RVhistoriqueActivity : AppCompatActivity() {

        companion object {
            fun getStartIntent(context: Context): Intent {
                return Intent(context, RVhistoriqueActivity::class.java)
            }
        }

        private lateinit var binding: ActivityRVhistoriqueBinding // <-- Référence à notre ViewBinding

        //redefinition de la navigation par la toolbar hautes
        override fun onSupportNavigateUp(): Boolean {
            finish()
            return true
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityRVhistoriqueBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.btEffacer

            //Pour modifier la ToolBar avec un bouton retour
            supportActionBar?.apply {
                setTitle("Historique")
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
            }


            var allHistorique = LocalPreferences.getInstance(this).getHistory()
            var listeHistorique = ArrayList<HistoriqueItem>()
            if(allHistorique!=null){
                for(element in allHistorique){
                    val monItem= Gson().fromJson(element,HistoriqueItem::class.java)
                    listeHistorique.add(monItem)
                }
            }
            //Si on clique sur le bouton effacer on efface tous les elements presents dans le LocalPreferences
            binding.btEffacer.setOnClickListener {
                LocalPreferences.getInstance(this).clear()
                this.recreate();
            }

            //Empeche de réouvrire l'activité historique si elle est vide
            if(LocalPreferences.getInstance(this).getHistory().isNullOrEmpty()){
                Toast.makeText(this, "L'historique est vide", Toast.LENGTH_SHORT).show()
                overridePendingTransition(0, 0);
                startActivity(MainActivity.getStartIntent(this))
                overridePendingTransition(0, 0);

            }

            binding.rvLayout.layoutManager = LinearLayoutManager(this)
            binding.rvLayout.adapter = HistoriqueAdapter(listeHistorique)
        }
    }
