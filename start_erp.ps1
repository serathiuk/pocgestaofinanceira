podman-compose -f docker-compose.prod.yaml down
podman-compose down
podman-compose up -d
./mvnw package
cd financeiro
podman build  -t serathiukerp/financeiro .
cd..
podman-compose -f docker-compose.prod.yaml up -d