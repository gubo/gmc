
package gubo.gmc.client;

import com.google.gwt.event.shared.*;

@SuppressWarnings("unused")
public class DoLogoutEvent extends GwtEvent<DoLogoutEventHandler>
{
	public static Type<DoLogoutEventHandler> TYPE = new Type<DoLogoutEventHandler>();
	
	@Override
	public Type<DoLogoutEventHandler> getAssociatedType() { return TYPE; }

	@Override
	protected void dispatch( final DoLogoutEventHandler handler ) { handler.onDoLogout( this ); }
}
