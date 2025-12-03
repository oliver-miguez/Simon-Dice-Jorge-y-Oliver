package gz.dam.simondicejorgeoliver.Utility.SharedPreference.Controlador

import android.content.Context
import androidx.core.content.edit

/**
 * Controlador para administrar el gurdado del record persistentemente
 */
object ControladorPreference{
    private  const val PREFS_NAME = "preferencias_app_Preference"
    private const val KEYRECORD = "record"


    /**
     * Permite actualizar el record si se supera
     */
    fun actualizarRecord(context: Context, nuevoRecord: Int){
        val sharedPreferences  = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit{
            putInt(KEYRECORD,nuevoRecord)
        }
    }

    /**
     * Permite obtener el valor del record actual
     */
    fun obtenerRecrod(context: Context):Int{
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(KEYRECORD,0)
    }

}