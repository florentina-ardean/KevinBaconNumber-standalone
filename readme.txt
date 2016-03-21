/*
 * $Author: florentina.ardean $
 * $Revision: 0.0.1 $
 * $Date: 201616/04/21 $
 *
 * ====================================================================
 *
 */

//////////////////////////////////////////////////////////////////////////////////
//      Kevin Bacon Number  -  $Date: 201616/21/ $
//////////////////////////////////////////////////////////////////////////////////



0. Table of Contents
==================================================================================

0. Table of Contents
1. Introduction
2. Build prerequisites
3. Build instructions
4. Run instructions

1. Introduction
==================================================================================
It's a programmatic version of the Six Degrees of Kevin Bacon: https://en.wikipedia.org/wiki/Six_Degrees_of_Kevin_Bacon .

For example, for the input "Bruce Willis", the program might (or might not...) output:
Bruce Willis -> Tom Hanks -> Kevin Bacon.

2. Build prerequisites
==================================================================================
You need to have/install: 
-Java 7 
-eclipse 
-git [optional]


3. Build instructions
==================================================================================
Get the project from the repository to your <project_location>:
$git clone https://florentina_ardean@bitbucket.org/florentina-ardean/KevinBaconNumber-standalone/

or by downloading project (zip file) from: https://github.com/florentina-ardean/KevinBaconNumber-standalone/

Import project in Eclipse.
Set up workspace compiler to JAVA 7.

Select the project
Right click on the project
Select Run as ->Run Configurations
Main
Project: SixDegreesOfKevinBacon
Main class Main(default package)

Click Apply

Go to: Eclipse->Export->Java->Runnable JAR file
Launch Configuration: Main-SixDegreesOfKevinBacon
Export destination: <output_folder>
Check: Extract required libraries into generated JAR

Go to <folder_output>
Create folder data
Copy there json file containing film and cast.



4. Run instructions
==================================================================================

Go to <output_folder>
Run command from cmd:

java -jar <jar_name> "<actor_name>"
e.g: java -jar KevinBacon.jar "Will Smith"

or 

GO to Eclipse
Select the project
Right click on the project
Select Run as ->Run Configuration
Arguments
Program arguments: "Will Smith"
Run

