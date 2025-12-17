package gz.dam.simondicejorgeoliver.Utility.Rooms

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val uid: Int?,
    @ColumnInfo(name = "record") var record: Int?,
    @ColumnInfo(name = "fecha") var fecha: String?
)

