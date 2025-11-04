package gz.dam.simondicejorgeoliver

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow

class ViewModel(){
    val estadoActual: MutableStateFlow<Estados> = MutableStateFlow(Estados.INICIO)


    fun numeroRandom(){
        estadoActual.value = Estados.GENERANDO
        Log.d("ViewModel","Estado generando")
    }
}