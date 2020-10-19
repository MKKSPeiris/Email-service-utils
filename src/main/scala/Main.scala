import com.pagero.service.ServiceApplication
import com.pagero.servicecomm.ServiceClient
import com.pagero.services.EmailLoadTest.EmailLoadTestServiceInfo
import com.pagero.services.async.Threadpools.blocking
import com.pagero.services.email.service.spec._
import com.typesafe.scalalogging.LazyLogging

import scala.util.{Failure, Success}

//import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object Main extends ServiceApplication with EmailLoadTestServiceInfo with LazyLogging {

  private val emailClient: ServiceClient = createClient(EmailServiceSpecProto)

 Future {
   Thread.sleep(5000)
   val t0 = System.currentTimeMillis()

   val requests = 1000
   for (x <- 1 to requests) {
     Future {
       emailClient.request(GetRecipientMetadataRequest(Some("1000"), Some("qezvugblth9910"), Some(DocumentType.INVOICE))).onComplete {
         case Success(v) =>
           v match {
             case msg: GetRecipientMetadataResponse =>
               logger.info("OK received : " + msg + s" Request No $x")

             case msg: EmailErrorResponse =>
               logger.info(s"Error received , $msg")
           }
           logger.info(s"SUCCESS : $x")
         case Failure(e) =>
           logger.info(s"FAILED : $x" + e.getMessage)
       }
     }
   }
   val t1 = System.currentTimeMillis()
   println("Elapsed time : " + (t1 - t0) / 1000.0 + " Seconds")
   println("RequestPerSecond : " + requests.toDouble / ((t1 - t0) / 1000.0))
 }
}
