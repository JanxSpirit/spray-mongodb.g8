import org.specs2.mutable._
import cc.spray._
import test._
import http._
import HttpMethods._
import StatusCodes._

class MsgServiceSpec extends Specification with SprayTest with MsgService {
  
  "The HelloService" should {
    "return a greeting for GET requests to the test path" in {
      testService(HttpRequest(GET, "/test")) {
        service
      }.response.content.as[String] mustEqual Right("<html>\n              <h1>Test resource</h1>\n            </html>")
    }
    "leave GET requests to /kermit path unhandled" in {
      testService(HttpRequest(GET, "/kermit")) {
        service
      }.handled must beFalse
    }
    "return a MethodNotAllowed error for POST requests to the test path" in {
      testService(HttpRequest(POST, "/test")) {
        service
      }.response mustEqual HttpResponse(MethodNotAllowed, "HTTP method not allowed, supported methods: GET")
    }
  }
  
}
