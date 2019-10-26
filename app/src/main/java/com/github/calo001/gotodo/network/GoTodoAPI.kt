package com.github.calo001.gotodo.network

import android.content.Intent
import com.github.calo001.gotodo.GoTodo
import com.github.calo001.gotodo.model.BasicTask
import com.github.calo001.gotodo.model.Login
import com.github.calo001.gotodo.model.responses.*
import com.github.calo001.gotodo.ui.login.LoginActivity
import com.github.calo001.gotodo.util.BASE_URL
import com.github.calo001.gotodo.util.MySharedPreferences
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface GoTodoAPI {
    @Headers("No-Authentication: true")
    @POST("/v1/login")
    fun login(@Body login: Login) : Observable<LoginTokenResponse>

    @Headers("No-Authentication: true")
    @POST("/v1/register")
    fun register(@Body signup: Login) : Observable<BasicResponse>

    @GET("/v1/todo/all")
    fun getAllTask() : Observable<TaskListResponse>

    @POST("/v1/todo/create")
    fun createTask(@Body basicTask: BasicTask) : Observable<TaskCreateResponse>

    @PUT("/v1/todo/update/{id}")
    fun update(@Body task: BasicTask, @Path("id") id: Int) : Observable<TaskUpdateResponse>

    @DELETE("/v1/todo/delete/{id}")
    fun delete(@Path("id") id: Int) : Observable<TaskDeleteResponse>

    companion object {
        fun create(): GoTodoAPI {
            val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor {
                    val header= it.request().header("No-Authentication")
                    val token = MySharedPreferences.getToken()
                    val newRequest =
                        if (header == null) {
                            it.request().newBuilder()
                                .addHeader("Authorization", "Bearer $token")
                                .build()
                        } else {
                            it.request()
                        }

                    it.proceed(newRequest)
                }
                .addInterceptor{
                    val request = it.request()
                    val response = it.proceed(request)
                    val intent = Intent(GoTodo.applicationContext(), LoginActivity::class.java)
                    if (request.header("No-Authentication") == null) {
                        when (response.code()) {
                            401 -> {
                                GoTodo.applicationContext().startActivity(intent)
                                MySharedPreferences.clearToken()
                            }
                        }
                    }
                    response
                }.build()

            val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(GoTodoAPI::class.java)
        }
    }
}