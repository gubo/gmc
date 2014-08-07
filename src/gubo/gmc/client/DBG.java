
package gubo.gmc.client;

public class DBG 
{
	public static void m( final String s ) { 
		System.out.println( s );
	}
	
	public static void m( final Throwable t ) { 
		if ( t != null ) { 
			t.printStackTrace();
		}
	}
	
	private DBG() {}
}