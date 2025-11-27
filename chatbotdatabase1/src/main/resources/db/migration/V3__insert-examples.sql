Insert Into MENSAGEM
(CONTEUDO)
Values
("Tem algum hospital aqui perto que aceite o plano de vocês?")
;
Insert Into RESPOSTA
(CONTEUDO, TEMA_PRINCIPAL, ID_MENSAGEM)
Values
("Aqui estão os resultados de Hospitais na sua região que aceitam nosso plano.",
"CHECAR_HOSPITAIS_PROXIMOS",
1
)
;


Insert Into MENSAGEM
(CONTEUDO)
Values
("Tive um imprevisto e preciso remarcar minha consulta, teria como?")
;
Insert Into RESPOSTA
(CONTEUDO, TEMA_PRINCIPAL, SATISFATORIO, ID_MENSAGEM)
Values
("Claro! Aqui está estão as datas disponíveis para sua consulta.",
"REMARCAR_OU_CANCELAR_AGENDAMENTO",
1,
2
)
;

Insert Into MENSAGEM
(CONTEUDO)
Values
("Teria algum Dermatologista em algum hospital que aceite seu convênio?")
;
Insert Into RESPOSTA
(CONTEUDO, TEMA_PRINCIPAL, SATISFATORIO, ID_MENSAGEM)
Values
("Temos Dermatologistas sim! Aqui está uma lista de especialidades disponíveis.",
"CONSULTAR_ESPECIALIDADES_MEDICAS_DISPONIVEIS",
1,
3
)
;


Insert Into MENSAGEM
(CONTEUDO)
Values
("Posso marcar um Otorrino em algum hospital aqui perto?")
;
Insert Into RESPOSTA
(CONTEUDO, TEMA_PRINCIPAL, SATISFATORIO, ID_MENSAGEM)
Values
("Aqui estão os resultados de Hospitais na sua região que aceitam nosso plano.",
"CHECAR_HOSPITAIS_PROXIMOS",
0,
4
)
;