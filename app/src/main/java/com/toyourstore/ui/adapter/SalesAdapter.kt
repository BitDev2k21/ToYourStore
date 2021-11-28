package com.toyourstore.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.toyourstore.R
import com.toyourstore.model.SalesTeamResponse

class SalesAdapter : RecyclerView.Adapter<SalesAdapter.SalesHolder>() {

    var salesList: MutableList<SalesTeamResponse.SalesTeam>? = null
    var myCallback: ((Int) -> Unit?)? = null
    fun setData(
        salesList: MutableList<SalesTeamResponse.SalesTeam>,
        myCallback: (pos: Int) -> Unit
    ) {
        this.salesList = salesList
        this.myCallback = myCallback
        notifyDataSetChanged()
    }

    class SalesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val llItemSale = itemView.findViewById<LinearLayout>(R.id.llItemSale)
        val txtName = itemView.findViewById<TextView>(R.id.txtName)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalesHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_sales, parent, false)
        return SalesHolder(itemView)
    }

    override fun onBindViewHolder(holder: SalesHolder, position: Int) {
        val sales = salesList?.get(position)
        holder.txtName.text = sales?.name!!
        holder.llItemSale.setOnClickListener {
//            myCallback?.invoke(position)
        }

    }

    override fun getItemCount(): Int {
        if (salesList == null) {
            return 0
        } else {
            return salesList!!.size
        }
    }

}