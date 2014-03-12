package controllers

import play.mvc.Controller
import play.api.mvc.Action
import play.api.mvc.Results._
import twitter4j.TwitterFactory
import com.typesafe.config.ConfigFactory
import scala.util.Marshal
import scala.collection.immutable.StringOps
import twitter4j.auth.RequestToken

object TwitterAuthentication extends Controller {
  
  val config = ConfigFactory.load("twitter.conf")
  
  val twitter = (new TwitterFactory).getInstance
		twitter.setOAuthConsumer(config.getString("twitter4j.oauth.consumerKey"),
		config.getString("twitter4j.oauth.consumerSecret"))
  
  def authenticate = Action { req =>
    
		val callbackURL =  s"http://${req.host}/twitter/callback"
		val requestToken = twitter.getOAuthRequestToken(callbackURL)
				
		Redirect(requestToken.getAuthenticationURL())
  }
  
  def callback = Action { req =>
    
  		(twitter, req.getQueryString("oauth_verifier")) match {
  		  
  		  case (twitter , Some(oauthVerifier)) => {
  		      
  		      val accessToken = twitter.getOAuthAccessToken(oauthVerifier)
  		      
  		      println(accessToken.getToken)
			  println(accessToken.getTokenSecret)
			  
			  Ok("Ver nice!")
  		  }
  		  case _ =>
  		      Forbidden
  		}
    
    
  }
  
}