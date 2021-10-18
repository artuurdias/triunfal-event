insert into Usuario values('Fabriciao', 'Fabricio Onofre', '08-05-2004', 'cc20130@g.unicamp.com', 'fabricio123')
insert into Usuario values('Arturzao', 'Artur Dias', '29-03-2004', 'cc20124@g.unicamp.com', 'artur123')
insert into Usuario values('Fabiao', 'Fabio Alves', '15-07-2004', 'cc20129@g.unicamp.com', 'fabio123')
select * from Usuario
delete from Usuario where username <> ' ';
DBCC CHECKIDENT(Usuario, RESEED, 0)

/**********************************************************************************************************************/

insert into TipoEvento values('Evento social', 'Os eventos sociais têm como objetivo a 
comemoração de algum momento marcante. Por isso, eles não possuem caráter comercial nem buscam 
obter lucros: aqui, a intenção é reunir família, amigos, colegas de trabalho e pessoas importantes
na sua vida para festejar!', 'Coquetel; Festas em geral; Chás; Noivado; Open House; Casamentos; Aniversários; Happy hours; Churrascos;')

insert into TipoEvento values('Evento corporativo', 'Os eventos corporativos são realizados por uma
empresa ou instituição. Podendo ser realizados nos mais diferentes formatos, esse tipo de evento é
muito importante para criar uma relação próxima com clientes, funcionários, parceiros e possíveis novos
usuários do serviço prestado pela empresa.', 'Feira; Conferência; Reunião; Encontros de Networking; Treinamento; Desfiles; Leilões; Visitas institucionais;')

insert into TipoEvento values('Evento religioso', 'Geralmente organizados por instituições religiosos ou
grupos de pessoas voltadas à espiritualidade, os eventos religiosos possuem cuidado especial com o 
desenvolvimento pessoal dos participantes através de experiências únicas. Muitas vezes, os eventos 
religiosos também contam com a arrecadação de recursos para alguma causa ou instituição.', 
'Retiro; Encontro espiritual; Culto; Bar e bat-mitzva; Batizados; Brit-miláh; Casamentos; Conclaves; Primeira comunhão;')

insert into TipoEvento values('Evento acadêmico', 'Nos eventos acadêmicos e educacionais, o 
grande objetivo é proporcionar aprendizado aos participantes. Eles podem ser voltados a estudantes, profissionais
ou pessoas leigas interessadas em obter capacitação em algum assunto ou área', 
'Simpósio; Palestra; Seminário; Curso; Workshop; Congresso; Webinário; Conferência; Fórum; Painel; Reunião; Convenção; Feira de ciências')


insert into TipoEvento values('Evento esportivo', 'Evento esportivo é tudo aquilo que pode ser organizado por um clube ou até mesmo escolas e associações,
com o objetivo de promover uma competição esportiva.', 
'Campeonato; Olimpíada; Copa; Gincana; Desafio; Esporte; Webinário; Ginástica;')

select * from TipoEvento
DBCC CHECKIDENT(TipoEvento, RESEED, 0)
delete from TipoEvento where id >= 0

/**********************************************************************************************************************/

insert into Evento values('JDN764', 'Churrascão do Fabio', 'Evento social', '23/09/2021', 'Churrascaria O Matuto', 'Fabiao')
insert into Evento values('298GCV', 'PD Party 2022', 'Evento social', '12/04/2021', 'Chácara Belo Horizonte', 'Fabriciao')
insert into Evento values('DCGFR4', 'Gincana COTUCA', 'Evento esportivo', '18/12/2021', 'Colégio Técnico de Campinas', 'Arturzao')
select * from Evento
delete from Evento where id  <> ' ';

/**********************************************************************************************************************/

insert into Convidado values('JDN764', 'Arturzao')
insert into Convidado values('298GCV', 'Arturzao')

select * from Convidado
delete from Convidado where idConvidado >= 0
DBCC CHECKIDENT(Convidado, RESEED, 0)

/**********************************************************************************************************************/

insert into Lembrete values('Dentista', '16/07/2021', 'Campinas', 'Manutenção do aparelho', 'Fabriciao')

select * from Lembrete
delete from Lembrete where id >= 0
DBCC CHECKIDENT(Lembrete, RESEED, 0)

/**********************************************************************************************************************/

insert into Feriado values('Confraternização Universal', '01/01')
insert into Feriado values('Carnaval', '16/02')
insert into Feriado values('Paixão de Cristo', '02/04')
insert into Feriado values('Tiradentes', '21/04')
insert into Feriado values('Dia do Trabalho', '01/05')
insert into Feriado values('Corpus Christi', '03/06')
insert into Feriado values('Independência do Brasil', '07/09')
insert into Feriado values('Nossa Senhora Aparecida', '12/10')
insert into Feriado values('Dia das Crianças', '02/11')
insert into Feriado values('Proclamação da República', '15/11')
insert into Feriado values('Natal', '25/12')

.
select * from Feriado
delete from Feriado where id >= 0
DBCC CHECKIDENT(Feriado, RESEED, 0)

/**********************************************************************************************************************/

insert into Dica values('Evento social',		'Dica 1')
insert into Dica values('Evento corporativo',	'Dica 2')
insert into Dica values('Evento religioso',		'Dica 3')
insert into Dica values('Evento acadêmico',		'Dica 4')
insert into Dica values('Evento esportivo',		'Dica 5')


select * from Dica
delete from Dica where id >= 0
DBCC CHECKIDENT(Dica, RESEED, 0)

/**********************************************************************************************************************/

insert into Convite values('02/04', 'Você está convidado!',	'JDN764', 'Fabriciao');
insert into Convite values('02/04', 'Você está convidado!',	'JDN764', 'Arturzao');


select * from Convite
delete from Convite where id >= 0
DBCC CHECKIDENT(Convite, RESEED, 0)
