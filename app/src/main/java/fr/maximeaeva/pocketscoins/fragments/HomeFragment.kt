package fr.maximeaeva.pocketscoins.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import fr.maximeaeva.pocketscoins.MainActivity
import fr.maximeaeva.pocketscoins.R

class HomeFragment(
    private val context: MainActivity
) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val view =  inflater?.inflate(R.layout.fragment_home, container, false)

        val choices = resources.getStringArray(R.array.home_page_description_spinner_input)
        val spinner = view.findViewById<Spinner>(R.id.module_spinner) as Spinner


        if (spinner != null) {
            spinner.setSelection(0)
            val adapter = ArrayAdapter(context,
                R.layout.spinner_list, choices)
            adapter.setDropDownViewResource(R.layout.spinner_list)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    Toast.makeText(context, choices[position].toString(), Toast.LENGTH_SHORT).show()
                }

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

        inp.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                inp.setText("")
            }
        }

        desc.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                desc.setText("")
            }
        }
        //Plus and minus button
        val minusButton = view.findViewById<ImageView>(R.id.imageView) as ImageView
        val plusButton = view.findViewById<ImageView>(R.id.imageView2) as ImageView

        minusButton.setOnClickListener{
            if(value.text.toString().trim().length>0)
            {
                var number = (value.text.toString()).toDouble()-inp.text.toString().toDouble();
                value.setText(number.toString())
            }
            view?.let { activity?.hideKeyboard(it) }
            inp.setText("0.0")
            inp.clearFocus()
            desc.setText("Description")
            desc.clearFocus()
            spinner.setSelection(0)
        }
        plusButton.setOnClickListener{
            if(value.text.toString().trim().length>0)
            {
                var number = (value.text.toString()).toDouble()+inp.text.toString().toDouble()
                value.setText(number.toString())
            }
            inp.setText("0.0")
            inp.clearFocus()
            desc.setText("Description")
            desc.clearFocus()

            view?.let { activity?.hideKeyboard(it) }
            spinner.setSelection(0)
        }

        return view

    }

}