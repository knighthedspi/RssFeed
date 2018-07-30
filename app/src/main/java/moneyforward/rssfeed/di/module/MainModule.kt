package moneyforward.rssfeed.di.module

import android.arch.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import moneyforward.rssfeed.di.ViewModelKey
import moneyforward.rssfeed.ui.main.MainFragment
import moneyforward.rssfeed.ui.main.MainViewModel

@Module
internal abstract class MainModule {

  @Binds
  @IntoMap
  @ViewModelKey(MainViewModel::class)
  abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

  @ContributesAndroidInjector
  abstract fun contributeMainFragment(): MainFragment

}