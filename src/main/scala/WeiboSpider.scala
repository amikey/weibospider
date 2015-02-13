import java.util
import java.util.logging.Logger

import cn.edu.zju.king._
import org.apache.http.NameValuePair
import org.apache.http.message.BasicNameValuePair
import us.codecraft.webmagic.utils.HttpConstant
import us.codecraft.webmagic.{Page, Site, Request, Spider}
import us.codecraft.webmagic.downloader.{HttpClientDownloader, Downloader}
import scala.collection.JavaConversions._

import scala.collection.mutable.{ListBuffer, ArrayBuffer}

/**
 * Created by king on 15-2-11.
 */

object WeiboSpider {

  val logger = Logger.getLogger(getClass.getName)

  val loginurl = "http://login.weibo.cn/login/"

  val weibourl = "http://weibo.cn/"

  def main(args: Array[String]) {
    val down = new HttpClientResponseDownload()

    val accounts = ConfigHelper.getAccounts()

    accounts.foreach(println)

    val lp = new LoginPageProcessor()
    Spider.create(lp).addUrl(loginurl).run()



    val loginpost = new Request(loginurl+lp.action.get)
    loginpost.setMethod(HttpConstant.Method.POST)
    val s1 = Site.me()
      .setUserAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/39.0.2171.65 Chrome/39.0.2171.65 Safari/537.36")
      .addHeader("Referer", loginurl)
      .addHeader("Content-Type", "application/x-www-form-urlencoded")
      .setAcceptStatCode(new util.HashSet[Integer]{{ add(200); add(302)}})
      .setCharset("UTF-8")
      .setDomain(".weibo.cn")



    // 构造form， 需要通过 NameValuePair，putExtra的Key为nameValuePair
    val nameValuePair = new Array[NameValuePair](8)
    for (i <- 0 until 4) nameValuePair(i) = new BasicNameValuePair(lp.params.get(i)._1, lp.params.get(i)._2)

    nameValuePair(4) = new BasicNameValuePair("mobile", accounts(0).username)
    nameValuePair(5) = new BasicNameValuePair(lp.passwordname.get, accounts(0).password)
    nameValuePair(6) = new BasicNameValuePair("remember", "on")
    nameValuePair(7) = new BasicNameValuePair("submit", "登录")

    loginpost.putExtra("nameValuePair", nameValuePair)

    println(loginpost)

    val page = down.download(loginpost, s1.toTask)

    down.response.get.getLastHeader("Location").getValue

    down.response.get.getAllHeaders.foreach(println)


    val weibocn: Site = Site.me().setDomain(".weibo.cn")

    down.response.get.getHeaders("Set-Cookie").map(_.getValue.split("; ")(0)).map(_.split("=")).foreach(t => weibocn.addCookie(t(0), t(1)))

    println(weibocn)

    val weibo = new WeiboProcessor()

    weibo.site = weibocn

    Spider.create(weibo).addUrl(weibourl).run()

  }

}
