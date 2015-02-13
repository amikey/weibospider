package cn.edu.zju.king

import us.codecraft.webmagic.{Page, Site}
import us.codecraft.webmagic.processor.PageProcessor
import scala.collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer

/**
 * Created by king on 15-2-13.
 */
class LoginPageProcessor extends PageProcessor {

  var passwordname: Option[String] = None
  var params: Option[List[(String, String)]] = None
  var action: Option[String] = None

  val site = Site.me().setUserAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/39.0.2171.65 Chrome/39.0.2171.65 Safari/537.36")

  override def process(page: Page): Unit = {
    passwordname = Some(page.getHtml.xpath("//[@type=password]/@name").toString)

    val names = Some(page.getHtml.xpath("//[@type=hidden]/@name").all())
    val values = Some(page.getHtml.xpath("//[@type=hidden]/@value").all())

    params = Some((names.get zip values.get).toList)

    action = Some(page.getHtml.xpath("//form/@action").toString)

    println(params.get)

//    println(names.get)
//    println(values.get)
//    println(action.get)
  }

  override def getSite: Site = site
}
