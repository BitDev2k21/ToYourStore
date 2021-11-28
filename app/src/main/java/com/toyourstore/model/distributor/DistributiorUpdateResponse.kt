package com.toyourstore.model.distributor

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DistributiorUpdateResponse(

	@field:SerializedName("user")
	val user: List<UserItem?>? = null
) : Parcelable

@Parcelize
data class DistributorDetailsItem(

	@field:SerializedName("owner_name")
	val ownerName: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("latitude")
	val latitude: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("routes")
	val routes: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("gst_no")
	val gstNo: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("working_hours")
	val workingHours: String? = null,

	@field:SerializedName("contact")
	val contact: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("credit_period")
	val creditPeriod: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("state_id")
	val stateId: Int? = null,

	@field:SerializedName("city_id")
	val cityId: Int? = null,

	@field:SerializedName("longitude")
	val longitude: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable

@Parcelize
data class DistributorLocationsItem(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("locations")
	val locations: LocationsDist? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("location_id")
	val locationId: Int? = null
) : Parcelable

@Parcelize
data class UserItem(

	@field:SerializedName("image")
	val image: String? = null,

//	@field:SerializedName("distributor_companies")
//	val distributorCompanies: List<DistributorCompaniesItem?>? = null,
//
//	@field:SerializedName("distributor_details")
//	val distributorDetails: List<DistributorDetailsItem?>? = null,
//
//	@field:SerializedName("distributor_businesslines")
//	val distributorBusinesslines: List<String?>? = null,

	@field:SerializedName("api_token")
	val apiToken: String? = null,

	@field:SerializedName("mobile")
	val mobile: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("email_verified_at")
	val emailVerifiedAt: String? = null,

	@field:SerializedName("user_type")
	val userType: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("identity")
	val identity: String? = null,

	@field:SerializedName("contact")
	val contact: String? = null,

	@field:SerializedName("distributor_id")
	val distributorId: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,
//
//	@field:SerializedName("distributor_locations")
//	val distributorLocations: List<DistributorLocationsItem?>? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null
) : Parcelable

@Parcelize
data class DistributorCompaniesItem(

	@field:SerializedName("companies")
	val companies: CompaniesDist? = null,

	@field:SerializedName("company_id")
	val companyId: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
) : Parcelable

@Parcelize
data class LocationsDist(

	@field:SerializedName("pincode")
	val pincode: String? = null,

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("latitude")
	val latitude: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("state_id")
	val stateId: Int? = null,

	@field:SerializedName("city_id")
	val cityId: Int? = null,

	@field:SerializedName("longitude")
	val longitude: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable

@Parcelize
data class CompaniesDist(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("address_1")
	val address1: String? = null,

	@field:SerializedName("address_2")
	val address2: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("location_id")
	val locationId: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable
