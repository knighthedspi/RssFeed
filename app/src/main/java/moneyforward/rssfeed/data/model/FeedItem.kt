package moneyforward.rssfeed.data.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root
import org.w3c.dom.Node
import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory


@Root(name = "item", strict = false)
data class FeedItem(
        @field:Element(name = "title")
        var title: String? = null,

        @field:Element(name = "link")
        var link: String? = null,

        @field:Element(name = "description", required = false)
        var description: String? = null,

        @field:Element(name = "encoded", required = false)
        var content: String? = null

) {
    fun thumbnail() : String {
        val documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        val doc = documentBuilder.parse(InputSource(StringReader(this.content)))

        val xPath = "//img[@class=\"entry-image\"]/@src"
        val node = XPathFactory.newInstance().newXPath().evaluate(xPath, doc, XPathConstants.NODE) as Node
        return node.nodeValue

    }
}
