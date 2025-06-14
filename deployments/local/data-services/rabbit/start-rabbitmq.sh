docker network create tanzu
docker run --name rabbitmq01  -d --network tanzu --rm -e RABBITMQ_MANAGEMENT_ALLOW_WEB_ACCESS=true -e RABBITMQ_SERVER_ADDITIONAL_ERL_ARGS="-rabbitmq_stream advertised_host localhost -rabbitmq_stream advertised_port 5552 -rabbitmq_stream tcp_listeners [5552]" -e RABBITMQ_USERNAME=guest -e RABBITMQ_PASSWORD=guest -p 5672:5672 -p 5552:5552 -p 15672:15672  -p  1883:1883  rabbitmq:4.1-management

sleep 10

docker exec rabbitmq01 rabbitmq-plugins enable rabbitmq_stream