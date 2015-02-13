package cn.edu.zju.king

import java.io.IOException

import org.apache.http.HttpResponse
import org.apache.http.impl.client.CloseableHttpClient
import us.codecraft.webmagic.{Site, Page, Request, Task}
import us.codecraft.webmagic.downloader.HttpClientDownloader
import us.codecraft.webmagic.selector.PlainText

/**
 * Created by king on 15-2-13.
 */
class HttpClientResponseDownload extends HttpClientDownloader {
  var response: Option[HttpResponse] = None

  @throws(classOf[IOException])
  override protected def handleResponse(request: Request, charset: String, httpResponse: HttpResponse, task: Task): Page = {
    val content: String = getContent(charset, httpResponse)
    val page: Page = new Page
    response = Some(httpResponse)
    page.setRawText(content)
    page.setUrl(new PlainText(request.getUrl))
    page.setRequest(request)
    page.setStatusCode(httpResponse.getStatusLine.getStatusCode)
    page
  }

}
