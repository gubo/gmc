
package gubo.gmc.client;

import com.google.gwt.user.client.rpc.*;

import gubo.gmc.shared.*;

@RemoteServiceRelativePath( "rpcservice" )
public interface RPCService extends RemoteService 
{
	Ping ping( String name ) throws IllegalArgumentException;
	
	String getUUID() throws IllegalArgumentException;
	
	AuthorizationRequest requestAuthorization() throws IllegalArgumentException;
	
	Authorization getAuthorization() throws IllegalArgumentException;
	Authorization rmvAuthorization() throws IllegalArgumentException;
	
	CalendarList getCalendarList( Authorization authorization ) throws IllegalArgumentException;
	CalendarEvents getCalendarEvents( Authorization authorization,String calendarId ) throws IllegalArgumentException;
	
	DraftResponse requestDraft( Authorization authorization,CalendarListItem calendar ) throws IllegalArgumentException;
}
