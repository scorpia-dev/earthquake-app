# earthquake-app

This is a "Earthquake finder" RESTful Web Service using Java 8 and Spring Boot.

[USGS Earthquake Hazards Program](https://earthquake.usgs.gov/aboutus/) is an organization that analyze earthquake threats around the world. They expose REST API outlining details of recent earthquakes happening around the world - location, magnitude, etc.

This program takes a given city (you provide lat/lon) and finds the 10 nearest earthquakes (earthquakes that happened in the closest proximity of that city).

## Getting list of earthquakes
Web services for fetching Earthquakes: https://earthquake.usgs.gov/earthquakes/feed/v1.0/geojson.php
All earthquakes that happened during last 30 days: https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson

For each earthquake there is a lat/lon location of that earthquake. This program connects to this web service and pulls the earthquake data.

## Calculating distance
the program calculates curve distance between two lat/lon points.

## Program input
Program accepts two numbers on standard input: the latitude and longitude of a city. So for New York the program should be started with numbers:
```
40.730610  
-73.935242  
```
Source: https://www.latlong.net/place/new-york-city-ny-usa-1848.html


## Summary
the program does the following:
* Reads two float numbers from standard input that represent the lat/lon of a city
* Reads a list of earthquakes that happened during last 30 days
* Calculates distance between given city and each of the earthquakes
* Prints the 10 earthquakes with the shortest distance to the given city
* The output list contains the earthquake title and distance in kilometers
* If two earthquakes happened in exactly the same location (they have the same lat/lon) only one of them is printed


## Example usage: 
Example Input:  
 * **Get list of 10 closest earthquakes**
```
GET - http://localhost:8080/earthquakes/{latitude}/{longitude}
 ```
i.e.
```
GET -  http://localhost:8080/earthquakes/40.730610/-73.935242
 ```
Example Output:
```
M 1.3 - 2km SSE of Contoocook, New Hampshire || 331  
M 1.3 - 2km ENE of Belmont, Virginia || 354  
M 2.4 - 83km ESE of Nantucket, Massachusetts || 406  
M 1.3 - 13km ENE of Barre, Vermont || 410  
M 0.7 - 18km NW of Norfolk, New York || 476  
M 2.0 - 17km NW of Norfolk, New York || 476  
M 1.7 - 19km NNW of Beaupre, Canada || 758  
M 1.9 - 13km SW of La Malbaie, Canada || 814  
M 2.4 - 16km N of Lenoir, North Carolina || 840  
M 2.4 - 12km ESE of Carlisle, Kentucky || 896  
```

### Spec -
------
* Accepts JSON 
* Response in JSON 
* JDK8 or higher
* Build with Maven
* Lombok has been used to reduce boilerplate code

### Running
Run as a Spring Boot App
