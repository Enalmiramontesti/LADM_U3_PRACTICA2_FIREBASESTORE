package tepic.tecnm.mx.eamtlmhm.ladm_u3_practica2_firebasefirestore

import android.os.Parcel
import android.os.Parcelable

data class ModeloRecilcer(val titulo: String,val texto: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(titulo)
        parcel.writeString(texto)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ModeloRecilcer> {
        override fun createFromParcel(parcel: Parcel): ModeloRecilcer {
            return ModeloRecilcer(parcel)
        }

        override fun newArray(size: Int): Array<ModeloRecilcer?> {
            return arrayOfNulls(size)
        }
    }
}
