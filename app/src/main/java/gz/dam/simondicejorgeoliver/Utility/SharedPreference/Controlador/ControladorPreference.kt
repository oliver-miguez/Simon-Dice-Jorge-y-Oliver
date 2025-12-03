package gz.dam.simondicejorgeoliver.Utility.SharedPreference.Controlador

import android.content.Context
import androidx.core.content.edit
import gz.dam.simondicejorgeoliver.Interfaz.InterfazRecord
import gz.dam.simondicejorgeoliver.Utility.Instancia.Record
import java.util.Date

/**
 * Controlador para administrar el gurdado del record persistentemente
 */
object ControladorPreference : InterfazRecord{
    private  const val PREFS_NAME = "preferencias_app_Preference"
    private const val KEYRECORD = "record"
    private const val KEYDATE = "date"

    /**
     * Permite recoger el record y la data
     */
    override fun obtenerRecord(context: Context): Record {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val rec = sharedPreferences.getInt(KEYRECORD,0)
        val dat = sharedPreferences.getString(KEYDATE,"")
        val dataAjusta = Date(dat)
        Record.valorRecord = rec
        Record.fechaSuperacion = dataAjusta
        return Record
    }


    /**
     * Permite actualizar el record si se supera
     */
    override fun actualizarRecord(context: Context, nuevoRecord: Int, dataActual: Date): Int {
        val sharedPreferences  = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val dataString = dataActual.toString()
        sharedPreferences.edit{
            putInt(KEYRECORD,nuevoRecord)
            putString(KEYDATE,dataString)
        }
        return 1
    }



}