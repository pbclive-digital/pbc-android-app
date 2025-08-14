package com.kavi.pbc.droid.network

import com.kavi.pbc.droid.network.dto.BaseResponse
import com.kavi.pbc.droid.network.model.NetConfig
import com.kavi.pbc.droid.network.model.ResultWrapper
import com.kavi.pbc.droid.network.session.Session
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import com.kavi.pbc.droid.network.dto.Error

class Network {

    private lateinit var netConfig: NetConfig
    private var retrofit: Retrofit? = null

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.HEADERS
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder().apply {
        this.addInterceptor(interceptor)
            .callTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
    }

    fun init(config: NetConfig) {
        netConfig = config
        netConfig.networkInterceptors?.forEach {
            client.addNetworkInterceptor(it)
        }
    }

    fun getBaseUrl(): String = netConfig.baseUrl

    fun getRetrofit(): Retrofit {
        // Add auth token to request
        client.apply {
            addInterceptor(Interceptor { chain ->
                val builder = chain.request().newBuilder()
                Session.getInstance()?.authToken?.token?.let {
                    builder.header("Authorization", "Bearer $it")
                }
                return@Interceptor chain.proceed(builder.build())
            })
        }

        // Add custom headers to request
        client.apply {
            addInterceptor(Interceptor { chain ->
                val builder = chain.request().newBuilder()
                builder.header("X-app-os", "android")
                Session.getInstance()?.let { session ->
                    session.user?.let {
                        builder.header("X-app-user", Json.encodeToString(it))
                    }
                    session.deviceFactor?.let {
                        builder.header("X-app-device-factor", it.toString())
                    }
                    session.appVersion?.let {
                        builder.header("X-app-version", it)
                    }
                }
                return@Interceptor chain.proceed(builder.build())
            })
        }

        retrofit = Retrofit.Builder().baseUrl(netConfig.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            // we need to add converter factory to
            // convert JSON object to Java object
            .client(client.build())
            .build()
        return retrofit!!
    }

    suspend fun <T>invokeApiCall(dispatcher: CoroutineDispatcher,
                                 apiCall: suspend () -> BaseResponse<T>
    ): ResultWrapper<BaseResponse<T>> {
        return withContext(dispatcher) {
            try {
                ResultWrapper.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> ResultWrapper.NetworkError
                    is HttpException -> {
                        val code = throwable.code()
                        var firstError: Error? = null
                        convertErrorBody<BaseResponse<T>>(throwable)?.let {
                            it.errors?.let { errorList ->
                                if(errorList.isNotEmpty())
                                    firstError = errorList[0]
                            }
                        }
                        when(code) {
                            401 -> ResultWrapper.UnAuthError(code, firstError)
                            else -> ResultWrapper.HttpError(code, firstError)
                        }
                    }
                    else -> {
                        ResultWrapper.HttpError(-1, Error(throwable.toString()))
                    }
                }
            }
        }
    }

    private fun <T>convertErrorBody(throwable: HttpException): T? {
        return try {
            throwable.response()?.errorBody()?.let {
                retrofit?.responseBodyConverter<T>(BaseResponse::class.java, arrayOf())?.convert(it)
            }?: run {
                null
            }
        } catch (exception: Exception) {
            null
        }
    }
}