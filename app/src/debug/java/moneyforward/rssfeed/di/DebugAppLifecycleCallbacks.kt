package moneyforward.rssfeed.di

import android.app.Application
import moneyforward.rssfeed.AppLifecycleCallbacks
import timber.log.Timber

class DebugAppLifecycleCallbacks : AppLifecycleCallbacks {

  override fun onCreate(application: Application) {
      Timber.plant(Timber.DebugTree())
  }

  override fun onTerminate(application: Application) {

  }
}
