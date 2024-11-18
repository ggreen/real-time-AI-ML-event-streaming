

```sql
CREATE EXTENSION pgml;
SELECT pgml.version();
```


Running Application


java --spring.profiles.active="postgres.ml.sentiment,quorum" sentiment-ai-processor-0.0.1-SNAPSHOT.jar