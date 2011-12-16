import cc.spray._
import directives.Remaining
import http.StatusCodes
import json._
import typeconversion.SprayJsonSupport
import http.MediaTypes._
import com.mongodb.casbah.Imports._

case class Msg(content: String)

object MsgJsonProtocol extends DefaultJsonProtocol {
  implicit val msgFormat = jsonFormat(Msg, "content")
}

trait MongoSupport {
  val akkaConfig = akka.config.Config.config
  val mongoHost = akkaConfig.getString("mongodb.host", "localhost")
  val mongoDb = akkaConfig.getString("mongodb.database", "msgs")
  val mongoColl = akkaConfig.getString("msgs.collection", "msgs")
  val mongoPort = akkaConfig.getInt("mongodb.port", 27017)

  val mongo = MongoConnection(mongoHost, mongoPort)(mongoDb)(mongoColl)
}

trait MsgService extends Directives
with SprayJsonSupport
with MongoSupport {

  import MsgJsonProtocol.msgFormat
  import MsgJsonProtocol.listFormat

  val service = {
    path("test") {
      get {
        respondWithMediaType(`text/html`) {
          _.complete {
            <html>
              <h1>Test resource</h1>
            </html>
          }
        }
      }
    } ~
      path("msgs") {
        get {
          respondWithMediaType(`text/html`) {
            _.complete {
              msgFormBody
            }
          } ~
            respondWithMediaType(`application/json`) {
              detach {
                _.complete((for (msg <- mongo) yield Msg(msg.getAs[String]("content").getOrElse("No content!"))).toList)
              }
            }
        } ~
          post {
            formFields('content) {
              content => ctx => {
                mongo += MongoDBObject("content" -> content)
                ctx.redirect("/msgs/")
              }
            }
          }
      } ~
      pathPrefix("msg" / Remaining) {
        id => {
          respondWithMediaType(`text/html`) {
            cache {
              detach {
                ctx =>
                  mongo.findOneByID(new ObjectId(id)) match {
                    case Some(msg) => {
                      ctx.complete(
                        <html>
                          <body>
                            <h1>
                              {msg.getOrElse("content", "No content!")}
                            </h1>
                          </body>
                        </html>
                      )
                    }
                    case _ => ctx.fail(StatusCodes.NotFound, "Msg %s not found" format id)
                  }
              }
            }
          } ~
            respondWithMediaType(`application/json`) {
              cache {
                detach {
                  ctx =>
                    mongo.findOneByID(new ObjectId(id)) match {
                      case Some(msg) => ctx.complete(Msg(msg.getAs[String]("content").getOrElse("No content!")))
                      case _ => ctx.fail(StatusCodes.NotFound, "Msg %s not found" format id)
                    }
                }
              }
            }
        }
      }
  }

  def msgFormBody = <html>
    <head>
      <title>Spray Mongo Example</title>
    </head>
    <body>
      <ul>
        {for (msg <- mongo) yield <li>
        <a href={"/msg/%s" format msg.get("_id")}>
          {msg.get("content")}
        </a>
      </li>}
      </ul>
      <form method="POST" action="/msgs">
          <input type="text" name="content"/>
          <input type="submit"/>
      </form>
    </body>
  </html>
} 
