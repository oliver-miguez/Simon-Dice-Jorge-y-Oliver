package gz.dam.simondicejorgeoliver

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow

class ViewModel(){
    val estadoActual: MutableStateFlow<Estados> = MutableStateFlow(Estados.INICIO)
    var numeroRandomGenerado = MutableStateFlow(0)

    fun numeroRandom(){
        estadoActual.value = Estados.GENERANDO
        Log.d("ViewModel","Estado Generando")
        numeroRandomGenerado.value = (0..3).random()
        Log.d("ViewModel","Número aleatorio generado: $numeroRandomGenerado")
        actualizarNumero(numeroRandomGenerado.value)
    }

    fun actualizarNumero(numero:Int){
        Log.d("ViewModel","Actualizando el numero de la clase Datos")
        Datos.numero = numero
        estadoActual.value = Estados.ADIVINANDO
        Log.d("ViewModel","Estado Adivinando")

    }

    fun correcionOpcionElegida(numeroColor:Int): Boolean{
        Log.d("ViewModel","Combrobando si la opción escogida es correcta...")
        return if (numeroColor == Datos.numero){
            Log.d("ViewModel","ES CORRECTO !")
            numeroRandom()
            true
        }else{
            Log.d("ERROR, HAS PERDIDO")
            false
        }
    }


}