package fr.maximeaeva.pocketscoins

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.maximeaeva.pocketscoins.fragments.HistoricFragment
import fr.maximeaeva.pocketscoins.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Load repository
        //val repo = MovementRepository()

        //Update repo
        //repo.updateData()

        // Inject fragment
        val transaction = supportFragmentManager.beginTransaction()
        //transaction.replace(R.id.fragment_container, HomeFragment(this))
        transaction.replace(R.id.fragment_container, HistoricFragment(this))
        transaction.addToBackStack(null)
        transaction.commit()
    }
}