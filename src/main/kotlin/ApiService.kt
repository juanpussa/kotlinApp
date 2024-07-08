// src/main/kotlin/ApiService.kt
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST(".")
    fun postVehicleData(
        @Query("plateId") plateId: String,
        @Body vehicleData: VehicleData
    ): Call<Void>
    @GET(".")
    fun getVehicleData(
        @Query("plateId") plateId: String
    ): Call<VehicleData>
}
