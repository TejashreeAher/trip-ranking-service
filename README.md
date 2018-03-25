## Build and compile :
   ```bash
   sbt clean compile test
   ```

## Running Service

```bash
sbt run
```
## Service end point :
 POST http://localhost:9000/rankTrips
 Request data :
``` [{
    {"trip_id":1,
     "departure_time":12,
     "departure_date": 2701,
     "trip_hours":1,
     "num_conenctions":3,
     "distance":400,
     "price":12.50
     },
     {
     "trip_id":10,
     "departure_time":12,
     "departure_date": 2701,
     "trip_hours":1,
     "num_conenctions":3,
     "distance":400,
     "price":12.50
     },
 }]
 ````

 Response  : List of the trip Ids sorted


## Logic:
    At the service level, the trips are divided into batches of size BATCH_SIZE (in this case 5) 
    and the model is called for each of these batches individually. The end result, right now is just the 
    concatenation of the result of all the groups.


