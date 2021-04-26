package studio.eyesthetics.sbdelivery.ui.adapterdelegates.managers

import android.widget.CheckedTextView

class CategoryClickManager {
    private val buttons: MutableList<CheckedTextView> = mutableListOf()

    fun initButton(button: CheckedTextView) {
        button.isChecked = true
        buttons.clear()
        buttons.add(button)
    }

    fun checkButton(button: CheckedTextView) {
        button.isChecked = true
        buttons.apply {
            this[0].isChecked = false
            clear()
            add(button)
        }
    }
}