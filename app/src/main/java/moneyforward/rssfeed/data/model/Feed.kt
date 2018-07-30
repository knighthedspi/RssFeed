package moneyforward.rssfeed.data.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "rdf:RDF", strict = false)
data class Feed(
        @field:Element(name = "channel")
        var feedChannel: FeedChannel? = null,

        @field:ElementList(name = "item", inline = true)
        var feedItems: List<FeedItem>? = null
)