
package gubo.gmc.client;

import com.google.gwt.event.shared.*;

@SuppressWarnings("unused")
public class DoLoginEvent extends GwtEvent<DoLoginEventHandler>
{
	public static Type<DoLoginEventHandler> TYPE = new Type<DoLoginEventHandler>();
	
	@Override
	public Type<DoLoginEventHandler> getAssociatedType() { return TYPE; }

	@Override
	protected void dispatch( final DoLoginEventHandler handler ) { handler.onDoLogin( this ); }
}
