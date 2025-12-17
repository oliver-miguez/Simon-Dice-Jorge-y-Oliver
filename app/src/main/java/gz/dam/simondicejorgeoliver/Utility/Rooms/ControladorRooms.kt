package gz.dam.simondicejorgeoliver.Utility.Rooms

import android.content.Context
import androidx.room.Room
import gz.dam.simondicejorgeoliver.Interfaz.InterfazRecord
import gz.dam.simondicejorgeoliver.Utility.Instancia.Record
import java.util.Date

class ControladorRooms(private val applicationContext: Context): InterfazRecord {

    val db = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "database-name"
    ).allowMainThreadQueries()
        .build()
    val userDao = db.userDao()

        // Permite obtener todos los usuarios de la base de datos
        //val users: List<User> = userDao.getAll() // SELECT * FROM user

    override fun obtenerRecord(context: Context): Record {
        // Busca usuarios por el record
        val userByRecord: User? = userDao.findByRecord() // SELECT * FROM user ORDER BY record DESC LIMIT 1

        var record = Record

        if (userByRecord?.record != null){
            record.valorRecord = userByRecord.record!!
            record.fechaSuperacion = Date(userByRecord.fecha)
        }

        return record
    }

    override fun actualizarRecord(
        context: Context,
        valorRecord: Int,
        valorData: Date
    ): Int {
        try {
            // Añade usuarios a la base
            val newUser = User(null, valorRecord, valorData.toString()) // INSERT INTO user (uid, record) VALUES (1, 12)
            userDao.insertAll(newUser)

            // Permite actualizar un usuario
            newUser.record = 15
            userDao.update(newUser)
            return 1

        }catch (a:Exception){
            return -1
        }
    }

    fun cerrar(){
        db.close()
    }
}