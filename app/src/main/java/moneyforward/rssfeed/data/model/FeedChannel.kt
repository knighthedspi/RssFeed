package moneyforward.rssfeed.data.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "channel", strict = false)
data class FeedChannel(
        @field:Element(name = "title")
        var title: String? = null,

        @field:Element(name = "link")
        var link: String? = null,

        @field:Element(name = "description", required = false)
        var description: String? = null
)