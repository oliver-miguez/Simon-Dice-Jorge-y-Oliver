package gz.dam.simondicejorgeoliver.Utility.Rooms

import android.content.Context
import android.util.Log
import androidx.room.Room

class ControladorRooms(applicationContext: Context) {
    val db = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "database-name"
    ).allowMainThreadQueries()
        .build()

    val userDao = db.userDao()
    val users: List<User> = userDao.getAll()

    val newUser = User(1, 12,"12-12-12")
    


}