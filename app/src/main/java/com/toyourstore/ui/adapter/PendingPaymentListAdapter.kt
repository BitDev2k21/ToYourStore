package com.toyourstore.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.toyourstore.R
import com.toyourstore.model.Order

class PendingPaymentListAdapter : RecyclerView.Adapter<PendingPaymentListAdapter.PlanningViewHolder>() {

    var searchList: MutableList<Order>? = null
    var myCallback: ((Int) -> Unit?)? = null


    fun setData(
            searchList: MutableList<Order>,
            myCallback: (pos: Int) -> Unit
     ) {
        this.searchList = searchList
        this.myCallback = myCallback
        notifyDataSetChanged()
    }

    class PlanningViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        val llItem = itemView.findViewById<LinearLayout>(R.id.llItem)
        val rlItem = itemView.findViewById<RelativeLayout>(R.id.rlItem)
        val tv_order_number = itemView.findViewById<TextView>(R.id.tv_order_number)
        val tv_shop_name = itemView.findViewById<TextView>(R.id.tv_shop_name)
        val tv_order_details = itemView.findViewById<TextView>(R.id.tv_order_details)
        val tv_amout = itemView.findViewById<TextView>(R.id.tv_amout)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanningViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_payment_list, parent, false)
        return PlanningViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PlanningViewHolder, position: Int) {
        val searchItem = searchList?.get(position)
        val shop = searchItem?.shop
        var orderPlaced  = "Order placed - "
        var orderDetails = "Order placed - 20th Apr\\nOrder Delivered - 25th Apr\\nEnd Date of Payment - 15th July"
        orderPlaced +=searchItem?.createdAt
        holder.tv_order_number.setText("Order No: "+searchItem!!.orderNo)
        holder.tv_amout.setText("Amount ₹"+searchItem!!.total.toString())
        holder.tv_order_details.setText(orderPlaced)
        if(shop!=null){
            holder.tv_shop_name.setText(shop!!.name.toString())
        }

        holder.rlItem.setOnClickListener {
            myCallback?.invoke(position)
        }

    }

    override fun getItemCount(): Int {
        if (searchList == null) {
            return 0
        } else {
            return searchList!!.size
        }
    }

}