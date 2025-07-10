# Intcomex Product API

Este proyecto implementa una API RESTful para la gestión de productos y categorías, usando Spring Boot 3, arquitectura limpia, seguridad con JWT y despliegue en contenedores Docker.

## Tecnologías utilizadas

- Java 21
- Spring Boot 3 (Spring Data JPA, Spring Security)
- PostgreSQL / H2
- Docker & Docker Compose
- MapStruct, Lombok
- JWT
- GitHub Actions para CI
- Swagger

## Estructura del repositorio

```bash
.
├── docker-compose.yml
├── .env.dev
├── .env.qa
├── .env.prod
├── application.yml
├── application-dev.yml
├── application-qa.yml
├── application-prod.yml
├── scripts/
│   ├── start-dev.sh
│   ├── start-qa.sh
│   └── start-prod.sh
└── db/
    └── data.sql
```

---

## Clonar el repositorio

```bash
git clone https://github.com/vdiquez/test_intcomex.git
cd intcomex-product-api
```

---

## Entornos disponibles

### 🔧 Desarrollo

#### 1. Ejecutar con Maven (modo local)

```bash
./mvnw clean spring-boot:run -Dspring-boot.run.profiles=dev
```

#### 2. Ejecutar con Docker

```bash
docker-compose --env-file .env.dev up --build
```

#### 3. Construir el proyecto

```bash
./mvnw clean package -DskipTests
```

#### 4. Base de datos y datos de prueba

- Motor: H2
- Script de datos: `resources/data-dev.sql`

Las tablas se crean automaticamente.

Se debe ejecutar el INSERT de Supplier.

Se debe ejecutar el entpoint POST /categories para crear al menos una categoria, para ser utilizada por la generación de productos.

#### 5. Variables y credenciales

- Archivo: `.env.dev`
- Contiene: `DB_USER`, `DB_PASSWORD`, `JWT_SECRET`, `SPRING_PROFILES_ACTIVE`

Las credenciales del usuario admin se encuentran en el archivo application-dev.yml. Estas credenciales sirven para generar un token bearer desde Swagger UI y autorizar el resto de los endpoints.

## Documentación API

Accede a la documentación en:
```
http://localhost:8080/swagger-ui/index.html
```

---

### 🧪 QA

#### 1. Ejecutar con Maven

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=qa
```

#### 2. Ejecutar con Docker

```bash
docker-compose --env-file .env.qa up --build
```

#### 3. Construcción previa

```bash
./mvnw clean package -DskipTests
```

#### 4. Base de datos y datos de prueba

- Motor: PostgreSQL
- Script: `resources/data-qa.sql`

Las tablas se deben crear a partir del query ubicado dentro del archivo .sql

Se debe ejecutar el INSERT de Supplier.

Se debe ejecutar el entpoint POST /categories para crear al menos una categoria, para ser utilizada por la generación de productos.

#### 5. Variables y credenciales

- Archivo: `.env.qa`

Las credenciales del usuario admin se encuentran en el archivo application-qa.yml. Estas credenciales sirven para generar un token bearer desde Swagger UI y autorizar el resto de los endpoints.

## Documentación API

Accede a la documentación en:
```
http://localhost:8081/swagger-ui/index.html
```

---

### 🚀 Producción

Los detalles de la ejecución en producción se encuentran en la colección postman enviada vía correo.

Sin embargo se mantiene el mismo formato de instrucciones como en los ambientes previos.

#### 1. Ejecutar con Maven

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

#### 2. Ejecutar con Docker

```bash
docker-compose --env-file .env.prod up --build
```

#### 3. Construcción previa

```bash
./mvnw clean package -DskipTests
```

#### 4. Base de datos

- Motor: PostgreSQL
- Asegúrate de provisionar la base de datos correctamente antes del despliegue.
- Script de datos: `resources/data-prod.sql`

Las tablas se deben crear a partir del query ubicado dentro del archivo .sql

Se debe ejecutar el INSERT de Supplier.

Se debe ejecutar el entpoint POST /categories para crear al menos una categoria, para ser utilizada por la generación de productos.

#### 5. Variables y credenciales

- Archivo: `.env.prod`

Las credenciales de este ambiente se encuentran dentro de la colección de Postman. Las mismas fueron configuradas en las variables de entorno del servidor.


---

## Seguridad

El proyecto implementa autenticación basada en JWT con `BCrypt` y extracción de roles desde base de datos. Todos los endpoints `GET` y `POST`,  están protegidos por token Bearer a excepción del endpoint para la generación del propio token.

---

## Pruebas

Incluye pruebas unitarias para servicios, pruebas de integración (`@MockBean` y  `Testcontainer`).

---

© Intcomex Technical Challenge - Líder Técnico Integraciones
