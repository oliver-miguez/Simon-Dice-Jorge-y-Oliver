package gz.dam.simondicejorgeoliver

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import gz.dam.simondicejorgeoliver.Utility.BaseDeDatosSQLite.FeedReaderContract
import gz.dam.simondicejorgeoliver.Utility.SQLite.Controlador.ControladorSQLite
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date

@RunWith(AndroidJUnit4::class)
class ControladorSQLiteTest {

    private lateinit var controlador: ControladorSQLite

    @Before
    fun setUp() {
        // Obtenemos el contexto de la aplicación para tests
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        
        // Antes de cada test, limpiamos la base de datos para asegurar un estado limpio
        val dbHelper = FeedReaderContract.FeedReaderDbHelper(appContext)
        dbHelper.writableDatabase.execSQL("DELETE FROM ${FeedReaderContract.FeedEntry.TABLE_NAME}")
        dbHelper.close()

        // Inicializamos el controlador
        controlador = ControladorSQLite(appContext)
    }

    @After
    fun tearDown() {
        controlador.cerrar()
    }

    @Test
    fun guardarYLeerDatos_funcionaCorrectamente() {
        // Arrange (Preparar datos)
        val puntuacionPrueba = 100
        val fechaPrueba = System.currentTimeMillis() // Fecha actual en milisegundos

        // Act (Ejecutar acción)
        controlador.guardarRecord(puntuacionPrueba, fechaPrueba)
        
        // Recuperar los datos
        val listaDatos = controlador.obtenerDatos()

        // Assert (Verificar resultados)
        // 1. Verificar que la lista no esté vacía
        assertTrue("La lista de datos no debería estar vacía", listaDatos.isNotEmpty())
        
        // 2. Verificar que contiene al menos 1 elemento
        assertEquals("Debería haber 1 elemento guardado", 1, listaDatos.size)

        // 3. Verificar que el contenido es el esperado
        // El formato en obtenerDatos() es "$score - $dateString"
        val registro = listaDatos[0]
        val fechaLegible = Date(fechaPrueba).toString()
        val esperado = "$puntuacionPrueba - $fechaLegible"
        
        assertEquals("El registro leído no coincide con el guardado", esperado, registro)
    }

    @Test
    fun guardarMultiplesRegistros_y_Orden() {
        // Guardamos 3 registros
        controlador.guardarRecord(10, System.currentTimeMillis() - 20000) // Fecha antigua
        controlador.guardarRecord(20, System.currentTimeMillis() - 10000) // Fecha media
        controlador.guardarRecord(30, System.currentTimeMillis())         // Fecha reciente

        val lista = controlador.obtenerDatos()

        // Verificar cantidad
        assertEquals(3, lista.size)

        // Verificar orden (obtenerDatos ordena por fecha DESC, el más reciente primero)
        // El primer elemento debería ser el de puntuación 30 (el más reciente)
        assertTrue("El primer elemento debería ser el más reciente (30)", lista[0].startsWith("30 -"))
        
        // El último elemento debería ser el de puntuación 10 (el más antiguo)
        assertTrue("El último elemento debería ser el más antiguo (10)", lista[2].startsWith("10 -"))
    }

    @Test
    fun actualizarUltimoRecord_modificaCorrectamente() {
        // Arrange: Guardamos dos registros
        val fechaAntigua = System.currentTimeMillis() - 50000
        val fechaReciente = System.currentTimeMillis()

        controlador.guardarRecord(50, fechaAntigua)
        controlador.guardarRecord(80, fechaReciente) // Este es el último

        // Act: Actualizamos la puntuación del último registro (de 80 a 999)
        controlador.actualizarUltimoRecord(999)

        // Assert: Recuperamos los datos
        val lista = controlador.obtenerDatos()

        // El primer elemento de la lista (el más reciente) debería tener la nueva puntuación
        // Formato esperado: "999 - fechaRecienteString"
        assertTrue("El último registro no se actualizó correctamente. Valor encontrado: ${lista[0]}",
            lista[0].startsWith("999 -"))

        // El segundo elemento (el antiguo) no debería haber cambiado
        assertTrue("El registro antiguo no debería haber cambiado",
            lista[1].startsWith("50 -"))
    }
}
