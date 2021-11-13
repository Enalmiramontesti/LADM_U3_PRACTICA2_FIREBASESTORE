package tepic.tecnm.mx.eamtlmhm.ladm_u3_practica2_firebasefirestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {
    private var escalalista = ArrayList<ModeloRecilcer>()
    private lateinit var rvEscalar: RecyclerView
    private lateinit var adaptador: EscalonarRecicler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        iniciarComponentes()
        //Boton + iniciar nueva ventana emergente de notas
        findViewById<Button>(R.id.fab).setOnClickListener {
            val view = Intent(this, MainActivity2::class.java)
            startActivity(view)
        }
    }

    private fun iniciarComponentes() {
        escalalista.clear()
        //Apunto a el layout donde se agregan las notas
        rvEscalar = findViewById(R.id.recicler)
        //Asigna un layout vertigal con 2 columnas al layout de las notas
        rvEscalar.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        //Crea adaptador para escalonar notas
        adaptador = EscalonarRecicler(this,escalalista)
        //Asigna el adaptador a el layout de las notas
        rvEscalar.adapter = adaptador
        //Carga todas las notas
        iniciaNotas()
    }

    private fun iniciaNotas() {
        //Leer de base de datos
        //agregar a escalalista
        try {
            val archivo = BufferedReader(InputStreamReader(openFileInput("archivo_10_.txt")))// Lee archivo separado por comas
            archivo.forEachLine {
                val listado = it.split(";") // crea arreglo linea a linea separado por comas
                //val Nota1:Nota = Nota() // Se crea objeto nota
                var nota1 = ModeloRecilcer(listado[0],listado[1])// Se agrega nota al adaptador de recicler
                escalalista.add(nota1) // Se agrega Nota al Recicler
            }
        }catch (io : IOException){
        }
    }
}
