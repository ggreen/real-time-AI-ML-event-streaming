

```sql
CREATE EXTENSION pgml;
SELECT pgml.version();
```


Running Application


```shell
java -jar applications/sentiment-ai-processor/target/sentiment-ai-processor-0.0.1-SNAPSHOT.jar  --spring.profiles.active="postgres.ml.sentiment,quorum"
```
