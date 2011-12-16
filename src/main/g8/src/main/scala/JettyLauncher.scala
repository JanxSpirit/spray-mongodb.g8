import org.slf4j.LoggerFactory
import org.eclipse.jetty.servlet.ServletContextHandler
import cc.spray.connectors.Servlet30ConnectorServlet
import java.util.Calendar
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.nio.SelectChannelConnector
import akka.event.slf4j.Logging

object JettyLauncher extends Logging {

  def main(args: Array[String]): Unit = {

    val port: Int = 8080

    val server: Server = new Server()

    //localhost connector
    val connector = new SelectChannelConnector();
    connector.setPort(port);
    server.addConnector(connector);

    val context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
    context.setContextPath("/");
    server.setHandler(context);

    context.addServlet(classOf[Servlet30ConnectorServlet], "/*");

    val initializer: SprayInitializer = new SprayInitializer();
    context.addEventListener(initializer);

    // finally, start the Jetty Server
    server.start()
    server.join()
  }
}