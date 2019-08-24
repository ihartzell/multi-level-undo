import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

// I'm extending the UserAccount class by creating this subclass FacebookUser.
// I then implement the Comparable class which has FacebookUser as it's generic.
public class FacebookUser extends UserAccount implements Comparable<FacebookUser>
{
	
	// I'm creating a hash map which is similar to an array list called likes.
	// The difference is that this likes list has all the likes as strings attached to a specific Integer key value.
	HashMap <String,Integer> likes = new HashMap<String,Integer>();
	
	// I had to have this in order to serialize my object.
	private static final long serialVersionUID = 1L;
	
	// I'm creating an array list of FacebookUser objects and the array list is named friends.
	private String passwordHint;
	private ArrayList<FacebookUser> friends = new ArrayList<FacebookUser>();
	
	// I'm calling for the constructor, the super keyword tells me that
	// it comes from the super class being UserAccount.
	FacebookUser(String username, String password) 
	{
		super(username, password);
	}
	
	// Essentially a setter. I'm setting the passwordHint variable assigned to the variable passed in.
	public void setPasswordHint(String hint)
	{
		this.passwordHint = hint;
	}
	
	// In the case that the friends list already contains this friend they're trying to add,
	// the I want to give an error message, other wise I want to add the new friend to the 
	// array list.
	public void friend(FacebookUser newFriend)
	{
		if(friends.contains(newFriend))
		{
			System.out.println("I couldn't find this friend or they already exist in your friend's list.");
		}
		else
		{
			friends.add(newFriend);
		}
		
	}
	// I  do the same thing I did with the friend method, only the opposite.
	// if the friends array list contains some former friend then I want to remove them from
	// the array list. Otherwise I give a message stating they aren't there.
	public void defriend(FacebookUser formerFriend)
	{
		if(friends.contains(formerFriend))
		{
			friends.remove(formerFriend);
		}
		else
		{
			System.out.println("There is no friend with this username. ");
		}
	}
	
	// This method makes a replica array list which has all the friends.
	public ArrayList <FacebookUser> getFriends()
	{
		ArrayList<FacebookUser> replicaOfFriendsList = new ArrayList<FacebookUser>(friends);
		return replicaOfFriendsList;
	}
	
	// This is the abstract method actually being created.
	@Override
	public void getPasswordHelp() 
	{
		System.out.println("The hint for your password is " + "(" + passwordHint + ").");
	}
	
	// I'm calling for the super class which has the getUsername method,
	// and I then compare it to what ever object is passed into the method from the 
	// FacebookUser.
	@Override
	public int compareTo(FacebookUser personObject) 
	{
		return super.getUsername().compareTo(personObject.getUsername());
	}
	
	// Like string text.
	public void like(String like)
	{
		Integer tempVar = likes.get(like);
		
		if(tempVar != null)
		{
			likes.put(like, likes.get(like) + 1);
		}
		else
		{
			likes.put(like, 1);
		}
	}
	// See the liked list.
	public void likeList()
	{
		//EXPLANATION! I used a tree map because it's the a great map to allow me to sort sort the likes HashMap list in sorted order.
		// which is what I want here.
		TreeMap <String,Integer> treeMapForSorting = new TreeMap<String,Integer>(likes);
		
		for(String treeKey : treeMapForSorting.keySet())
		{	
			
			System.out.println(treeKey + " (Liked)");
		}
	}
}
