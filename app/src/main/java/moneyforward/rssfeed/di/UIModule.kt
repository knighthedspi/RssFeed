package moneyforward.rssfeed.di

import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import moneyforward.rssfeed.di.module.MainModule
import moneyforward.rssfeed.ui.main.MainActivity

@Module
internal abstract class UIModule {

  @Binds
  abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

  @ContributesAndroidInjector(modules = [MainModule::class])
  internal abstract fun contributeMainActivity(): MainActivity

}