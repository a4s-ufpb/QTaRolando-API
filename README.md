
# QTáRolando-API

Projeto para fornecer serviços para o gerenciamento de dados sobre eventos de diversas áreas na Paraíba.


## 🛠 Tecnologias utilizadas

**Back-end:** Java 8, Maven, Spring Boot

**Banco de dados:** PostgreSQL


## 💻 Rodando localmente

Clone o projeto

```bash
git clone https://github.com/a4s-ufpb/QTaRolando-API.git
```

Entre no diretório do projeto

```bash
cd QTaRolando-API
```

Instale as dependências

```bash
mvn dependency:resolve
```

Configure o acesso ao banco de dados configurando o arquivo `application-dev.properties` localizado em `./src/main/resources/application-dev.properties`

```yaml
# Configurando o caminho de acesso para o banco de dados PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/qtarolando-api

# Configurando o nome do usuário do banco para acessá-lo
spring.datasource.username=nome_usuario

# Configurando a senha do usuário
spring.datasource.password=senha
```

Inicie o projeto

```bash
spring-boot:run
```

## 📖 Documentação da API

### **AUTORIZAÇÃO**

#### **Cadastrar um usuário**

```
POST /api/auth/signup
```

Deve ser passodo um body como mostrado no exemplo abaixo:

```json
{
  "username": "Nome do usuário",
  "email": "email@usuario.com",
  "password": "S&nh@D#Usu@rI#"
}
```

#### **Realizar login**

```
POST /api/auth/login
```

Deve ser passodo um body como mostrado no exemplo abaixo:

```json
{
  "email": "email@usuario.com",
  "password": "S&nh@D#Usu@rI#"
}
```

#### **Encerrar seção**

```
POST /api/auth/signout
```


### **EVENTOS**

Os endpoints que não sejam do tipo `GET` só podem ser acessados por usuários logados e com as funções: **ROLE_ADMIN** ou **ROLE_USER**

#### **Retorna um evento**

```
GET /api/events/${id}
```

| Parâmetro   | Tipo       | Obrigatório | Descrição                                   |
| :---------- | :--------- | :---------- | :------------------------------------------ |
| `id`      | `int` | Sim | O ID do evento que se deseja obter |

#### **Filtro de eventos**

```
GET /api/events/filter
```

| Parâmetro   | Tipo       | Obrigatório  | Descrição                           |
| :---------- | :--------- | :----------- | :---------------------------------- |
| `title` | `string` | Opcional | Título completo ou incompleto de um evento |
| `categoryId` | `int` | Opcional | Id de uma categoria de eventos |
| `modality` | `string` | Opcional | Modalidade do evento: **PRESENCIAL** ou **ONLINE** |
| `dateType` | `string` | Opcional | Tipo da data para filtrar: **HOJE**, **AMANHA**, **ESTA_SEMANA**, **FIM_SEMANA**,<br> **PROX_SEMANA**, **ESTE_MES**, ou **ESCOLHER_INTERVALO** |
| `initialDate` | `string` | Opcional | Data para definir o intervalo inicial usado apenas caso `dateType` seja igual a **ESCOLHER_INTERVALO**. Formato para data é **YYYY-MM-DD**|
| `finalDate` | `string` | Opcional | Data para definir o intervalo final usado apenas caso `dateType` seja igual a **ESCOLHER_INTERVALO**. Formato para data é **YYYY-MM-DD**|
| `page` | `int` | Opcional | Número da página com eventos que será enviado. Por padrão envia sempre a primeira página = `0` |
| `pageSize` | `int` | Opcional | Quantidade máxima de eventos por página. Por padrão são `24` eventos por página|

#### **Cadastrar evento**

```
POST /api/events
```

Deve ser passodo um body como mostrado no exemplo abaixo:

```json
{
  "title": "Título do evento",
  "subtitle": "Subtítulo opcional",
  "categories": [
  	{
  		"id": 1,
  		"name": "Festas e Shows"
  	}
  ],
  "description": "Descrição do evento",
  "initialDate": "2021-11-26T19:00:00",
  "finalDate": "2021-11-30T13:00:00",
  "imagePath": "Imagem em base64",
  "modality": "Modalidade: PRESENCIAL ou ONLINE",
  "location": "Endereço do evento obrigatório caso seja PRESENCIAL",
  "site": "Site onde ocorrerá o evento, obrigatório caso seja ONLINE"
}
```

#### **Atualizar evento**

```
PUT /api/events/${id}
```

| Parâmetro   | Tipo       | Obrigatório | Descrição                                   |
| :---------- | :--------- | :---------- | :------------------------------------------ |
| `id`      | `int` | Sim | O ID do evento que se deseja atualizar |

Deve ser passodo um body semelhante ao de **cadastro de evento** com as informações que deseja atualizar.

#### **Remover evento**

```
DELETE /api/events/${id}
```

| Parâmetro   | Tipo       | Obrigatório | Descrição                                   |
| :---------- | :--------- | :---------- | :------------------------------------------ |
| `id`      | `int` | Sim | O ID do evento que se deseja remover |

## 🚀 Roadmap

- Melhorar a resposta em casos de erro

- Adicionar mais validações para evitar erros

