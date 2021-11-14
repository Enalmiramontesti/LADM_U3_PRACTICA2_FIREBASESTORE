package tepic.tecnm.mx.eamtlmhm.ladm_u3_practica2_firebasefirestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
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
        sincronizar.setOnClickListener {
            if (!escalalista.isEmpty()){
                //Devuelve la lista que encontro en la nube de los datos
                var listaFirebase = Nota(this).selectAllFirestore()
                //Devuelve la lista de datos que se insertaran
                var listaFirebaseAInsertar = comparaInsert(listaFirebase,escalalista)
                //Devuelve la lista de datos que se modificaran
                var listaFirebaseAModificar = comparaUpdate(listaFirebase,escalalista)
                //Devuelve la lista de datos que se borraran
                var listaFirebaseABorrar = comparaDelete(listaFirebase,escalalista)

                //Insertamos firebase
                listaFirebaseAInsertar.forEach { it.insertarFirebase() }
                //Modificamos firebase
                listaFirebaseAModificar.forEach { it.update(it.id.toString()) }
                //Eliminamos firebase
                listaFirebaseABorrar.forEach { it.delete(it.id.toString()) }
                Toast.makeText(this,"SE SINCRONIZO CON EXITO",Toast.LENGTH_LONG)
            }else{
                Toast.makeText(this,"Agrege una nota para sincronizar",Toast.LENGTH_LONG)
            }
        }
        timer.start()
    }//en onCreate

    fun comparaDelete(listaFirebase: ArrayList<Nota>, escalalista: ArrayList<Nota>): ArrayList<Nota> {
        var listaDelete = ArrayList<Nota>()
        //Compara las listas para ver que dato se eliminara
        listaFirebase.forEach {
            var notaLeida = it
            //Revisa las notas que se eliminaran
            (0..escalalista.size - 1).forEach {
                var bandera = false
                //Compara si id de firebase existe en LOCAL
                if (notaLeida.id == escalalista[it].id) {
                    //Coloca bandera true si encontro el id en el arreglo LOCAL
                    bandera = true
                }
                if (!bandera) {
                    //Se agrega la nota al arreglo cuando no se encontro en local pero si en firebase
                    listaDelete.add(notaLeida)
                }
            }
        }
        return listaDelete
    }

    fun comparaUpdate(listaFirebase: ArrayList<Nota>, escalalista: ArrayList<Nota>): ArrayList<Nota> {
        var listaUpdate = ArrayList<Nota>()
        //Compara las listas para ver que dato se modificara
        listaFirebase.forEach {
            var notaLeida = it
            //Revisa las notas que se actualizaran
            (0..escalalista.size-1).forEach{
                //Compara si titulo o contenido son diferentes
                if(notaLeida.titulo != escalalista[it].titulo || notaLeida.contenido != escalalista[it].contenido){
                    //Se agrega la nota al arreglo cuando se encontro una modificacion
                    listaUpdate.add(notaLeida)
                }
            }
        }
        return listaUpdate
    }

    fun comparaInsert(listaFirebase: ArrayList<Nota>, escalalista: ArrayList<Nota>): ArrayList<Nota> {
        var listaInsert = ArrayList<Nota>()
        //Compara las listas para ver que dato se insertara
        escalalista.forEach {
            var notaLeida = it
            //Revisa las notas que se insertaran
            (0..listaFirebase.size-1).forEach{
                var bandera = false
                //Compara si id de LOCAL existe en Firebase
                if(notaLeida.id == listaFirebase[it].id){
                    //Coloca bandera true si encontro el id en el arreglo Firebase
                    bandera = true
                }
                if (!bandera){
                    //Se agrega la nota al arreglo cuando no se encontro en Firebase pero si en local
                    listaInsert.add(notaLeida)
                }
            }
        }
        return listaInsert
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
        var notas = Nota(this).selectAll()
        if (notas != null){
            notas.forEach {
                escalalista.add(it)
            }//forEach
        }//end if
    }//iniciarNotas

}//end class

