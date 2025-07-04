# Intcomex Product API

Este proyecto implementa una API RESTful para la gestión de productos y categorías, usando Spring Boot 3, arquitectura limpia, seguridad con JWT y despliegue en contenedores Docker.

## Tecnologías utilizadas

- Java 17+
- Spring Boot 3 (Spring Data JPA, Spring Security)
- PostgreSQL / H2
- Docker & Docker Compose
- MapStruct, Lombok
- JWT real con roles
- GitHub Actions para CI
- Swagger (opcional)

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
```

## Cómo levantar la aplicación

### Entorno de desarrollo (H2 / PostgreSQL local)

```bash
cd scripts
./start-dev.sh
```

### Entorno QA

```bash
cd scripts
./start-qa.sh
```

### Entorno producción

```bash
cd scripts
./start-prod.sh
```

> Asegúrate de tener Docker y Docker Compose instalados, y revisar los archivos `.env.*` con las credenciales y claves adecuadas.

## Variables de entorno

Puedes usar el archivo `.env.example` como plantilla:

```env
SPRING_PROFILES_ACTIVE=dev
DB_USER=postgres
DB_PASSWORD=changeme
JWT_SECRET=your-secret-key
```

## Seguridad

El proyecto implementa autenticación basada en JWT con `BCrypt` y extracción de roles desde base de datos. Todos los endpoints `POST`, `PUT`, `DELETE` están protegidos por token Bearer.

## Pruebas

Incluye pruebas unitarias para servicios, pruebas de integración para login y uso de token en endpoints protegidos (`MockMvc` + `@MockBean`).

## Documentación API

Puedes integrar fácilmente Swagger con:

```xml
<dependency>
  <groupId>org.springdoc</groupId>
  <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
  <version>2.2.0</version>
</dependency>
```

Y acceder a:  
`http://localhost:8080/swagger-ui.html`

---

© Intcomex Technical Challenge - Líder Técnico Integraciones
