
package gubo.gmc.client;

import com.google.gwt.core.client.*;
import com.google.gwt.resources.client.*;
import com.google.gwt.resources.client.CssResource.NotStrict;

public interface GMCResources extends ClientBundle 
{
	@NotStrict
	@Source( "GMC.css" )
	CssResource css();
	
	public static final GMCResources instance = GWT.create( GMCResources.class );

	ImageResource xtitle();
}
