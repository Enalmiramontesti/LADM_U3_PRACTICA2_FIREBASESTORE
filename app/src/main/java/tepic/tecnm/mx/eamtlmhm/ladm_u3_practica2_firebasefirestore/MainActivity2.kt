package tepic.tecnm.mx.eamtlmhm.ladm_u3_practica2_firebasefirestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity2 : AppCompatActivity() {
    var modificacion = false
    var id = ""
    var titulo = ""
    var texto = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
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
        if(getIntent().hasExtra("titulo")
            && getIntent().hasExtra("texto")
            && getIntent().hasExtra("ID")){//Revisa si se mando algo al activity
            var intent = getIntent()
            titulo = intent.getStringExtra("titulo").toString()//Crashea si no existe un Extra
            texto = intent.getStringExtra("texto").toString()
            id = intent.getStringExtra("ID").toString()
            modificacion = true
            //Coloca el contenido mandado a los txt
            findViewById<EditText>(R.id.txtTitle).setText(titulo)
            findViewById<EditText>(R.id.txtNote).setText(texto)
        }
    }

    private fun actualizar(){

    }

    private fun borrarNotas() {

    }

    private fun guardar(){
        if (modificacion){
            actualizar()
        }else {

        }
    }
}

