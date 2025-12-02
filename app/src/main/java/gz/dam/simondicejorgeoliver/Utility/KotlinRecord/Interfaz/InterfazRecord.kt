package gz.dam.simondicejorgeoliver.Utility.KotlinRecord.Interfaz

import gz.dam.simondicejorgeoliver.Utility.KotlinRecord.Instancia.Record

/**
 * Interfaz que recoge las funcionalidades que podremos utilizar con el Record obtenido en el juego
 */
interface InterfazRecord{

    // Permite recoger el record actual
    fun obtenerRecord(): Record

    //Restablece el record a 0
    fun borrarRecord(): Int

    // Actualiza el record
    fun actualizarRecord(valorRecord: Int): Int
}