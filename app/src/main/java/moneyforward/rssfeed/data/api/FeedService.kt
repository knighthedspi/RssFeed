package moneyforward.rssfeed.data.api

import io.reactivex.Flowable
import moneyforward.rssfeed.data.model.Feed
import retrofit2.http.GET
import retrofit2.http.Url

interface FeedService {

    @GET()
    fun feed(@Url url: String): Flowable<Feed>
}