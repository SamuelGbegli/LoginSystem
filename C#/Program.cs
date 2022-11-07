/*This is a simulation of a login system with a User Class and a list that simulates
 *a database of accounts. Users can create new accounts and set up a password
 *and logged in users can change their username or password. Accounts must have
 *unique usernames.
 *This was inspired by a login system wtitten in C++ in this video: https://youtu.be/I_aWPGCaaFA 
 */

using Login_system__CSharp_;

//List that acts as a "database" for users in the program
List <User> users = new List <User> ();

//Function that acts as the program's menu
void Menu()
{
    string userinput = "";

    while(userinput != "exit")
    {
        Console.WriteLine("Please select an option:");
        Console.WriteLine("'Register' to create a new account");
        Console.WriteLine("'Login' to login");
        Console.WriteLine("'Users' to show the names of all users");
        Console.WriteLine("'Exit' to quit the program");
        userinput = Console.ReadLine().ToLower();

        while(userinput != "register" && userinput != "login" && userinput != "users" && userinput != "exit")
        {
            Console.WriteLine("Invalid input. Please select from one of these options:");
            Console.WriteLine("'Register' to create a new account");
            Console.WriteLine("'Login' to login");
            Console.WriteLine("'Users' to show the names of all users");
            Console.WriteLine("'Exit' to quit the program");
            userinput = Console.ReadLine().ToLower();
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
                Console.WriteLine("There are " + users.Count + " user(s).");
                foreach (User user in users) Console.WriteLine(user.Username);
                break;
        }
    }
}

//Function for creating a new account
void Register()
{
    Console.WriteLine("Enter a username. Names must be at least 5 characters long. Enter 'c' to cancel.");
    string username = SetUsername();
    if(username is not null)
    {
        Console.WriteLine("Enter a password. Passwords must be at least 8 characters long, and have at least one upper case letter, lower case letter, and a non-alphanumeric character.");
        string password = SetPassword();
        
        while (password == "r")
        {
            Console.WriteLine("Enter a password. Passwords must be at least 8 characters long, and have at least one upper case letter, lower case letter, and a non-alphanumeric character.");
            password = SetPassword();
        }

        if (password is not null)
        {
            User user = new User { Username = username, Password = password };
            users.Add(user);
            UserHome(user);
        }
    }
   
}

string SetUsername()
{
    string userinput = Console.ReadLine();
    while (userinput.Length < 5 || users.Any(u => u.Username == userinput))
    {
        if (userinput.ToLower() == "c") return null;
        if (userinput.Length < 5)
            Console.WriteLine("Usernames must be at least 5 characters long. Please try again:");
        else Console.WriteLine(userinput + " has already been taken. Please enter a different username:");
        userinput= Console.ReadLine();
    }
    return userinput;
}

string SetPassword()
{
    string password = Console.ReadLine();
    if (password.ToLower() == "c") return null;
    while (!PasswordCheck(password))
    {
        Console.WriteLine("Invalid password. Please try again.");
        PasswordErrors(password);
        password = Console.ReadLine();
    }
    Console.WriteLine("Confirm password. Enter 'r' to reset password.");
    string confirmpassword = Console.ReadLine();
    while (confirmpassword != password && confirmpassword != "r" && confirmpassword != "c")
    {
        Console.WriteLine("Passwords do not match. Please try again.");
        confirmpassword = Console.ReadLine();
    }

    if (confirmpassword.ToLower() == "c") return null;
    if (confirmpassword.ToLower() == "r") return "r";
    return password;
}

//Function to verify a password
bool PasswordCheck(string password)
{
    if (password.Any(char.IsUpper) && password.Any(char.IsLower)
        && password.Any(char.IsPunctuation) && password.Length >= 8) return true;
    return false;
}

//Funtion to print out errors in an invalid password
void PasswordErrors(string password)
{
    if (password.Length < 8)
        Console.Error.WriteLine("Password must be at least 8 characters long.");
    if (!password.Any(char.IsUpper))
        Console.Error.WriteLine("Password must have at least one upper case letter.");
    if (!password.Any(char.IsLower))
        Console.Error.WriteLine("Password must have at least one lower case letter.");
    if (!password.Any(char.IsPunctuation))
        Console.Error.WriteLine("Password must have at least one non-alphanumeric character.");
}

//Function for a user to log in
void Login()
{
    Console.WriteLine("Enter username:");
    string userinput = Console.ReadLine();
    while(!users.Exists(x => x.Username == userinput))
    {
        if (userinput.ToLower() == "c") return;
        Console.WriteLine("A user with that name cannot be found. Please try again:");
        userinput = Console.ReadLine();
    }
    User user = users.Find(x => x.Username == userinput);
    Console.WriteLine("Enter password for " + user.Username + ":");
    userinput = Console.ReadLine();
    while (userinput != user.Password)
    {
        if (userinput.ToLower() == "c") return;
        Console.WriteLine("Incorrect password. Please try again:");
        userinput = Console.ReadLine();
    }
    UserHome(user);

}

void UserHome(User user)
{
    Console.WriteLine("Welcome " + user.Username);
    string userinput = "";
    while (userinput != "3")
    {
        Console.WriteLine("Select an option:");
        Console.WriteLine("Change username: 1");
        Console.WriteLine("Change password: 2");
        Console.WriteLine("Log off: 3");

        userinput = Console.ReadLine();
        while (userinput != "1" && userinput != "2" && userinput != "3")
        {
            Console.WriteLine("Invalid input. Please select from one of these options:");
            Console.WriteLine("Change username: 1");
            Console.WriteLine("Change password: 2");
            Console.WriteLine("Log off: 3");
            userinput = Console.ReadLine();
        }
        switch (userinput)
        {
            case "1":
                Console.WriteLine("Enter a new username. Names must be at least 5 characters long:");
                userinput = SetUsername();
                if (userinput is not null) user.Username = userinput;
                break;

            case "2":
                Console.WriteLine("Enter current password:");
                userinput = Console.ReadLine();
                while (userinput != user.Password && userinput != "c") {
                    Console.WriteLine("Incorrect password. Please try again:");
                    userinput = Console.ReadLine();
                    if (userinput.ToLower() != "c"){
                        Console.WriteLine("Enter a new password.");
                        userinput = SetPassword();

                        while (userinput == "r")
                        {
                            Console.WriteLine("Enter a new password.");
                            userinput = SetPassword();
                        }

                        if (userinput is not null) user.Password = userinput;
                    }
                }
                break;
        }
    }
}

Menu();