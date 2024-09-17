package br.com.intelipec.firebase.dropdown

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.sntsb.mypokedex.data.model.dto.TipoDTO
import com.sntsb.mypokedex.utils.UiUtils
import java.util.Locale

class DropdownTipoList(
    private val mContext: Context,
    private var values: ArrayList<TipoDTO>,
    private var textViewResourceId: Int,
) : ArrayAdapter<TipoDTO>(
    mContext, textViewResourceId, values
) {

    var filtered = ArrayList<TipoDTO>()
    var original = ArrayList<TipoDTO>()

    init {
        this.filtered = values
        this.original = values
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val label = super.getView(position, convertView, parent) as TextView

        label.text = UiUtils(mContext).getTipoLabel(filtered[position].descricao)

        return label
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val label = super.getDropDownView(position, convertView, parent) as TextView

        label.text = UiUtils(mContext).getTipoLabel(filtered[position].descricao)
        return label
    }

    override fun add(str: TipoDTO?) {

    }

    override fun getCount() = filtered.size

    override fun getItem(position: Int) = filtered[position]

    override fun getFilter() = filter

    private var filter: Filter = object : Filter() {

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val results = FilterResults()

            val query =
                if (constraint != null && constraint.isNotEmpty()) autocomplete(constraint.toString())
                else original

            results.values = query
            results.count = query.size

            return results
        }

        private fun autocomplete(input: String): ArrayList<TipoDTO> {
            val results = arrayListOf<TipoDTO>()

            for (value in values) {
                if (value.descricao.lowercase(Locale.getDefault())
                        .contains(input.lowercase(Locale.getDefault()))
                ) results.add(
                    value
                )
            }

            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults) {
            filtered = results.values as ArrayList<TipoDTO>
            notifyDataSetInvalidated()
        }

        override fun convertResultToString(result: Any) =
            UiUtils(mContext).getTipoLabel((result as TipoDTO).descricao)
    }
}