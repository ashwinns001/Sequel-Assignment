package com.sqt.sequelassignment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sqt.sequelassignment.R
import com.sqt.sequelassignment.databinding.ItemDashboardBinding
import com.sqt.sequelassignment.model.SearchResultItem
import com.sqt.sequelassignment.ui.activity.MainActivity
import com.squareup.picasso.Picasso

class SearchAdapter(private val mList: ArrayList<SearchResultItem>, val mcontext: Context) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    private lateinit var itemDashboardBinding: ItemDashboardBinding

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        itemDashboardBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_dashboard, parent, false
        )

        return ViewHolder(itemDashboardBinding.root)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        itemDashboardBinding.response = mList[position]
        Picasso.get().load(
            mList[position].Poster
        ).into(itemDashboardBinding.imagePoster)
        holder.itemView.setOnClickListener {
            (mcontext as MainActivity).setClickItem(mList[position])
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to  text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView)
}
