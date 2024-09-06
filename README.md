# Teste Técnico para a vaga Engenheiro de Software Fullstack da empresa Phoebus

Este repositório contém uma aplicação desenvolvida com Java e Spring Boot, com MongoDB como banco de dados, para gerenciar centros comunitários e recursos associados. O projeto inclui funcionalidades para adicionar centros comunitários, trocar recursos entre centros e gerar relatórios de negociações.

## Funcionalidades

- **Adicionar Centro Comunitário**: Adiciona um novo centro comunitário com endereço e recursos.
- **Trocar Recursos**: Permite a troca de recursos entre centros comunitários, garantindo que as trocas sejam válidas com base nos pontos dos recursos e a ocupação dos centros.
- **Gerar Relatórios de Negociações**: Gera relatórios de negociações quando os recursos são trocados entre centros comunitários.
- **Outros Endpoints**: Inclui funcionalidades adicionais como atualização de ocupação, obtenção de média de recursos por centro comunitário, e listagem de centros comunitários com alta ocupação.

## Tecnologias Utilizadas

- **Java**: Linguagem de programação principal.
- **Spring Boot**: Framework para construção de aplicações Java baseadas em Spring.
- **MongoDB**: Banco de dados NoSQL utilizado para armazenar dados.
- **JUnit e Mockito**: Frameworks para testes unitários e mock.
- **Maven**: Gerenciador de dependências e build tool.

## Pré-requisitos

- Java 17 ou superior
- MongoDB
- Maven

## Configuração do Projeto

### 1. Clonar o Repositório

```bash
git clone git@github.com:valdirsantos714/teste-tecnico-phebus.git
cd teste-tecnico-phebus
```

### 2. Configurar o Banco de Dados

Certifique-se de que o MongoDB está em execução e configure a URL de conexão no arquivo `application.properties`.

Exemplo de configuração em `src/main/resources/application.properties`:

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/centro-comunitario
```

### 3. Construir e Executar o Projeto

Use o Maven para construir e executar o projeto:

```bash
mvn clean install
mvn spring-boot:run
```

### 4. Testes

Os testes unitários são escritos com JUnit e Mockito. Para executá-los, use:

```bash
mvn test
```

## Estrutura do Projeto

- `src/main/java/com/valdirsantos714/communitycenter`: Código-fonte da aplicação.
    - `controller`: Controladores REST para gerenciamento de recursos e centros comunitários.
    - `service`: Lógica de negócio.
    - `infra`: Documentação e tratador de erros HTTP.
    - `repository`: Interfaces de acesso a dados.
    - `payload`: Objetos de trocas de dados (DTOs).
    - `model`: Modelos de dados.
- `src/test/java/com/valdirsantos714/centro-comunitario`: Testes unitários e integração.

## Exemplos de Uso

### Adicionar Centro Comunitário

POST `/api/community-centers`

**Request Body:**

```json
{
  "name": "Centro Comunitário A",
  "address": {
    "cep": "12345",
    "street": "Rua Exemplo",
    "city": "Cidade Exemplo",
    "number": 100,
    "complement": "Complemento",
    "reference": "Referência"
  },
  "location": "Local Exemplo",
  "maxCapacity": 100,
  "currentOccupancy": 90,
  "resources": []
}
```

### Trocar Recursos

POST `/api/community-centers/exchangeResources?fromCenterId={idCommunityCenter}&toCenterId={idDaOutraCommunityCenter}`

**Request Body:**

```json
{
  "fromResources": [
    {"id": "1", "name": "Recurso A", "totalPoints": 10}
  ],
  "toResources": [
    {"id": "2", "name": "Recurso B", "totalPoints": 15}
  ]
}
```


