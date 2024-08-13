

# Postgres


Setup 

```shell
docker pull pgvector/pgvector:pg16

docker run -it --rm --name postgres -p 5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres pgvector/pgvector:pg16
```



```shell
docker run \
    -it \
    --rm \
     --name postgres \
      -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres \
    -v postgresml_data:/var/lib/postgresql \
    -p 5433:5432 \
    -p 8000:8000 \
    ghcr.io/postgresml/postgresml:2.7.12 
```


```json
[
  {
 
    "content": "Spring AI rock"
  }
]
```

```shell
 INSERT INTO vector_store (id, 
 content, metadata, embedding) 
 VALUES (gen_random_uuid (), '[ -0.0116,   -0.0326,   -0.0053]', 
 null, '[ -0.0116,   -0.0326]') 
```


```json

SELECT embedding <->
'[-0.0025, -7.0E-4, -0.0121, 0.0118, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]'
AS distance FROM vector_store;

```