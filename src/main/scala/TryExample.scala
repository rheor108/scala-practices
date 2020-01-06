import scala.io.Source
import java.net.URL
import scala.util.Try

object TryExample {

  def parseURL(url: String): Try[URL] = Try(new URL(url))

  def getURLContent1(url: String): Try[Iterator[String]] =
    for {
      url         <- parseURL(url)
      connection  <- Try(url.openConnection())
      is          <- Try(connection.getInputStream)
      source      =  Source.fromInputStream(is)
    } yield source.getLines()

  // which is translated introduced
  def getURLContent2(url: String): Try[Iterator[String]] =
    parseURL(url).flatMap(
      url => Try(url.openConnection()).flatMap(
        connection => Try(connection.getInputStream).map(
          is => {
            val source = Source.fromInputStream(is)
            source.getLines()
          }
        )
      )
    )
}
