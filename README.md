# Example for kafka using multiple consumer and load balancing with partition

## Startup

```
docker-compose up -d --build
```

## Send Request
```
curl --location 'localhost:8080/api/single-notification/send' \
--header 'Content-Type: application/json' \
--data-raw '{
    "message": "Otis_Corkery52@yahoo.com",
    "totalMessageCount": 500
}'
```