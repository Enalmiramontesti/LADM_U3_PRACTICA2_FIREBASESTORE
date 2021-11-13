package tepic.tecnm.mx.eamtlmhm.ladm_u3_practica2_firebasefirestore

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseDatos(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) :
    SQLiteOpenHelper(context,name,factory,version) {


}