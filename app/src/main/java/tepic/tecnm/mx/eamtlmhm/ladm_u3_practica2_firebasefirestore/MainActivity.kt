package tepic.tecnm.mx.eamtlmhm.ladm_u3_practica2_firebasefirestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var escalalista = ArrayList<Nota>()
    private lateinit var rvEscalar: RecyclerView
    private lateinit var adaptador: EscalonarRecicler

    val timer = object  : CountDownTimer(20000,200){
        override fun onTick(p0: Long) {
            iniciarComponentes()
        }

        override fun onFinish() {
            start()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        iniciarComponentes()

        //Boton + iniciar nueva ventana emergente de notas
        insertar.setOnClickListener {
            val view = Intent(this, MainActivity2::class.java)
            startActivity(view)
        }///insertar
        timer.start()
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
        if (notas != null){
            notas.forEach {
                escalalista.add(it)
            }//forEach
        }//end if
    }//iniciarNotas

}//end class

