package refactor

import javax.inject.Inject

import play.api.Environment
import play.api.libs.mailer._
import play.api.mvc.{AbstractController, ControllerComponents}

class EmailTestController @Inject()(mailer: MailerClient,
                                    environment: Environment,
                                    cc: ControllerComponents) extends AbstractController(cc) {

  def send = Action {
    val cid = "1234"
    val email = Email(
      "Simple email",
      "Mister FROM <from@email.com>",
      Seq("salao4989@gmail.com"),
//      attachments = Seq(
//        AttachmentFile("favicon.png", new File(environment.classLoader.getResource("public/images/favicon.png").getPath), contentId = Some(cid)),
//        AttachmentData("data.txt", "data".getBytes, "text/plain", Some("Simple data"), Some(EmailAttachment.INLINE))
//      ),
      bodyText = Some("A text message"),
      bodyHtml = Some(s"""<html><body><p>An <b>html</b> message with cid <img src="cid:$cid"></p></body></html>""")
    )
    val id = mailer.send(email)
    Ok(s"Email $id sent!")
  }

  def sendWithCustomMailer = Action {
    val mailer = new SMTPMailer(SMTPConfiguration("typesafe.org", 1234))
    val id = mailer.send(Email("Simple email", "Mister FROM <from@email.com>"))
    Ok(s"Email $id sent!")
  }
}

