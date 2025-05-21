

Start Postgres ML

```shell
./deployments/local/data-services/postgres/podman/start-postgresml.sh
```

Start RabbitMQ

```shell
./deployments/local/data-services/rabbit/podman/start-rabbitmq.sh
```

Start ValKey

```shell
./deployments/local/data-services/valkey/podman/start.sh
```

# Application

Processor

```shell
java -jar applications/sentiment-ai-processor/target/sentiment-ai-processor-0.0.1-SNAPSHOT.jar  --spring.profiles.active="postgres.ml.sentiment,quorum"
```

Consumer

```shell
java -jar applications/consumer-ai-sink/target/consumer-ai-sink-0.0.1-SNAPSHOT.jar
```

Web UI

```shell
java -jar applications/web-ai-app/target/web-ai-app-0.0.1-SNAPSHOT.jar
```
--------------
# Testing

````shell
open http://localhost:8080
````

```shell
open http://localhost:8080/mail.html
```

```properties
to=queen
from=josiah
subject=But, I just cannot eat anymore meat loaf
message=I mean really. I do not know what is it about meatloaf. <br/> When I think of loaf, I think it should be bread. <br/> But, meat, that it is just nasty
```

```properties
to=queen
from=josiah
subject=I love my beautiful wife
message=I really really do! There is no doubt in my heart. <br/> From the first time I saw her, I knew.
```


## API to submit sentiment 


```shell
curl -X 'POST' \
  'http://localhost:8080/letters/posts' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "author": "daughter",
  "receiver": "queen",
  "subject": "NO MORE MEATLOAF, we do not like",
  "body": "I have started a petition, to STOP the production of NASTY MEATLOAF</b>. Thank you for your time and attention!!"
}'
```


```shell
curl -X 'POST' \
  'http://localhost:8080/letters/posts' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "author": "son",
  "receiver": "queen",
  "subject": "YOU ARE AWESOME",
  "body": "Thank you for your LOVE, KINDEST and PATIENCE with ME!! <br/><b>I LOVE YOU SO MUCH</b>"
}'
```


```shell
curl -X 'POST' \
  'http://localhost:8080/letters/posts' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "author": "son",
  "receiver": "queen",
  "subject": "MEATLOAF IS NOT YUMMY - please stop",
  "body": "PLEASE PLEASE PLEASE NO MORE MEATLOAF</b>"
}'
```



```shell
curl -X 'POST' \
  'http://localhost:8080/letters/posts' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "author": "daughter",
  "receiver": "queen",
  "subject": "WHAT is WORST - MEATLOAF or Discipline",
  "body": "I would rather have NO TV for a YEAR then eat any more meatloaf"
}'
```


####

Cleanup


```shell
delete from letter_entity;
```