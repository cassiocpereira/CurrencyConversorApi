# Currency Conversor Api

   * [Sobre](#Sobre)
   * [Propósito](#proposito)
   * [Features](#features)
   * [Tecnologias e Ferramentas](#tecnologias)
   * [Arquitetuta ](#arquitetura)
   * [Instalação](#instalacao)  


## Sobre

API Rest que realiza a conversão entre duas moedas, utilizando taxas de conversões atualizadas, obtidas do serviço externo [Exchange Rates](http://api.exchangeratesapi.io/latest?base=EUR)

O serviço encontra-se em execução no [Heroku](https://currencyconversorapi.herokuapp.com/v1/init)


## Features

* Realizar conversão valores entre as moedas USD, BRL, EUR e JPY
* Consultar as trnsações de conversão realizadas por um usuário
* Cadastrar um usuário


## Tecnologiase Ferramentas

As tecnologias e ferramentas utilizadas foram de acordo com a expertise do desenvolvedor

* Java 11
* Spring Framework 2.5.6
* Maven 4.0
* Rest
* JPA | Hibernate
* Junit 4.0
* Swagger 2.0
* Git 2.33.0
* Spring Tool Suite 4
* H2 Database
* Apache Tomcat/9.0.54
* Postman 9.1.5
 

## Arquitetura

* Camada de Recursos/Controladores
* Camada de Serviços/Negócios
* Camada de Persistência


## Instalação

1. Após clonar o repositório localmente, entre no diretório do projeto e mude para a branch master (se já não estiver nela):

`   $ git clone {url-do-repositorio}`

`   $ cd {seu-workspace}/CurrencyConversorApi`

2. Baixe as dependências do projeto através do Maven

`   $ mvn install`

3. Importe o projeto para sua IDE como **Maven Project**

4. Execute o projeto via IDE

5. Acesse no navegador ou via postman o endereço http://localhost:8080/v1/init para confirmar que o serviço está rodando

6. Acesse o endereço http://localhost:8080/swagger-ui.html para verificar a documentação dos endpoints disponíveis no serviço
