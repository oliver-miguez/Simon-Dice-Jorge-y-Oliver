package gz.dam.simondicejorgeoliver.KotlinBase

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.sin


@Composable
fun UI(viewModel: MyViewModel){
    Menu(viewModel)
}


@Composable
fun Menu(viewModel: MyViewModel) {
    val puntuacionRecogida by viewModel.puntuacion.collectAsState()
    val rondaRecogida by viewModel.ronda.collectAsState()
    val recordRecogida by viewModel.record.collectAsState()
    val estado  by viewModel.estadoActual.collectAsState()

    if (estado == Estados.INICIO) reproducirTono(549.25,500)


    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Column (modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Puntuacion(puntuacionRecogida,rondaRecogida,recordRecogida, estado)
            Botonera(viewModel)
            Boton_inicio(viewModel)
        }

    }

}

@Composable
fun Puntuacion(puntuacion: Int?, ronda: Int?, record: Int, estado: Estados){
    Text(
        text = "Estado: $estado"
    )
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
    var _activo by remember { mutableStateOf( activo.boton_activo)}
    val estado  by viewModel.botonPresionado.collectAsState()
    var _color by remember { mutableStateOf(enum_color.color)}
    val scope = rememberCoroutineScope()
    LaunchedEffect(estado) {
        Log.d("Debug","Cambie de estado ${enum_color.txt}")
        if (estado==enum_color.ordinal) {

            _color = enum_color.color.copy(alpha = 0.41f)
            delay(400)
            reproducirTono(enum_color.nota,200)
            _color = enum_color.color

            viewModel.continuarSecuencia()
        }



        Log.d("Debug","Cambie de estado ${enum_color.txt}")
    }
    Button(
        enabled = activo.boton_activo,
        colors = ButtonDefaults.buttonColors(_color),
        onClick = {
            scope.launch {
                reproducirTono(enum_color.nota,150)
            }
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


fun reproducirTono(frecuencia: Double, duracionMs: Int) {
    val sampleRate = 44100
    val numSamples = (duracionMs / 1000.0 * sampleRate).toInt()
    val buffer = ShortArray(numSamples)

    // Generar la onda senoidal
    for (i in buffer.indices) {
        val angle = 2.0 * PI * i * frecuencia / sampleRate
        buffer[i] = (sin(angle) * Short.MAX_VALUE).toInt().toShort()
    }

    // Crear un AudioTrack para reproducir el tono
    val audioTrack = AudioTrack(
        AudioManager.STREAM_MUSIC,
        sampleRate,
        AudioFormat.CHANNEL_OUT_MONO,
        AudioFormat.ENCODING_PCM_16BIT,
        buffer.size * 2,
        AudioTrack.MODE_STATIC
    )

    audioTrack.write(buffer, 0, buffer.size)
    audioTrack.play()
    // Detener luego de la duración
    Thread.sleep(duracionMs.toLong())
    audioTrack.release()
}

/*
@Preview(showBackground = true)
@Composable
fun UIPreview() {
    UI(viewModel = MyViewModel)
}
*/