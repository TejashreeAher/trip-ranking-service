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
The idea is to do merge sort. divide the trips in a batch of 5, individually sort them and them merge the sorted trips.
    At the service level, the trips are divided into batches of size BATCH_SIZE (in this case 5) 
    and the model is called for each of these batches individually. The merge step is incomplete. The end result, right now is just the concatenation of the result of all the groups.

## Next Steps : 
Actually remember the previous results and use them to somehow predict the sequence (at least for some). This cache can be in-memory or in a remote cache like redis. 
To avoid using redis, one can write the cache to hdfs or any file and then whenever the service restarts, load the file into an in-memory cache and can proceed.
