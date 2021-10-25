create table Usuario (
	username varchar(25) primary key,
	nome varchar(100) not null,
	nascimento varchar(10) not null,
	email varchar(100) unique not null,
	senha varchar(20) not null
)

create table TipoEvento (
	id int identity(1,1) primary key,
	nome varchar(50) unique not null,
	definicao varchar(500) not null,
	exemplos  varchar(200) not null
)

create table Evento (
	id char(6) primary key,
	nome varchar(50) not null,
	tipo varchar(50) not null,
	data varchar(10) not null,
	local varchar(50) not null,
	organizador varchar(25) not null,
	foreign key (organizador) references Usuario(username),
	foreign key (tipo)	      references TipoEvento(nome)
)


/*create table Convidado (
	idConvidado int identity(1,1) primary key,
	idEvento char(6) not null,
	nomeUsuario varchar(25) not null,
	foreign key (idEvento)    references Evento(id),
	foreign key (nomeUsuario) references Usuario(username)
)*/

create table Convidado (
	idEvento char(6) not null,
	nomeUsuario varchar(25) not null,
	foreign key (idEvento)    references Evento(id),
	foreign key (nomeUsuario) references Usuario(username),
	primary key (idEvento, nomeUsuario)
)


create table Lembrete (
	id int identity(1,1) primary key,
	titulo varchar(50) unique not null,
	data varchar(10) not null,
	local varchar(50) not null,
	descricao varchar(100) null,
	usuario varchar(25) not null,
	foreign key (usuario)    references Usuario(username),
)


create table Feriado (
	id int identity(1,1) primary key,
	nome varchar(50) unique not null,
	data varchar(10) not null
)


create table Dica (
	id int identity(1,1) primary key,
	tipoEvento varchar(50) not null,
	conteudo varchar(500) not null,
	foreign key (tipoEvento)    references TipoEvento(nome),
)

create table Convite (
	id int identity(1,1) primary key,
	data varchar(10) not null,
	mensagem varchar(100) null, 
	idEvento char(6) not null,
	nomeUsuario varchar(25) not null,
	foreign key (idEvento)    references Evento(id),
	foreign key (nomeUsuario) references Usuario(username)
)

drop table Usuario
drop table TipoEvento
drop table Evento
drop table Convidado
drop table Lembrete
drop table Feriado
drop table Dica
drop table Convite


/*
	Adicionei usuario no lembrete
	Alterei campo nome para titulo
	Adicionei unique no email usuario
	Adicionei unique no nome do TipoEVento
	Adicionei unique no nome do Feriado
	Defini a Data pra varchar de 10 em todas tabelas
	Criei tabela TipoEvento 
	Alterei a tabela EVento para q recebesse a foreing key TipoEvento
	coloquei um campo descrição para a tabela lembrete
	Adicionei organizador na tabela evento como fk
*/

