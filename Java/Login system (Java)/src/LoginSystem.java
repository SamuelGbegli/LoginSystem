import java.util.ArrayList;
import java.util.Scanner;

public class LoginSystem {
	//List that acts as a "database" for users in the program
	static ArrayList<User> users = new ArrayList<User>();
	
	//Scanner used for user input in the program
	static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		Menu();

	}
	
	static void Menu() {
		String userinput = "";

	    while(!userinput.equals("exit"))
	    {
	        System.out.println("Please select an option:");
	        System.out.println("'Register' to create a new account");
	        System.out.println("'Login' to login");
	        System.out.println("'Users' to show the names of all users");
	        System.out.println("'Exit' to quit the program");
	        userinput = scanner.nextLine().toLowerCase();

	        while(!userinput.equals("register") && !userinput.equals("login") && !userinput.equals("users") && !userinput.equals("exit"))
	        {
	        	System.out.println("Invalid input. Please select from one of these options:");
	        	System.out.println("'Register' to create a new account");
	        	System.out.println("'Login' to login");
	        	System.out.println("'Users' to show the names of all users");
	        	System.out.println("'Exit' to quit the program");
	            userinput = scanner.nextLine().toLowerCase();
	        }

	        switch (userinput)
	        {
	            case "register":
	                Register();
	                break;
	            case "login":
	                Login();
	                break;
	            case "users":
	                System.out.println("There are " + users.size() + " user(s).");
	                for (User user : users) System.out.println(user.getUsername());
	                break;
	        }
	    }
	}

	//Function for creating a new account
	static void Register()
	{
	    System.out.println("Enter a username. Names must be at least 5 characters long. Enter 'c' to cancel.");
	    String username = SetUsername();
	    if(username != null)
	    {
	    	System.out.println("Enter a password. Passwords must be at least 8 characters long, and have at least one upper case letter, lower case letter, and a non-alphanumeric character.");
	        String password = SetPassword();
	        
	        if(password != null) while (password.equals("r"))
	        {
	        	System.out.println("Enter a password. Passwords must be at least 8 characters long, and have at least one upper case letter, lower case letter, and a non-alphanumeric character.");
	            password = SetPassword();
	        }

	        if (password != null)
	        {
	            User user = new User();
	            user.setUsername(username);
	            user.setPassword(password);          
	            users.add(user);
	            UserHome(user);
	        }
	    }
	   
	}

	static String SetUsername()
	{
	    String userinput = scanner.nextLine();
	    		
	    while (userinput.length() < 5 || FindUser(userinput) != null)
	    {
	        if (userinput.toLowerCase().equals("c")) return null;
	        if (userinput.length() < 5)
	            System.out.println("Usernames must be at least 5 characters long. Please try again:");
	        else System.out.println(userinput + " has already been taken. Please enter a different username:");
	        userinput= scanner.nextLine();
	    }
	    return userinput;
	}

	static String SetPassword()
	{
	    String password = scanner.nextLine();
	    if (password.toLowerCase().equals("c")) return null;
	    while (!PasswordCheck(password))
	    {
	        System.out.println("Invalid password. Please try again.");
	        PasswordErrors(password);
	        password = scanner.nextLine();
	    }
	    System.out.println("Confirm password. Enter 'r' to reset password.");
	    String confirmpassword = scanner.nextLine();
	    while (!confirmpassword.equals(password) && !confirmpassword.equals("r") && !confirmpassword.equals("c"))
	    {
	        System.out.println("Passwords do not match. Please try again.");
	        confirmpassword = scanner.nextLine();
	    }

	    if (confirmpassword.toLowerCase().equals("c")) return null;
	    if (confirmpassword.toLowerCase().equals("r")) return "r";
	    return password;
	}

	//Function to verify a password
	static boolean PasswordCheck(String password)
	{
		if(password.length() >= 8) {
			boolean upperflag = false;
			boolean lowerflag = false;
			boolean symbolflag = false;
			
			char c;
			
		    for (int i = 0; i < password.length(); i++) {
		    	c = password.charAt(i);
		    	if(Character.isUpperCase(c)) upperflag = true;
		    	if(Character.isLowerCase(c)) lowerflag = true;
		    	if(!Character.isLetter(c) && !Character.isDigit(c)) symbolflag = true;
		    	
		    	if(upperflag && lowerflag && symbolflag) return true;
		    }
		}
	    return false;
	}

	//Function to print out errors in an invalid password
	static void PasswordErrors(String password)
	{
		boolean upperflag = false;
		boolean lowerflag = false;
		boolean symbolflag = false;
		
		char c;
		
	    for (int i = 0; i < password.length(); i++) {
	    	c = password.charAt(i);
	    	if(Character.isUpperCase(c)) upperflag = true;
	    	if(Character.isLowerCase(c)) lowerflag = true;
	    	if(!Character.isLetter(c) && !Character.isDigit(c)) symbolflag = true;
	    }
		
		if (password.length() < 8)
	        System.err.println("Password must be at least 8 characters long.");
	    if (!upperflag)
	        System.err.println("Password must have at least one upper case letter.");
	    if (!lowerflag)
	        System.err.println("Password must have at least one lower case letter.");
	    if (!symbolflag)
	        System.err.println("Password must have at least one non-alphanumeric character.");
	}

	//Function for a user to log in
	static void Login()
	{
	    System.out.println("Enter username:");
	    String userinput = scanner.nextLine();
	    while(FindUser(userinput) == null)
	    {
	        if (userinput.toLowerCase().equals("c")) return;
	        System.out.println("A user with that name cannot be found. Please try again:");
	        userinput = scanner.nextLine();
	    }
	    User user = FindUser(userinput);
	    System.out.println("Enter password for " + user.getUsername() + ":");
	    userinput = scanner.nextLine();
	    while (userinput.equals(user.getPassword()))
	    {
	        if (userinput.toLowerCase().equals("c")) return;
	        System.out.println("Incorrect password. Please try again:");
	        userinput = scanner.nextLine();
	    }
	    UserHome(user);

	}

	static void UserHome(User user)
	{
	    System.out.println("Welcome " + user.getUsername());
	    String userinput = "";
	    while (!userinput.equals("3"))
	    {
	        System.out.println("Select an option:");
	        System.out.println("Change username: 1");
	        System.out.println("Change password: 2");
	        System.out.println("Log off: 3");

	        userinput = scanner.nextLine();
	        while (!userinput.equals("1") && !userinput.equals("2") && !userinput.equals("3"))
	        {
	            System.out.println("Invalid input. Please select from one of these options:");
	            System.out.println("Change username: 1");
	            System.out.println("Change password: 2");
	            System.out.println("Log off: 3");
	        }
	        switch (userinput)
	        {
	            case "1":
	                System.out.println("Enter a new username. Names must be at least 5 characters long:");
	                userinput = SetUsername();
	                if (userinput != null) user.setUsername(userinput);
	                break;

	            case "2":
	                System.out.println("Enter current password:");
	                userinput = scanner.nextLine();
	                while (!userinput.equals(user.getPassword()) && !userinput.equals("c")) {
	                    System.out.println("Incorrect password. Please try again:");
	                    userinput = scanner.nextLine();
	                }
                    if (!userinput.toLowerCase().equals("c")){
                        System.out.println("Enter a new password.");
                        userinput = SetPassword();
                        
                        while (userinput.equals("r"))
                        {
                            System.out.println("Enter a new password.");
                            userinput = SetPassword();
                        }
                        
                        if (userinput != null) user.setPassword(userinput);
	                }
	                break;
	        }
	    }
	}
	
	static User FindUser(String username) {
		return users.stream()
				.filter(u -> u.getUsername().equals(username)).findAny().orElse(null);
		
	}
}
