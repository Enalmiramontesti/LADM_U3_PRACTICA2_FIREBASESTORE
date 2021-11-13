package tepic.tecnm.mx.eamtlmhm.ladm_u3_practica2_firebasefirestore

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EscalonarRecicler(val context: Context,var lista: List<Nota>):
    RecyclerView.Adapter<EscalonarRecicler.EscalarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EscalarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_linear, parent, false)
        return EscalarViewHolder(view)
    }

    override fun onBindViewHolder(holder: EscalarViewHolder, position: Int) {
        val escalarlista = lista[position]


        holder.txTitulo.setText(escalarlista.titulo)
        holder.txEscalar.setText(escalarlista.contenido)

        holder.itemView.setOnClickListener{
            //mandar lista a base de datos

            val view1 = Intent(context, MainActivity2::class.java)
            //Se busca el ID del objeto seleccionado
            view1.putExtra("titulo",escalarlista.titulo)
            view1.putExtra("texto",escalarlista.contenido)
            context.startActivity(view1)
        }
    }

    override fun getItemCount(): Int {
        return  lista.size
    }


    class EscalarViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView){
        val txTitulo = itemView.findViewById<TextView>(R.id.titulo)
        val txEscalar = itemView.findViewById<TextView>(R.id.texto1)
    }

}
