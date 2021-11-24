package fr.maximeaeva.pocketscoins.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.core.view.View
import fr.maximeaeva.pocketscoins.MainActivity
import fr.maximeaeva.pocketscoins.Movement
import fr.maximeaeva.pocketscoins.R
import kotlinx.android.synthetic.main.item_historic.*

class HistoricAdapter(
    private val context: MainActivity,
    private val movList: List<Movement>,
    private val layoutId: Int
) : RecyclerView.Adapter<HistoricAdapter.ViewHolder>(){

    //Box of controlled components
    class ViewHolder(view: android.view.View) : RecyclerView.ViewHolder(view){
        val stonks = view.findViewById<ImageView>(R.id.item_stonks)
        val value = view.findViewById<TextView>(R.id.item_value)
        val mod = view.findViewById<TextView>(R.id.item_module)
        val moreButton = view.findViewById<ImageView>(R.id.item_more) as ImageView
        val desc = view.findViewById<TextView>(R.id.item_description) as TextView
        var bool = false

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context).inflate(layoutId, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Catch movement info
        val currentMov = movList[position]

        holder.moreButton.setOnClickListener {
            if (holder.bool) {
                holder.desc.layoutParams.height = ViewGroup.INVISIBLE
                holder.desc.requestLayout()
                holder.bool = false
            } else {
                holder.desc.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                holder.desc.requestLayout()
                holder.bool = true
            }
        }
    }

    override fun getItemCount(): Int = movList.size
}