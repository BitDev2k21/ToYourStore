package com.toyourstore.api

import com.toyourstore.model.*
import com.toyourstore.model.businessline.BusinessLineModelResponse
import com.toyourstore.model.distributor.DistributiorUpdateResponse
import com.toyourstore.model.distributor.DistributorResponse
import com.toyourstore.model.distributor.Locations
import de.footprinttech.wms.retrofit.RetrofitProvider
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @Headers(
        value = ["Accept: application/json",
            "Content-type:application/x-www-form-urlencoded"]
    )
    @FormUrlEncoded
    @POST("user-login")
    suspend fun login(@FieldMap request: Map<String, String>): Response<LoginResponse>

    @FormUrlEncoded
    @POST("get-user")
    suspend fun getUser(@FieldMap request: Map<String, String>): Response<GetUserResponse>

    @Headers(
        value = ["Accept: application/json",
            "Content-type:application/x-www-form-urlencoded"]
    )
    @FormUrlEncoded
    @POST("distributor-pending-orders")
    suspend fun orderList(@FieldMap request: Map<String, String>): Response<PendingOrderResponse>


    @Headers(
        value = ["Accept: application/json",
            "Content-type:application/x-www-form-urlencoded"]
    )
    @FormUrlEncoded
    @POST("distributor-pending-payments")
    suspend fun paymentList(@FieldMap request: Map<String, String>): Response<PendingOrderResponse>



    @Headers(
        value = ["Accept: application/json",
            "Content-type:application/x-www-form-urlencoded"]
    )
    @FormUrlEncoded
    @POST("distributor-sales-team")
    suspend fun apiSales(@FieldMap request: Map<String, String>): Response<SalesTeamResponse>


    @Headers(
        value = ["Accept: application/json",
            "Content-type:application/x-www-form-urlencoded"]
    )
    @FormUrlEncoded
    @POST("distributor-operator-team")
    suspend fun apiOperator(@FieldMap request: Map<String, String>): Response<OperatorsResponse>

    @Headers(
        value = ["Accept: application/json",
            "Content-type:application/x-www-form-urlencoded"]
    )
    @FormUrlEncoded
    @POST("distributor-inventory")
    suspend fun apiInventry(@FieldMap request: Map<String, String>): Response<InventoryResponse>


    @Headers(
        value = ["Accept: application/json",
            "Content-type:application/x-www-form-urlencoded"]
    )
    @FormUrlEncoded
    @POST("dashboard")
    suspend fun apiForDashBoard(@FieldMap request: Map<String, String>): Response<DashBoardResponse>


    @Headers(
        value = ["Accept: application/json",
            "Content-type:application/x-www-form-urlencoded"]
    )
    @FormUrlEncoded
    @POST("user-registration")
    suspend fun register(@FieldMap request: Map<String, String>): Response<LoginResponse>



    @Headers(
        value = ["Accept: application/json",
            "Content-type:application/x-www-form-urlencoded"]
    )
    @FormUrlEncoded
    @POST("get-location-list")
    suspend fun getLocationListApi(@FieldMap request: Map<String, String>): Response<GetLocation>


    @Headers(
        value = ["Accept: application/json",
            "Content-type:application/x-www-form-urlencoded"]
    )
    @FormUrlEncoded
    @POST("get-state-list")
    suspend fun stateList(@FieldMap request: Map<String, String>): Response<StateResponse>

    @Headers(
        value = ["Accept: application/json",
            "Content-type:application/x-www-form-urlencoded"]
    )
    @FormUrlEncoded
    @POST("get-city-list")
    suspend fun cityList(@FieldMap request: Map<String, String>): Response<CityResponse>


    @Headers(
        value = ["Accept: application/json",
            "Content-type:application/x-www-form-urlencoded"]
    )
    @FormUrlEncoded
    @POST("update-distributor-details")
    suspend fun apiCallingUpdateDist(@FieldMap request: Map<String, String>): Response<DistributiorUpdateResponse>


    @Headers(
            value = ["Accept: application/json",
                "Content-type:application/x-www-form-urlencoded"]
    )
    @FormUrlEncoded
    @POST("get-businessline-list")
    suspend fun apiCallingBusinessLine(@FieldMap request: Map<String, String>): Response<BusinessLineModelResponse>


    @Headers(
        value = ["Accept: application/json",
            "Content-type:application/x-www-form-urlencoded"]
    )
    @FormUrlEncoded
    @POST("add-shop")
    suspend fun apiCallingForAddShop(@FieldMap request: Map<String, String>): Response<LoginResponse>

    companion object Factory {
        operator fun invoke(): ApiService {
            return RetrofitProvider.getRetrofitInstance().create(ApiService::class.java)
        }

    }


}