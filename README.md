# mobileRepo
## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)
* [AppArchitecture](#AppArchitecture)

## General info
This project is used to monitor the Air quality Index of various cities
At first, AQI data of all the cities is fetched at an interval selected by the user, and on click of a particular city, Its AQI index is monitored and displayed every 30 sec.
	
## Technologies
Project is created with:
* Kotlin 1.4.32
* JavaVersion.VERSION_1_8
	
## Setup
To run this project, clone the repo :
git clone https://github.com/PrachiJainAndroid/mobileRepo.git

## AppArchitecture
The MVVM architecture is being used.KOIN is used for DI.
On First Screen, A webSocket connection is established in the Repo layer and the data is captured by the view Model.This connection is only established after a particular time interval is lapsed.
On click of the City Row, Another request is made i.e socket connection is established, and data is saved into the Database only after 30 seconds .
It then directs to the second screen containing AQI data of the selected city every 30 seconds.

Color Coding is done to highlight if AQI is Good,Average or Poor.

