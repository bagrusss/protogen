package ru.bagrusss.generator.model

import ru.bagrusss.generator.fields.Field
import java.util.*


abstract class ModelBuilder {

    internal var protoClassFullName = ""

    val fieldsList: LinkedList<Field<*>> = LinkedList()

    internal var isMap = false

    fun isMap(isMap: Boolean) = apply {
        this.isMap = isMap
    }

    fun addField(field: Field<*>) = apply {
        fieldsList.add(field)
    }

    fun protoClassFullName(protoClassFullName: String) = apply {
        this.protoClassFullName = protoClassFullName
    }

    abstract fun build(): Model

}