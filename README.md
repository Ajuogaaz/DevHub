Original App Design Project - README Template
===
#Week 2 walk through.  
 -  <img src='https://github.com/Ajuogaaz/DevHub/blob/master/week2.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

#Week 1 walk through.  
 -  <img src='https://github.com/Ajuogaaz/DevHub/blob/master/sprint1.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

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

#### Required Must-have Stories

1. **SignUp Requirement** [Issue #6](https://github.com/Ajuogaaz/DevHub/issues/6)  *Status*: ***Closed***  
    - Users are able to sign up new accounts in the register activity  
2. **Login and Logout Requirement** [Issue #6](https://github.com/Ajuogaaz/DevHub/issues/6) *Status*: ***Closed***    
    - User is able to login through the login activity and logout of the app
3. **Database Requirement** [Issue #1](https://github.com/Ajuogaaz/DevHub/issues/1) *Status*: ***Closed***
    - The app is configured with the Parse nosql database hosted in heroku
    - User can compose a post that is posted to the tiomeline after being saved on the parse database
4. **Camera Requirement** [Issue #18](https://github.com/Ajuogaaz/DevHub/issues/18) *Status*: ***Closed***
    - Users are able to use the camera to take a picture and set their profile picture using it 
5. **Sdk Requirement** [Issue #19](https://github.com/Ajuogaaz/DevHub/issues/19) *Status*: ***Closed***
    - Users are required Auth their github accounts to access full functionality.
6. **MultipleViews** [Issue #3](https://github.com/Ajuogaaz/DevHub/issues/3) *Status*: ***Closed***
    - User lands on the home fragment where they see the floating compose icon for compose activity.
    - User can switch to the repo fragment where they’ll be able to see all their repositories in a recycleview
7. **Gesture Recognizer Requirement** [Issue #13](https://github.com/Ajuogaaz/DevHub/issues/13) *Status*: ***Open***
    - User can double tap the like icon to like a post
8. **Complex Algorithm Requirement**. [Issue #11](https://github.com/Ajuogaaz/DevHub/issues/11) *Status*: ***Open***
    - In the timeline View user will see posts ranked.
9. **Incoporated External Library for polish**. [Issue #20](https://github.com/Ajuogaaz/DevHub/issues/20) *Status*: ***Closed***
    - Use glide an external library to load Images.
    - Add the External material Design Libraries.
10. **Animation Requirements** [Issue #12](https://github.com/Ajuogaaz/DevHub/issues/12) *Status*: ***Open***
    - Add an animation to the profile activity changing the toolbar.

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
