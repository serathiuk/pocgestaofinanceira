# Projeto Integrado - Software de Gestão – Módulo de Gestão Financeira 
Projeto desenvolvido como trabalho de conclusão do curso de Arquitetura de Software Distribuído, turma 2021 da PUC Minas.

## Organização desse projeto

A seguir a descrição de cada arquivo/diretório do projeto e sua finalidade:

* [apigateway](apigateway) - Um Api gateway simples escrito em node, apenas para simulações em ambiente de desenvolvimento.
* [core](core) - Subprojeto core - Classes utilitárias e compartilhadas entre os serviços
* [docker_startup](docker_startup) - Scripts de criação das instâncias docker de dev
* [documentos](documentos) - Documentos do projeto. Os documentos de cada etapa de entrega dessa POC.
* [erplegado](erplegado) - Projeto de simulação do ERP Legado. Nele existem algumas funcionalidades de dependência para o Financeiro, como a API de Filiais e Pessoas. Também existe um de simulação de operação de vendas, que serve de teste para integração entre ERP Legado e módulo Financeiro.
* [financeiro](financeiro) - Módulo financeiro. Esse subprojeto que a raiz dessa dock. Ele que é a prova de conceito de um módulo separado de um monolito. Nele existe as API's de Conta Bancária, Lançamento de Conta Bancária, Pagar e Receber e Baixa de Pagar e Receber. Também a comunicação com operações do ERP legado e com integrações bancárias, como pagamento e recebimento eletrônico.
nanceiro.
* [frontend](frontend) - Frontend desenvolvido e Vue 3 e Amplify.
* [integracaobancaria](integracaobancaria) - Simula um serviço externo de gateway bancária, de recebimentos e pagamentos eletrônicos. Possui funcionalidade de simulação de aprovação e reprovação de pagamentos e recebimentos.

## Como iniciar esse projeto em modo de desenvolvimento.

Para iniciar o projeto em modo de desenvolvimento, primeiramente podem ser iniciados o banco de dados e o localstack utilizando o seguinte comando na raiz do projeto, já com o docker e docker compose instalados no seu ambiente:

```bash
docker compose up -d
```

Esse comando irá levantar um container com o banco de dados PosgreSQL e a ferramenta Localstack, que simula o serviço AWS SQS localmente.

Foi criado um User Pool publico na AWS para testes do AWS Cognito. Ele será mantido até o final da avaliação desse projeto. Após, será removido da minha conta e deverá ser alterado caso queira testar na sua máquina.

## Como testar localmente o sistema

Para iniciar o projeto localmente.

Na raiz do projeto, rodar os seguintes comandos:
```bash
./mvnw clean package install
```
```bash
docker compose -f docker-compose.yaml -f docker-compose.dev.yaml up -d --remove-orphans --build
```

No diretório frontend:
```bash
npm run serve
```

As seguintes url's serão disponibilizadas:
* http://localhost:5000 - API Gateway local
* http://localhost:5001 - Módulo financeiro
* http://localhost:5002 - ERP Legado 
* http://localhost:5003 - Mock Bancário
* http://localhost:8080 - Frontend

As comunicações SQS poderão ser acompanhadas via Localstack utilizando o website https://app.localstack.cloud.
