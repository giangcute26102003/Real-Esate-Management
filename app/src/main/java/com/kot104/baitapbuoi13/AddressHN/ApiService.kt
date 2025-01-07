package com.kot104.baitapbuoi13.AddressHN
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


data class District(
    val name: String,
    val code: Int,
    val division_type: String,
    val codename: String,
    val province_code: Int,
    val wards: List<Ward>
)
data class Ward(
    val name: String,
    val code: Int,
    val division_type: String,
    val codename: String,
    val district_code: Int
)interface AddressAPI {
    @GET("d")
    suspend fun getDistricts(): List<District>

    @GET("w")
    suspend fun getWards(): List<Ward>

}