# wallet

## Sobre o Projeto
Conforme o pedido no desafio criei uma aplicação de carteira onde possibilito a fazer 4 operações, sendo elas: deposito, transferencia entre contas, saque e pagamento de contas. 
Pensando nisso criei 4 controllers onde dividi da seguinte maneira: 
- Operações: Onde fica as operações financeiras
- Account: Onde fica criação de contas e consulta de contas: ja que uma conta é pré requisito para uma operação financeira, sendo que uma conta usa como chaves CPF do usuario e numero de conta um usuario pode ter mais de uma conta desde que o numero da conta seja diferente
- User: Onde fica a criação de usuario já que é pré requisito para criação de uma conta utilizando como chave o CPF já que é um valor unico por usuario
- Historic: Todas as operações financeiras são enviadas para uma fila de historico onde é consumida e salvo no mongoDB

## O que foi utilizado
Utilizei as seguintes tecnologias:
- Java 15
- JUnit
- ContainerTest: Para testes de integração
- Kafka
- MongoDB

## Como executar
Subir as seguintes imagens antes de executar o projeto após isso executar o WalletApplication.java
#### Mongo
docker run -d -p 27017:27017 -p 28017:28017 -e AUTH=no tutum/mongodb
#### Kafka
