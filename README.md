Original App Design Project - README Template
===

Sprint1 walk through  <img src='https://github.com/Ajuogaaz/DevHub/blob/master/sprint1.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

# DEVHUB

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
 This is a social networking app that connects different developers and help them keep touch with different trending open source communities in github and whatever they are interested in.

### App Evaluation
[Evaluation of your app across the following attributes]
- **Category:** Social Networking
- **Mobile:** Android with a mobile first experience
- **Story:** Allows users to share whatever they are working developing and also keep touch with what people in their network are developing
- **Market:** Any person interested in seeing what different software and developments are being made. Ability to follow other developers and see their posts and get updates on the latest developments
- **Habit:** Users can post throughout the day many times. Users can scroll through endless post of from other developers and see what they are working on and also get ideas on what tools are getting popular with the developers field. 
- **Scope:** Everyone above 

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* Users are able to sign up new accounts in the register activity (Sign up requirement)
* User is able to login through the login activity and logout of the app (login requirement) 
* The app is configured with the Parse nosql database hosted in heroku ( Database requirement)
* Users are able to use the camera to take a picture and set their profile picture using it (Camera requirement)
* Users are required Auth their github accounts to access full functionality. (sdk requirement).
* User lands on the home fragment where they see the floating compose icon for compose activity. (Multiple views)
* User can compose a post that is posted to the tiomeline after being saved on the parse database (Database requirement)
* User can double tap the like icon to like a post (gesture recognizer requirement)
* User can switch to the repo fragment where they’ll be able to see all their repositories in a recycleview(multiple views requirements)
* In the timeline View user will see posts ranked in order of the latest factoring in who they follow and popular posts with more likes.(complex algorithm requirements)
* Use glide an external library to shape the image for user to be circular. (incoporate external library fpr polish).
* Add an animation for the login screen as the screen appears. (Animation requirement)

**Optional Nice-to-have Stories**

* Users can be able to send messages
* User can be able to comment and view posts
* User can be able to see number of post they have.
* User can see number opf followers and following they have
* Users can see previous posts below their profile and scroll through them
* Users will be able to see additional information on each repository such as number of stars, downloads, and forks


### 2. Screen Archetypes

* Signup Screen
   * Users are able to sign up new accounts in the register activity
   * Users are required Auth their github accounts to access full functionality.
* Login Activity
   * User is able to login through the login activity and logout of the app 
* Timeline Activity 
    *  User lands on the home fragment where they see the floating compose icon for compose activity.
    *  User can double tap the like icon to like an app
    *  User will see posts ranked in order of the latest factoring in who they follow and popular posts with more likes.
* Compose Activity
    * User can compose a post that is posted to the tiomeline after being saved on the parse database
* Profile Activity
    * Users are able to use the camera to take a picture and set their profile picture using it 
    * Use glide an external library to shape the image for user to be circular.
* repo Activity
    * User can switch to the repo fragment where they’ll be able to see all their repositories in a recycleview
    
    
### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Interact with posts in timeline
* Profile
* Repositories

optional
* Messages
* Search
* Settings


**Flow Navigation** (Screen to Screen)

* Sign Up screen the authenticate Github
* Login screen then auth github
* Profile -> Texts and posts
* Timeline -> Posts 
* Compose -> Create new posts
     

## Wireframes
These are the pictures of the hand drawn wireframes with the core functionalities potrayed in though UI.
![](https://i.imgur.com/19WeuWH.jpg)
![](https://i.imgur.com/OE1dBs6.jpg)
 

### Digital Wireframes & Mockups

click [here](https://framer.com/share/DEVHUB-yahfAQ8BazAXITmUOE8C) to view a digital wire frame

### Interactive Prototype

click [here](https://framer.com/share/DEVHUB-yahfAQ8BazAXITmUOE8C) to view an interactive prototype in framer. It is fully equiped inside a virtual mobile frame and you get a simulation of how the app will exactly work.

Or watch this short summary.




## Schema 
### Models

### Post model

| property      | Type.    | Description                                                      |
| ------------- | -------- |:---------------------------------------------------------------- |
| User          | Pointer to user  | image and username of author                                                                 |
| objectId      | String   | Unique Id for the user post                                      |
| Title         | String   | the title of the post with format user and the github repository |
| body          | String   | The body say by the author                                       |
| commentsCount | Number   | number of comments                                               |
| likesCount    | Number   | number of likes                                                  |
| createdAT     | DateTime | Date when a post was created                                     |
| updatedAt     | DateTime | date when post was last updated                                  |
| objectId      | String   | Unique Id for the user post                                      |



### User Model



| Column 1 | Column 2 | Column 3 |
| -------- | -------- | -------- |
| createdAT     | DateTime | Date when a post was created                                     |
| updatedAt     | DateTime | date when post was last updated                                  |
| objectId      | String   | Unique Id for the user post 
| FollowersCount | Number   | number of followers                                               |
| FollowingCount    | Number   | number of following
| githubUser name         | String | username of user
| UserProfile pic          | File | image of user
| User Number of repository        | Number  | number of github repositories






### Networking

* Profile Activity
    * (Read/GET) Query all post where user is author
    * (Read/Get) Query user information such as folowers and following
    * (Update/PUT) Add a profile pic

* Compose Activity
    * (Create/PUT) Create a new post
* Timeline Activity
    * (Read/GET) Query all posts where user is author
    * (Create/POST) Create a new like on a post
    * (Create/POST) Create a new comment on a post
* Repo Activity
    * (Read/GET) Get a list of all repositories
