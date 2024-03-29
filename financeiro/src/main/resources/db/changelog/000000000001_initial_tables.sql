-- liquibase formatted sql
-- changeset serathiuk:000000000001
CREATE TABLE CONTAS_BANCARIAS (
	ID VARCHAR(100) NOT NULL,
	NOME VARCHAR(64) NOT NULL,
	TIPO_CONTA VARCHAR(32) NOT NULL
);
ALTER TABLE CONTAS_BANCARIAS ADD CONSTRAINT CONTAS_BANCARIAS_PK PRIMARY KEY(ID);
ALTER TABLE CONTAS_BANCARIAS ADD CONSTRAINT CONTAS_BANCARIAS_UN1 UNIQUE(NOME);

CREATE TABLE PLANO_CONTA_FLUXO_CAIXA (
    ID VARCHAR(100) NOT NULL,
    NOME VARCHAR(64) NOT NULL,
    ID_SUPERIOR VARCHAR(36) NULL
);
ALTER TABLE PLANO_CONTA_FLUXO_CAIXA ADD CONSTRAINT PLANO_CONTA_FLUXO_CAIXA_PK PRIMARY KEY(ID);
ALTER TABLE PLANO_CONTA_FLUXO_CAIXA ADD CONSTRAINT PLANO_CONTA_FLUXO_CAIXA_UN1 UNIQUE(ID_SUPERIOR, NOME);
ALTER TABLE PLANO_CONTA_FLUXO_CAIXA ADD CONSTRAINT PLANO_CONTA_FLUXO_CAIXA_FK1 FOREIGN KEY (ID_SUPERIOR) REFERENCES PLANO_CONTA_FLUXO_CAIXA(ID);

CREATE TABLE LANCAMENTOS_FLUXO_CAIXA (
    ID VARCHAR(100) NOT NULL,
    ID_FILIAL VARCHAR(100) NOT NULL,
    ID_CONTA_BANCARIA VARCHAR(100) NOT NULL,
    ID_CONTA_FLUXO_CAIXA VARCHAR(100) NULL,
    TIPO_LANCAMENTO VARCHAR(16) NOT NULL,
    DATA_HORA_OPERACAO TIMESTAMP NOT NULL,
    VALOR_OPERACAO DECIMAL(15,2) NOT NULL,
	ID_ORIGEM_OPERACAO VARCHAR(100) NULL
);
ALTER TABLE LANCAMENTOS_FLUXO_CAIXA ADD CONSTRAINT LANCAMENTOS_FLUXO_CAIXA_PK PRIMARY KEY(ID);
ALTER TABLE LANCAMENTOS_FLUXO_CAIXA ADD CONSTRAINT LANCAMENTOS_FLUXO_CAIXA_FK1 FOREIGN KEY (ID_CONTA_BANCARIA) REFERENCES CONTAS_BANCARIAS(ID);
ALTER TABLE LANCAMENTOS_FLUXO_CAIXA ADD CONSTRAINT LANCAMENTOS_FLUXO_CAIXA_FK2 FOREIGN KEY (ID_CONTA_FLUXO_CAIXA) REFERENCES PLANO_CONTA_FLUXO_CAIXA(ID);

CREATE TABLE PAGAR_RECEBER (
    ID VARCHAR(100) NOT NULL,
    ID_FILIAL_MOVIMENTO VARCHAR(100) NOT NULL,
    ID_FILIAL_COBRANCA VARCHAR(100) NOT NULL,
    ID_PESSOA VARCHAR(100) NOT NULL,
    DESCRICAO_LANCAMENTO VARCHAR(64) NULL,
    TIPO_OPERACAO VARCHAR(16) NOT NULL,
    DATA_EMISSAO DATE NOT NULL,
    DATA_VENCIMENTO DATE NOT NULL,
    VALOR_OPERACAO DECIMAL(15,2) NOT NULL,
    SITUACAO VARCHAR(24) NOT NULL,
    ID_CONTA_FLUXO_CAIXA  VARCHAR(100) NOT NULL
);
ALTER TABLE PAGAR_RECEBER ADD CONSTRAINT PAGAR_RECEBER_PK PRIMARY KEY(ID);
ALTER TABLE PAGAR_RECEBER ADD CONSTRAINT PAGAR_RECEBER_FK1 FOREIGN KEY (ID_CONTA_FLUXO_CAIXA) REFERENCES PLANO_CONTA_FLUXO_CAIXA(ID);


CREATE TABLE BAIXAS_PAGAR_RECEBER (
      ID VARCHAR(100) NOT NULL,
      ID_PAGAR_RECEBER VARCHAR(100) NOT NULL,
      DATA_HORA_BAIXA TIMESTAMP NOT NULL,
      VALOR_TOTAL_BAIXA DECIMAL(15,2) NOT NULL,
      VALOR_DESCONTO DECIMAL(15,2) NOT NULL,
      VALOR_JUROS DECIMAL(15,2) NOT NULL,
      SITUACAO_BAIXA VARCHAR(24) NOT NULL,
      ID_CONTA_BANCARIA VARCHAR(100) NOT NULL,
      ID_CONTA_FLUXO_CAIXA VARCHAR(100) NOT NULL
);
ALTER TABLE BAIXAS_PAGAR_RECEBER ADD CONSTRAINT BAIXAS_PAGAR_RECEBER_PK PRIMARY KEY(ID);
ALTER TABLE BAIXAS_PAGAR_RECEBER ADD CONSTRAINT BAIXAS_PAGAR_RECEBER_FK1 FOREIGN KEY (ID_PAGAR_RECEBER) REFERENCES PAGAR_RECEBER(ID);
ALTER TABLE BAIXAS_PAGAR_RECEBER ADD CONSTRAINT BAIXAS_PAGAR_RECEBER_FK2 FOREIGN KEY (ID_CONTA_BANCARIA) REFERENCES CONTAS_BANCARIAS(ID);
ALTER TABLE BAIXAS_PAGAR_RECEBER ADD CONSTRAINT BAIXAS_PAGAR_RECEBER_FK3 FOREIGN KEY (ID_CONTA_FLUXO_CAIXA) REFERENCES PLANO_CONTA_FLUXO_CAIXA(ID);

insert into contas_bancarias (id, nome, tipo_conta) values ('762fe7e8-3629-49e4-b92e-d99bfb1ac7a7', 'Caixa', 'CAIXA');
insert into contas_bancarias (id, nome, tipo_conta) values ('7b9f186c-7a96-4b82-b551-df3753024a64', 'Conta Corrente Banco 1', 'CONTA_CORRENTE');
insert into contas_bancarias (id, nome, tipo_conta) values ('e6a88f46-1ac4-4072-a154-7c02b5ff6664', 'Conta Corrente Banco 2', 'CONTA_CORRENTE');
insert into contas_bancarias (id, nome, tipo_conta) values ('0d2f0825-97ac-44df-8770-cb60332b7a55', 'Conta Poupança Banco 1', 'CONTA_POUPANCA');
insert into contas_bancarias (id, nome, tipo_conta) values ('869d3076-c00c-42b3-a37a-25b0fa9f4802', 'Conta Poupança Banco 2', 'CONTA_POUPANCA');

insert into plano_conta_fluxo_caixa (id, nome) values ('74c95eb5-a2a6-4473-85bb-afbae9465208', 'VENDAS');
insert into plano_conta_fluxo_caixa (id, nome) values ('906b158e-0690-41f1-8706-9734575d9d94', 'OUTRAS ENTRADAS');
insert into plano_conta_fluxo_caixa (id, nome) values ('757c2196-31d1-4315-8c5a-b607cb72bd45', 'PAGAMENTOS A FORNECEDORES');
insert into plano_conta_fluxo_caixa (id, nome) values ('676545c4-fbd9-4490-bd97-eea2b5b9d46d', 'OUTROS PAGAMENTOS');

