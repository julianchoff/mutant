# API_MUTANT
API para verificar si un ADN enviado corresponde a un mutante o un humano. Adicional a lo anterior tenemos la posibilidad de consultar las estadísticas de los ADN analizados.
# Tecnologías usadas
- Java jdk-11
- Spring Boot
- Gradle
- MySQL
- Swagger
# Arquitectura
Se utilizó una arquitectura de capas del módulo de spring framework , spring boot.

![image](https://user-images.githubusercontent.com/65981603/132271031-5bba020a-84cf-453f-ba94-1ad44672d999.png)
# Cobertura
Se logró la siguiente cobertura de pruebas unitarias:

![image](https://user-images.githubusercontent.com/65981603/132272721-1a153652-eb2b-4f39-b851-19b956ad7795.png)

# Despliegue
Para el despliegue de la aplicación se utilizó Google Cloud Platform

![image](https://user-images.githubusercontent.com/65981603/132274701-bbb3d688-6ee2-48ac-842b-fc89a4206414.png)

# Documentación
Se utilizó el framework swagger para la documentación del api rest: https://is-mutant-meli.rj.r.appspot.com/swagger-ui.html#


# Consumo de los servicios

- Para el consumo de los servicios se puede utilizar por ejemplo la herramienta postman y lanzar peticiones de manera local y en nube:

  - Nube:
    - Método:POST
    - URL:https://is-mutant-meli.rj.r.appspot.com/v1/mutant
    - Body:{"dna":["CGGATG","CAGTGC","TGATGT","AGTAGA","CACCTA","AGAAGG"]}

  - Local:
    - Método:POST
    - URL: http://localhost:8080/v1/mutant
    - Body:{"dna":["CGGATG","CAGTGC","TGATGT","AGTAGA","CACCTA","AGAAGG"]}

  - Nube:
    - Método:GET
    - URL:https://is-mutant-meli.rj.r.appspot.com/v1/stats

  - Local:
    - Método:GET
    - URL: http://localhost:8080/v1/stats


