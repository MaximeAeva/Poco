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
        var stonks = view.findViewById<ImageView>(R.id.item_stonks)
        var value = view.findViewById<TextView>(R.id.item_value)
        var mod = view.findViewById<TextView>(R.id.item_module)
        var moreButton = view.findViewById<ImageView>(R.id.item_more) as ImageView
        var desc = view.findViewById<TextView>(R.id.item_description) as TextView
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
        val choices = context.resources.getStringArray(R.array.home_page_description_spinner_input)

        holder.value.setText(currentMov.value.toString())
        holder.mod.setText(choices[currentMov.module].toString())
        holder.desc.setText(currentMov.description)
        if(currentMov.add){
            holder.stonks.setImageResource(R.drawable.ic_climb_foreground)
        }else if(currentMov.value<50){
            holder.stonks.setImageResource(R.drawable.ic_decrease_slow_foreground)
        }else if(currentMov.value<500){
            holder.stonks.setImageResource(R.drawable.ic_decrease_med_foreground)
        }else{
            holder.stonks.setImageResource(R.drawable.ic_decrease_fast_foreground)
        }
        holder.stonks

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