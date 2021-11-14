package tepic.tecnm.mx.eamtlmhm.ladm_u3_practica2_firebasefirestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main2.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity2 : AppCompatActivity() {
    var modificacion = false
    var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        txtDate.setText(SimpleDateFormat("yyyy-MM-dd").format(Date()).toString())
        txtTime.setText(SimpleDateFormat("HH:MM").format(Date()))
        borrarArchivo.isEnabled = false

        //Inicia la lectura de notas del dispositivo
        agregarContenidoInicial()
        //Agrega funcion guardar al boton guardar
        agregarNota.setOnClickListener {
            guardar()
        }//agregarNota
        if (modificacion){
            borrarArchivo.isEnabled = true
        }
        borrarArchivo.setOnClickListener {
            borrarNotas()
        }
    }

    private fun agregarContenidoInicial() {
        if(getIntent().hasExtra("titulo") && getIntent().hasExtra("texto")){//Revisa si se mando algo al activity
            var intent = getIntent()
            val titulo = intent.getStringExtra("titulo").toString()//Crashea si no existe un Extra
            val texto = intent.getStringExtra("texto").toString()
            id = intent.getStringExtra("id").toString().toInt()

            modificacion = true
            //Coloca el contenido mandado a los txt
            txtTitle.setText(titulo)
            txtContenido.setText(texto)
        }
    }

    private fun actualizar(){
        val nota = Nota(this)
        nota.id = id
        nota.titulo =  txtTitle.text.toString()
        nota.contenido = txtContenido.text.toString()
        nota.hora = txtTime.text.toString()
        nota.fecha =  txtDate.text.toString()

        if(nota.updateNota()){ Toast.makeText(this,"Se Actualizo Exitosamente",Toast.LENGTH_LONG).show()}
        else{ Toast.makeText(this,"Error al actualizar",Toast.LENGTH_LONG).show()}
    }//end actualizar

    private fun borrarNotas() {
        val nota = Nota(this)
        nota.id = id
        if(nota.deleteNota()){ Toast.makeText(this,"Se elimino exitosamente",Toast.LENGTH_LONG).show()}
        else{ Toast.makeText(this,"Error al eliminar",Toast.LENGTH_LONG).show()}
    }

    private fun guardar(){
        if (modificacion){
            actualizar()
        }//end if
        else {
            val nota = Nota(this)
            nota.titulo =  txtTitle.text.toString()
            nota.contenido = txtContenido.text.toString()
            nota.hora = txtTime.text.toString()
            nota.fecha =  txtDate.text.toString()
            if(nota.insertNota()){
                Toast.makeText(this,"Nota Agregada Exitosamente",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"Error al agregar Nota",Toast.LENGTH_LONG).show()
            }
        }//end else
    }//end guardar

}//end class

