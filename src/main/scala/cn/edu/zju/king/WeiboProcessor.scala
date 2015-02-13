package cn.edu.zju.king

import us.codecraft.webmagic.{Page, Site}
import us.codecraft.webmagic.processor.PageProcessor

/**
 * Created by king on 15-2-11.
 */

class WeiboProcessor extends PageProcessor {

  var site: Site = Site.me().setRetryTimes(3).setSleepTime(1000)

  override def process(page: Page): Unit = {
    System.out.println(page)
  }

  override def getSite: Site = site
}
