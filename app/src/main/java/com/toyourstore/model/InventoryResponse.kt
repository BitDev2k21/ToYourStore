package com.toyourstore.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class InventoryResponse {
    @SerializedName("inventory")
    @Expose
    var inventory: List<Inventory?>? = null

    @SerializedName("image_path")
    @Expose
    var image_path: String? = ""


    class Inventory {
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("distributor_id")
        @Expose
        var distributorId: Int? = null

        @SerializedName("product_id")
        @Expose
        var productId: Int? = null

        @SerializedName("dist_price")
        @Expose
        var distPrice: Int? = null

        @SerializedName("inventory")
        @Expose
        var inventory: Int? = null

        @SerializedName("created_at")
        @Expose
        var createdAt: Any? = null

        @SerializedName("updated_at")
        @Expose
        var updatedAt: Any? = null

        @SerializedName("products")
        @Expose
        var products: Products? = null


        class Products {
            @SerializedName("id")
            @Expose
            var id: Int? = null

            @SerializedName("category_id")
            @Expose
            var categoryId: Int? = null

            @SerializedName("businessline_id")
            @Expose
            var businesslineId: Int? = null

            @SerializedName("type_id")
            @Expose
            var typeId: Int? = null

            @SerializedName("company_id")
            @Expose
            var companyId: Int? = null

            @SerializedName("code")
            @Expose
            var code: String? = null

            @SerializedName("min_qty")
            @Expose
            var minQty: String? = null

            @SerializedName("name")
            @Expose
            var name: String? = null

            @SerializedName("weight")
            @Expose
            var weight: Int? = null

            @SerializedName("exp_validity")
            @Expose
            var expValidity: String? = null

            @SerializedName("exp_validity_unit")
            @Expose
            var expValidityUnit: String? = null

            @SerializedName("price")
            @Expose
            var price: Int? = null

            @SerializedName("image")
            @Expose
            var image: Any? = null

            @SerializedName("status")
            @Expose
            var status: String? = null

            @SerializedName("created_at")
            @Expose
            var createdAt: Any? = null

            @SerializedName("updated_at")
            @Expose
            var updatedAt: Any? = null

            @SerializedName("company")
            @Expose
            var company: Company? = null

            class Company {
                @SerializedName("id")
                @Expose
                var id: Int? = null

                @SerializedName("location_id")
                @Expose
                var locationId: Int? = null

                @SerializedName("code")
                @Expose
                var code: String? = null

                @SerializedName("name")
                @Expose
                var name: String? = null

                @SerializedName("address_1")
                @Expose
                var address1: String? = null

                @SerializedName("address_2")
                @Expose
                var address2: String? = null

                @SerializedName("image")
                @Expose
                var image: Any? = null

                @SerializedName("status")
                @Expose
                var status: String? = null

                @SerializedName("created_at")
                @Expose
                var createdAt: Any? = null

                @SerializedName("updated_at")
                @Expose
                var updatedAt: Any? = null
            }


        }
    }

}