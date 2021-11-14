package tepic.tecnm.mx.eamtlmhm.ladm_u3_practica2_firebasefirestore

import android.content.ContentValues
import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.collections.ArrayList


class Nota(p:Context) {
    var titulo = ""
    var contenido = ""
    var hora =  ""
    var fecha = ""
    var id : Int? = null
    var pointer = p
    //val baseRemota = FirebaseFirestore.getInstance()

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



}