
package gubo.gmc.client;

import com.google.gwt.event.shared.*;

import gubo.gmc.shared.*;

@SuppressWarnings("unused")
public class PrimaryCalendarEvent extends GwtEvent<PrimaryCalendarEventHandler>
{
	public static Type<PrimaryCalendarEventHandler> TYPE = new Type<PrimaryCalendarEventHandler>();
	
	@Override
	public Type<PrimaryCalendarEventHandler> getAssociatedType() { return TYPE; }

	public final CalendarListItem primary;

	public PrimaryCalendarEvent( final CalendarListItem primary ) {
		this.primary = primary;
	}
	
	@Override
	protected void dispatch( final PrimaryCalendarEventHandler handler ) { handler.onPrimaryCalendar( this ); }
}
