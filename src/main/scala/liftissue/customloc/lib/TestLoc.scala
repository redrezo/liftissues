package liftissue.customloc.lib {

  import _root_.scala.xml.{ NodeSeq, Text }

  import _root_.net.liftweb.http._
  import S._
  import provider.HTTPRequest

  import _root_.net.liftweb.common._
  import _root_.net.liftweb.util._

  import _root_.net.liftweb.sitemap._

  import _root_.net.liftweb.util._
  import Helpers._

  
  abstract class TestPage
  case class Default(id: String) extends TestPage
  
  object TestLoc extends Loc[TestPage] with Loggable {
    import Loc._
    val name = "TestPage"
    val text = new LinkText[TestPage](_ match {
      case Default(id) => Text("TestPage id=" + id )
    })
    
    def link = new Link[TestPage]( "showTest" :: Nil )
    
//    def link = new Link[TestPage](currentValue match {
//    	case Full(Default(id)) => "test" :: id :: Nil
//    	case _ => "test" :: "default" :: Nil
//    }) 

    def defaultValue = Full(Default("default"))

    def params =  Nil

    override def rewrite = Full({
    case RewriteRequest(ParsePath( "test" :: id :: Nil, _, _, _), _, _) => 
    	(RewriteResponse( "showTest" :: Nil), Default(id))
    case RewriteRequest(ParsePath( "test" :: id :: "index" :: Nil, _, _, _), _, _) => 
    	(RewriteResponse( "showTest" :: Nil), Default(id))
    })

    // TODO find out where title is used
    override def title(param: TestPage) = param match {
      case Default(id) => Text("This is the the title of " + id)
    }

    override def snippets = {
      case ("testTitle", Full(Default(id))) => "h2 *" #> Text("TestPage id="+id)
      case ("testTitle", _) => "*" #> Text("WTF?")
    }
  }
}