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

| Método |                Path                |
|:------:|:----------------------------------:|
|   GET  |          /api/listar-todos         |
|   GET  |          /api/evento/{id}          |
|  POST  |           /api/cadastrar           |
|   PUT  |          /api/evento/{id}          |
| DELETE |              /api/{id}             |

## Tecnologias Usadas

- Java 8 
- Apache Maven 3.6.3
- Spring Boot 4
- PostgreSQL 12.3


