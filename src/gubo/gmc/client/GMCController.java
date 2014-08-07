
package gubo.gmc.client;

import com.google.gwt.user.client.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.event.logical.shared.*;

import gubo.gmc.shared.*;

@SuppressWarnings("nls")
public class GMCController implements ValueChangeHandler<String>
{
	private final Authorization authorization;
	private HasWidgets container;
	
	public GMCController( final Authorization authorization ) {
		this.authorization = authorization;
	}
	
	public void go( final HasWidgets _container ) {
		bind();
		this.container = _container;
	    if ( "".equals( History.getToken() ) ) {
	    	final GMCPresenter.Display display = new GMCViewII();
		    final Presenter presenter = new GMCPresenter( display,authorization );
		    presenter.go( container );
	    } else {
	    	History.fireCurrentHistoryState();
	    }		
	}
	
	@Override
	public void onValueChange( final ValueChangeEvent<String> event ) {
		Presenter presenter = null;
		final String token = event.getValue();
		if ( GMCPresenter.class.getName().equals( token ) ) {
			final GMCPresenter.Display display = new GMCViewII();
			presenter = new GMCPresenter( display,authorization );
		}
		if ( presenter != null ) { presenter.go( container ); }
	}

	@SuppressWarnings({ "unused" })
	private void bind() {
		DOLOGINEVENTHANDLER: {
			final DoLoginEventHandler handler = new DoLoginEventHandler() {
				@Override
				public void onDoLogin( final DoLoginEvent event ) {
					new RequestAuthorization().go();
				} 
			};
			GMC.eventBus.addHandler( DoLoginEvent.TYPE,handler );
		}

		DOSENDEVENTHANDLER: {
			final DoSendEventHandler handler = new DoSendEventHandler() {
				@Override
				@SuppressWarnings("synthetic-access")
				public void onDoSend( final DoSendEvent event ) {
					new RequestSend( authorization,event.calendar ).go();
				} 
			};
			GMC.eventBus.addHandler( DoSendEvent.TYPE,handler );
		}
		
		DOLOGOUTEVENTHANDLER: {
			final DoLogoutEventHandler handler = new DoLogoutEventHandler() {
				@Override
				public void onDoLogout( final DoLogoutEvent event ) {
					new RemoveAuthorization().go();
				} 
			};
			GMC.eventBus.addHandler( DoLogoutEvent.TYPE,handler );
		}
	}
}
