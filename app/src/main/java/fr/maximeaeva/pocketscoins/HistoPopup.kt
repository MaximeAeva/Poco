import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import fr.maximeaeva.pocketscoins.R
import fr.maximeaeva.pocketscoins.adapter.HistoricAdapter

class HistoPopup{

    @SuppressLint("ResourceType")
    fun showPopMenu(context: Context, view: View) {
        Log.d("In popup function", "")

        val pop = PopupMenu(context, view)
        //pop.inflate(R.menu.edit_histo_popup)

        pop.setOnMenuItemClickListener {
            when (it!!.itemId) {
                R.id.popup_value -> {
                    Toast.makeText(context, "item value", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.popup_spinner -> {
                    Toast.makeText(context, "item spin", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.popup_description -> {
                    Toast.makeText(context, "item desc", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false

            }
        }

        try {

            val fieldMpopup = PopupMenu::class.java.getDeclaredField("mPopup")
            fieldMpopup.isAccessible= true
            val mPopup = fieldMpopup.get(pop)
            mPopup.javaClass
                .getDeclaredMethod("setFoeceShowIcon",Boolean::class.java)
                .invoke(mPopup,true)

        }catch (e:Exception){

        }finally {
            pop.show()
        }

    }

}