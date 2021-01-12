package studio.eyesthetics.sbdelivery.ui.base

import android.os.Bundle
import studio.eyesthetics.sbdelivery.ui.delegates.RenderProp
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
import kotlin.reflect.KProperty

abstract class Binding {
    val delegates = mutableMapOf<String, RenderProp<out Any>>()
    var isInflated = false

    open val afterInflated: (() -> Unit)? = null
    fun onFinishInflate() {
        if(!isInflated) {
            afterInflated?.invoke()
            isInflated = true
        }
    }

    fun rebind() {
        delegates.forEach { it.value.bind() }
    }

    abstract fun bind(data : IViewModelState)

    open fun saveUi(outState: Bundle) {}

    open fun restoreUi(savedState: Bundle?) {}

    @Suppress("UNCHECKED_CAST")
    fun <A, B, C, D> dependsOn(
        vararg fields: KProperty<*>,
        onChange: (A, B, C, D) -> Unit
    ) {
        check(fields.size == 4) {"Names size must be 4, current ${fields.size}"}
        val names = fields.map { it.name }

        names.forEach {
            delegates[it]?.addListener {
                onChange(
                    delegates[names[0]]?.value as A,
                    delegates[names[1]]?.value as B,
                    delegates[names[2]]?.value as C,
                    delegates[names[3]]?.value as D
                )
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <A, B> dependsOn(
        vararg fields: KProperty<*>,
        onChange: (A, B) -> Unit
    ) {
        check(fields.size == 2) {"Names size must be 2, current ${fields.size}"}
        val names = fields.map { it.name }

        names.forEach {
            delegates[it]?.addListener {
                onChange(
                    delegates[names[0]]?.value as A,
                    delegates[names[1]]?.value as B
                )
            }
        }
    }
}