
package gubo.gmc.client;

import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.*;

import gubo.gmc.shared.*;

public class CalendarPresenter implements Presenter
{
	public interface Display
	{
		public Widget asWidget();
		public void showCalendarEvents( CalendarEvents calendarEvents );
	}
	
	private final CalendarPresenter.Display display;
	private final Authorization authorization;
	
	public CalendarPresenter( final CalendarPresenter.Display display,final Authorization authorization ) {
		this.display = display;
		this.authorization = authorization;
	}
	
	@Override
	public void go( final HasWidgets container ) {
		container.clear();
		container.add( display.asWidget() );
		bind();
		getCalendarList();
	}
	
	private void bind() {
		
	}
	
	private void getCalendarList() {
		AsyncCallback<CalendarList> callback = new AsyncCallback<CalendarList>() {
			@Override
			@SuppressWarnings("synthetic-access")
			public void onSuccess( final CalendarList calendarList ) {
				CalendarListItem primary = CalendarPresenter.getPrimaryCalendarListItem( calendarList );
				if ( primary != null ) {
					getCalendarEvents( primary );
					GMC.eventBus.fireEvent( new PrimaryCalendarEvent( primary ) );
				} else {
					// TODO: show failure to get calendar
				}
			}
			@Override
			public void onFailure( final Throwable t ) {
				DBG.m( t );
			}
		};
		GMC.rpcService.getCalendarList( authorization,callback );
	}
	
	private void getCalendarEvents( final CalendarListItem item ) {
		if ( item == null ) { return; }
		AsyncCallback<CalendarEvents> callback = new AsyncCallback<CalendarEvents>() {
			@SuppressWarnings("synthetic-access")
			@Override
			public void onSuccess( final CalendarEvents calendarEvents ) {
				display.showCalendarEvents( calendarEvents );
			}
			@Override
			public void onFailure( Throwable t ) {
				DBG.m( t );
			}
		};
		GMC.rpcService.getCalendarEvents( authorization,item.id,callback );
	}
	
	private static CalendarListItem getPrimaryCalendarListItem( final CalendarList calendarList ) {
		CalendarListItem primary = null;
		if ( (calendarList != null) && (calendarList.items != null) ) {
			for ( final CalendarListItem item : calendarList.items ) {
				if ( item != null ) {
					if ( item.primary ) {
						primary = item;
						break;
					}
				}
			}
		}
		return primary;
	}
}
