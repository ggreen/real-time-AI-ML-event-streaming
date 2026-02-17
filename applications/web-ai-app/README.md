
# Getting Started

Start Rabbit
```sheel
deployments/local/data-services/rabbit/podman/start-rabbitmq.sh
```

Start Valkey

```shell
deployments/local/data-services/valkey/podman/start.sh
```

Start Postgres

```shell
deployments/local/data-services/postgres/podman/pgvector.sh
```

Start Web application
```shell
java -jar applications/web-ai-app/target/web-ai-app-0.0.1-SNAPSHOT.jar --spring.profiles.active=amqp1
```


# Postgres ML Queries

```sql
select * from vector_store
delete from vector_store;
delete from letter_entity;
drop table letter_entity;

SELECT embedding <-> 
'[-0.0025, -7.0E-4, -0.0121, 0.0118, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]' 
AS distance FROM vector_store;

 INSERT INTO vector_store (id, 
 content, metadata, embedding) 
 VALUES (gen_random_uuid (), '[ -0.0116,   -0.0326,   -0.0053]', 
 null, '[ -0.0116,   -0.0326]') 
 
 
 ON CONFLICT (id) 
 
 DO UPDATE SET content = [ -0.0116,   -0.0326,   -0.0053] 
```