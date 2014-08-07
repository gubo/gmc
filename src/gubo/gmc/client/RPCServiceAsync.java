
package gubo.gmc.client;

import com.google.gwt.user.client.rpc.*;

import gubo.gmc.shared.*;

public interface RPCServiceAsync 
{
	void ping( String name,AsyncCallback<Ping> callback ) throws IllegalArgumentException;
	
	void getUUID( AsyncCallback<String> callback ) throws IllegalArgumentException;
	
	void requestAuthorization( AsyncCallback<AuthorizationRequest> callback ) throws IllegalArgumentException;
	
	void getAuthorization( AsyncCallback<Authorization> callback ) throws IllegalArgumentException;
	void rmvAuthorization( AsyncCallback<Authorization> callback ) throws IllegalArgumentException;
	
	void getCalendarList( Authorization authorization,AsyncCallback<CalendarList> callback ) throws IllegalArgumentException;
	void getCalendarEvents( Authorization authorization,String calendarId,AsyncCallback<CalendarEvents> callback ) throws IllegalArgumentException;
	
	void requestDraft( Authorization authorization,CalendarListItem calendar,AsyncCallback<DraftResponse> callback ) throws IllegalArgumentException;
}
