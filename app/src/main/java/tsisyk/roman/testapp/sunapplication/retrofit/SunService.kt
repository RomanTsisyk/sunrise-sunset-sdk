package tsisyk.roman.testapp.sunapplication.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface SunService {

    @GET("json")
    fun getSunData(
        @Query("lat") lat: String,
        @Query("lng") lng: String,
        @Query("const") const: String
    )
            : Call<SunResponse>

}