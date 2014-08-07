
package gubo.gmc.client;

import com.google.gwt.core.client.*;
import com.google.gwt.event.shared.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.*;

import gubo.gmc.shared.*;

@SuppressWarnings("nls")
public class GMC implements EntryPoint 
{
	public static final String CLIENT_ID = "734548113195-stjg1t5i9bq9p0f66b5hk3ia2q5apea0.apps.googleusercontent.com";
	public static final String CLIENT_SECRET = "pKEwhtRyPpxdtL3cSxEYp8z3";
	public static final String API_KEY = "AIzaSyA1PJmrr8RAj3io0cuSYOyU-HMiyaxHO9s";
	
//	public static final String REDIRECT_URL = "http://localhost:8888/gmc/oauthredirect?gwt.codesvr=localhost:9997";
//	public static final String HOME_URL = "http://localhost:8888/index.html?gwt.codesvr=localhost:9997";
	public static final String REDIRECT_URL = "http://gubo-xp.appspot.com/gmc/oauthredirect";
	public static final String HOME_URL = "/";

	public static final String GMAIL_COMPOSE_URL = "https://mail.google.com/mail/u/0/#drafts"; // TODO: HOW CAN GET THIS FROM CMAIL API ?
	
	public static final String LOGIN_SCOPE_EMAIL = "email";
	public static final String LOGIN_SCOPE_CALENDAR = "https://www.googleapis.com/auth/calendar";
	public static final String LOGIN_SCOPE_CALENDAR_READONLY = "https://www.googleapis.com/auth/calendar.readonly";
	public static final String LOGIN_SCOPE_GMAIL_COMPOSE = "https://www.googleapis.com/auth/gmail.compose";
	
	public static RPCServiceAsync rpcService; 
	public static HandlerManager eventBus; 
	
	public static final String fakeEmailAddress = "fakeemail@nowhere.net";
	
	/*
	 * https://developers.google.com/accounts/docs/GettingStarted?csw=1
	 * https://developers.google.com/google-apps/calendar/
	 */
	
	@Override
	public void onModuleLoad() {
		DBG.m( "GMCModule.onLoad " + GWT.getModuleBaseURL() + " " + System.currentTimeMillis() );

		GMCResources.instance.css().ensureInjected();

		// ISSUE 7527: java.lang.ClassNotFoundException: com.google.gwt.core.client.GWTBridge occurs if i put these static final initialize outside of method
		GMC.rpcService = GWT.create( RPCService.class );
		GMC.eventBus = new HandlerManager( null );

		getAuthorization();
	}
	
	@SuppressWarnings("static-method")
	private void getAuthorization() {
		final AsyncCallback<Authorization> callback = new AsyncCallback<Authorization>() {
			@Override
			public void onSuccess( final Authorization authorization ) {
				final GMCController controller = new GMCController( authorization );
				controller.go( RootLayoutPanel.get() );
			}
			@Override
			public void onFailure( final Throwable t ) {
				DBG.m( t );
			}
		};
		GMC.rpcService.getAuthorization( callback );
	}
}
