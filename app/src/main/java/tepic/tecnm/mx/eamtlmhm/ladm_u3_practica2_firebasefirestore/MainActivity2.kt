package tepic.tecnm.mx.eamtlmhm.ladm_u3_practica2_firebasefirestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity2 : AppCompatActivity() {
    var modificacion = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        findViewById<TextView>(R.id.txtDate).setText(SimpleDateFormat("yyyy-MM-dd").format(Date()).toString())
        findViewById<TextView>(R.id.txtTime).setText(SimpleDateFormat("HH:MM").format(Date()))
        findViewById<Button>(R.id.borrarArchivo).isEnabled = false

        //Inicia la lectura de notas del dispositivo
        agregarContenidoInicial()
        //Agrega funcion guardar al boton guardar
        findViewById<Button>(R.id.agregarNota).setOnClickListener {
            guardar()
        }//fab
        if (modificacion){
            findViewById<Button>(R.id.borrarArchivo).isEnabled = true
        }
        findViewById<Button>(R.id.borrarArchivo).setOnClickListener {
            borrarNotas()
        }
    }

    private fun agregarContenidoInicial() {
        if(getIntent().hasExtra("titulo") && getIntent().hasExtra("texto")){//Revisa si se mando algo al activity
            var intent = getIntent()
            val titulo = intent.getStringExtra("titulo").toString()//Crashea si no existe un Extra
            val texto = intent.getStringExtra("texto").toString()
            modificacion = true
            //Coloca el contenido mandado a los txt
            findViewById<EditText>(R.id.txtTitle).setText(titulo)
            findViewById<EditText>(R.id.txtContenido).setText(texto)
        }
    }

    private fun actualizar(){

    }

    private fun borrarNotas() {

    }

    private fun guardar(){
        if (modificacion){
            actualizar()
        }//end if
        else {
            val nota = Nota(this)
            nota.titulo =  findViewById<EditText>(R.id.txtTitle).text.toString()
            nota.contenido = findViewById<EditText>(R.id.txtContenido).text.toString()
            nota.hora = findViewById<TextView>(R.id.txtTime).text.toString()
            nota.fecha =  findViewById<TextView>(R.id.txtDate).text.toString()
        }//end else
    }//end guardar

}//end class

