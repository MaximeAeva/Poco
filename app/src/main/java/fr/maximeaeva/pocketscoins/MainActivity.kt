package fr.maximeaeva.pocketscoins

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import fr.maximeaeva.pocketscoins.fragments.HistoricFragment
import fr.maximeaeva.pocketscoins.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var page = 0
        val pageCount = 2
        val layout = findViewById<ConstraintLayout>(R.id.mainLayout) as ConstraintLayout

        fun loader(movList: ArrayList<Movement>, page:Int){
            when(page){
                0 -> supportFragmentManager.beginTransaction().
                replace(R.id.fragment_container, HomeFragment(this)).commit()
                1 -> supportFragmentManager.beginTransaction().
                replace(R.id.fragment_container, HistoricFragment(this, movList)).commit()
            }
        }
        //creating the instance of DatabaseHandler class
        val databaseHandler = MovementRepository(this)
        //calling the viewEmployee method of DatabaseHandler class to read the records
        fun loadMovList():ArrayList<Movement>{
            val pseudoMvList: List<Movement> = databaseHandler.viewMovement().reversed()
            val movList = arrayListOf<Movement>()

            if(pseudoMvList.isEmpty()){
                movList.add(Movement(1, "None", 0,
                    false, 0.0, "2021-11-20"))
            }else{
                for(i in pseudoMvList)
                    movList.add(i)
            }
            return movList
        }
        var movList = loadMovList()

        // Inject fragment
        val transaction = supportFragmentManager.beginTransaction()
        loader(movList, page)
        layout.setOnTouchListener(object : OnSwipeTouchListener(this@MainActivity) {
            override fun onSwipeLeft() {
                super.onSwipeLeft()
                page = (page + 1) % pageCount
                var movList = loadMovList()
                loader(movList, page)
            }
            override fun onSwipeRight() {
                super.onSwipeRight()
                page = (page - 1) % pageCount
                var movList = loadMovList()
                loader(movList, page)
            }
            override fun onSwipeUp() {
                super.onSwipeUp()
            }
            override fun onSwipeDown() {
                super.onSwipeDown()
                var movList = loadMovList()
                loader(movList, page)
            }
        })
        //transaction.replace(R.id.fragment_container, HistoricFragment(this, movList))

    }
}