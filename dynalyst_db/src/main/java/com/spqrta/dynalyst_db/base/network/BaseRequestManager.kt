package com.spqrta.dynalyst_db.base.network

import com.spqrta.dynalyst_db.utility.CustomApplication
import com.spqrta.dynalyst_db.utility.Logg
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Suppress("LeakingThis")
abstract class BaseRequestManager {

    protected lateinit var retrofit: Retrofit

    init {
        buildRetrofit()
    }

    protected fun buildRetrofit() {
        val interceptor = HttpLoggingInterceptor { message ->
            if (CustomApplication.appConfig.debugMode) {
                if(message.length < 256) {
                    Logg.v(message)
                } else {
                    try {
                        Logg.v(JSONObject(message).toString(4))
                    } catch (e: JSONException) {
                        Logg.v(message)
                    }
                }
            }
        }
        interceptor.level = HttpLoggingInterceptor.Level.BODY
//        interceptor.level = HttpLoggingInterceptor.Level.BASIC

        val clientBuilder = OkHttpClient.Builder()

        buildClient(clientBuilder)
        if (CustomApplication.appConfig.debugMode) {
            clientBuilder.addInterceptor(interceptor)
        }


        val client = clientBuilder
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .client(client)
//            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
//            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    abstract fun getBaseUrl(): String

    open fun buildClient(builder: OkHttpClient.Builder) {

    }


}