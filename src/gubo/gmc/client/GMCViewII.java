
package gubo.gmc.client;

import gubo.gmc.shared.Authorization;

import com.google.gwt.core.client.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.event.dom.client.*;

@SuppressWarnings("nls")
public class GMCViewII extends Composite implements GMCPresenter.Display
{
	@UiField Button buttonLog;
	@UiField SimplePanel calendarContainer;
	@UiField Label labelInfo;
	@UiField Button buttonEmail;

	private static GMCViewIIUiBinder uiBinder = GWT.create( GMCViewIIUiBinder.class );

	interface GMCViewIIUiBinder extends UiBinder<Widget,GMCViewII> {}

	public GMCViewII() {
		initWidget( uiBinder.createAndBindUi( this ) );
		buttonEmail.setEnabled( false );
	}

	@Override
	public HasWidgets asCalendarContainer() { return calendarContainer; }

	@Override
	public boolean isLogin() { return "LOGIN".equals( buttonLog.getText() ); }

	@Override
	public boolean isLogout() { return "LOGOUT".equals( buttonLog.getText() ); }

	@Override
	public HasClickHandlers getLogButton() { return buttonLog; }

	@Override
	public HasClickHandlers getSendButton() { return buttonEmail; }
	
	@Override
	public void setAuthorization( final Authorization authorization ) {
		buttonLog.setText( "LOGIN" );
		buttonEmail.setEnabled( false );
		if ( authorization != null ) {
			if ( authorization.token != null ) {
				buttonEmail.setEnabled( true );
				buttonLog.setText( "LOGOUT" );
			}
		}
	}

	@Override
	public void setName( final String title ) {
		labelInfo.setText( title );
	}
}
