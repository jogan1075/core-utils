package com.klapcomercio.core_utils.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitWebServiceFactory<TRetrofitWebService> {


    fun create(
        isDebug: Boolean,
        tClass: Class<TRetrofitWebService>,
        baseUrl: String
    ): TRetrofitWebService {

        val okHttpClient = makeOkHttpClient(
            makeLoggingInterceptor(true)
        )
        return create(okHttpClient, tClass, baseUrl)
    }

    private fun create(
        okHttpClient: OkHttpClient,
        tClass: Class<TRetrofitWebService>,
        baseUrl: String
    ): TRetrofitWebService {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setLenient()
                        .create()
                )
            )

            .build()
        return retrofit.create(tClass)
    }

    private fun makeOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

    }

    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }


}
