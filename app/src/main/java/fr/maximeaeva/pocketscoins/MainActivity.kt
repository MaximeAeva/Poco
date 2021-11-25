package fr.maximeaeva.pocketscoins

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import fr.maximeaeva.pocketscoins.fragments.HistoricFragment
import fr.maximeaeva.pocketscoins.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //creating the instance of DatabaseHandler class
        val databaseHandler = MovementRepository(this)
        //calling the viewEmployee method of DatabaseHandler class to read the records
        val pseudoMvList: List<Movement> = databaseHandler.viewMovement()
        val movList = arrayListOf<Movement>()

        if(pseudoMvList.isEmpty()){
            movList.add(Movement(1, "None", 0,
                false, 0.0, "2021-11-20 18:00:00.000"))
        }else{
            for(i in pseudoMvList)
            movList.add(i)
        }

        // Inject fragment
        val transaction = supportFragmentManager.beginTransaction()
        //transaction.replace(R.id.fragment_container, HomeFragment(this, movList))
        transaction.replace(R.id.fragment_container, HistoricFragment(this, movList))
        transaction.addToBackStack(null)
        transaction.commit()
    }
}