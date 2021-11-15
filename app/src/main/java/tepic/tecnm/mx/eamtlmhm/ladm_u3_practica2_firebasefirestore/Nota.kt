package tepic.tecnm.mx.eamtlmhm.ladm_u3_practica2_firebasefirestore

import android.content.ContentValues
import android.content.Context
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.collections.ArrayList


class Nota(p:Context) {
    var titulo = ""
    var contenido = ""
    var hora =  ""
    var fecha = ""
    var id : Int? = null
    var idFirestore = ""
    var pointer = p


    //Inserta una nota
    fun insertNota(): Boolean{
        val tabla = BaseDatos(pointer,"BDNOTAS",null,1)
        val datos = ContentValues()
        datos.put("TITULO",titulo)
        datos.put("CONTENIDO",contenido)
        datos.put("HORA",hora)
        datos.put("FECHA",fecha)
        val resultado = tabla.writableDatabase.insert("NOTA",null,datos)
        val cursor = tabla.readableDatabase.rawQuery("SELECT COUNT(*) FROM NOTA",null)
        if(cursor.moveToFirst())
            id = cursor.getInt(0)
        if(resultado == -1L)
            return false
        return true
    }//end inserNota


    //Actualizar nota
    fun updateNota(): Boolean{
        val tabla = BaseDatos(pointer,"BDNOTAS",null,1).writableDatabase
        val datos = ContentValues()
        datos.put("TITULO",titulo)
        datos.put("CONTENIDO",contenido)
        datos.put("HORA",hora)
        datos.put("FECHA",fecha)
        val resultado = tabla.update("NOTA",datos,"ID =${id}",null)
        if(resultado == 0)
            return false
        return true
    }//end updateNota

    fun deleteNota():Boolean{
        val tabla = BaseDatos(pointer,"BDNOTAS",null,1).writableDatabase
        val resultado = tabla.delete("NOTA","ID=${id}",null)
        if (resultado == 0)
            return false
        return true
    }//deleteNota

    //Seleccionar todas las notas
    fun selectAll():ArrayList<Nota>{
        var notas = ArrayList<Nota>()
        val tabla = BaseDatos(pointer,"BDNOTAS",null,1).readableDatabase
        val cursor = tabla.rawQuery("SELECT * FROM NOTA",null)
        if(cursor.moveToFirst()){
            do{
                val nota = Nota(pointer)
                nota.id = cursor.getInt(0)
                nota.titulo = cursor.getString(1)
                nota.contenido = cursor.getString(2)
                nota.hora = cursor.getString(3)
                nota.fecha = cursor.getString(4)
                notas.add(nota)
            }while (cursor.moveToNext())
        }//end if

        return notas
    }//end selectAll

    fun consultNota(identificador:Int):Boolean{
        val tabla = BaseDatos(pointer,"BDNOTAS",null,1).readableDatabase
        val cursor = tabla.rawQuery("SELECT * FROM NOTA WHERE ID = ${identificador}",null)
        if(cursor.moveToFirst()){
            return true
        }//end if
        return false
    }//end consultNota

    fun delete(id: String): Boolean{
        if(BaseDatos(pointer,"BDNOTAS",null,1).writableDatabase.delete("NOTA","ID=?",arrayOf(id)) == 0)
            return false
        return true
    }//end delete

    fun update(id: String): Boolean{
        val datos = ContentValues()
        datos.put("TITULO",titulo)
        datos.put("CONTENIDO",contenido)
        datos.put("HORA",hora)
        datos.put("FECHA",fecha)
        val tabla = BaseDatos(pointer,"BDNOTAS",null,1).writableDatabase
        val result = tabla.update("NOTA",datos,"ID=?",arrayOf(id))
        if(result == 0)
            return false
        return true
    }//end update

    fun toString2():String{
        return "ID = ${id} titulo = ${titulo} contenido = ${contenido}"
    }
//-------------------------------------FIREBASE----------------------------------
    var baseRemota = FirebaseFirestore.getInstance()
    fun insertarFirebase() {
        var datosInsertar = hashMapOf(
            "TITULO" to titulo,
            "CONTENIDO" to contenido,
            "HORA" to hora,
            "FECHA" to fecha
        )
        baseRemota.collection("NOTAS")
            .document(id.toString())
            .set(datosInsertar)
    }

    fun eliminarFirebase(idElegido: String) {
        baseRemota.collection("NOTAS")
            .document(idElegido)
            .delete()
    }

    fun actualizaFirebase(idElegido: String) {
        baseRemota.collection("NOTAS")
            .document(idElegido)
            .update("TITULO",titulo)
        baseRemota.collection("NOTAS")
            .document(idElegido)
            .update("CONTENIDO",contenido)
        baseRemota.collection("NOTAS")
            .document(idElegido)
            .update("HORA",hora)
        baseRemota.collection("NOTAS")
            .document(idElegido)
            .update("FECHA",fecha)
    }

    fun selectAllFirestore(){
        var listaFirestore = ArrayList<Nota>()
        var listaLocal = selectAll()
        baseRemota.collection("NOTAS")
            .addSnapshotListener{querySnapshot,error->
                if(error!=null){
                    AlertDialog.Builder(pointer).setTitle("ATENCION")
                        .setMessage(error.message!!)
                        .setPositiveButton("OK"){s,i->}
                        .show()
                    return@addSnapshotListener
                }//if END
                listaFirestore.clear()
                for (document in querySnapshot!!){
                    val nota = Nota(pointer)
                    nota.id = document.id.toInt()
                    nota.titulo = document.getString("TITULO")!!
                    nota.contenido = document.getString("CONTENIDO")!!
                    nota.hora = document.getString("HORA")!!
                    nota.fecha = document.getString("FECHA")!!
                    listaFirestore.add(nota)
                }//for END
                //Elimina los documentos que no esten en local pero si en Firestore
                listaFirestore.forEach {
                    //Busca si id de firestore esta en LOCAL
                    if(!consultNota(it.id!!)){
                        it.eliminarFirebase(it.id.toString())
                    }
                }//forEach END
                //Agrega los documentos que esten en local pero no en firestore
                if (!listaFirestore.isEmpty()){
                    var listaInsert = selectIDMAYORA(listaFirestore[listaFirestore.size-1].id.toString())
                    listaInsert.forEach { it.insertarFirebase() }
                }else{
                    listaLocal.forEach{it.insertarFirebase()}
                }
                //actualiza los documentos que se modifican en local a firestore
                comparaUpdate(listaFirestore,listaLocal)
                Toast.makeText(pointer,"SE SINCRONIZO CON EXITO", Toast.LENGTH_LONG).show()
            }//addSnapshotListener END
    }//selectAllFirestore END

    private fun selectIDMAYORA(idFirestore: String):ArrayList<Nota> {
        var notas = ArrayList<Nota>()
        val tabla = BaseDatos(pointer,"BDNOTAS",null,1).readableDatabase
        val cursor = tabla.rawQuery("SELECT * FROM NOTA WHERE ID > ${idFirestore}",null)
        if(cursor.moveToFirst()){
            do{
                val nota = Nota(pointer)
                nota.id = cursor.getInt(0)
                nota.titulo = cursor.getString(1)
                nota.contenido = cursor.getString(2)
                nota.hora = cursor.getString(3)
                nota.fecha = cursor.getString(4)
                notas.add(nota)
            }while (cursor.moveToNext())
        }//end if
        return notas
    }

    fun comparaUpdate(listaFirebase: ArrayList<Nota>, listaLocal: ArrayList<Nota>) {
        //Compara las listas para ver que dato se modificara
        listaLocal.forEach {
            var notaLeida = it
            //Revisa las notas que se actualizaran
            (0..listaFirebase.size-1).forEach{
                //Compara si titulo o contenido son diferentes
                if(notaLeida.id == listaFirebase[it].id && (
                            notaLeida.titulo != listaFirebase[it].titulo ||
                                    notaLeida.contenido != listaFirebase[it].contenido
                            )){
                    //Se agrega la nota al arreglo cuando se encontro una modificacion
                    notaLeida.actualizaFirebase(notaLeida.id.toString())
                }
            }
        }
    }

}