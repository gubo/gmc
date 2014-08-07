
package gubo.gmc.client;

import com.google.gwt.user.client.*;
import com.google.gwt.user.client.rpc.*;

import gubo.gmc.shared.*;

class RemoveAuthorization 
{
	@SuppressWarnings("static-method")
	void go() {
		final AsyncCallback<Authorization> callback = new AsyncCallback<Authorization>() {
			@Override
			public void onSuccess( final Authorization a ) {
				Window.Location.assign( GMC.HOME_URL );
			}
			@Override
			public void onFailure( Throwable t ) {
				DBG.m( t );
			}
		};
		GMC.rpcService.rmvAuthorization( callback );
	}
}
