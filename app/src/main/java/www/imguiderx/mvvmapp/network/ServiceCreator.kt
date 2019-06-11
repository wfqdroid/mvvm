package www.imguiderx.mvvmapp.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object ServiceCreator {

    private const val BASE_URL = "https://www.wanandroid.com/"

    private val builder = OkHttpClient.Builder()
        .connectTimeout(60,TimeUnit.SECONDS)
        .callTimeout(60,TimeUnit.SECONDS)
        .readTimeout(60,TimeUnit.SECONDS)
        .writeTimeout(60,TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(builder.build())
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(clazz: Class<T>) : T = retrofit.create(clazz)

}