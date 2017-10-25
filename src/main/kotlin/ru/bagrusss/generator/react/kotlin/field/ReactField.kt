package ru.bagrusss.generator.react.kotlin.field

import ru.bagrusss.generator.Utils
import ru.bagrusss.generator.fields.Field


abstract class ReactField<T: ReactField<T>>(builder: ReactFieldBuilder<T>): Field<T>(builder) {

    private val checkOptionalToMap = "if (${Utils.getHas(fieldName)}())\n\t\t"

    abstract fun getReactType(): String
    open fun needSkip() = false

    protected fun putToArrayMethodName() = "push" + getReactType()
    protected fun getFromArrayMethodName() = "get" + getReactType()

    protected open fun fromMapInit() = fieldName + " = map.get" + getReactType() + "(\"" + fieldName + "\")"
    protected open fun toMapInit() = "put" + getReactType() + "(\"" + fieldName + "\", " + fieldName + ")"

    protected open fun toMapConverter() = "it"

    protected open fun getFromMapInit() = "$protoFullTypeName.newBuilder().${Utils.fromMapMethod}("

    /*в карту
    if (colorsList.isNotEmpty()) {
        val colorsArray = Arguments.createArray()
        colorsList.forEach { colorsArray.pushMap(it.toWritableMap()) }
    }*/

    /*из карты
    val colorsElements = map.getArray("colors")
    for (i in 0 until colorsElements.size()) {
        val element = ru.rocketbank.protomodel.ProtoApi.Color.newBuilder().fromReadableMap(colorsElements.getMap(i))
        addColors(element)
    }*/

    fun toMapInitializer(): String {
        return when {
            repeated -> {
                val array = Utils.fieldArray(fieldName)
                StringBuilder().append(Utils.checkListSize(fieldName))
                               .append(" {\n\t\t")
                               .append("val ")
                               .append(array)
                               .append(" = ")
                               .append(Utils.createArray)
                               .append("\n\t\t")
                               .append(Utils.getList(fieldName))
                               .append(".forEach { ")
                               .append(array)
                               .append('.')
                               .append(putToArrayMethodName())
                               .append('(')
                               .append(toMapConverter())
                               .append(") }\n\t}")
                               .toString()
            }
            optional -> checkOptionalToMap + toMapInit()
            else -> toMapInit()
        }
    }

    fun fromMapInitializer(): String {
        return if (repeated) {
            val array = Utils.fieldArray(fieldName)
            val item = "element"
            val builder = StringBuilder().append("\n\tval ")
                           .append(array)
                           .append(" = map.getArray(\"")
                           .append(fieldName)
                           .append("\")\n")
                           .append("\tfor (i in 0 until ")
                           .append(array)
                           .append(".size()) {\n")
                           .append("\t\tval ")
                           .append(item)
                           .append(" = ")

            if (!isPrimitive()) {
                builder.append(getFromMapInit())
            }

            builder.append(array)
                   .append('.')
                   .append(getFromArrayMethodName())
                   .append("(i)")

            if (!isPrimitive())
                builder.append(")")

            builder.append("\n\t\t")
                   .append(Utils.addToArray(fieldName))
                   .append('(')
                   .append(item)
                   .append(")\n\t}\n")

            builder.toString()
        } else fromMapInit()
    }

}