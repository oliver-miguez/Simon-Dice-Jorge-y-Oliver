package gz.dam.simondicejorgeoliver

import androidx.compose.ui.graphics.Color

object Datos {
    var numero: ArrayList<Int> = ArrayList()
    var record = 0

}

enum class Colores(val color: Color, val txt: String, val nota: Double) {
    CLASE_ROJO(color = Color.Red, txt = "roxo",261.63),
    CLASE_VERDE(color = Color.Green, txt = "verde",293.66),
    CLASE_AZUL(color = Color.Blue, txt = "azul",329.63),
    CLASE_AMARILLO(color = Color.Yellow, txt = "melo",349.23),
    CLASE_START(color = Color.LightGray, txt = "Start",349.23)
}

enum class Estados(val start_activo: Boolean, val boton_activo: Boolean) {
    INICIO(start_activo = true, boton_activo = false),
    GENERANDO(start_activo = false, boton_activo = false),
    MOSTRANDO_SECUENCIA(start_activo = false,boton_activo = false),
    ADIVINANDO(start_activo = false, boton_activo = true),
}