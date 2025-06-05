package abhimanpower.app.abhihire.zDependencyInjection

import abhimanpower.app.abhihire.zAPIEndPoints.ApiHelper
import abhimanpower.app.abhihire.zAPIEndPoints.ApiHelperImplementation
import abhimanpower.app.abhihire.zAPIEndPoints.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideBaseUrl() = "https://powerapps.sbs/AbhiHire/"

//    @Singleton

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        // Disable caching by setting cache to null and ensuring no cache-related headers are included
        builder.cache(null) // This disables OkHttp cache

        if (true) {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            builder.addInterceptor(loggingInterceptor)
        }

        // Add a network interceptor to force no caching in headers
        builder.addNetworkInterceptor { chain ->
            val request = chain.request().newBuilder()
                .header("Cache-Control", "no-cache")
                .header("Pragma", "no-cache")
                .build()
            chain.proceed(request)
        }

        return builder.build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiInterface = retrofit.create(ApiInterface::class.java)

    @Provides
    fun provideApiHelper(apiHelper: ApiHelperImplementation): ApiHelper = apiHelper
}