import com.pagero.services.async.Threadpools.blocking
import org.apache.commons.io.IOUtils
import org.apache.commons.text.RandomStringGenerator
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.{ContentType, StringEntity}
import org.apache.http.impl.client.HttpClients

import scala.concurrent.Future


object AddData {
  val generator: RandomStringGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z').build()

  def main(args: Array[String]): Unit = {

    /** Change customer ID here **/
    val customerId = "3000"
    /** Change documentType here **/
    val documentType = "INVOICE"
    /** ADD bearerToken here **/
    val bearerToken = "Bearer 6dff72bc-6ba9-4c02-8695-512359f4122f"
    /** Amount of request that need to send **/
    val requestAmount = 1000

    val post = new HttpPost("https://api-dev.pageroonline.com/email/v1/recipientConnections")
    val client = HttpClients.createDefault()
    post.setHeader("Content-Type", "application/json")
    post.setHeader("Authorization", bearerToken)

    for (x <- 1 to requestAmount) {
      Future {
        val erpId = generator.generate(10) + x
        val email = generator.generate(10) + x
        val ccEmail = generator.generate(10) + x
        val ccEmail2 = generator.generate(10) + x
        val host = generator.generate(5) + x
        val body = s"""{"customerId":"$customerId","erpId":["$erpId"],"email":"$email@$host.com","documentType":"$documentType","ccAddresses":"$ccEmail@$host.com,$ccEmail2@$host.com"}"""

        val stringEntity = new StringEntity(body, ContentType.APPLICATION_JSON)
        post.setEntity(stringEntity)

        val response = client.execute(post)
        val respString = response.getEntity.getContent
        val responseBody = IOUtils.toString(respString, "UTF-8")
        println(responseBody + " ErrorCode:" + response.getStatusLine.getStatusCode)
      }
    }
  }
}
