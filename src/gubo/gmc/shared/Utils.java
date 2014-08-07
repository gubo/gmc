
package gubo.gmc.shared;

import gubo.gmc.client.DBG;

@SuppressWarnings("nls")
public class Utils 
{
	public static String getPrettyYear( final String dateTimeString ) {
		String pretty = "";
		try {
			// RFC 3339 "yyyy-mm-ddTHH:MM:ss" 
			// ex. 2011-06-03T10:00:00-07:00 (optional ms)
			
			final String [] split = dateTimeString.split( "T" );
			
			final String date = split[ 0 ];
			final String [] yyyymmdd = date.split( "-" );
			//final String yyyy = yyyymmdd[ 0 ];
			final String   mm = yyyymmdd[ 1 ];
			final String   dd = yyyymmdd[ 2 ];

			pretty = mm+"-"+dd; //+"-"+yyyy;
		} catch ( Exception x ) {
			DBG.m( x );
		}
		
		return pretty;
	}
	
	public static String getPrettyTime( final String dateTimeString ) {
		String pretty = "";
		try {
			// RFC 3339 "yyyy-mm-ddTHH:MM:ss" 
			// ex. 2011-06-03T10:00:00-07:00 (optional ms)
			
			final String [] split = dateTimeString.split( "T" );
			
			final String time = split[ 1 ];
			final String [] HHMMss = time.split( ":" );
			final String HH = HHMMss[ 0 ];
			final String MM = HHMMss[ 1 ];
			
			boolean am = true;
			
			int hour = Integer.parseInt( HH );
			if ( hour > 12 ) { 
				hour -= 12;
				am = false;
			}
			if ( hour == 12 ) { am = false; }
			
			pretty = hour+":"+MM+(am ? "AM" : "PM");
		} catch ( Exception x ) {
			DBG.m( x );
		}
		
		return pretty;
	}
	
	private Utils() {}
}
