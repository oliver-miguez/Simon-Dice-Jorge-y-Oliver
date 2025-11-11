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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun UI(viewModel: MyViewModel){
    Menu(viewModel)
}


@Composable
fun Menu(viewModel: MyViewModel) {
    val puntuacionRecogida by viewModel.puntuacion.collectAsState()
    val rondaRecogida by viewModel.ronda.collectAsState()
    val recordRecogida by viewModel.record.collectAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Column (modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Puntuacion(puntuacionRecogida,rondaRecogida,recordRecogida)
            Botonera(viewModel)
            Boton_inicio(viewModel)
        }

    }

}

@Composable
fun Puntuacion(puntuacion: Int?, ronda: Int?, record: Int){
    Text(
        text = "Ronda: $ronda",
    )
    Text(
        text = "Puntuación: $puntuacion\n Record: $record",
        modifier = Modifier.padding(top = 100.dp)
    )
}


@Composable
fun Botonera(viewModel: MyViewModel) {
    Column (modifier = Modifier.padding(16.dp)) {
        Row {
            Boton(Colores.CLASE_ROJO,viewModel)
            Boton(Colores.CLASE_AMARILLO,viewModel)
        }
        Row {
            Boton(Colores.CLASE_AZUL,viewModel)
            Boton(Colores.CLASE_VERDE,viewModel)
        }
    }
}

@Composable
fun Boton(enum_color: Colores, viewModel: MyViewModel){
    val activo = viewModel.estadoActual.collectAsState().value
    Button(
        enabled = activo.boton_activo,
        colors = ButtonDefaults.buttonColors(enum_color.color),
        onClick = {
            Log.d("Juego","Click!"+ enum_color.txt+" numero: "+enum_color.ordinal)
            viewModel.correcionOpcionElegida(enum_color.ordinal)
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
fun Boton_inicio(viewModel: MyViewModel) {
    val activo = viewModel.estadoActual.collectAsState().value
    Button(
        enabled = activo.start_activo,
        onClick = {
            Log.d("Juego","Empieza el juego")
            viewModel.numeroRandom()
        }) {
        Text(
            text = "Start"
        )
    }
}


@Preview(showBackground = true)
@Composable
fun UIPreview() {
    UI(viewModel = MyViewModel())
}
