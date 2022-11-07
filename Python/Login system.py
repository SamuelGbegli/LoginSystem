# -*- coding: utf-8 -*-
"""
Created on Sun Nov  6 01:23:06 2022

@author: SamuelGbegli

A simulation of a login system with a User Class and a list that simulates
a database of accounts. Users can create new accounts and set up a password
and logged in users can change their username or password. Accounts must have
unique usernames.

This was inspired by a login system wtitten in C++ in this video: https://youtu.be/I_aWPGCaaFA
"""

class User: # The class used for user objects. Made up of a username and password

   users = []#Static list of users that are accessed througout the program 

   def __init__(self, username, password):
       self.username = username #User's name
       self.password = password #Password used to log in

#Main menu function. Constantly loops until the user enters exit in it
def Menu():
    userinput = "" #A string used for the user's input throughout the program
    while(userinput != "exit"):
        print("Please select an option:")
        print("'Register' to create a new account")
        print("'Login' to login")
        print("'Users' to show the names of all users")
        print("'Exit' to quit the program")
        userinput = input().lower()
       
       #input prompt is repeated if not valid
        while (userinput != "register" and userinput != "login" and userinput != "users" and userinput != "exit"):
            print("Invalid input. Please select from one of these options:")
            print("'Register' to create a new account")
            print("'Login' to login")
            print("'Users' to show the names of all users")
            print("'Exit' to quit the program")
            userinput = input().lower()
        if (userinput == "register"):
            Register()
        elif (userinput == "login"):
            Login()
        elif (userinput == "users"):
            count = len(User.users)
            print("There are " + str(count) + " user(s).")
            for i in range(len(User.users)): print (User.users[i].username)

#Method for creating a new account
def Register():
    print ("Enter a username. Names must be at least 5 characters long. Enter 'c' to cancel.")
    username = SetUsername()
    
    if(username is not None):    
        print("Enter a password. Passwords must be at least 8 characters long, and have at least one upper case letter, lower case letter, and a non-alphanumeric character.")
        password = SetPassword()
    
        while (password == "r"):
            print("Enter a password. Passwords must be at least 8 characters long, and have at least one upper case letter, lower case letter, and a non-alphanumeric character.")
            password = SetPassword()
    
        if(password is not None):   
            user = User(username, password)
            User.users.append(user)
            UserHome(user)

#Method for setting up a new username
def SetUsername():
    userinput = input()    
    while((len(userinput) < 5 or len([u for u in User.users if u.username == userinput]) > 0)):
        if userinput.lower() == "c": return None
        if len(userinput) < 5:
            print("Usernames must be at least 5 characters long. Please try again:")
        else:
            print(userinput + " has already been taken. Please enter a different username:")
        userinput = input()
    return userinput    

#Method for setting a password for an account
def SetPassword():
    password = input()
    if (password.lower() == "c"): return None
    while (not PasswordCheck(password)):
        print ("Invalid password. Please try again.")
        PasswordErrors(password)
        password = input()
    print("Confirm password. Enter 'r' to reset password.")
    confirmpassword = input()
    while (not confirmpassword == password) and (not confirmpassword == "r") and (not confirmpassword == "c"):
        print ("Passwords do not match. Please try again.")
        confirmpassword = input()
    
    if (confirmpassword.lower() == "c"): return None
    if (confirmpassword.lower() == "r"): return "r"
    return password

#Function to check if password is at least 8 characters long, and has at least 1 upper case and lower case letter and a symbol
def PasswordCheck(password):
    if (any (x.isupper() for x in password) and any (x.islower() for x in password) and any (not x.isalnum() for x in password) and len(password) >= 8):
        return True
    return False

#Method that prints out failed password validation tests
def PasswordErrors(password):
    if len(password) < 8:
        print("Password must be at least 8 characters long.")
    if not any (x.isupper() for x in password):
        print("Password must have at least one upper case letter.")
    if not any (x.islower() for x in password):
        print("Password must have at least one lower case letter.")
    if not any (not x.isalnum() for x in password):
        print("Password must have at least one non-alphanumeric character.")

#Function for a user to log in
def Login():
    print("Enter username:")
    userinput = input()
    while(len([u for u in User.users if u.username == userinput]) == 0):
        if userinput.lower() == "c": return
        print("A user with that name cannot be found. Please try again:")
        userinput = input()
    user = [u for u in User.users if u.username == userinput][0]
    print("Enter password for " +user.username +":")
    userinput = input()
    while(userinput != user.password):
        if(userinput.lower() == "c"): return
        print("Incorrect password. Please try again:")
        userinput = input()
    UserHome(user)
      
#Function that runs when a user is "logged in"
def UserHome(user):
    print("Welcome " + user.username)
    userinput = ""
    while userinput != "3":
        print("Select an option:")
        print("Change username: 1")
        print("Change password: 2")
        print("Log off: 3")
        userinput = input()
        while (userinput != "1" and userinput != "2" and userinput != "3"):
            print("Invalid input. Please select from one of these options:")
            print("Change username: 1")
            print("Change password: 2")
            print("Log off: 3")
        if (userinput == "1"):
            print("Enter a new username. Names must be at least 5 characters long:")
            userinput = SetUsername()
            if userinput is not None: user.username = userinput
        if (userinput == "2"):
            print("Enter current password:")
            userinput = input()
            while (userinput != user.password and userinput != "c"):
                print("Incorrect password. Please try again:")
                userinput = input()
            if userinput.lower != "c":
                print("Enter a new password.")
                userinput = SetPassword()
                if userinput is not None: user.password = userinput

#Menu() is called at the beginning of 
Menu()
    
        