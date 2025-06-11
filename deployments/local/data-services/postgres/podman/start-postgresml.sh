podman run \
    -it \
    --name postgresml \
    --rm \
     -v postgres_data:$PWD/runtime/postgres_data \
    -p 5432:5432 \
    -p 8000:8000 \
    ghcr.io/postgresml/postgresml:2.7.12 \
    sudo -u postgresml psql -d postgresml



#postgresml:2.7.12
#ghcr.io/postgresml/postgresml:2.10.0

#docker run -it --rm -p 5432:5432 -p 8000:8000 -e ALLOW_EMPTY_PASSWORD=true \
# oawofolu/postgresml-bitnami:15