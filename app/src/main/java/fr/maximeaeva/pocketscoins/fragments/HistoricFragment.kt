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

import android.content.Intent
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView


class HistoricFragment(
    private val context: MainActivity
): Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val view = inflater?.inflate(R.layout.fragment_historic, container, false)

        //List of movement
        val movList = arrayListOf<Movement>()

        //Save first elemtn
        movList.add(Movement(1, "I will decrease so fast", 0, false, 1000.0, ""))
        movList.add(Movement(1, "Med decr", 1, false, 200.0, ""))
        movList.add(Movement(1, "Just buy flowers !", 2, false, 30.0, ""))
        movList.add(Movement(1, "Ow woaw !!", 3, true, 2500.0, ""))
        movList.add(Movement(1, "Hey there", 5, false, 1.99, ""))
        movList.add(Movement(1, "Hey there", 6, false, 100.0, ""))
        movList.add(Movement(1, "I will decrease so fast", 0, false, 1000.0, ""))
        movList.add(Movement(1, "Med decr", 1, false, 200.0, ""))
        movList.add(Movement(1, "Just buy flowers !", 2, false, 30.0, ""))
        movList.add(Movement(1, "Ow woaw !!", 3, true, 2500.0, ""))
        movList.add(Movement(1, "Hey there", 5, false, 1.99, ""))
        movList.add(Movement(1, "Hey there", 6, false, 100.0, ""))

        //Catch historic recycler
        val recyclerView = view.findViewById<RecyclerView>(R.id.historicView)
        recyclerView.adapter = HistoricAdapter(context, movList, R.layout.item_historic)
        recyclerView.addItemDecoration(HistoricItemDecoration())
        return view
    }

}