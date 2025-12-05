package gz.dam.simondicejorgeoliver.Utility.SharedPreference.Controlador

import android.content.Context
import android.content.SharedPreferences
import gz.dam.simondicejorgeoliver.Utility.Instancia.Record
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import java.util.Date

class ControladorPreferenceTest {

    private lateinit var context: Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    @BeforeEach
    fun setUp() {
        // Mockear las dependencias
        context = mock()
        sharedPreferences = mock()
        editor = mock()

        // Configurar el comportamiento de los mocks
        whenever(context.getSharedPreferences(any(), any())).thenReturn(sharedPreferences)
        whenever(sharedPreferences.edit()).thenReturn(editor)
        // Permitir el encadenamiento de llamadas en el editor (aunque kotlin ktx lo maneja diferente, es buena práctica)
        whenever(editor.putInt(any(), any())).thenReturn(editor)
        whenever(editor.putString(any(), any())).thenReturn(editor)
    }



    @Test
    fun `actualizarRecord guarda datos en SharedPreferences`() {
        // Arrange
        val newScore = 100
        val newDate = Date()
        val newDateString = newDate.toString()

        // Act
        val result = ControladorPreference.actualizarRecord(context, newScore, newDate)

        // Assert
        // 1. Verificamos que devuelve 1 (según la implementación actual)
        assertEquals(1, result)

        // 2. Verificamos que se pusieron los valores correctos en el editor
        verify(editor).putInt("record", newScore)
        verify(editor).putString("date", newDateString)
        
        // 3. Verificamos que se guardaron los cambios (apply es llamado internamente por la extensión edit {})
        verify(editor).apply() 
    }
}
