
package gubo.gmc.client;

import com.google.gwt.event.shared.*;

import gubo.gmc.shared.*;

@SuppressWarnings("unused")
public class DoSendEvent extends GwtEvent<DoSendEventHandler>
{
	public static Type<DoSendEventHandler> TYPE = new Type<DoSendEventHandler>();
	
	@Override
	public Type<DoSendEventHandler> getAssociatedType() { return TYPE; }

	public final CalendarListItem calendar;
	
	public DoSendEvent( final CalendarListItem calendar ) {
		this.calendar = calendar;
	}
	
	@Override
	protected void dispatch( final DoSendEventHandler handler ) { handler.onDoSend( this ); }
}
