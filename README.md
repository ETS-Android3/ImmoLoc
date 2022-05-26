<img align="left" width="70" height="70" src="https://user-images.githubusercontent.com/76013394/170589344-7d286dcc-33cf-4d6c-a01e-842c9f7549e9.png" alt="ImmoLoc">

# ImmoLoc

### Quick description 

ImmoLoc is the name of our real estate rental mobile app that runs on Android platforms. It has been developped as part of a school project for a course called "mobile development" of the first year of our master's degree. 

### Technologies used
#### Development
* IDE : Android Studio
* Language : Java
* Database : Room persistence library [^1]
#### Project management and conception
* Trello for tasks
* Drawio for MCU
* GanttProject for planning

### What can you do with this app ?
This app allows you to search for real estate rentals. You can filter a wide rang of information, such as the most basics : price, square meter, start and end date of rental, place, geographic coordinates and more. There is also a chatting feature which allow you to contact directly user on the app.<br>
This application is designed to be easy to use, quick and with clarity for everyone.

-------------------------------------

### How to use our project ?
&rarr; Open a terminal and type `git clone https://github.com/adlina1/ImmoLoc.git`<br>
&rarr; Open Android Studio software <br>
&rarr; Import the project which is in the folder you runned the previous command <br>
&rarr; Once openned, click on Trust Project <br>
&rarr; Just run the project (green triangle) in your own device (enable developper options mode) or with an emulator. <br>

**_NOTE:_** If there are problems with n.json file, make sure you have the right migrations (json files) that corresponds to the ones in the AppDataBase.java file, entry point of our DB with Room.

### Details of our app

The greatest part of the functionalities inherent to real estate rentals are accessible by creating an account. Otherwise, you will just be able to see ads published by users already registered. <br>
Two type of registration are provided : Particulier and Profesionnel. Theoretically, you could access to more functionalities by registering profesionnally. It's a prospect for improvement of our application. For now, it has not any effect on your continuation in the app.

<p align="center">
  <img src="https://user-images.githubusercontent.com/76013394/170589631-bf2ac861-adbf-42e3-bef5-f5f4bff00f6f.jpg" width="200" hspace="40" />
  <img src="https://user-images.githubusercontent.com/76013394/170589641-4f1b3397-cd19-492a-8076-b602151ebb22.jpg" width="200" />
</p>

Once your account is created, you can access the functionalities developped : you can publish your own ad, manage your profile, contact users if an ad interest you, modify your own ad(s), search for a specific real estate through a set of filters, and so on.


### Prospects for improvement
As stated before, we could distinguish the two types of registration: individual or profesional. We could provide a machine learning tool for ads recommandations. To strenghten the posting of an ad and visualize some statistics for profesional registered users about ads they have published.
Also put an ad in favorite, get an alert when an ad that potentially could interest us or when a property has been rented, for example. <br> <br>
&copy; Special thanks to Angie for the nice logo designed for our application!

### Contributors 
* Adrien Linares
* Hugo Maître


[^1]: https://developer.android.com/training/data-storage/room
