package gz.dam.simondicejorgeoliver.KotlinBase

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gz.dam.simondicejorgeoliver.Utility.KotlinRecord.Controlador.ControladorKotlin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

object MyViewModel: ViewModel(){
    val estadoActual: MutableStateFlow<Estados> = MutableStateFlow(Estados.INICIO)
    var numeroRandomGenerado = MutableStateFlow(0)

    val puntuacion = MutableStateFlow<Int>(0)

    val record = MutableStateFlow<Int>(0)

    var ronda = MutableStateFlow<Int?>(1)
    var botonPresionado = MutableStateFlow<Int?>(-1)


    var posicion = 0

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
        record.value = ControladorKotlin.obtenerRecord().valorRecord

        if (record.value < puntuacion.value){
            record.value = puntuacion.value
            ControladorKotlin.actualizarRecord(record.value)
        }

        puntuacion.value = 0
        posicion=0
        ronda.value = 1
        estadoActual.value = Estados.INICIO
        Datos.numero = ArrayList()

    }
}