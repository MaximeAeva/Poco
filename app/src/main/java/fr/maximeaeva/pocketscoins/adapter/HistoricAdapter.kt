package fr.maximeaeva.pocketscoins.adapter

import HistoPopup
import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import fr.maximeaeva.pocketscoins.MainActivity
import fr.maximeaeva.pocketscoins.Movement
import fr.maximeaeva.pocketscoins.MovementRepository
import fr.maximeaeva.pocketscoins.R

class HistoricAdapter(
    private val context: MainActivity,
    private var movList: ArrayList<Movement>,
    private val layoutId: Int
) : RecyclerView.Adapter<HistoricAdapter.ViewHolder>(){

    //Box of controlled components
    class ViewHolder(view: android.view.View) : RecyclerView.ViewHolder(view){
        var stonks = view.findViewById<ImageView>(R.id.item_stonks)
        var value = view.findViewById<TextView>(R.id.item_value)
        var mod = view.findViewById<TextView>(R.id.item_module)
        var moreButton = view.findViewById<ConstraintLayout>(R.id.item_histo) as ConstraintLayout
        var dat = view.findViewById<TextView>(R.id.item_date) as TextView
        var desc = view.findViewById<TextView>(R.id.item_description) as TextView
        var lay = view.findViewById<LinearLayout>(R.id.linear_popup) as LinearLayout
        var del = view.findViewById<ImageView>(R.id.item_delete) as ImageView
        var edi = view.findViewById<ImageView>(R.id.item_edit) as ImageView
        var bool = false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context).inflate(layoutId, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Catch movement info
        val editPopop = HistoPopup()
        val currentMov = movList[position]
        val choices = context.resources.getStringArray(R.array.home_page_description_spinner_input)
        //creating the instance of DatabaseHandler class
        val databaseHandler = MovementRepository(context)
        holder.moreButton.setOnClickListener {
            if (holder.bool) {
                holder.lay.layoutParams.height = ViewGroup.INVISIBLE
                holder.lay.requestLayout()
            } else {
                holder.lay.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                holder.lay.requestLayout()
            }
            holder.bool = !(holder.bool)
        }
        if(currentMov.del){
            holder.moreButton.setBackgroundColor(R.color.red_transparent)
        }
        holder.value.setText(currentMov.value.toString())
        holder.mod.setText(choices[currentMov.module].toString())
        holder.desc.setText(currentMov.description)
        holder.dat.setText(currentMov.date)
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
        holder.del.setOnClickListener{
            val position = holder.adapterPosition
            currentMov.del= !(currentMov.del)
            databaseHandler.updateMovement(currentMov)
            holder.lay.layoutParams.height = ViewGroup.INVISIBLE
            holder.lay.requestLayout()
            Log.d("Position value", "$position")
            Log.d("Bool", "${currentMov.del}")
            if(currentMov.del){
                movList.removeAt(position)
                notifyItemRemoved(position)
            }
        }
        holder.edi.setOnClickListener {
            editPopop.showPopMenu(context, it)
            holder.lay.layoutParams.height = ViewGroup.INVISIBLE
            holder.lay.requestLayout()
        }
    }

    override fun getItemCount(): Int = movList.size
}