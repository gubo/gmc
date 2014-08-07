
package gubo.gmc.server;

import java.net.*;

import com.google.appengine.api.oauth.*;
import com.google.appengine.api.users.*;
import com.google.appengine.api.urlfetch.*;

import com.google.gson.*;
import com.google.gwt.user.server.rpc.*;

import gubo.gmc.client.*;
import gubo.gmc.shared.*;

@SuppressWarnings( { "nls","serial" } )
public class RPCServiceImpl extends RemoteServiceServlet implements RPCService 
{
	@Override
	public Ping ping( final String name ) throws IllegalArgumentException {
		if ( name == null ) { throw new IllegalArgumentException( "name is null" ); }
		final Ping ping = new Ping();
		try {
			final String ename = escapeHTML( name );
			ping.greeting = "greetings " + ename;
		} catch ( Exception x ) {
			DBG.m( x );
		}
		return ping;
	}

	@Override
	public String getUUID() throws IllegalArgumentException {
		return java.util.UUID.randomUUID().toString();
	}
	
	@Override
	public AuthorizationRequest requestAuthorization() throws IllegalArgumentException {
		final AuthorizationRequest authorizationrequest = new AuthorizationRequest();
		try {
			final String scope = 	GMC.LOGIN_SCOPE_EMAIL + "%20" + 
									GMC.LOGIN_SCOPE_CALENDAR + "%20" + GMC.LOGIN_SCOPE_CALENDAR_READONLY + "%20" + 
									GMC.LOGIN_SCOPE_GMAIL_COMPOSE;
			
			final String state = GMC.fakeEmailAddress;
			
			final StringBuilder querybuilder = new StringBuilder();
			querybuilder.append( "?response_type=code" );
			querybuilder.append( "&client_id=" + GMC.CLIENT_ID );
			querybuilder.append( "&redirect_uri=" + GMC.REDIRECT_URL );
			querybuilder.append( "&scope=" + scope );
			querybuilder.append( "&access_type=online" );
			querybuilder.append( "&approval_prompt=force" );
			querybuilder.append( "&state=" + state );
			final String query = querybuilder.toString();
							          
			final String _url = "https://accounts.google.com/o/oauth2/auth" + query;
			
			final URL url = new URL( _url );
			final FetchOptions fetchOptions = FetchOptions.Builder.doNotFollowRedirects();
			final HTTPRequest httpRequest = new HTTPRequest( url,HTTPMethod.GET,fetchOptions );
			
			final URLFetchService fetchService = URLFetchServiceFactory.getURLFetchService();
			final com.google.appengine.api.urlfetch.HTTPResponse httpResponse = fetchService.fetch( httpRequest );
			
			final int responseCode = httpResponse.getResponseCode();
			switch ( responseCode ) {
			case 302:
				for ( final HTTPHeader header : httpResponse.getHeaders() ) {
					DBG.m( header.getName() + " " + header.getValue() );
					if ( "Location".equalsIgnoreCase( header.getName() ) ) {
						authorizationrequest.redirectURL = header.getValue();
					}
				}
				break;
			default:
				throw new Exception( "failed request authorization: " + responseCode );
			}
		} catch ( Exception x ) {
			DBG.m( x );
		}
		return  authorizationrequest;
	}
	
	@Override
	public Authorization getAuthorization() throws IllegalArgumentException {
		final Authorization authorization = new Authorization(); 
		try {
			final EMail email = new EMail();
			try {
				final OAuthService oauthService = OAuthServiceFactory.getOAuthService();
				final User user = oauthService.getCurrentUser();
				email.address = user.getEmail();
			} catch ( Exception x ) {
				email.address = GMC.fakeEmailAddress; // TODO: this is a temporary hack
				DBG.m( x );
			}
			final Authorization a = Data.authorizations.get( email );
			if ( a != null ) {
				authorization.emailaddress = a.emailaddress;
				authorization.accessCode = a.accessCode;
				authorization.token = a.token;
			}
		} catch ( Exception x ) {
			DBG.m( x );
		}
		return authorization;
	}

	private static String escapeHTML( final String html ) {
		if ( html == null ) { return null;  }
		return html.replaceAll( "&", "&amp;" ).replaceAll( "<", "&lt;" ).replaceAll( ">", "&gt;" );
	}

	@Override
	public Authorization rmvAuthorization() throws IllegalArgumentException {
		final Authorization authorization = new Authorization(); 
		try {
			final EMail email = new EMail();
			try {
				final OAuthService oauthService = OAuthServiceFactory.getOAuthService();
				final User user = oauthService.getCurrentUser();
				email.address = user.getEmail();
			} catch ( Exception x ) {
				email.address = GMC.fakeEmailAddress; // TODO: this is a temporary hack
				DBG.m( x );
			}
			final Authorization a = Data.authorizations.get( email );
			if ( a != null ) {
				authorization.emailaddress = a.emailaddress;
				authorization.accessCode = a.accessCode;
				authorization.token = a.token;
			}
			Data.authorizations.remove( email );
		} catch ( Exception x ) {
			DBG.m( x );
		}
		return authorization;
	}

	/*
	 * https://developers.google.com/google-apps/calendar/v3/reference/calendarList/list#examples
	 */
	@Override
	public CalendarList getCalendarList( final Authorization authorization ) throws IllegalArgumentException {
		CalendarList calendarlist = new CalendarList(); 
		try {
			final StringBuilder querybuilder = new StringBuilder();
			querybuilder.append( "?key=" + GMC.API_KEY );
			final String query = querybuilder.toString();
			
			final String _url = "https://www.googleapis.com/calendar/v3/users/me/calendarList" + query;
			DBG.m( "StringBuilder: " + _url );

			final URL url = new URL( _url );
			final FetchOptions fetchOptions = FetchOptions.Builder.doNotFollowRedirects();
			final HTTPRequest httpRequest = new HTTPRequest( url,HTTPMethod.GET,fetchOptions );
			httpRequest.setHeader( new HTTPHeader( "Authorization","Bearer " + authorization.token.access_token ) );
			
			final URLFetchService fetchService = URLFetchServiceFactory.getURLFetchService();
			final com.google.appengine.api.urlfetch.HTTPResponse httpResponse = fetchService.fetch( httpRequest );
			
			final int responseCode = httpResponse.getResponseCode();
			switch ( responseCode ) {
			case 200:
				final String json = new String( httpResponse.getContent(),"UTF-8" );
				calendarlist = new Gson().fromJson( json,CalendarList.class );
				break;
			default:
				throw new Exception( "failed get calendarList: " + responseCode );
			}
		} catch ( Exception x ) {
			DBG.m( x );
		}
		return calendarlist;
	}

	@Override
	public CalendarEvents getCalendarEvents( final Authorization authorization,final String calendarId ) throws IllegalArgumentException {
		CalendarEvents calendarevents = new CalendarEvents(); 
		try {
			final StringBuilder querybuilder = new StringBuilder();
			querybuilder.append( "?key=" + GMC.API_KEY );
			final String query = querybuilder.toString();
			
			final String idpart = URLEncoder.encode( ""+calendarId,"UTF-8" ); 
			final String _url = "https://www.googleapis.com/calendar/v3/calendars/" + idpart + "/events" + query;
			DBG.m( "StringBuilder: " + _url );

			final URL url = new URL( _url );
			final FetchOptions fetchOptions = FetchOptions.Builder.doNotFollowRedirects();
			final HTTPRequest httpRequest = new HTTPRequest( url,HTTPMethod.GET,fetchOptions );
			httpRequest.setHeader( new HTTPHeader( "Authorization","Bearer " + authorization.token.access_token ) );
			
			final URLFetchService fetchService = URLFetchServiceFactory.getURLFetchService();
			final com.google.appengine.api.urlfetch.HTTPResponse httpResponse = fetchService.fetch( httpRequest );
			
			final int responseCode = httpResponse.getResponseCode();
			switch ( responseCode ) {
			case 200:
				final String json = new String( httpResponse.getContent(),"UTF-8" );
				calendarevents = new Gson().fromJson( json,CalendarEvents.class );
				break;
			default:
				throw new Exception( "failed get calendarEvents: " + responseCode );
			}
		} catch ( Exception x ) {
			DBG.m( x );
		}
		return calendarevents;
	}

	@Override
	public DraftResponse requestDraft( final Authorization authorization,final CalendarListItem calendar ) throws IllegalArgumentException {
		DraftResponse draftresponse = new DraftResponse();
		try {
			final CalendarEvents events = this.getCalendarEvents( authorization,calendar.id );
			
			final StringBuilder querybuilder = new StringBuilder();
			querybuilder.append( "?key=" + GMC.API_KEY );
			final String query = querybuilder.toString();

			final String idpart = URLEncoder.encode( ""+calendar.id,"UTF-8" );
			final String _url = "https://www.googleapis.com/gmail/v1/users/" + idpart + "/drafts" + query;
			
			DBG.m( "draft: " + _url );
			
			StringBuilder bodybuilder = new StringBuilder();
			bodybuilder.append( "\n" );
			bodybuilder.append( "SENT FROM GMC:" );
			bodybuilder.append( "\n" );
			for ( final CalendarEventItem item : events.items ) {
				final String line = Utils.getPrettyTime( item.start.dateTime ) + " - " + Utils.getPrettyTime( item.end.dateTime ) + "  " + item.summary;
				bodybuilder.append( line );
				bodybuilder.append( "\n" );
			}
			bodybuilder.append( "\n" );
			final String body = com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString( bodybuilder.toString().getBytes() );
			
			final DraftRequest draftrequest = new DraftRequest();
			draftrequest.id = calendar.id;
			draftrequest.message = new DraftRequestMessage();
			draftrequest.message.raw = body;
			final String payload = new Gson().toJson( draftrequest );
			
			final URL url = new URL( _url );
			final FetchOptions fetchOptions = FetchOptions.Builder.doNotFollowRedirects();
			final HTTPRequest httpRequest = new HTTPRequest( url,HTTPMethod.POST,fetchOptions );
			httpRequest.setHeader( new HTTPHeader( "Content-Type","application/json" ) );
			httpRequest.setHeader( new HTTPHeader( "Authorization","Bearer " + authorization.token.access_token ) );
			httpRequest.setPayload( payload.getBytes() );
			
			final URLFetchService fetchService = URLFetchServiceFactory.getURLFetchService();
			final com.google.appengine.api.urlfetch.HTTPResponse httpResponse = fetchService.fetch( httpRequest );
			
			final int responseCode = httpResponse.getResponseCode();
			switch ( responseCode ) {
			case 200:
				final String json = new String( httpResponse.getContent(),"UTF-8" );
				draftresponse = new Gson().fromJson( json,DraftResponse.class );
				DBG.m( json );
				break;
			default:
				throw new Exception( "failed get calendarEvents: " + responseCode );
			}
		} catch ( Exception x ) {
			DBG.m( x );
		}
		return draftresponse;
	}
}









