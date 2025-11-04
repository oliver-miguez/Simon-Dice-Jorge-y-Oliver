package gz.dam.simondicejorgeoliver

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun UI(){
    Menu()
}


@Composable
fun Menu(){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Column (modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Puntuacion(1)
            Botonera()
            Boton_inicio()
        }

    }

}

@Composable
fun Puntuacion(puntuacion: Int){
    Text(
        text = "Puntuación: $puntuacion",
        modifier = Modifier.padding(top = 100.dp)
    )
}


@Composable
fun Botonera(){
    Column (modifier = Modifier.padding(16.dp)) {
        Row {
            Boton(Colores.CLASE_ROJO)
            Boton(Colores.CLASE_AMARILLO)
        }
        Row {
            Boton(Colores.CLASE_AZUL)
            Boton(Colores.CLASE_VERDE)
        }
    }
}

@Composable
fun Boton(enum_color: Colores){
    Button(
        colors = ButtonDefaults.buttonColors(enum_color.color),
        onClick = {
            Log.d("Juego","Click!"+ enum_color.txt+" numero: "+enum_color.ordinal)
        },
        shape = RoundedCornerShape(0.dp),
        modifier = Modifier.size(150.dp).padding(15.dp)
        ) {
        Text(text = enum_color.txt,
            fontSize = 15.sp,
            color = Color.Black)
    }
}

@Composable
fun Boton_inicio(){
    Button(
        onClick = {
            Log.d("Juego","Empieza el juego")
        }) {
        Text(
            text = "Start"
        )
    }
}


@Preview(showBackground = true)
@Composable
fun UIPreview() {
    UI()
}
