package fr.maximeaeva.pocketscoins

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.maximeaeva.pocketscoins.MovementRepository.Singleton.databaseRef
import fr.maximeaeva.pocketscoins.MovementRepository.Singleton.movList

class MovementRepository {

    object Singleton{
    //Connect
    val databaseRef = FirebaseDatabase.getInstance().getReference("movements")

    //Mov list
    val movList = arrayListOf<Movement>()
    }

    //Collect data to parse to mov list
    fun updateData() {
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Clear
                movList.clear()
                // Gather list of movements
                for (ds in snapshot.children){
                    // Build movement element
                    val mov = ds.getValue(Movement::class.java)

                    //Check if not null
                    if (mov != null) {
                        //Add to list
                        movList.add(mov)
                    }
                }
            }

            override fun onCancelled(p0: DatabaseError) {}

        })
    }
}