package com.sqt.sequelassignment.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sqt.sequelassignment.R
import com.sqt.sequelassignment.databinding.ItemDashboardBinding
import com.sqt.sequelassignment.databinding.ItemGenereBinding


class GenereAdapter(private val mList: List<String>) : RecyclerView.Adapter<GenereAdapter.ViewHolder>() {
    private lateinit var itemGenereBinding: ItemGenereBinding

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        itemGenereBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_genere, parent, false)


        return ViewHolder(itemGenereBinding.root)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
itemGenereBinding.response=mList[position]

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to  text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
       val textView: TextView = itemView.findViewById(R.id.text_genere)
    }
}
