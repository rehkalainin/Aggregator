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

    val priceSqmData = element >?> extractor(".jss1478", text) // class="jss1478" sometimes changing
    val priceSqm = priceSqmData match {
      case Some(price) => price.split("[$]").head.split(" ").mkString
      case None => {
        val priceSqmPattern = "(?:(?<=[$] )[0-9| ]*)".r
        priceSqmPattern.findFirstIn(data) match {
          case Some(price) => price.split(" ").mkString
          case None => "none"
        }
      }
    }
    val locationData = element >?> extractor(".jss1472", text) // class="jss1472" sometimes changing
    val location = locationData match {
      case Some(loc) => loc.toLowerCase
      case None =>
        val locationPattern = "[А-Я][а-я]*[,][ ][А-Я][а-я]*".r
        locationPattern.findFirstIn(data) match {
          case Some(loc) => loc
          case None => "none"
        }
    }
    FlatfyModel(name, priceSqm, location,link)
  }
}
