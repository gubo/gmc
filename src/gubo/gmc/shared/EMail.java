
package gubo.gmc.shared;

@SuppressWarnings("serial")
public class EMail implements java.io.Serializable 
{
	public String address;
	
	@Override
	public boolean equals( final Object o ) {
		if ( o instanceof EMail ) {
			final EMail that = ( EMail)o;
			if ( this.address != null ) {
				return this.address.equals( that.address );
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() { return ( address != null ? address.hashCode() : -1 ); }
}
