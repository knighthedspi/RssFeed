package moneyforward.rssfeed

import android.app.Application

interface AppLifecycleCallbacks {

  fun onCreate(application: Application)

  fun onTerminate(application: Application)
}