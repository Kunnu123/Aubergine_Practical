package com.example.aubergine_practical.injection

import android.content.Context
import com.example.aubergine_practical.BuildConfig
import com.example.aubergine_practical.common.extentionviews.getPrefInstance
import com.example.aubergine_practical.model.UserHolder
import com.example.aubergine_practical.shareddata.EndPoints.ApiEndPoint
import com.example.aubergine_practical.shareddata.repositories.UserRepo
import com.example.aubergine_practical.shareddata.repositories.UserRepository
import com.example.aubergine_practical.ui.auth.login.LoginViewModel
import com.example.aubergine_practical.ui.workout.WorkoutViewModel
import com.example.aubergine_practical.utils.Config
import com.readystatesoftware.chuck.ChuckInterceptor
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * This File contains the KOIN implementation and module declaration
 */

/**
 *  User repository and viewModel module defined
 */
val viewModelModule = module {
    single<UserRepo> { UserRepository(get(), get()) }

    viewModel { LoginViewModel(get()) }
    viewModel { WorkoutViewModel(get()) }
}

/**
 * Shared Preference module defined
 */
val sharedPreferenceModule = module {
    single { provideUserHolder(androidContext()) }
}

/**
 * Retrofit API call setup module defined
 */
val networkModule = module {
    single { provideHttpLogging(androidContext()) }
    single { provideRetrofit(get()) }
    single { provideApiService(get()) }
}

/**
 * All the modules combined in appModules object
 * This object is used in AppApplication class
 */
val appModules = viewModelModule + networkModule + sharedPreferenceModule

/**
 * @param retrofit Retrofit object reference
 * @return return the ApiEndPoint reference
 */
fun provideApiService(retrofit: Retrofit): ApiEndPoint = retrofit.create(ApiEndPoint::class.java)

/**
 * To setup the Retrofit Object
 * @param client OkHttpClient object
 * @return return the Retrofit object reference
 */
fun provideRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .client(client)
        .build()
}

/**
 * Here ChuckInterceptor is used for showing API request in notification. It's only enable in debug build
 * @param context Context object
 * @return OkHttpClient object
 */
fun provideHttpLogging(context: Context): OkHttpClient {
    val logging = HttpLoggingInterceptor()
    logging.level =
        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

    return OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .addNetworkInterceptor(ChuckInterceptor(context))
        .addNetworkInterceptor(logging)
        .build()
}

/**
 * @param context Context object
 * @return UserHolder class reference from shared preference
 */
fun provideUserHolder(context: Context): UserHolder =
    UserHolder(context.getPrefInstance(Config.FIT_BIT_SHARED_PREFERENCE))
