
package gubo.gmc.client;

import gubo.gmc.shared.Authorization;

import com.google.gwt.core.client.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.event.dom.client.*;

@SuppressWarnings("nls")
public class GMCView extends Composite implements GMCPresenter.Display
{
	@UiField Button buttonLog;
	@UiField Label labelInfo;
	@UiField Label labelDebug;
	@UiField SimplePanel calendarContainer;
	@UiField Button buttonEmail;

	private static GMCViewUiBinder uiBinder = GWT.create( GMCViewUiBinder.class );

	interface GMCViewUiBinder extends UiBinder<Widget,GMCView> {}

	public GMCView() {
		initWidget( uiBinder.createAndBindUi( this ) );
		//labelInfo.setText( GMC.REDIRECT_URL );
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
		if ( authorization != null ) {
			//labelDebug.setText( System.currentTimeMillis() + " authorization<" + authorization.emailaddress + "," + authorization.accessCode + "," + authorization.token + ">" );
			if ( authorization.token != null ) {
				buttonLog.setText( "LOGOUT" );
			}
		} else {
			//labelDebug.setText( System.currentTimeMillis() + " authorization<null>" );
		}
	}

	@Override
	public void setName( final String title ) {
		labelInfo.setText( title );
	}
}
