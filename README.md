# BeerTech - Banco Beer

##### Pending

- Implementar Eureka Naming Server / Spring Cloud Discovery
- Gateway de requisiçoes zuul 

LEGACY POC - Controle de operações bancárias

- Desenvolvimento de API com integração com banco de dados relacional MYSQL.
- SpringBoot
- MYSQL
- JPA
- RabbitMQ
- JWT


##### Instalação do Ambiente
############################################

## Utilize as seguintes ferramentas:  

* IDE IntelliJ Community ou Ultimate
* Eclipse
* Docker
* MYSQL
* Postman

## IDE  
Instale o IntelliJ Community: https://www.jetbrains.com/pt-br/idea/download/#section=windows  
* Faça o import do projeto e espere resolver as dependencias do pom.xml no carregamento do projeto.

Eclipse (https://www.eclipse.org/downloads/packages/release/2020-03)
* Abra o eclipse e faça o import do projeto e espere resolver as dependencias do pom.xml no carregamento do projeto.

-------------------------------------------------------------------------------------------  

## Docker / RabbitMQ
Instale o docker do seguinte endereço (https://hub.docker.com/editions/community/docker-ce-desktop-windows/)  

* Se por ventura o docker não funcionar, verifique se o WSL2 está instalado: https://docs.docker.com/docker-for-windows/wsl/
* Após a instalação do docker verifique no powershell a versão: docker --version  
* Instale a imagem do RABBITMQ MANAGEMENT: rabbitmq:3-management
* Após a instalação da imagem do rabbitmq no docker verifique se o container foi instalado: docker ps -a.  Se retornar o CONTAINER_ID tudo certo! 
* Dê um start no container gerado acima do rabbitmq: docker run -d -p 15672:15672 -p 5672:5672 --name rabbitmqfusion rabbitmq:3-management e pronto!  
-------------------------------------------------------------------------------------------

## MYSQL / Windows
Instale o mysql do seguinte endereço na sua máquina local. (https://dev.mysql.com/downloads/mysql/)  
Instale o mysql WorkBench para gerenciamento do banco ( https://dev.mysql.com/downloads/workbench/ )

* Após a instalação verifique se o serviço do mysql está rodando no windows rodando no exec:  services.msc
* Se preferir linha de comando rode o comando no cmd: mysql.exe –uroot –p  ou abra o workbench e inicie uma nova conexão no localhost:3306  com usuário root


## MYSQL / Docker
Após a instalação do docker finalizada.

* Instale a imagem da ultima versão do mysql via cmd: docker pull mysql/mysql-server:latest
* Verifique a instalação com o docker images
* Crie o container de acordo com a imagem acima:  docker run --name=[nome_qualquer_do_container] -d mysql/mysql-server:latest   " (-d sinaliza a execução em background)"
* Rode o comando docker ps -a e verifique os containers instalados.
* Instale o mysql client:  apt-get install mysql-client
* Inicie o client:  docker exec -it [nome_qualquer_do_container] mysql -uroot -p

## Postman
* Após a instalação de todos os passos acima, instale o postman para testar as chamadas da api do projeto: https://www.postman.com/downloads/


Author: Time Fusion
Patricia Crevelário Lisboa  
Lucas Bergamo  
Maria Luísa Souza Matos  
Sabrina Jodely Rufo  


