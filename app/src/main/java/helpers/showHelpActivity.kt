package helpers

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat

import com.objetivobolso.activity.HelpActivity


fun showHelpActivity(context: Context){

    val i = Intent(context, HelpActivity::class.java)
    ContextCompat.startActivity(context,i,null)

}
