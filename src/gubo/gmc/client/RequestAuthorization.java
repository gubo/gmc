
package gubo.gmc.client;

import com.google.gwt.user.client.*;
import com.google.gwt.user.client.rpc.*;

import gubo.gmc.shared.*;

@SuppressWarnings("nls")
public class RequestAuthorization 
{
	@SuppressWarnings("static-method")
	void go() {
		final AsyncCallback<AuthorizationRequest> callback = new AsyncCallback<AuthorizationRequest>() {
			@Override
			public void onSuccess( final AuthorizationRequest authorizationrequest ) {
				if ( authorizationrequest.redirectURL != null ) {
					Window.Location.assign( authorizationrequest.redirectURL );
				} else {
					DBG.m( "FAILED REDIRECT FROM REQUESTAUTHORIZATION" );
				}
			}
			@Override
			public void onFailure( Throwable t ) {
				DBG.m( t );
			}
		};
		GMC.rpcService.requestAuthorization( callback );
	}
}
