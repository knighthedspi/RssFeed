package moneyforward.rssfeed.data.repository

import moneyforward.rssfeed.data.api.FeedService
import javax.inject.Inject

class FeedRepository @Inject constructor(private val feedService: FeedService) {
    fun feed(url: String) = feedService.feed(url)
}