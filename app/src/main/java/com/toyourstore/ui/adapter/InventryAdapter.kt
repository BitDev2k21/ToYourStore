package com.toyourstore.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.toyourstore.R
import com.toyourstore.model.InventoryResponse

class InventryAdapter(context:Context) : RecyclerView.Adapter<InventryAdapter.PlanningViewHolder>() {

    var inventryList: MutableList<InventoryResponse.Inventory>? = null
    var myCallback: ((Int) -> Unit?)? = null
    val mContext = context
    var imagePath = ""
    fun setData(
        inventryList: MutableList<InventoryResponse.Inventory>,
        imagePath:String,
        myCallback: (pos: Int) -> Unit
    ) {
        this.inventryList = inventryList
        this.myCallback = myCallback
        this.imagePath = imagePath
        notifyDataSetChanged()
    }

    class PlanningViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rlItem = itemView.findViewById<RelativeLayout>(R.id.rlItem)
        val imgProfile = itemView.findViewById<ImageView>(R.id.imgProfile)
        val txtProductType = itemView.findViewById<TextView>(R.id.txtProductType)
        val txtCategory = itemView.findViewById<TextView>(R.id.txtCategory)
        val txtCompany = itemView.findViewById<TextView>(R.id.txtCompany)
        val txtProductName = itemView.findViewById<TextView>(R.id.txtProductName)
        val txtMrpUnit = itemView.findViewById<TextView>(R.id.txtMrpUnit)
        val tv_shelf_life = itemView.findViewById<TextView>(R.id.tv_shelf_life)
        val current_stock = itemView.findViewById<TextView>(R.id.current_stock)
        val tv_min_qty = itemView.findViewById<TextView>(R.id.tv_min_qty)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanningViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_invertry, parent, false)
        return PlanningViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PlanningViewHolder, position: Int) {
        val inventory = inventryList?.get(position)
        val product = inventory?.products
        val price = inventory?.distPrice
        val stocks = inventory?.inventory
        val expValidity = product?.expValidity
        val expValidityUnit = product?.expValidityUnit
        val minQty = product?.minQty
        val imageName = product?.image
        val imageUrl = imagePath+imageName
        holder.txtProductName.text = product?.name
        holder.txtProductType.text = product?.name
        holder.txtCategory.text = inventory?.products?.categoryId.toString()
        holder.txtCompany.text = inventory?.products?.company?.name

        holder.txtMrpUnit.text = price.toString()
        holder.tv_shelf_life.text = expValidity+" "+expValidityUnit
        holder.current_stock.text = stocks.toString()
        holder.tv_min_qty.text = minQty

        Glide.with(mContext).load(imageUrl).placeholder(R.drawable.ic_no_data).into(holder.imgProfile);
        holder.rlItem.setOnClickListener {
            myCallback?.invoke(position)
        }


    }

    override fun getItemCount(): Int {
        if (inventryList == null) {
            return 0
        } else {
            return inventryList!!.size
        }
    }

}