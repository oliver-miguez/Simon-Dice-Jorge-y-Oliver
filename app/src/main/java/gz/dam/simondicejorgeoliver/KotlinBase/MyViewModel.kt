package gz.dam.simondicejorgeoliver.KotlinBase

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import gz.dam.simondicejorgeoliver.Utility.SharedPreference.Controlador.ControladorPreference
import gz.dam.simondicejorgeoliver.Utility.SQLite.Controlador.ControladorSQLite // Importar tu controlador SQLite
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Date

class MyViewModel(application: Application): AndroidViewModel(application){
    val estadoActual: MutableStateFlow<Estados> = MutableStateFlow(Estados.INICIO)
    var numeroRandomGenerado = MutableStateFlow(0)

    val puntuacion = MutableStateFlow<Int>(0)

    val record = MutableStateFlow<Int>(0)

    var ronda = MutableStateFlow<Int?>(1)
    var botonPresionado = MutableStateFlow<Int?>(-1)


    var posicion = 0

    var data = Date()
    
    // Instancia del controlador de SQLite
    private val controladorSQLite = ControladorSQLite(application)

    init {
        // Inicializamos el record visual con lo que haya en Preferences (si quieres mantener la compatibilidad visual actual)
        record.value = obtenerRecord() 
        
        // OPCIONAL: Leer todo el historial al iniciar para ver en Logcat qué hay guardado
        controladorSQLite.obtenerDatos()
    }

    fun numeroRandom(){
        estadoActual.value = Estados.GENERANDO
        Log.d("ViewModel","Estado Generando")
        numeroRandomGenerado.value = (0..3).random()
        Log.d("ViewModel","Número aleatorio generado: $numeroRandomGenerado")
        actualizarNumero(numeroRandomGenerado.value)
    }

    fun actualizarNumero(numero:Int){
        Log.d("ViewModel","Actualizando el numero de la clase Datos")
        Datos.numero.add(numero)
        estadoActual.value = Estados.ADIVINANDO
        mostrarSecuencia(Datos.numero)

    }

    fun correcionOpcionElegida(numeroColor:Int): Boolean{
        Log.d("ViewModel","Combrobando si la opción escogida es correcta...")
        return if (numeroColor == Datos.numero[posicion]){
            Log.d("ViewModel","ES CORRECTO !")
            posicion++
            if (Datos.numero.size == posicion) {
                cambiarRonda()
            }
            puntuacion.value = puntuacion.value.plus(1)

            true
        }else{
            Log.d("ViewModel","ERROR, HAS PERDIDO")
            derrota()
            false
        }
    }

    fun mostrarSecuencia(secuencia: ArrayList<Int>){
        Log.d("ViewModel","Estado Adivinando, secuencia: $secuencia")
        viewModelScope.launch {
            for (boton in secuencia){
                botonPresionado.value=boton
                delay(800)
            }
        }
    }

    fun continuarSecuencia(){
        botonPresionado.value=-1

    }

    fun cambiarRonda(){
        posicion=0
        ronda.value = ronda.value?.plus(1)

        numeroRandom()
    }

    fun derrota(){
        // Guardamos la partida en SQLite (Historial completo)
        Log.d("ViewModel", "Guardando partida en SQLite...")
        controladorSQLite.guardarRecord(puntuacion.value, System.currentTimeMillis())
        
        //  Probar a leer todo para ver que se guardó (saldrá en Logcat)
        controladorSQLite.obtenerDatos()

        // Lógica original de SharedPreferences para el "Récord Máximo" de la UI
        if (puntuacion.value > obtenerRecord()){
            Log.d("DataMia", "Hola $data")
            ControladorPreference.actualizarRecord(getApplication(),puntuacion.value,data)
            record.value = puntuacion.value
            Log.d("DataMia", "NUEVA"+ControladorPreference.obtenerRecord(getApplication()).toString())
        }

        puntuacion.value = 0
        posicion=0
        ronda.value = 1
        estadoActual.value = Estados.INICIO
        Datos.numero = ArrayList()
    }
    
    // Método para limpiar recursos cuando el ViewModel muera
    override fun onCleared() {
        super.onCleared()
        controladorSQLite.cerrar()
    }

    fun obtenerRecord():Int{
        record.value = ControladorPreference.obtenerRecord(getApplication()).valorRecord
        return record.value
    }
}
