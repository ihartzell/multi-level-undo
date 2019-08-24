import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.Stack;

public class Driver 
{
	public static void main(String[] args) 
	{
		//EXPLANATION, I choose to use a stack data structure because
		//for this particular problem it seemed highly optimal. The scenario is that someone does an action
		//and then they need to have the ability to remove that action.
		//That action can be thought of the thing that's on the top of a stack.
		//I want to remove that thing from the top of the stack so the next thing pops up that can be removed.
		//The stack gives a perfect way for keeping track of data that a user can remove the latest thing they did
		//which makes sense for this problem.
		Stack<Undo> stack = new Stack<Undo>();
		
		Scanner input = new Scanner(System.in);
		
		// boolean test variables to allow me to have scenarios for adding and deleteing a user vs friending or defriending work in
		// case 12.
		boolean testForFriendAndDefriend = false;
		boolean testForAddAndDeleteUser = false;
		String username = null;
		String password = null;
		String passwordHint = null;
		String newFriendName = null;
		String formerFriendName = null;
		String liking = null;
		
		int menuChoice = 0;
		
		// I created a facebook object from my Facebook class and assign it to my deSerialization method which
		//serializes an object so everytime I load up the program data is saved.
		Facebook facebookObj = deSerialization();
		
		do
		{	
			// I assign my menu method to the variable menuChoice so I can make a switch statement for my menu.
			menuChoice = menu();
			
			switch(menuChoice){
			
			// Lists all users.
			case 1:	// I simply use my facebook object from my Facebook class to call my getUsers method
					// which displays a copy array list of the everybody array list.
					System.out.println(facebookObj.getUsers());
					System.out.println();
					
				break;
				
			// List users by number of friends. From most to least. If Isaac has more friends then josiah list that first.
			case 2:		ArrayList<FacebookUser> listUsersByMostFriendsToLeast = facebookObj.getUsers();
						Collections.sort(listUsersByMostFriendsToLeast, new FriendsCountComparator());
						
						for(FacebookUser userThatHasMostToLeast: listUsersByMostFriendsToLeast)
						{
							System.out.println(userThatHasMostToLeast.getUsername() + " has " + userThatHasMostToLeast.getFriends().size() + " friends.");
						}
						
						System.out.println();
						
				break;
				
			// Adds a user.	
			case 3: 
					testForAddAndDeleteUser = true;
					System.out.println("Creation of new Facebook user:");
					System.out.print("----------------------");
					System.out.println();
					username = requestString("Username:");
					password = requestString("Password:");
					passwordHint = requestString("What do you want the hint for your password to be?");
					System.out.println("Added: " + username);
					
					facebookObj.addUser(username, password, passwordHint);
					
					Undo undoObjForAddUser = new Undo(username,password,"Add User");
					
					stack.push(undoObjForAddUser);
					System.out.println();
					
				break;
				
			// Removes a user.	
			case 4: testForAddAndDeleteUser = true;
					System.out.println("Deletion of Facebook user:");
					System.out.println("----------------");
					username = requestString("Remove user:");
					
					facebookObj.deleteUser(username);
					
					Undo undoObjForDeleteUser = new Undo(username,password,"Delete User");
					
					stack.push(undoObjForDeleteUser);
					System.out.println();
					
				break; 
				
			// Gives the password help.	
			case 5:	System.out.println("*Password Help*");
					username = requestString("What is the username?");
					FacebookUser userObj = facebookObj.getUser(username);
					
					// This simply checks to make sure the FacebookUser object isn't equal to nothing.
					// if it is I put out an error message. Every case where I have a username request I have this if else branch.
					if (userObj == null)
					{
						System.out.println("No user with username: " + username);
						System.out.println();
						break;
					}
					else
					{
						userObj.getPasswordHelp();
						System.out.println();
					}
					
				break; 
				
			// Friending someone.
			case 6:	System.out.println("Friending someone");
					System.out.println("-----------------");
					username = requestString("Before adding a friend, what is your Facebook username?");
					
					// I'm creating an object from my FacebookUser class so I can assign it to my facebookObj
					FacebookUser userObjForFriendsCase = facebookObj.getUser(username);
					
					// if the username exists.
					if (userObjForFriendsCase == null)
					{
						System.out.println("No user with username: " + username);
						System.out.println();
						break;
					}
					// else what's the password to the existing username and continue.
					else
					{
						password = requestString("What is the password for the account?");
						FacebookUser passObjForFriendsCase = facebookObj.getPassword(password);
						
						if(passObjForFriendsCase == null)
						{
							userObjForFriendsCase.checkPassword(password);
							System.out.println();
							break;
						}
						
						System.out.println("Everything is in order.");
						System.out.println();
						
						// Creation of the friend essentially.
						newFriendName = requestString("What is the new friend's name you wish to add?");
						
						// Creation of a newFriend object from the FacebookUser class and I assign it to the facebook object.
						FacebookUser newFriend = facebookObj.getUser(newFriendName);
						
						// If the newFriend is nothing. I print out an error message and recyle the menu.
						if(newFriend == null)
						{
							System.out.println("This friend doesn't exist. Please first add this user using");
							System.out.println("menu option 2.");
							System.out.println();
							break;
						}
						// Other wise I use my object that I created near the beginning of this case so I can simply friend whoever
						// was choosen.
						else
						{
							testForFriendAndDefriend = true;
							// I need a third argument which is some action.
							// I'm creading an undo object which has two FacebookUser objects and the string which I'll
							// use in my if branch for case 12.
							Undo undoObj = new Undo(userObjForFriendsCase,newFriend,"Add");
							
							// I'm pusing this undo object to the top of the list.
							stack.push(undoObj);	
							
							userObjForFriendsCase.friend(newFriend);
							System.out.println("Added " + newFriend);
							System.out.println();
						}
					} 
					
				break; 
				
			// de-friending someone.
			case 7: System.out.println("De-Friending someone.");
					System.out.println("--------------------");
					username = requestString("What is your Facebook username?");
					
					// I'm creating a facebook user object and assigning it to the facebook object.
					FacebookUser userForDeFriendingCase = facebookObj.getUser(username);
					
					if(userForDeFriendingCase == null)
					{
						System.out.println("No user with username: " + username);
						System.out.println();
						break;
					}
					else
					{
						formerFriendName = requestString("What is the name of the friend you wish to remove?");
						FacebookUser formerFriendObjForFriendCase = facebookObj.getUser(formerFriendName);
						
						if(formerFriendName != null)
						{
							testForFriendAndDefriend = true;
							// I'm creading an undo object which has two FacebookUser objects and the string which I'll
							// use in my if branch for case 12.
							Undo undoObj = new Undo(userForDeFriendingCase,formerFriendObjForFriendCase,"Remove");
							
							// I'm using my stack list to push this undo object to the top of the stack. 
							stack.push(undoObj);
							
							userForDeFriendingCase.defriend(formerFriendObjForFriendCase);
							System.out.println("Removed " + "(" + formerFriendName + ")" );
							System.out.println();
							break;
						}
						else
						{
							System.out.println("The friend " + formerFriendName + " doesn't exist!");
							System.out.println();
						}
					}
					
				break; 
				
			// Listing Friends.
			case 8: System.out.println("Listing all friends of Facebook user.");
					System.out.println("-------------------------------------");
					username = requestString("What is your Facebook username?");
					
					// Here I'm creating a FacebookUser object and assigning it to the facebook object from the Facebook class.
					FacebookUser facebookUserForListAllFriendsCaseObj = facebookObj.getUser(username);
					
					if (facebookUserForListAllFriendsCaseObj == null)
					{
						System.out.println("No user with username: " + username);
						System.out.println();
						break;
					}
					else
					{
						facebookUserForListAllFriendsCaseObj.getFriends();
						System.out.println(username + "'s friends:" +facebookUserForListAllFriendsCaseObj.getFriends());
						System.out.println();
					}
					
				break;
				
			// Recommending friends. Use Recursion.
			case 9:	System.out.println("Friends Recommendations");
					System.out.println("-----------------------");
					username = requestString("What is your Facebook username?");
					FacebookUser fbUserObjForRecommendingCase = facebookObj.getUser(username);
					
					if (fbUserObjForRecommendingCase == null)
					{
						System.out.println("No user with username: " + username);
						System.out.println();
						break;
					}
					else
					{	
						// I'm creating an array list called recommendations and assigning it to my recommendFriends method.
						// Then I print out the array list.
						// I use the Collections class to sort this recommendations array list of facebook users, and I also use
						// my FriendsCountComparator so that It recommends friends not arbitrarily, but based on who has more friends.
						// It'll recommend friends from who has the most to least friends.
						ArrayList<FacebookUser> recommendations = facebookObj.recommendFriends(facebookObj.getUser(username));
						Collections.sort(recommendations, new FriendsCountComparator());
						System.out.println();
						System.out.println(username + "'s potential friends from who has the most friends to the least is the following " + recommendations);
						System.out.println();	
					}
					
				break;
				
			// Like stuff
			case 10: System.out.println("Liking comment.");
					 System.out.println("--------------");
					 username = requestString("What is your Facebook username?");
					 
					 FacebookUser case10ObjForUsername = facebookObj.getUser(username);
					 
					 	if (case10ObjForUsername == null)
						{
							System.out.println("No user with username: " + username);
							System.out.println();
							break;
						}
					 	else
						{
							password = requestString("What is the password for the account?");
							FacebookUser case10ObjForPass = facebookObj.getPassword(password);
							
							if(case10ObjForPass == null)
							{
								case10ObjForPass.checkPassword(password);
								System.out.println();
								break;
							}
							
							System.out.println("Everything is in order.");
							System.out.println();
							liking = requestString("What do you wish to want to like? Enter text.");
							case10ObjForUsername.like(liking);
							System.out.println(username + " Likes " + "(" + liking + ")");
							System.out.println("if " + username + " has already liked " + liking + " it won't be added to the list.");
							System.out.println();
						}
				break;
				
			// Listing likes
			case 11: System.out.println("Listing likes for specific Facebook users");
					 System.out.println("-----------------------------------------");
					 username = requestString("What is a valid Facebook username?");
					 FacebookUser case11ObjForUsername = facebookObj.getUser(username);
					
					if (case11ObjForUsername == null)
					{
						System.out.println("No user with username: " + username);
						System.out.println();
						break;
					}
			
					else
					{
						System.out.println();
						System.out.println(username + "'s list of likes in ABC order is \n");
						case11ObjForUsername.likeList();
						System.out.println();
					}
				break;
				
			// UNDO
			case 12: 
					 try
					 {	
						// item is the undo object that contains what was popped off the stack.
						// For example, a user, their friend, and some String argument like Add. 
						Undo item = stack.pop();
					 	FacebookUser user = item.getUser();
					 	FacebookUser friend = item.getFriend();
					 	String action = item.getAction();
					 	String usernameForUndo = item.getUsername();
					 	
					 	// if branch in regards to undoing adding a username and removing a username
					 	if(usernameForUndo == item.getUsername() && testForAddAndDeleteUser == true)
					 	{
					 		testForAddAndDeleteUser = false;
					 		password = requestString("What is the password for " + usernameForUndo + "?");
					 		FacebookUser passObj = facebookObj.getPassword(password);
					 		
					 		//password is valid.
					 		if(passObj != null)
					 		{
					 			passObj.checkPassword(password);
								System.out.println();
								
								if(action == "Add User")
								{
									facebookObj.deleteUser(usernameForUndo);
									System.out.println();
								}
								else if(action == "Delete User")
								{
									facebookObj.addUser(usernameForUndo, password, passwordHint);
									System.out.println("Added " + usernameForUndo);
									System.out.println();
								}
					 		}
					 		// if the password isn't valid
					 		else
						 	{
						 		System.out.println(password + " isn't a valid password! \n");
						 	}
					 		
					 	}
					 	
					 	// if branch for the user object in regards to undoing friending and defriending
					 	if(user == item.getUser() &&  testForFriendAndDefriend == true)
					 	{	
					 		testForFriendAndDefriend = false;
					 		password = requestString("What is the password for " + user +"?");
					 
					 		if(user.checkPassword(password))
					 		{
					 			// if the password checks out.
					 			System.out.println(password + " is a valid password!");
						
					 			// if the action is Add meaning that they choose to friend someone, undo that.
					 			if(action == "Add")
					 			{
					 				user.defriend(friend);
					 				System.out.println("Removed " + friend  );
					 				System.out.println();
					 			}
					 			// if the action is Remove meaning that they choose to remove a friend, undo that.
					 			else if (action == "Remove")
					 			{
					 				user.friend(friend);
					 				System.out.println("Added " + friend +"!");
					 				System.out.println();
					 			}
					 		}
					 		// if the password doesn't check out.
						 	else
						 	{
						 		System.out.println(password + " isn't a valid password! \n");
						 	}
					 	}
					 }
					 catch(EmptyStackException ex)
					 {
						 System.out.println("There is nothing to undo, you must add something before you can undo it. \n");
					 }
					 
				break;
				
			// Serializing my Facebook object.
			case 13: serialization(facebookObj);
					 System.out.println();
					 System.out.println("Logging off...");
				break;
				
			} // end of switch statement or else-if essentially.
		} while(menuChoice >= 1 && menuChoice <= 12 ); 	
	} // end of the main method.
	
	// This method makes String input much easier and cleaner throughout the program, removing all the scanner objects.
	public static String requestString(String request)
	{
		Scanner input = new Scanner(System.in);
		System.out.print(request);
		
		return input.nextLine();
	}
	// This method purely displays the menu as well as checks to see
	// if the user's input is < 1 or > 12, and if it is the menu will repeat
	// untill the user selects a valid menu option.
	public static int menu()
	{
		int menuChoice = 0;
		Scanner input = new Scanner(System.in);
		
		System.out.println("Facebook Menu:");
		System.out.println("-------------");
		System.out.println("1.List User(s) alphabetically:");
		System.out.println("2.List User(s) by number of friends:");
		System.out.println("3.Add User:");
		System.out.println("4.Delete User:");
		System.out.println("5.Password Hint:");
		System.out.println("6.Friend:");
		System.out.println("7.De-friend:");
		System.out.println("8.List friends:");
		System.out.println("9.Recommend new friends:");
		System.out.println("10.Like:");
		System.out.println("11. Like List:");
		System.out.println("12. Undo:");
		System.out.println("13.Quit:");
		System.out.println();
		System.out.print("Choose 1-13.");
		menuChoice = input.nextInt();
		System.out.println();
		while (menuChoice < 1 || menuChoice > 13 )
		{
			System.out.println("Facebook Menu:");
			System.out.println(menuChoice + " is an invalid option, please choose 1-13.");
			System.out.println("-------------");
			System.out.println("1.List User(s) alphabetically:");
			System.out.println("2.List User(s) by number of friends:");
			System.out.println("3.Add User:");
			System.out.println("4.Delete User:");
			System.out.println("5.Password Hint:");
			System.out.println("6.Friend:");
			System.out.println("7.De-friend:");
			System.out.println("8.List friends:");
			System.out.println("9.Recommend new friends:");
			System.out.println("10.Like");
			System.out.println("11. Like List");
			System.out.println("12. Undo:");
			System.out.println("13.Quit:");
			System.out.println();
			System.out.print("Choose 1-13.");
			menuChoice = input.nextInt();
			System.out.println();
		}
		return menuChoice;
	} // end of menu method.
	
	// This method serializes the Facebook object. In other words it writes the object to the file.
	public static void serialization(Facebook facebookObj)
	{
		try 
		{
			// I'm creating a file output object which has the file name assigned to it.
			FileOutputStream fbDataOutput = new FileOutputStream("fbDataFile.txt");
			
			// Here I'm creating an output stream object which is assigned to the file output object.
			ObjectOutputStream objOutStream = new ObjectOutputStream(fbDataOutput);
			
			// I am then writing the facebookObj to the file.
			objOutStream.writeObject(facebookObj.getUsers());
			
			//closing file object and output stream object.
			objOutStream.close();
			fbDataOutput.close();
			
			System.out.printf("Data saved successfully, and is now viewable in your file which can only be read by the computer. ");
		} 
		catch (IOException ex) 
		{
			ex.printStackTrace();
		}
		
	} // end of serialization method.
	
	@SuppressWarnings("unchecked")
	public static Facebook deSerialization()
	{
		// Creating a facebook object.
		Facebook facebookObj = new Facebook();
		try 
		{
			// I'm making a file input object so I can now read in the program the file.
			FileInputStream fbDataInput = new FileInputStream("fbDataFile.txt");
			
			// I'm making an input stream object so I can read in the facebook object.
			ObjectInputStream objInStream = new ObjectInputStream(fbDataInput);
			
			// This line is involved but I'm basically using the facebook object to call for the setUsers method where I essentially
			// cast the object input stream and call for the readObject method so I can read in the object.
			facebookObj.setUsers((ArrayList<FacebookUser>) objInStream.readObject());
			
            // I'm closing the file input object and the object input stream.
			objInStream.close();
			fbDataInput.close();
			
			return facebookObj;
		} 
		catch (IOException ex) 
		{
			System.out.println("Starting app with no data!");
			return facebookObj;
		}
		catch (ClassNotFoundException ex)
		{
			System.out.println("Failed to serialize! possibility of data corruption.");
			System.out.println("Starting app with no data!");
			return facebookObj;
		}
	} 	// End of deSerialization method.
} 	