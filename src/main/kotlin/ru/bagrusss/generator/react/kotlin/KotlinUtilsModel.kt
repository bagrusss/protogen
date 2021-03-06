package ru.bagrusss.generator.react.kotlin

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KotlinFile
import ru.bagrusss.generator.react.UtilsModelBuilder
import ru.bagrusss.generator.react.UtilsModel

class KotlinUtilsModel private constructor(builder: Builder): UtilsModel<FunSpec>(builder) {

    private val body: String

    init {
        val fileBuilder = KotlinFile.builder(builder.packageName, builder.fileName)

        builder.functions.forEach {
            fileBuilder.addFun(it.getSpec())
        }
        val file = fileBuilder.build()
        body = file.toJavaFileObject()
                   .getCharContent(true)
                   .toString()
    }

    override fun getBody() = body


    class Builder: UtilsModelBuilder<FunSpec>() {

        override fun build() = KotlinUtilsModel(this)
    }

}