package fr.maximeaeva.pocketscoins.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.maximeaeva.pocketscoins.MainActivity
import fr.maximeaeva.pocketscoins.Movement
import fr.maximeaeva.pocketscoins.R
import fr.maximeaeva.pocketscoins.adapter.HistoricAdapter
import fr.maximeaeva.pocketscoins.adapter.HistoricItemDecoration


class HistoricFragment(
    private val context: MainActivity,
    private val movList: ArrayList<Movement>
): Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val view = inflater?.inflate(R.layout.fragment_historic, container, false)
        val styleList = view.findViewById<ImageView>(R.id.item_style_list) as ImageView
        styleList.setImageResource(R.drawable.ic_outline_foreground)
        val movListCast = movList.toList<Movement>()
        var boolClick = false
        //Catch historic recycler
        val recyclerView = view.findViewById<RecyclerView>(R.id.historicView)
        recyclerView.adapter = HistoricAdapter(context,
            movListCast.filter { !it.del } as ArrayList<Movement>, R.layout.item_historic)

        styleList.setOnClickListener {
            boolClick = !boolClick
            if(boolClick){
                recyclerView.adapter = HistoricAdapter(context, movList, R.layout.item_historic)
                styleList.setImageResource(R.drawable.ic_delete_foreground)
            }else{
                recyclerView.adapter = HistoricAdapter(context,
                    movListCast.filter { !it.del } as ArrayList<Movement>, R.layout.item_historic)
                styleList.setImageResource(R.drawable.ic_outline_foreground)
            }
        }

        recyclerView.addItemDecoration(HistoricItemDecoration())
        return view
    }

}