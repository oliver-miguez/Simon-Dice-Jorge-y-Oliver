package gz.dam.simondicejorgeoliver.Utility.SQLite.Controlador

import android.content.Context
import gz.dam.simondicejorgeoliver.Utility.BaseDeDatosSQLite.FeedReaderContract

/**
 * Administra la base de datos SQLite y permite realizar acciones con la data y el record
 */
class ControladorSQLite(context: Context) {
    val dbHelper = FeedReaderContract.FeedReaderDbHelper(context)// Referencia al helper para crear una base de datos
    val db = dbHelper.readableDatabase // BD que utilizaré

    /**
     * Permite obtener los datos de la base de datos
     */
    fun obtenerDatos(): List<String> {
        // Consulta a la base de datos para obtener los datos
        // Usar projection permite seleccionar los campos que queremos obtener
        // En este caso solo queremos el data y el record
        var projection = arrayOf(FeedReaderContract.FeedEntry.COLUMN_NAME_SCORE, FeedReaderContract.FeedEntry.COLUMN_NAME_DATE)

        // Ejecuta la consulta
        // Simula un WHERE "title"  = "TablaDeRecords"
        // Tiene que coincidir con el nombre de la tabla de la base de datos
        val selection = "${FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE} = ?"
        val selectionArgs = arrayOf("TablaDeRecords")

        val cursor = db.query(

        )


    }

}