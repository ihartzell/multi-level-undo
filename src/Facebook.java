import java.util.ArrayList;
import java.util.Collections;
public class Facebook 
{
	// This array list is an array list of facebook user objects and
	// encompasses everyone across the board in regards to facebook.
	private ArrayList<FacebookUser> everybody = new ArrayList<FacebookUser>();
	
	// Here I'm creating an array list for the friends recommendations.
	private ArrayList<FacebookUser> recommendations = new ArrayList<FacebookUser>();
	
	// In this method I'm adding a specific user to the everybody array list.
	public void addUser(String username, String password, String passwordHint)
	{
		if(getUser(username) == null)
		{
			FacebookUser newUser = new FacebookUser(username, password);
			newUser.setPasswordHint(passwordHint);
			everybody.add(newUser);
		}
		else
		{
			System.out.println("Username: " + username + " already exists!");
		}
	}
	
	// Here I'm creating a facebook user object and if the user exists then I remove that user object from the everybody array list.
	// Other wise that username doesn't exist.
	public void deleteUser(String username)
	{
		FacebookUser user = getUser(username);
		
		if (user != null)
		{
			everybody.remove(user);
			System.out.println("Removed " + username);
		}
		else
		{
			System.out.println("Username: " + username + " doesn't exist!");
		}
	}
	
	// This method cycles through the everybody array list and if the username is it self I return the user object.
	public FacebookUser getUser(String username)
	{
		for (FacebookUser user: everybody)
		{
			if(user.getUsername().equalsIgnoreCase(username))
				return user;
		}
		return null;
	}
	
	// This method sets the users in the everybody array list to what is passed into this method.
	public void setUsers(ArrayList<FacebookUser> users)
	{
		everybody = new ArrayList<FacebookUser>(users);
	}
	
	// I'm making an array list that's a pure copy of the everybody array list.
	// getUsers has a return type of an array list of FacebookUser objects.
	public ArrayList<FacebookUser> getUsers()
	{
		ArrayList<FacebookUser> copyList = new ArrayList<FacebookUser>(everybody);
		
		// I'm using the Collections class to call for the sort method so I can sort this array list.
		Collections.sort(copyList);
		
		return copyList;
	}
	// This method basically does the exact same thing as the getUser method accept that it tackles passwords and not usernames.
	public FacebookUser getPassword(String passwordArg)
	{
		for(FacebookUser password: everybody)
		{
			if(password.getPassword().equalsIgnoreCase(passwordArg))
				return password;
		}
		return null;
	}
	
	// This method  cycles through the getFriends method and if
	// the recommendations array list doesn't contain a friend with whatever argument is passed into it,
	// then I add that friend to the recommendations array list.
	public ArrayList<FacebookUser> recommendFriends(FacebookUser friendsArg)
	{	
		int count = 0;
		// for each friend's array list if the recommendations does contain a friend then I return the recommendations
		// other wise, I add that friend to the recommendation array list  and recommend friends and friends of friends.
		for(FacebookUser friend: friendsArg.getFriends())
		{
			count++; 
			if(recommendations.contains(friend))
			{
				return recommendations;
			}
			else
			{	
				recommendations.add(friend);
				System.out.println("Recommendations from friend " + friend + "'s friend list " + recommendFriends(friend));
			}
		}
		return recommendations;
	} 
	
} // End of Facebook class.

