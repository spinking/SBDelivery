package studio.eyesthetics.sbdelivery.ui.custom

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import studio.eyesthetics.sbdelivery.R

class CustomAutoCompleteAdapter(
    context: Context,
    resource: Int,
    objects: List<String>,
    private val clearClickListener: (String) -> Unit
) : ArrayAdapter<String>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val item = getItem(position) ?: ""

        val view = TextView.inflate(context, R.layout.item_suggestion, null)
        val textView = view.findViewById<TextView>(R.id.tv_suggestion)
        val clearButton = view.findViewById<ImageView>(R.id.btn_delete)

        clearButton.setOnClickListener {
            clearClickListener.invoke(item)
        }

        textView.text = item
        return view
    }
}