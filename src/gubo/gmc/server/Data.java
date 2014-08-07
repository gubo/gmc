
package gubo.gmc.server;

import java.util.*;

import gubo.gmc.shared.*;

class Data 
{
	static Map<EMail,Authorization> authorizations = Collections.synchronizedMap( new HashMap<EMail,Authorization>() );

	private Data() {}
}
