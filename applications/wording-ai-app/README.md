

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