package moneyforward.rssfeed.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moneyforward.rssfeed.data.model.Feed
import moneyforward.rssfeed.data.repository.FeedRepository
import moneyforward.rssfeed.utils.toLiveData
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: FeedRepository, channels: Map<String, String>) : ViewModel() {

    inner class FeedData(val channel: String, val liveData: LiveData<Feed>)

    val data = channels.map {
        val liveData = repository.feed(it.value)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn {
                    Timber.e(it)
                    if (it is HttpException || it is IOException) {
                        return@onErrorReturn null
                    } else {
                        throw it
                    }
                }
                .onErrorResumeNext(Flowable.empty())
                .toLiveData()
         FeedData(it.key, liveData)
    }

}