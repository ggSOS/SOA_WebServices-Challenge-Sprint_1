/*MySQL*/
Drop Table If Exists MENSAGEM;
Drop Table If Exists RESPOSTA;


Create Table MENSAGEM
(
    ID_MENSAGEM     BigInt          Not Null    Auto_increment,
    CONTEUDO        VarChar(500)    Not Null,
    ATIVO           tinyint         Not Null,
    Constraint	MENSAGEM_PK		Primary Key(ID_MENSAGEM)
);

Create Table RESPOSTA
(
    ID_RESPOSTA     BigInt          Not Null    Auto_increment,
    CONTEUDO        VarChar(500)    Not Null,
    TEMA_PRINCIPAL  VarChar(50)     Not Null,
    ATIVO           tinyint         Not Null,
    SATISFATORIO    TinyInt,
    ID_MENSAGEM     BigInt			Not Null,
    Constraint	RESPOSTA_PK				Primary Key(ID_RESPOSTA),
    Constraint	RESPOSTA_MENSAGEM_PK	Foreign Key(ID_MENSAGEM)	References MENSAGEM(ID_MENSAGEM)
);