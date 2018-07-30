package moneyforward.rssfeed.data.di

import dagger.Module
import dagger.Provides
import moneyforward.rssfeed.App
import moneyforward.rssfeed.data.api.FeedService
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
internal object DataModule {

    private val cacheSize: Long = 10 * 1024 * 1024
    private val cacheTimeSec = 30

    private val cacheInterceptor: Interceptor = Interceptor {
            val response = it.proceed(it.request())
            val cacheControl = CacheControl.Builder()
                    .maxAge(cacheTimeSec, TimeUnit.SECONDS)
                    .build()

            response.newBuilder()
                    .header("Cache-Control", cacheControl.toString())
                    .build()
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideChannels() : Map<String, String> = linkedMapOf(
            "総合" to "http://b.hatena.ne.jp/hotentry.rss",
            "世の中" to "http://b.hatena.ne.jp/hotentry/social.rss",
            "政治と経済" to "http://b.hatena.ne.jp/hotentry/economics.rss",
            "暮らし" to "http://b.hatena.ne.jp/hotentry/life.rss")

    @Singleton
    @Provides
    @JvmStatic
    fun provideOkHttpClient(app: App): OkHttpClient {
        val cache = Cache(File(app.cacheDir, "feed-http-cache"), cacheSize)
        return OkHttpClient.Builder()
                .addInterceptor(cacheInterceptor)
                .cache(cache)
                .build()
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideRetrofit(oktHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .client(oktHttpClient)
            .baseUrl("http://b.hatena.ne.jp")
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @Singleton
    @Provides
    @JvmStatic
    fun provideFeedService(retrofit: Retrofit): FeedService = retrofit.create(FeedService::class.java)

}