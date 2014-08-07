
package gubo.gmc.shared;

@SuppressWarnings("serial")
public class CalendarEvents implements java.io.Serializable
{
	 public String kind;
	 public String etag; 
	 public String summary; 
	 public String updated; 
	 public String timeZone; 
	 public String accessRole;
	 public CalendarEventReminder [] defaultReminders = new CalendarEventReminder[ 0 ];
	 public String nextSyncToken;
	 public CalendarEventItem [] items = new CalendarEventItem[ 0 ]; 
}
