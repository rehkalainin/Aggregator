package services

import java.net.URLEncoder

import models.FlatfyModel
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.Element
import org.jsoup.HttpStatusException
import modules.FlatfyModul._

object ScrapingService {

  val browser = JsoupBrowser()

  val sellApartments = "продаж-квартир"
  val odessa = "-одеса"
  val orderDate = "?order=add-time"

  def createStreetUrl(street: String): String = {
    siteUrl + URLEncoder.encode(sellApartments, "UTF-8") +
      URLEncoder.encode("-вул-" + street.toLowerCase, "UTF-8") +
      URLEncoder.encode(odessa, "UTF-8") +
      orderDate
  }

  def scrapeListings(street: String) = {
    val url = createStreetUrl(street)
    try {
      val doc = browser.get(url)

      val listings: Option[List[Element]] = doc >?> extractor("article", elementList)
      listings match {
        case Some(value) => value
        case None => List.empty
      }
    } catch {
      case ex: HttpStatusException => List.empty
    }
  }

  def scrapeData(element: Element) = {

    val nameData = element >> extractor("span", text)
    val name = nameData.split(" ").last.toLowerCase
    val href = element >> extractor("a", attr("href"))
    val link = "https://flatfy.lun.ua".concat(href)

    val data = element >> extractor("div", allText)

    //    val priceSqm = element >?> extractor(".jss192", text) // class="jss192" not stable sometimes changing
    val priceSqmPattern = "(?:(?<=[$] )[0-9| ]*)".r
    val priceSqm = priceSqmPattern.findFirstIn(data) match {
      case Some(price) => price.split(" ").mkString
      case None => "none"
    }

    //    val location = element >?> extractor(".jss186", text) // class="jss186" not stable sometimes changing
    val location = data.split(" ").drop(5).head

    FlatfyModel(name, priceSqm, location, link)
  }
}
