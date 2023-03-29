create database BigBanco; 

 use bigbanco;
 
 
 create table ContaUsuario(CPF int primary key not null,nome varchar(50),Endereco varchar(100),Renda float ,Profissao  varchar(100),DepositaValor float);
select * from contausuario;
desc contausuario;
drop table contausuario;

