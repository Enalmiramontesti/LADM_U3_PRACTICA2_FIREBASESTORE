package tepic.tecnm.mx.eamtlmhm.ladm_u3_practica2_firebasefirestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class MainActivity : AppCompatActivity() {
    private var escalalista = ArrayList<Nota>()
    private lateinit var rvEscalar: RecyclerView
    private lateinit var adaptador: EscalonarRecicler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        iniciarComponentes()

        //Boton + iniciar nueva ventana emergente de notas
        findViewById<Button>(R.id.insertar).setOnClickListener {
            val view = Intent(this, MainActivity2::class.java)
            startActivity(view)
        }///insertar




    }//en onCreate

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
        var notas = Nota(this).selectAll()
        if (!notas.isEmpty()){
            notas.forEach {
                escalalista.add(it)
            }//forEach
        }//end if
    }//iniciarNotas

}//end class
