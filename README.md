# Codingame Challenge

API CRUD de super héroes.
Utiliza una base de datos en memoria H2.
Todos los endpoints expuestos a excepción de /api/v1/authenticate esperan un JWT como header.

# Endpoints expuestos y curls de ejemplo

Autenticación /api/v1/authenticate
Las credenciales para gestionar el token de autenticación se encuentran explícitas en JWTUserDetailsService y son las siguientes (solo existe un usuario dentro de la aplicación):

```
username: brian
password: password
```

**POST /api/v1/authenticate** *Request*

```
curl --location --request POST 'http://localhost:8080/api/v1/authenticate' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "brian",
    "password": "password"
}'
```

---

**POST /api/v1/authenticate** *Response*

```
200 OK
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJicmlhbiIsImV4cCI6MTY2ODUzNzAyNSwiaWF0IjoxNjY4NTE5MDI1fQ.YZMNMCF3SEzOEiNJzld9V5t7KpSOY7MPRlD_djWLv3m27-nnabx1dMRsAxoYLB0I7r1vrOxICRBQkPJv4NDBRw"
}
```

Una vez se ejecuta esta petición en *Postman* se corre un script que escribe en una variable de entorno el token JWT para utilizar en los recursos protegidos.

**POST /api/v1/super-hero** *Request*

```
curl --location --request POST 'http://localhost:8080/api/v1/super-hero' \
--header 'Authorization: Bearer {{accessToken}}' \
--header 'Content-Type: application/json' \
--data-raw '{
    "uuid": "2d46131e-6453-11ed-81ce-0242ac120002",
    "name": "Spiderman"
}'
```

**POST /api/v1/super-hero** *Response*

```
201 Created 
{
    "uuid": "2d46131e-6453-11ed-81ce-0242ac120002",
    "name": "Spiderman"
}
```

---

**GET /api/v1/super-hero** *Request*

```
curl --location --request GET 'http://localhost:8080/api/v1/super-hero' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer {{accessToken}}'
```

**GET /api/v1/super-hero** *Response*

```
200 OK 
[
    {
        "uuid": "2d46131e-6453-11ed-81ce-0242ac120002",
        "name": "Spiderman"
    }
]
```

---

**GET /api/v1/super-hero/{{uuid}}** *Request*

```
curl --location --request GET 'http://localhost:8080/api/v1/super-hero/2d46131e-6453-11ed-81ce-0242ac120002' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer {{accessToken}}'
```

**GET /api/v1/super-hero/{{uuid}}** *Response*

```
200 OK 
{
    "uuid": "2d46131e-6453-11ed-81ce-0242ac120002",
    "name": "Spiderman"
}
```

---

**GET /api/v1/super-hero/search?q={{query}}** *Request*

```
curl --location --request GET 'http://localhost:8080/api/v1/super-hero/search?q=man' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer {{accessToken}}'
```

**GET /api/v1/super-hero/search?q={{query}}** *Response*

```
200 OK 
[
    {
        "uuid": "2d46131e-6453-11ed-81ce-0242ac120002",
        "name": "Spiderman"
    },
    {
        "uuid": "2d46131e-6453-11ed-81ce-0242ac120003",
        "name": "Superman"
    }
]
```

---

**DELETE /api/v1/super-hero/{{uuid}}** *Request*

```
curl --location --request DELETE 'http://localhost:8080/api/v1/super-hero/2d46131e-6453-11ed-81ce-0242ac120002' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer {{accessToken}}'
```

**DELETE /api/v1/super-hero/{{uuid}}** *Response*

```
200 OK 
```

---

**PATCH /api/v1/super-hero/{{uuid}}** *Request*

```
curl --location --request PATCH 'http://localhost:8080/api/v1/super-hero/2d46131e-6453-11ed-81ce-0242ac120002' \
--header 'Authorization: Bearer {{accessToken}}' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Hombre araña"
}'
```

**PATCH /api/v1/super-hero/{{uuid}}** *Response*

```
200 OK 
```

*Respuestas en caso de error*

**El recurso no existe**

```
404 Not Found
{
    "timestamp": "2022-11-15T15:36:42.833+00:00",
    "message": "Super Hero with UUID2d46131e-6453-11ed-81ce-0242ac120002 not found.",
    "details": "uri=/api/v1/super-hero/2d46131e-6453-11ed-81ce-0242ac120002"
}
```

**El recurso ya existe**

```
422 Unprocessable Entity
{
    "timestamp": "2022-11-15T15:37:10.132+00:00",
    "message": "Super Hero with UUID2d46131e-6453-11ed-81ce-0242ac120002 already exists.",
    "details": "uri=/api/v1/super-hero"
}
```

## Ejecución en entorno local

Para ejecutar el servicio localmente (por defecto el servidor escucha en el puerto 8080):

```sh
mvn spring-boot:run
```

## Ejecución de tests unitarios y de integración

Para ejecutar los tests unitarios y de integración:

```sh
mvn test
```

## Colección Postman

Se encuentra en la carpeta [docs](.docs/Codingame_Challenge.postman_collection.json).

## Docker

Para construir la imagen según el Dockerfile en la raíz del proyecto:

```sh
docker build -t codingame-challenge .  
```
Se generará una imagen con el nombre codingame-challenge cuyo tag será latest.
Para correr la imagen generada en el paso previo:

```sh
docker run -p 8080:8080 codingame-challenge -d
```

Esto creará un contenedor, lo pondrá en ejecución con un nombre al azar que expondrá el puerto 8080 utilizado por Spring Boot y correrá en background.

## Flyway para el manejo de migraciones

Se encuentra a nivel raíz del proyecto la carpeta db/migration que contiene la creación de la única tabla en la aplicación a modo ejemplo.
Para ejecutar la migración (escribe en la misma base de datos en memoria H2):

```sh
 mvn clean flyway:migrate
```

La configuración del plugin se encuentra declarada en el pom junto con las credenciales de la base de datos.

