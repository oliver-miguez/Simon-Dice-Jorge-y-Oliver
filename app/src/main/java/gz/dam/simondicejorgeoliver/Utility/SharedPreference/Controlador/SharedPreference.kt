package gz.dam.simondicejorgeoliver.Utility.SharedPreference.Controlador

import androidx.datastore.preferences.core.intPreferencesKey
import gz.dam.simondicejorgeoliver.Interfaz.InterfazRecord
import gz.dam.simondicejorgeoliver.Utility.KotlinRecord.Instancia.Record

/**
 * Persistencia de datos hasta cuando se cierra la aplicación
 */
class SharedPreference : InterfazRecord{

    val ALMACEN_DATOS = intPreferencesKey("almacen_datos")

    override fun obtenerRecord(): Record {
        TODO("Not yet implemented")
    }

    override fun borrarRecord(): Int {
        TODO("Not yet implemented")
    }

    override fun actualizarRecord(valorRecord: Int): Int {
        TODO("Not yet implemented")
    }
}