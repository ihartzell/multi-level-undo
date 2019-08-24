import java.util.Comparator;

public class FriendsCountComparator implements Comparator<FacebookUser>
{
	// I'm comparing two facebook user objects friends lists.
	@Override
	public int compare(FacebookUser fbUser1Compare, FacebookUser fbUser2Compare) 
	{
		return fbUser2Compare.getFriends().size() - fbUser1Compare.getFriends().size();
	}

}