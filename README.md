# API CEP

# Tecnologias

## Backend
- Java (Versão 17)
- Maven
- Lombok
- Spring Boot
- JPA/Hibernate
- MockMvc e JUnit
- Bancos de dados H2
- Swagger (Documentação)
- Jacoco

## Sobre o projeto

Esta aplicação tem como objetivo buscar informações de endereço utilizando apenas o CEP, por meio da integração com uma API externa simulada (mockada).

![Busca de endereço](https://github.com/zGabrielZ/assets/blob/main/API%20CEP/chamada%20postman.png)


## Diagrama de fluxo 
![Diagrama de fluxo](https://github.com/zGabrielZ/assets/blob/main/API%20CEP/fluxo.png)

# Como executar o projeto

## Backend 

Pré requisito: Java 17 e Docker

```
# clonar o projeto cep service
git clone https://github.com/zGabrielZ/cep-service.git

# após baixar, entrar no diretório utilitarios/mockoon
cd .\utilitarios\mockoon\

# executar o script docker yaml ou se preferir importar o mock.json para usar no Mockoon Desktop
docker-compose up -d

# apos isso, entrar na pasta backend do projeto cep service
cd backend

# rodar a aplicação
./mvnw spring-boot:run
```

Após executar o projeto, entra no navegador e digite http://localhost:9090/api/swagger-ui/index.html#/. Vai ser mostrado a documentação da API.

![Documentação](https://github.com/zGabrielZ/assets/blob/main/API%20CEP/swagger.png)

Acesso ao banco de dados H2
![Banco H2](https://github.com/zGabrielZ/assets/blob/main/API%20CEP/banco%20h2%20login.png)
![Banco H2](https://github.com/zGabrielZ/assets/blob/main/API%20CEP/banco%20h2.png)

# Autor

Gabriel Ferreira

https://www.linkedin.com/in/gabriel-ferreira-4b817717b/
