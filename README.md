## ToldCalculator

#### Motivation for creating this app
My motivation for creating this app stems from when I was a medevac pilot.  The time from receiving a call to being airborne is critical and I often thought of ways to shorten that time. I could easily find mobile apps for filing flight plans and viewing weather but performance apps proved more challenging to find.  

#### Current state
I would consider this a bare bones functional prototype that a skilled user could test. The app currently has the basic functionality that I originally envisioned. In most cases it is able to gather weather/airport data and calculate performance numbers. Some unimplemented features include:  

* Allowing the user to manually enter weather information if service in unavailable  
* Better error handling to prevent the app from crashing when data is invalid/unexpected.  
* The weather page needs  more functionality to really be useful.  

#### Testing
Android Emulator - Nexus 5X API 27  
Orientation locked to portrait

#### 3rd party libraries
* [Retrofit 2.4.0](http://square.github.io/retrofit/)  
* [Retrofit-SimpleXML 2.2.0](http://simple.sourceforge.net/)  
* [Apache commons-csv 1.5](https://commons.apache.org/proper/commons-csv/)  

#### External services
* Aviation Weather Center Text Data Server (TDS) ver 1.3  
* Federal Aviation Administration - AIS Open Data  
**csv files (airports/runways)

#### Aesthetic/cosmetic improvements
* Improve the Saved Told Data and Weather pages look and readability.  
* Ability to swipe to move back and forth between fragments.  

#### Functional stretch goals
* Add a few generic profiles for common aircraft.  
* Allow the user to create there own aircraft profiles and enter in there own performance data for a specific aircraft.  
* Allowing the user to share performance numbers in some form. Possibly text or email.  

#### Links to wireframes and user stories
[Wireframe/User stories](docs/Wireframe.pdf)  

#### Links to ERD and DDL
[ERD](docs/ERD.pdf)  
[DDL](docs/DDL.sql)  

#### Link to Javadoc
[Javadoc](docs/api/index.html)  

#### Links to licenses
[Licenses](docs/markdown/LICENSES.md)  

#### Instructions for building the app
[Build Instructions](docs/markdown/BUILD.md)  

#### Instructions for using the app
[User Instructions](docs/markdown/INSTRUCTIONS.md)  
