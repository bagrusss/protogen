package bagrusss.generator.kotlin.fields

import bagrusss.generator.fields.FieldBuilder
import com.squareup.kotlinpoet.PropertySpec

/**
 * Created by bagrusss on 12.07.17
 */
class MessageField private constructor(builder: Builder): KotlinField<MessageField>(builder) {

    class Builder: FieldBuilder<MessageField>() {
        override fun build() = MessageField(this)
    }

    override fun getPropSpec(): PropertySpec {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getFieldType(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}