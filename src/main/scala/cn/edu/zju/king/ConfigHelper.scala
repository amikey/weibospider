package cn.edu.zju.king

import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule

import scala.collection.immutable.HashMap

/**
 * Created by king on 15-2-12.
 */

case class Account(var username: String, var password: String)

class ConfigHelper {



}


object ConfigHelper {

  def getAccounts(fp: String = "/account.json"): Array[Account] = {
    val mp = new ObjectMapper
    mp.registerModule(DefaultScalaModule)
    mp.readValue(getClass.getResourceAsStream(fp), classOf[Array[Account]])
  }

}