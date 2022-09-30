# Tecnologias utilizadas

- **[Spring Boot](https://spring.io/projects/spring-boot)**
- **[Spring Data JPA](https://spring.io/projects/spring-data-jpa#overview)** 
- **[Hibernate](https://hibernate.org/orm/)**
- **[Lombok](https://projectlombok.org/)**
- **[Docker](https://www.docker.com/)**
- **[PostgreSQL](https://www.postgresql.org/)**
- **[Swagger](https://swagger.io/)**

#Arquitetura
##Banco de dados
<img src="database_model.png" width="500" height="500">

##RabbitMQ
<img src="RabbitMQ.png" width="500" height="500">
- fila subscription-purchased: recebe mensagens de assinaturas realizadas.
- fila subscription-canceled: recebe mensagens de assinaturas canceladas.
- fila subscription-restarted: recebe mensagens de assinaturas reiniciadas.
- fila x-dead-letter-queue: recebe mensagens que não foram reconhecidas pelas filas especificadas de assinaturas, seja por erro ou qualquer outro fator. DLQ implementado.

##Como executar

###Futuras features
- Testes unitários/funcionais.
- Spring Secutiry para adicionar autenticação na rota.
- Database migration com Flyway.
- Pré-handle no controller para retornar uma mensagem de erro amigável para o usuário se o campo "notification_type" não for preenchido com possíveis opções do Enum.
- Verificação no producer para não enviar para a fila se a subscription já existir naquele estado.
- Automatizar o erro proposital no consumer lançando um Throw condicional através de uma variável de ambiente para exibir as mensagens não reconhecidas caindo na fila DLQ do RabbitMQ.