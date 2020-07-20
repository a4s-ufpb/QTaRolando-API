# API QTaRolando

API para persistência e validação de dados, que fornecerá informações dos eventos para o app QTaRolando.

### Rodando a API

Execute no terminal os comandos abaixo
```sh
$ git clone --branch api https://github.com/a4s-ufpb/QTaRolando.git
$ cd QTaRolando/qtarolando-v1
$ ./mvnw spring-boot:run
```
Após rodar o ultimo comando, o spring iniciará vários processos, incluindo o tomcat na porta 8080, conforme foi configurado no arquivo 'application.properties'. 

Feito isso basta acessar o endereço ``` http://localhost:8080 ``` para acessar a página inicial de cadastro. E para listar os eventos cadastrados acesse o endereço ``` http://localhost:8080/api/listar-todos ```.
