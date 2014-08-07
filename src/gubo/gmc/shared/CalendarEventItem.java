
package gubo.gmc.shared;

@SuppressWarnings("serial")
public class CalendarEventItem implements java.io.Serializable
{
	/*
	 * https://developers.google.com/google-apps/calendar/concepts
	 */
	
	public String kind;
	public String etag;
	public String id;
	public String status;
	public String htmlLink;
	public String created;
	public String updated;
	public String summary;
	public CalendarEventItemCreator creator; 
	public CalendarEventItemOrganizer organizer;
	public CalendarEventItemDateTime start;
	public CalendarEventItemDateTime end;
	public String iCalUID; 
	public int sequence;
	public CalendarEventItemReminders reminders;
}
