-- liquibase formatted sql
-- changeset serathiuk:000000000002
insert into filiais (id, nome) values('bfc59d81-b713-4fee-a959-0f230d395f4c', 'Matriz');
insert into filiais (id, nome) values('98381919-1ad0-4df0-9702-f12e29707d49', 'Filial 1');
insert into filiais (id, nome) values('74552b3f-3049-4a91-8cb4-88f07f84edb0', 'Filial 2');
insert into filiais (id, nome) values('be2c374a-0d01-443f-bb9f-c34d5eaa1186', 'Filial 3');

insert into pessoas (id, nome, tipo_pessoa, documento) values ('70a1187d-f327-46e3-a021-796f8d50e7d7', 'Joao da Silva', 'PESSOA_FISICA', '18147621086');
insert into pessoas (id, nome, tipo_pessoa, documento) values ('d48ff645-e723-425c-932c-eaaa42302011', 'Maria da Silva', 'PESSOA_FISICA', '66451526045');
insert into pessoas (id, nome, tipo_pessoa, documento) values ('436409d7-7cd2-4296-ac4d-4c9dcc2d1b6a', 'Pedro Souza', 'PESSOA_FISICA', '69772262002');
insert into pessoas (id, nome, tipo_pessoa, documento) values ('c28a9b2b-ec2a-4d36-8337-5d934f0dc9f5', 'Empresa Qualqier Ltda', 'PESSOA_JURIDICA', '79287548000114');
insert into pessoas (id, nome, tipo_pessoa, documento) values ('d5479bc1-766e-47fa-af58-dff4976de512', 'Grande empresa SA', 'PESSOA_JURIDICA', '53987366000162');
insert into pessoas (id, nome, tipo_pessoa, documento) values ('eb981acf-4e56-4913-8b65-774d9d17e1f0', 'Empresa Individual EIRELI', 'PESSOA_JURIDICA', '49436115000112');