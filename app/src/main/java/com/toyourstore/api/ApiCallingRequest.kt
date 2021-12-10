package com.toyourstore.api

import com.toyourstore.model.*
import com.toyourstore.model.businessline.BusinessLineModelResponse
import com.toyourstore.model.distributor.DistributiorUpdateResponse
import com.toyourstore.model.distributor.DistributorResponse
import com.toyourstore.model.distributor.Locations

class ApiCallingRequest : SafeApiRequest() {

    var apiService: ApiService

    init {
        apiService = ApiService.invoke()
    }


    suspend fun apiCallingForLogin(
        params: HashMap<String, String>
    ): LoginResponse {
        return apiRequest { apiService.login(params) }
    }

    suspend fun getUser(
        params: HashMap<String, String>
    ): GetUserResponse {
        return apiRequest { apiService.getUser(params) }
    }


    suspend fun apiCallingBusinessLine(
        params: HashMap<String, String>
    ): BusinessLineModelResponse {
        return apiRequest { apiService.apiCallingBusinessLine(params) }
    }

    suspend fun apiCallingForDashBoard(
        params: HashMap<String, String>
    ): DashBoardResponse {
        return apiRequest { apiService.apiForDashBoard(params) }
    }

    suspend fun apiCallingForUpdateDist(
        params: HashMap<String, String>
    ): DistributiorUpdateResponse {
        return apiRequest { apiService.apiCallingUpdateDist(params) }
    }

    suspend fun apiCallingRegister(
        params: HashMap<String, String>
    ): LoginResponse {
        return apiRequest { apiService.register(params) }
    }

    suspend fun getLocationList(
        params: HashMap<String, String>
    ): GetLocation {
        return apiRequest { apiService.getLocationListApi(params) }
    }

    suspend fun apiCallingAddShop(
        params: HashMap<String, String>
    ): LoginResponse {
        return apiRequest { apiService.apiCallingForAddShop(params) }
    }

    suspend fun apiCallingForSateResponse(
        params: HashMap<String, String>
    ): StateResponse {
        return apiRequest { apiService.stateList(params) }
    }

    suspend fun apiCallingForCityResponse(
        params: HashMap<String, String>
    ): CityResponse {
        return apiRequest { apiService.cityList(params) }
    }


    suspend fun apiCallingForOrderList(
        params: HashMap<String, String>
    ): PendingOrderResponse {
        return apiRequest { apiService.orderList(params) }
    }

    suspend fun apiCallingForPaymentList(
        params: HashMap<String, String>
    ): PendingOrderResponse {
        return apiRequest { apiService.paymentList(params) }
    }

    suspend fun apiCallingForSales(
        params: HashMap<String, String>
    ): SalesTeamResponse {
        return apiRequest { apiService.apiSales(params) }
    }

    suspend fun apiCallingForOperator(
        params: HashMap<String, String>
    ): OperatorsResponse {
        return apiRequest { apiService.apiOperator(params) }
    }


    suspend fun apiCallingInventry(
        params: HashMap<String, String>
    ): InventoryResponse {
        return apiRequest { apiService.apiInventry(params) }
    }


}


