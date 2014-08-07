
package gubo.gmc.server;

public class DBG 
{
	/*
	 * TODO: proper server-side logging
	 */
	
	public static void m( final String s ) { System.out.println( s ); }
	
	public static void m( final Throwable t ) { if ( t != null ) { t.printStackTrace(); } }
	
	private DBG() {}
}
