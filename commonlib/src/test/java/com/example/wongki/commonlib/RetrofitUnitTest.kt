package com.example.wongki.commonlib

//import com.example.wongki.commonlib.retrofit.*
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import org.junit.Test
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.*

/*import org.junit.Test
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.**/

class RetrofitUnitTest {
    companion object {
        val PARAM_IP = "118.26.65.194"
        val PARAM_KEY = "aa205eeb45aa76c6afe3c52151b52160"
        val BASE_URL = "https://apis.juhe.cn"

    }

    interface HostApiService {
        @GET("/ip/ipNew")
        fun queryGet(@Query("ip") ip: String, @Query("key") key: String): Call<ResponseBody>

        @POST("/ip/ipNew")
        @FormUrlEncoded
        fun queryPost(@Field("ip") ip: String, @Field("key") key: String):Call<ResponseBody>
    }

    @Test
    fun request() {
        val okClient = OkHttpClient.Builder().build()
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okClient)
                .build()
        val hostApiService = retrofit.create<HostApiService>(HostApiService::class.java)
        val call = hostApiService.queryGet(ip = PARAM_IP, key = PARAM_KEY)
        val response = call.execute()
        print(String(response.body()!!.bytes()))

    }
}