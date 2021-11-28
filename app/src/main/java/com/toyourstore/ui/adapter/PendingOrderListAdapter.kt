package com.toyourstore.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.toyourstore.R
import com.toyourstore.model.Order
import com.toyourstore.model.PendingOrderResponse

class PendingOrderListAdapter : RecyclerView.Adapter<PendingOrderListAdapter.PlanningViewHolder>() {

    var searchList: List<Order>? = null
    var myCallback: ((Int) -> Unit?)? = null
    fun setData(
        searchList: List<Order>,
        myCallback: (pos: Int) -> Unit
    ) {
        this.searchList = searchList
        this.myCallback = myCallback
        notifyDataSetChanged()
    }

    class PlanningViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        val llItem = itemView.findViewById<LinearLayout>(R.id.llItem)
        val rlItem = itemView.findViewById<RelativeLayout>(R.id.rlItem)
        val rvOrderItem = itemView.findViewById<RecyclerView>(R.id.rvOrderItem)
        val order_number = itemView.findViewById<TextView>(R.id.order_number)
        val tv_total_amout = itemView.findViewById<TextView>(R.id.tv_total_amout)
        val tv_store_name = itemView.findViewById<TextView>(R.id.tv_store_name)
        val tv_created_date = itemView.findViewById<TextView>(R.id.tv_created_date)
        val tv_order_waiting = itemView.findViewById<TextView>(R.id.tv_order_waiting)
        val tv_order_accepted = itemView.findViewById<TextView>(R.id.tv_order_accepted)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanningViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_order_list, parent, false)
        return PlanningViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PlanningViewHolder, position: Int) {
        val searchItem = searchList?.get(position)
        val shop = searchItem?.shop
        val order_status = searchItem?.status

//        holder.txtSearchItem.text = searchItem
//        holder.imgSelect.isSelected = true
        holder.order_number.setText("Order No: "+searchItem!!.orderNo)
        holder.tv_total_amout.setText(searchItem!!.total.toString())
        holder.tv_created_date.setText(searchItem!!.createdAt.toString())
        if(shop!=null){
            holder.tv_store_name.setText(shop!!.name.toString())
        }
        val orderListAdapter = OrderItemListAdapter()
        holder.rvOrderItem.adapter = orderListAdapter
        orderListAdapter.setData(searchItem?.orderitems!!)
        holder.rlItem.setOnClickListener {
            myCallback?.invoke(position)
        }

        if(order_status!=null &&  order_status.equals("1")){
            holder.tv_order_waiting.visibility = View.GONE
            holder.tv_order_accepted.visibility = View.VISIBLE
        }else{
            holder.tv_order_waiting.visibility = View.VISIBLE
            holder.tv_order_accepted.visibility = View.GONE
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