# Elimina la base anterior en caso de existir
drop database SistemaTec;

# Creacion y uso la base de datos
create database SistemaTec;
use SistemaTec;

# Creacion de las tablas Independientes
create table Usuario(cveUsuario varchar(5) not null,
					usuario varchar(50) not null,
					contraseña varchar(50) not null,
					nombres varchar(50),
					apellidos varchar(50),
					genero varchar(1),
					correo varchar(50),
					fechaNac date,
					fotoPerfil longblob,
					constraint UsuarioPK primary key (cveUsuario),
					constraint UsuarioCK1 check (genero = 'F' or genero = 'M')
					);

create table Carrera(noCarrera varchar(5) not null,
					nombre varchar(50),
					constraint CarreraPK primary key (noCarrera)
					);

create table Departamento(noDepartamento varchar(5) not null,
					nombre varchar(50),
					constraint DepartamentoPK primary key ()
					);


# Creacion de tablas dependientes
create table Encuesta(noEncuesta int not null auto_increment,
					respuesta1 int(1),
					respuesta2 int(1),
					respuesta3 int(1),
					respuesta4 int(1),
					respuesta5 int(1),
					respuesta6 int(1),
					respuesta7 int(1),
					respuesta8 int(1),
					respuesta9 int(1),
					respuesta10 int(1),
					respuesta11 int(1),
					respuesta12 int(1),
					respuesta13 int(1),
					otrosSintomas varchar(100),
					cveUsuario varchar(5) not null,
					constraint EncuestaPK primary key (noEncuesta),
					constraint EncuestaFK foreign key (cveUsuario) references Usuario (cveUsuario);
					);