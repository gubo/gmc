
package gubo.gmc.shared;

@SuppressWarnings("serial")
public class Authorization implements java.io.Serializable 
{
	public String emailaddress;
	public String accessCode;
	public Token token;
	
	public static boolean isAuthorized( final Authorization authorization ) {
		boolean authorized = false;
		if ( (authorization != null) && (authorization.token != null) ) {
			authorized = ( authorization.token.access_token != null );
		}
		return authorized;
	}
}
