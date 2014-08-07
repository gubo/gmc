
package gubo.gmc.client;

import com.google.gwt.user.client.*;
import com.google.gwt.user.client.rpc.*;

import gubo.gmc.shared.*;

@SuppressWarnings("nls")
class RequestSend 
{
	private final CalendarListItem calendar;
	private final Authorization authorization;
	
	RequestSend( final Authorization authorization,final CalendarListItem calendar ) {
		this.calendar = calendar;
		this.authorization = authorization; 
	}
	
	void go() {
		final AsyncCallback<DraftResponse> callback = new AsyncCallback<DraftResponse>() {
			@Override
			public void onSuccess( final DraftResponse draftresponse ) {
				try {
					final String redirectURL = GMC.GMAIL_COMPOSE_URL + "?compose=" + draftresponse.message.id;
					Window.Location.assign( redirectURL );
				} catch ( Exception x ) {
					DBG.m( x );
				}
			}
			@Override
			public void onFailure( final Throwable t ) {
				DBG.m( t );
			}
		};
		GMC.rpcService.requestDraft( authorization,calendar,callback );
	}
}
