package com.example.ewen1.ui.Settings.Adapter

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.ewen1.ui.Settings.Item.HistoriqueItem
import com.example.eweng_evalapp.R

class HistoriqueAdapter(private val deviceList: ArrayList<HistoriqueItem>) : RecyclerView.Adapter<HistoriqueAdapter.ViewHolder>() {

    // Comment s'affiche ma vue
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //'elements' est un objet de SettingItem...........
        fun showItem(elements: HistoriqueItem, onClick: ((selectedDevice: HistoriqueItem) -> Unit)? = null) {
            val gmmIntentUri =
                Uri.parse("geo:0,0?q=ESEO, Angers, France")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            itemView.findViewById<TextView>(R.id.adresse).text = elements.address
            itemView.findViewById<TextView>(R.id.coordonée).text = "Coordonées :" + elements.coords

            }
    }

    // Retourne une « vue » / « layout » pour chaque élément de la liste
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_historique, parent, false)
        return ViewHolder(
            view
        )
    }


    override fun getItemCount(): Int {
        return deviceList.size
    }
    // Connect la vue ET la données
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.showItem(deviceList[position])
        if(position %2 == 1)
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#606c38"));
        }
        else
        {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

}
