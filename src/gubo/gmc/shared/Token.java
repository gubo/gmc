
package gubo.gmc.shared;

@SuppressWarnings("serial")
public class Token implements java.io.Serializable 
{
	/*
	 * ex.
	 * 	{
     *		"access_token": "ya29.VgAC30kkRw2ZvRsAAACNCre5Y3Dmc0tZdGgBawRBos49HkI1RsMhrBGr86GVoQ",
     *		"token_type": "Bearer",
     *		"expires_in": 3600,
     *		"id_token": "eyJhbGciOiJSUzI1NiIsImtpZCI6ImUzMWY4YzZiZDQyNjc3M2U3YjcyMjMxZjU5YzY2ZGI1NzVmODliOWMifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwiaWQiOiIxMTc1MzQ3Njk4MzA0MzQyNTc4MTIiLCJzdWIiOiIxMTc1MzQ3Njk4MzA0MzQyNTc4MTIiLCJhenAiOiI3MzQ1NDgxMTMxOTUtc3RqZzF0NWk5YnE5cDBmNjZiNWhrM2lhMnE1YXBlYTAuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJlbWFpbCI6Imd1Ym8uY2FsZW5kYXJAZ21haWwuY29tIiwiYXRfaGFzaCI6IkVsNUxNeF9ZYVpvVzBZbDZsQ0pzWUEiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiYXVkIjoiNzM0NTQ4MTEzMTk1LXN0amcxdDVpOWJxOXAwZjY2YjVoazNpYTJxNWFwZWEwLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwidG9rZW5faGFzaCI6IkVsNUxNeF9ZYVpvVzBZbDZsQ0pzWUEiLCJ2ZXJpZmllZF9lbWFpbCI6dHJ1ZSwiY2lkIjoiNzM0NTQ4MTEzMTk1LXN0amcxdDVpOWJxOXAwZjY2YjVoazNpYTJxNWFwZWEwLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiaWF0IjoxNDA3MDQ1NDkyLCJleHAiOjE0MDcwNDkzOTJ9.WSjjVLli3yJ_C0Dtp9CnW3_pgzqVBVNHakcF8qrc7c96XXLR1LVYpGqn_8Gt97iC5tKY4a836L5s6fd6gnon0aAOVskIc1gwLxiwRlHvHezhmcnDeRc1cdnI87vqh7yuuxR8hqdt0HyDNRBpoAKe5SvHWZZLQI9ad4P26S1hIKk" 
     *	}
	 */
	
	public String access_token;
	public String refresh_token;
	public String expipres_in; 
	public String token_type;
	public String id_token;
}
