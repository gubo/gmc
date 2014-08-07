
package gubo.gmc.client;

import com.google.gwt.user.client.ui.*;
import com.google.gwt.event.dom.client.*;

import gubo.gmc.shared.*;

public class GMCPresenter implements Presenter
{
	public interface Display
	{
		public Widget asWidget();
		public HasWidgets asCalendarContainer();
		public boolean isLogin();
		public boolean isLogout();
		public HasClickHandlers getLogButton();
		public HasClickHandlers getSendButton();
		public void setAuthorization( Authorization authorization );
		public void setName( String title );
	}

	private final GMCPresenter.Display display;
	private final Authorization authorization;
	private CalendarListItem calendar;
	
	public GMCPresenter( final GMCPresenter.Display display,final Authorization authorization ) {
		this.display = display;
		this.authorization = authorization;
		bind();
	}
	
	@Override
	public void go( final HasWidgets container ) {
		container.clear();
		container.add( display.asWidget() );
		
		display.setAuthorization( authorization );
		
		if ( Authorization.isAuthorized( authorization ) ) {
			final CalendarPresenter.Display calendardisplay = new CalendarView();
			final Presenter presenter = new CalendarPresenter( calendardisplay,authorization );
			presenter.go( display.asCalendarContainer() );
		}
	}
	
	@SuppressWarnings("unused")
	private void bind() {
		LOGINBUTTON: {
			final ClickHandler clickhandler = new ClickHandler() {
				@Override
				@SuppressWarnings("synthetic-access")
				public void onClick( final ClickEvent event ) {
					if ( display.isLogin() ) {
						GMC.eventBus.fireEvent( new DoLoginEvent() );
					} else {
						GMC.eventBus.fireEvent( new DoLogoutEvent() );
					}
				} 
			};
			display.getLogButton().addClickHandler( clickhandler );		
		}

		PRIMARYEVENT: {
			final PrimaryCalendarEventHandler handler = new PrimaryCalendarEventHandler() {
				@Override
				@SuppressWarnings("synthetic-access")
				public void onPrimaryCalendar( final PrimaryCalendarEvent event ) {
					calendar = event.primary;
					display.setName( calendar.id );
				}
			};
			GMC.eventBus.addHandler( PrimaryCalendarEvent.TYPE,handler );
		}
		
		SENDBUTTON: {
			final ClickHandler clickhandler = new ClickHandler() {
				@SuppressWarnings("synthetic-access")
				@Override
				public void onClick( final ClickEvent event ) {
					GMC.eventBus.fireEvent( new DoSendEvent( calendar ) );
				} 
			};
			display.getSendButton().addClickHandler( clickhandler );		
		}
	}
}
