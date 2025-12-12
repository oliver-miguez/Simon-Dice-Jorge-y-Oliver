package gz.dam.simondicejorgeoliver.Utility.SQLite.Controlador

import android.content.ContentValues
import android.content.Context
import android.util.Log
import gz.dam.simondicejorgeoliver.Utility.BaseDeDatosSQLite.FeedReaderContract
import java.util.Date

/**
 * Administra la base de datos SQLite y permite realizar acciones con la data y el record
 */
class ControladorSQLite(context: Context) {
    // Es recomendable instanciar el helper, pero abrir la conexión (readableDatabase/writableDatabase)
    // solo cuando se necesite, o usar 'lazy' si queremos mantenerla.
    private val dbHelper = FeedReaderContract.FeedReaderDbHelper(context)

    /**
     * Guarda un nuevo record en la base de datos
     * @param puntuacion La puntuación obtenida
     * @param fecha La fecha en milisegundos
     */
    fun guardarRecord(puntuacion: Int, fecha: Long) {
        // Obtenemos la base de datos en modo escritura
        val db = dbHelper.writableDatabase

        // Creamos los valores a insertar
        val values = ContentValues().apply {
            put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, "Partida Simon Dice") // Título por defecto
            put(FeedReaderContract.FeedEntry.COLUMN_NAME_SCORE, puntuacion)
            put(FeedReaderContract.FeedEntry.COLUMN_NAME_DATE, fecha)
        }

        // Insertamos la nueva fila
        val newRowId = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values)
        Log.d("SQLite", "Record guardado correctamente. ID fila: $newRowId. Score: $puntuacion")
    }

    /**
     * Permite obtener los datos de la base de datos
     */
    fun obtenerDatos(): List<String> {
        val db = dbHelper.readableDatabase

        // Columnas que queremos recuperar
        val projection = arrayOf(
            FeedReaderContract.FeedEntry.COLUMN_NAME_SCORE,
            FeedReaderContract.FeedEntry.COLUMN_NAME_DATE
        )
  
        // Pasamos null para traer todo.
        val selection = null
        val selectionArgs = null
        
        // Ordena por fecha descendente (lo más nuevo primero)
        val sortOrder = "${FeedReaderContract.FeedEntry.COLUMN_NAME_DATE} DESC"

        val items = mutableListOf<String>()

        // Usamos 'use' para asegurar que el cursor se cierre automáticamente al terminar el bloque
        db.query(
            FeedReaderContract.FeedEntry.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder // Ordenamos para ver los últimos primero
        ).use { cursor ->
            while (cursor.moveToNext()) {
                // Obtenemos el score como Int
                val score = cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_SCORE))
                
                // Obtenemos la fecha como Long (timestamp)
                val dateLong = cursor.getLong(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_DATE))
                
                // Convertimos el timestamp a un objeto Date o String legible para mostrar
                val dateString = Date(dateLong).toString()
                
                items.add("$score - $dateString")
                Log.d("SQLite", "Leído: Score=$score, Date=$dateString")
            }
        }
        
        return items
    }
    
    // Método auxiliar para cerrar la base de datos cuando ya no se use el controlador
    fun cerrar() {
        dbHelper.close()
    }
}
