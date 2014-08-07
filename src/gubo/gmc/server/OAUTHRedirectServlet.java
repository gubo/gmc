
package gubo.gmc.server;

import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.google.appengine.api.oauth.*;
import com.google.appengine.api.users.*;
import com.google.appengine.api.urlfetch.*;

import com.google.gson.*;

import gubo.gmc.client.*;
import gubo.gmc.shared.*;

@SuppressWarnings({ "nls", "serial" })
public class OAUTHRedirectServlet extends HttpServlet 
{
	/*
	 * https://developers.google.com/accounts/docs/OAuth2WebServer
	 * https://developers.google.com/google-apps/tasks/oauth-authorization-callback-handler
	 */
	
	@Override
	public void doGet( final HttpServletRequest request,final HttpServletResponse response ) throws ServletException,IOException {
		try {
			final String state = request.getParameter( "state" );
			
			final String code = request.getParameter( "code" );
			final StringBuilder parameters = new StringBuilder();
			parameters.append( "code=" + code );
			parameters.append( "&client_id=" + GMC.CLIENT_ID );
			parameters.append( "&client_secret=" + GMC.CLIENT_SECRET );
			parameters.append( "&redirect_uri=" + GMC.REDIRECT_URL );
			parameters.append( "&grant_type=authorization_code" );
			
			final URL url = new URL( "https://accounts.google.com/o/oauth2/token" );
			final FetchOptions fetchOptions = FetchOptions.Builder.followRedirects();
			final com.google.appengine.api.urlfetch.HTTPRequest httpRequest = new com.google.appengine.api.urlfetch.HTTPRequest( url,HTTPMethod.POST,fetchOptions );
			httpRequest.setHeader( new HTTPHeader( "Host","accounts.google.com" ) );
			httpRequest.setHeader( new HTTPHeader( "Content-Type","application/x-www-form-urlencoded" ) );
			httpRequest.setPayload( parameters.toString().getBytes() );

			final URLFetchService fetchService = URLFetchServiceFactory.getURLFetchService();
			final com.google.appengine.api.urlfetch.HTTPResponse httpResponse = fetchService.fetch( httpRequest );
			
			final int responseCode = httpResponse.getResponseCode();
			switch ( responseCode ) {
			case 200:
				final String json = new String( httpResponse.getContent(),"UTF-8" );
				final Token token = new Gson().fromJson( json,Token.class );
				final Authorization authorization = new Authorization();
				
				final EMail email = new EMail();
				try {
					final OAuthService oauthService = OAuthServiceFactory.getOAuthService();
					final User user = oauthService.getCurrentUser();
					email.address = user.getEmail();
				} catch ( Exception x ) {
					email.address = state; // TODO: this is a temporary hack
					DBG.m( x );
				}
				
				authorization.emailaddress = email.address;
				authorization.accessCode = code;
				authorization.token = token;
				Data.authorizations.put( email,authorization );

				response.sendRedirect( GMC.HOME_URL );
				break;
			default:
				throw new Exception( "failed get token: " + responseCode );
			}
		} catch ( ServletException x ) {
			throw x;
		} catch ( IOException x ) {
			throw x;
		} catch ( Exception x ) {
			DBG.m( x );
			response.setContentType( "text/plain" );
			response.getWriter().print( x.getClass() + " " + x.getMessage() );
			throw new IOException( x );
		}
	}
}
