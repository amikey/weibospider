package cn.edu.zju.king


import java.util.UUID

import us.codecraft.webmagic.{Site, Task}

/**
 * Created by king on 15-2-12.
 */
class LoginTask extends Task{
  var site: Option[Site] = None
  var uuid: Option[String] = None

  override def getUUID: String = {
    if (uuid != None) {
      uuid.get
    }
    if (site != None) {
      site.get.getDomain
    }
    uuid = Option(UUID.randomUUID().toString)
    uuid.get
  }

  override def getSite: Site = {



    site.get
  }
}
