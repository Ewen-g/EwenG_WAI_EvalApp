package com.example.eweng_evalapp.ui.Settings.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eweng_evalapp.R
import com.example.eweng_evalapp.ui.Settings.Item.SettingsItem

class ParametresAdapter(private val deviceList: Array<SettingsItem>, private val onClick: ((selectedDevice: SettingsItem) -> Unit)? = null) : RecyclerView.Adapter<ParametresAdapter.ViewHolder>() {

    // Comment s'affiche ma vue
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //'elements' est un objet de SettingItem...........
        fun showItem(elements: SettingsItem, onClick: ((selectedDevice: SettingsItem) -> Unit)? = null) {
            itemView.findViewById<TextView>(R.id.title).text = elements.name
            itemView.findViewById<ImageView>(R.id.iconName).setImageResource(elements.icon)
            itemView.setOnClickListener{
                elements.onClick()
            }

        }
    }

    // Retourne une « vue » / « layout » pour chaque élément de la liste
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_parametre, parent, false)
        return ViewHolder(
            view
        )
    }

    // Connect la vue ET la données
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.showItem(deviceList[position], onClick)
    }

    override fun getItemCount(): Int {
        return deviceList.size
    }

}
