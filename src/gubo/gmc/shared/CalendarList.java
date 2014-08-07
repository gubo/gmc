
package gubo.gmc.shared;

@SuppressWarnings("serial")
public class CalendarList implements java.io.Serializable
{
	public String kind;
	public String etag;
	public String nextSynctoken;
	public CalendarListItem[] items = new CalendarListItem[ 0 ];
}
