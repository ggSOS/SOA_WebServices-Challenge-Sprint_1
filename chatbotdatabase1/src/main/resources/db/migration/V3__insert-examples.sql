Insert Into MENSAGEM
(CONTEUDO, ATIVO)
Values
("Tem algum hospital aqui perto que aceite o plano de vocês?", 1)
;
Insert Into RESPOSTA
(CONTEUDO, TEMA_PRINCIPAL, ID_MENSAGEM, ATIVO)
Values
("Aqui estão os resultados de Hospitais na sua região que aceitam nosso plano.",
"Checar Hospitais próximos",
1,
1
)
;


Insert Into MENSAGEM
(CONTEUDO, ATIVO)
Values
("Tive um imprevisto e preciso remarcar minha consulta, teria como?", 1)
;
Insert Into RESPOSTA
(CONTEUDO, TEMA_PRINCIPAL, SATISFATORIO, ID_MENSAGEM, ATIVO)
Values
("Claro! Aqui está estão as datas disponíveis para sua consulta.",
"Remarcar ou cancelar agendamento",
1,
2,
1
)
;

Insert Into MENSAGEM
(CONTEUDO, ATIVO)
Values
("Teria algum Dermatologista em algum hospital que aceite seu convênio?", 1)
;
Insert Into RESPOSTA
(CONTEUDO, TEMA_PRINCIPAL, SATISFATORIO, ID_MENSAGEM, ATIVO)
Values
("Temos Dermatologistas sim! Aqui está uma lista de especialidades disponíveis.",
"Consultar especialidades médicas disponíveis",
1,
3,
1
)
;


Insert Into MENSAGEM
(CONTEUDO, ATIVO)
Values
("Posso marcar um Otorrino em algum hospital aqui perto?", 1)
;
Insert Into RESPOSTA
(CONTEUDO, TEMA_PRINCIPAL, SATISFATORIO, ID_MENSAGEM, ATIVO)
Values
("Aqui estão os resultados de Hospitais na sua região que aceitam nosso plano.",
"Checar Hospitais próximos",
0,
4,
1
)
;