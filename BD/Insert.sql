insert into Usuario values('Fabriciao', 'Fabricio Onofre', '08-05-2004', 'cc20130@g.unicamp.com', 'fabricio123')
insert into Usuario values('Arturzao', 'Artur Dias', '29-03-2004', 'cc20124@g.unicamp.com', 'artur123')
insert into Usuario values('Fabiao', 'Fabio Alves', '15-07-2004', 'cc20129@g.unicamp.com', 'fabio123')
select * from Usuario
delete from Usuario where username <> ' ';
DBCC CHECKIDENT(Usuario, RESEED, 0)

/**********************************************************************************************************************/

insert into TipoEvento values('Evento social', 'Os eventos sociais t�m como objetivo a 
comemora��o de algum momento marcante. Por isso, eles n�o possuem car�ter comercial nem buscam 
obter lucros: aqui, a inten��o � reunir fam�lia, amigos, colegas de trabalho e pessoas importantes
na sua vida para festejar!', 'Coquetel; Festas em geral; Ch�s; Noivado; Open House; Casamentos; Anivers�rios; Happy hours; Churrascos;')

insert into TipoEvento values('Evento corporativo', 'Os eventos corporativos s�o realizados por uma
empresa ou institui��o. Podendo ser realizados nos mais diferentes formatos, esse tipo de evento �
muito importante para criar uma rela��o pr�xima com clientes, funcion�rios, parceiros e poss�veis novos
usu�rios do servi�o prestado pela empresa.', 'Feira; Confer�ncia; Reuni�o; Encontros de Networking; Treinamento; Desfiles; Leil�es; Visitas institucionais;')

insert into TipoEvento values('Evento religioso', 'Geralmente organizados por institui��es religiosos ou
grupos de pessoas voltadas � espiritualidade, os eventos religiosos possuem cuidado especial com o 
desenvolvimento pessoal dos participantes atrav�s de experi�ncias �nicas. Muitas vezes, os eventos 
religiosos tamb�m contam com a arrecada��o de recursos para alguma causa ou institui��o.', 
'Retiro; Encontro espiritual; Culto; Bar e bat-mitzva; Batizados; Brit-mil�h; Casamentos; Conclaves; Primeira comunh�o;')

insert into TipoEvento values('Evento acad�mico', 'Nos eventos acad�micos e educacionais, o 
grande objetivo � proporcionar aprendizado aos participantes. Eles podem ser voltados a estudantes, profissionais
ou pessoas leigas interessadas em obter capacita��o em algum assunto ou �rea', 
'Simp�sio; Palestra; Semin�rio; Curso; Workshop; Congresso; Webin�rio; Confer�ncia; F�rum; Painel; Reuni�o; Conven��o; Feira de ci�ncias')


insert into TipoEvento values('Evento esportivo', 'Evento esportivo � tudo aquilo que pode ser organizado por um clube ou at� mesmo escolas e associa��es,
com o objetivo de promover uma competi��o esportiva.', 
'Campeonato; Olimp�ada; Copa; Gincana; Desafio; Esporte; Webin�rio; Gin�stica;')

select * from TipoEvento
DBCC CHECKIDENT(TipoEvento, RESEED, 0)
delete from TipoEvento where id >= 0


/************************************************************************************************************************/


insert into Evento values('JDN764', 'Churrasc�o do Fabio', 'Evento social', '23/09/2021', 'Festa de comemora��o de 17 anos', 'Rua Amadeu Gardini 249', 'Fabiao')
insert into Evento values('298GCV', 'PD Party 2022', 'Evento social', '12/04/2021', 'Ch�cara Belo Horizonte', 'Fabriciao')
insert into Evento values('DCGFR4', 'Gincana COTUCA', 'Evento esportivo', '18/12/2021', 'Festa de comemora��o do fim da pandemia', 'Col�gio T�cnico de Campinas', 'Arturzao')
insert into Evento values('HDBNWU', 'Formatura 2022', 'Evento social', '15/12/2022', 'Festa de formatura de RA20', 'COTUCA', 'Arturzao')

select * from Evento

delete from Evento where id  <> ' ';

/**********************************************************************************************************************/

insert into Convidado values('JDN764', 'Fabriciao')
insert into Convidado values('298GCV', 'Fabiao')
insert into Convidado values('VT18HV', 'Fabiao')
insert into Convidado values('HDBNWU', 'Fabriciao')

select * from Convidado
delete from Convidado where idEvento = 'DCGFR4'
delete from Convidado where 1 >= 0

/**********************************************************************************************************************/

/*insert into Lembrete values('Dentista', '16/07/2021', 'Campinas', 'Manuten��o do aparelho', 'Fabriciao')*/
insert into Lembrete values('Dentista', '16/07/2021', 'Rua Amadeu Gardini 249',  'Fabriciao', 'Manuten��o do aparelho')
insert into Lembrete values('Dentist', '16/07/2021', 'Campinas', 'Fabriciao', 'Manuten��o do aparelho')

select * from Lembrete


/**********************************************************************************************************************/

insert into Feriado values('Confraterniza��o Universal', '01/01')
insert into Feriado values('Carnaval', '16/02')
insert into Feriado values('Paix�o de Cristo', '02/04')
insert into Feriado values('Tiradentes', '21/04')
insert into Feriado values('Dia do Trabalho', '01/05')
insert into Feriado values('Corpus Christi', '03/06')
insert into Feriado values('Independ�ncia do Brasil', '07/09')
insert into Feriado values('Nossa Senhora Aparecida', '12/10')
insert into Feriado values('Dia das Crian�as', '02/11')
insert into Feriado values('Proclama��o da Rep�blica', '15/11')
insert into Feriado values('Natal', '25/12')

select * from Feriado
delete from Feriado where id >= 0
DBCC CHECKIDENT(Feriado, RESEED, 0)

/**********************************************************************************************************************/

insert into Dica values('Evento social',		'Dica 1')
insert into Dica values('Evento corporativo',	'Dica 2')
insert into Dica values('Evento religioso',		'Dica 3')
insert into Dica values('Evento acad�mico',		'Dica 4')
insert into Dica values('Evento esportivo',		'Dica 5')
insert into Dica values('Evento social',		'Dica 2')


select * from Dica
delete from Dica where id >= 0
DBCC CHECKIDENT(Dica, RESEED, 0)

/**********************************************************************************************************************/

insert into Convite values('Fabriciao', 'JDN764', 'Voc� est� convidado!', '02/04/2022' );
insert into Convite values('Arturzao', 'JDN764', 'Voc� est� convidado!', '02/04/2022' );
insert into Convite values('Arturzao', '298GCV', 'Voc� est� convidado!', '02/04' );
insert into Convite values('Fabiao', 'JDN764', 'Voc� est� convidado!', '02/04' );
insert into Convite values('Fabriciao', 'DCGFR4', 'Voc� est� convidado!', '02/04' );

select * from Convite
delete from Convite where id >= 0
DBCC CHECKIDENT(Convite, RESEED, 0)
