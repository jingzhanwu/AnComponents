package com.mti.component.master.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.*

/**
 * @anthor created by jzw
 * @date 2020/6/18
 * @change
 * @describe 警情实体
 **/
@Parcelize
class ConditionEntry(var title: String? = "", var checked: Boolean? = false) : Parcelable {
    @Throws(IOException::class, OptionalDataException::class, ClassNotFoundException::class)
    public fun deepClone(): Any? {
        // 将对象写到流里
        val bo = ByteArrayOutputStream()
        val oo = ObjectOutputStream(bo)
        oo.writeObject(this)
        // 从流里读出来
        val bi = ByteArrayInputStream(bo.toByteArray())
        val oi = ObjectInputStream(bi)
        oo.close()
        bo.close()
        val newObj = oi.readObject()
        oi.close()
        bi.close()
        return newObj
    }
}