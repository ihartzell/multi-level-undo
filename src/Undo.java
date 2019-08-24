// Storage class. All this class does is stores information in regards to my three private variables.
public class Undo 
{
	private FacebookUser user;
	private FacebookUser friend;
	private String action;
	private String username;
	private String password;
	
	// I have two facebook user arguments because I need user and a friend for case 6 and 7.
	// the action is weather it's an add action or a remove action or a liking action whatever.
	//Undo case for friending and defriending.
	public Undo(FacebookUser user,FacebookUser friend, String action)
	{
		this.user = user;
		this.friend = friend;
		this.action = action;
	}
	// Undo constructor for adding and deleteing users.
	public Undo(String username, String password, String action)
	{
		this.username = username;
		this.password = password;
		this.action = action;
	}
	
	
	public String getUsername() 
	{
		return username;
	}

	public void setUsername(String username) 
	{
		this.username = username;
	}

	public String getPassword() 
	{
		return password;
	}

	public void setPassword(String password) 
	{
		this.password = password;
	}

	public FacebookUser getUser() 
	{
		return user;
	}
	public void setUser(FacebookUser user) 
	{
		this.user = user;
	}
	public FacebookUser getFriend() 
	{
		return friend;
	}
	public void setFriend(FacebookUser friend) 
	{
		this.friend = friend;
	}
	public String getAction() 
	{
		return action;
	}
	public void setAction(String action) 
	{
		this.action = action;
	}
}
