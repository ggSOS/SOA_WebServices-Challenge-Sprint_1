Drop Table If Exists MENSAGEM;
Create Table MENSAGEM
(
    ID_MENSAGEM     BigInt          Not Null    Auto_increment,
    CONTEUDO        VarChar(500)    Not Null,
    Constraint	MENSAGEM_PK		Primary Key(ID_MENSAGEM)
);