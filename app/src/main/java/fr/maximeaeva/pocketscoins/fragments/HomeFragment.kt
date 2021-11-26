package fr.maximeaeva.pocketscoins.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.get
import androidx.fragment.app.Fragment
import fr.maximeaeva.pocketscoins.MainActivity
import fr.maximeaeva.pocketscoins.Movement
import fr.maximeaeva.pocketscoins.MovementRepository
import fr.maximeaeva.pocketscoins.R

class HomeFragment(
    private val context: MainActivity
) : Fragment() {
    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val view =  inflater?.inflate(R.layout.fragment_home, container, false)

        val choices = resources.getStringArray(R.array.home_page_description_spinner_input)
        val spinner = view.findViewById<Spinner>(R.id.module_spinner) as Spinner

        //creating the instance of DatabaseHandler class
        val databaseHandler = MovementRepository(context)

        if (spinner != null) {
            spinner.setSelection(0)
            val adapter = ArrayAdapter(context,
                android.R.layout.simple_spinner_item, choices)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {}

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }

        fun Context.hideKeyboard(view: View) {
            val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }

        val value = view.findViewById<TextView>(R.id.Value) as TextView
        val inp = view.findViewById<EditText>(R.id.Input) as EditText
        val desc = view.findViewById<EditText>(R.id.description) as EditText

        //Set origine value
        value.setText(String.format("%.2f", databaseHandler.bilan()))

        //Plus and minus button
        val minusButton = view.findViewById<ImageView>(R.id.imageView) as ImageView
        val plusButton = view.findViewById<ImageView>(R.id.imageView2) as ImageView

        minusButton.setOnClickListener{
            var safeDesc = "None"
            if(inp.text.toString().trim().isNotEmpty())
            {
                if(desc.text.toString().trim().isNotEmpty()){
                    safeDesc = desc.text.toString()
                }
                val mov = Movement(0,
                    safeDesc,
                    spinner.selectedItemPosition,
                    false,
                    inp.text.toString().toDouble())
                databaseHandler.addMovement(mov)
                value.setText(String.format("%.2f", databaseHandler.bilan()))

            }
            view?.let { activity?.hideKeyboard(it) }
            inp.clearFocus()
            inp.text.clear()
            desc.clearFocus()
            desc.text.clear()
            spinner.setSelection(0)
        }
        plusButton.setOnClickListener{
            var safeDesc = "None"
            if(inp.text.toString().trim().isNotEmpty())
            {
                if(desc.text.toString().trim().isNotEmpty()){
                    safeDesc = desc.text.toString()
                }
                val mov = Movement(0,
                    safeDesc,
                    spinner.selectedItemPosition,
                    true,
                    inp.text.toString().toDouble())
                databaseHandler.addMovement(mov)
                value.setText(String.format("%.2f", databaseHandler.bilan()))
            }
            view?.let { activity?.hideKeyboard(it) }
            inp.clearFocus()
            inp.text.clear()
            desc.clearFocus()
            desc.text.clear()
            spinner.setSelection(0)
        }

        return view

    }

}