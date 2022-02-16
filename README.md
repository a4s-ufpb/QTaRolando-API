# API de Eventos

Projeto para fornecer dados sobre eventos de diversas áreas na Paraíba.

## Rodando a API

Execute no terminal os comandos abaixo
```sh
$ git clone https://github.com/a4s-ufpb/QTaRolando-API.git
$ cd QTaRolando-API/
$ ./mvnw spring-boot:run
```
Após rodar o último comando, o spring iniciará vários processos, incluindo o tomcat na porta 8080 por padrão.
Endereço da aplicação: ```http://localhost:8080/```

## Métodos de Requisição HTTP
### Métodos para eventos:
| Método |                Path                |
|:------:|:----------------------------------:|
|   GET  |          /api/events         |
|   GET  |          /api/events/page?page=&linesPerPage=&orderBy=&direction=  
|   GET  |          /api/events/{id}   |
|   GET  |          /api/events/category/{id}   |
|  POST  |           /api/events           |
|   PUT  |          /api/events/{id}          |
| DELETE |              /api/events/{id}             |

### Métodos para usuários:

| Método |                Path                |
|:------:|:----------------------------------:|
|  GET  |          /api/users/{id}         |
|  GET  |          /api/users/email?value=         |
|  GET  |          /api/users         |
|  GET  |          /api/users/page?page=&linesPerPage=&orderBy=&direction=         |
|  POST  |           /login          |
|  POST  |           /api/users/refresh_token         |
|  POST  |           /api/users         |
|  PUT  |          /api/users/{id}          |
| PATCH  |          /api/users/password         |
| DELETE |              /api/users/{id}             |
## Tecnologias Usadas

- Java 8 
- Apache Maven 3.6.3
- Spring Boot 4
- PostgreSQL 12.3


