import java.io.Serializable;

// The user account is made abstract because the is a general or abstract case.
// The idea is that you create an abstract class and get more and more specific.
// In my FacebookUser class a facebook user is a specific type of account.
// I'm also implementing the Serializable class so I can serialize the everybody array list 
// later in the Facebook class.
public abstract class UserAccount implements Serializable	
{
	private static final long serialVersionUID = 1L;
	// Field variables.
	private String username;
	private String password;
	private boolean active;
	
	// My constructor which has the username and password passed into it.
	// The this keyword essentially says "this field variable is assigned to username for example."
	UserAccount(String username, String password)
	{
		this.username = username;
		this.password = password;
		this.active = true;
	}
	
	public boolean checkPassword(String passwordCheck)
	{
		if(this.password.equals(passwordCheck))
		{
			System.out.println("Everything checks out!");
			return true;
		}
		else
		{
			System.out.println("The passwords " + passwordCheck + " doesn't match this account's password.");
			return false;
		}
	}
	
	// Getters and setters so I can use these in my FacebookUser sub-class.
	// All these methods have to be public so I can gain access to the private field variables
	// via the sub-class.
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
	
	// The hash code essentially compares two objects, via memory addresses.
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		UserAccount other = (UserAccount) obj;
		if (username == null) 
		{
			if (other.username != null)
			{
				return false;
			}
		} 
		else if (!username.equals(other.username))
		{
			return false;
		}
		return true;
	}
	
	// Pretty self explanatory, I'm just deactivating the account
	// by setting active = true;
	public void deactivateAccount()
	{
		active = false;
	}
	
	// If I don't have this, essentially java will look at memory addresses.
	// This gives a clean representation of what I want.
	public String toString()
	{
		if(active)
		{	
			return  username + " (Active)";
		}
		else
		{
			return username + " (Inactive)";
		}
	}
	
	// In order to have an abstract method, the class has to be abstract.
	// Also abstract classes can't have instances or objects created.
	// I'm just declaring this method, and in the FacebookUser class I'll actually make the method.
	public abstract void getPasswordHelp();
}
