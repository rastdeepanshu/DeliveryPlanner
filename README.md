# Delivery Planner

A service which can be used to determine the delivery/destination order of a vehicle/person.  
The service uses Dynamic programming to implement the solution to the Travelling Salesman Problem (TSP).

Actual latitude and longitude can be given as inputs. The service uses **Google's distance matrix API** to calculate the road distance between the points given. The service doesn't takes into consideration the traffic situation

### Algorithm

The Algorithm used is [Held–Karp algorithm](https://en.wikipedia.org/wiki/Held%E2%80%93Karp_algorithm)

### Run

`mvn spring-boot:run`

Swagger can be accessed at: *{host}*/swagger-ui.html