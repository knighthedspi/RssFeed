package moneyforward.rssfeed.di

import dagger.Module
import dagger.Provides
import moneyforward.rssfeed.AppLifecycleCallbacks
import moneyforward.rssfeed.data.di.DataModule
import javax.inject.Singleton

@Module(includes = [DataModule::class])
internal object AppModule {

    @Singleton
    @Provides
    @JvmStatic
    fun provideAppLifecycleCallbacks(): AppLifecycleCallbacks = ReleaseAppLifecycleCallbacks()
}