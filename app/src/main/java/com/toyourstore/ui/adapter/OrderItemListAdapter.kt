package com.toyourstore.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.toyourstore.R
import com.toyourstore.model.Order
import com.toyourstore.model.Orderitem
import com.toyourstore.model.PendingOrderResponse

class OrderItemListAdapter : RecyclerView.Adapter<OrderItemListAdapter.OrderItemHolder>() {

    var listOfOrderItem: List<Orderitem>? = null

    fun setData(
        listOfOrderItem: List<Orderitem>
    ) {
        this.listOfOrderItem = listOfOrderItem
        notifyDataSetChanged()
    }

    class OrderItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtProductName = itemView.findViewById<TextView>(R.id.txtProductName)
        val txtPrice = itemView.findViewById<TextView>(R.id.txtPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_product_item, parent, false)
        return OrderItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: OrderItemHolder, position: Int) {
        val orderItem = listOfOrderItem?.get(position)
        val product = orderItem?.product
        if(product!=null){
            holder.txtProductName.text = product.name.toString()
            holder.txtPrice.text = product.price.toString()
        }
    }

    override fun getItemCount(): Int {
        if (listOfOrderItem == null) {
            return 0
        } else {
            return listOfOrderItem!!.size
        }
    }

}