package gz.dam.simondicejorgeoliver.Utility.Rooms

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "record") val record: Int?,
    @ColumnInfo(name = "fecha") val fecha: String?
)

