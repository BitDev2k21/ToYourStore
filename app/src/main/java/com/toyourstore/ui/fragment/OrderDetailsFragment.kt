package com.toyourstore.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.toyourstore.R
import com.toyourstore.model.Order
import com.toyourstore.model.Orderitem
import de.footprinttech.wms.retrofit.WsParamUtils

class OrderDetailsFragment : BaseFragment() {

    private var rootView: View? = null
    private var order: Order? = null
    private lateinit var iv_waiting_acceptance : ImageView
    private lateinit var iv_order_accepted : ImageView
    private lateinit var iv_awaiting_Delivery : ImageView
    private lateinit var iv_order_delivered : ImageView
    private lateinit var txtProductName :TextView
    private lateinit var txtShopNo:TextView
    private lateinit var txtShopName:TextView
    private lateinit var txtArea:TextView
    private lateinit var txtSalesPeron:TextView
    private lateinit var txtNoOfUnit:TextView
    private lateinit var txtSalPrice:TextView
    private lateinit var txtPaid:TextView
    private lateinit var txtFinalPrice:TextView
    private lateinit var txtOrderNo:TextView
    private lateinit var imgOrder:ImageView
    val args:OrderDetailsFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        order = arguments?.getParcelable("order")
        order = args.orderitem
        Log.e("order", order.toString())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_order_details, container, false)

        iv_waiting_acceptance = rootView?.findViewById(R.id.iv_waiting_acceptance)!!
        iv_order_accepted = rootView?.findViewById(R.id.iv_order_accepted)!!
        iv_awaiting_Delivery = rootView?.findViewById(R.id.iv_awaiting_Delivery)!!
        iv_order_delivered = rootView?.findViewById(R.id.iv_order_delivered)!!
        txtProductName  = rootView?.findViewById(R.id.txtProductName)!!
        txtShopNo  = rootView?.findViewById(R.id.txtShopNo)!!
        txtShopName  = rootView?.findViewById(R.id.txtShopName)!!
        txtArea  = rootView?.findViewById(R.id.txtArea)!!
        txtSalesPeron  = rootView?.findViewById(R.id.txtSalesPeron)!!
        txtNoOfUnit  = rootView?.findViewById(R.id.txtNoOfUnit)!!
        txtSalPrice  = rootView?.findViewById(R.id.txtSalPrice)!!
        txtPaid  = rootView?.findViewById(R.id.txtPaid)!!
        txtFinalPrice = rootView?.findViewById(R.id.txtFinalPrice)!!
        txtOrderNo = rootView?.findViewById(R.id.txtOrderNo)!!
        imgOrder= rootView?.findViewById(R.id.imgOrder)!!
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle("Order Details")
        hideMenu()
        hideSearch()
        showBack()

        if(order!=null && order!!.status!=null){
            order!!.status?.let { updateOrderStatus(it) }
            refreshUi(order!!)
        }

    }

    fun refreshUi(orderValue:Order){
        var productName = ""
        var shopNum = ""
        var shopName = ""
        var salePersonName = ""
        var numOfUnit = ""
        var sellingPrice = ""
        var totalPrice = ""
        var orderId = ""
        var image = ""
        if(orderValue.orderitems!=null){
            val item : Orderitem = orderValue.orderitems!!.get(0)
            if(item!=null){
                productName =item.product!!.name.toString()
                numOfUnit =item.requestedQty.toString()
                sellingPrice =item.unitPrice.toString()
                totalPrice=item.totalPrice.toString()
                image = WsParamUtils.BaseUrl+item.product!!.image.toString()
            }
        }
        if(orderValue.shop!=null){
            shopNum = orderValue.shop!!.id.toString()
            shopName= orderValue.shop!!.name.toString()
        }
        if(orderValue.distributor!=null){
            salePersonName = orderValue.distributor!!.name.toString()
        }
        if(orderValue.orderNo!=null){
            orderId = orderValue.orderNo.toString()
        }

        txtProductName.setText(productName)
        txtShopNo.setText(shopNum)
        txtShopName.setText(shopName)
        txtArea.setText("")
        txtSalesPeron.setText(salePersonName)
        txtNoOfUnit.setText(numOfUnit)
        txtSalPrice.setText(sellingPrice)
        txtFinalPrice.setText(totalPrice)
        txtOrderNo.setText(orderId)

        Glide.with(requireContext())
            .load(image)
            .into(imgOrder)
            .onLoadFailed(ResourcesCompat.getDrawable(resources,R.drawable.image_parle_ji,null))

    }
    fun updateOrderStatus(status:String){
        when(status){
            "0" -> {
                Glide.with(requireContext()).load(ResourcesCompat.getDrawable(resources,R.drawable.icon_circle_blue,null)).into(iv_waiting_acceptance)
                txtPaid.setText(resources.getString(R.string.waiting_for_acceptance))
            }
            "1" -> {
                Glide.with(requireContext()).load(ResourcesCompat.getDrawable(resources,R.drawable.icon_circle_blue,null)).into(iv_order_accepted)
                txtPaid.setText(resources.getString(R.string.order_accepted))
            }
            "2" -> {
                Glide.with(requireContext()).load(ResourcesCompat.getDrawable(resources,R.drawable.icon_circle_blue,null)).into(iv_awaiting_Delivery)
                txtPaid.setText(resources.getString(R.string.awaiting_delievery))
            }
            else ->{
                Glide.with(requireContext()).load(ResourcesCompat.getDrawable(resources,R.drawable.icon_circle_blue,null)).into(iv_order_delivered)
                txtPaid.setText(resources.getString(R.string.delivered))
            }

        }
    }

}