# Elimina la base anterior en caso de existir

drop database SistemaTec;

# Creacion y uso la base de datos

create database SistemaTec;
use SistemaTec;

# Creacion de las tablas Independientes

create table Usuario(cveUsuario varchar(5) not null,
			usuario varchar(30) not null,
			contraseña varchar(30) not null,
			nombres varchar(50),
			apellidos varchar(50),
			genero varchar(1),
			correo varchar(50),
			fechaNac date,
			fotoPerfil longblob,
			constraint UsuarioPK primary key (cveUsuario),
			constraint UsuarioCK check (genero = 'F' or genero = 'M')
			);

create table Carrera(cveCarrera varchar(5) not null,
			nombre varchar(50),
			constraint CarreraPK primary key (cveCarrera)
			);

create table Departamento(cveDepartamento varchar(5) not null,
			nombre varchar(100),
			constraint DepartamentoPK primary key (cveDepartamento)
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
			constraint EncuestaFK foreign key (cveUsuario) references Usuario (cveUsuario)
			);

# Las 'cveAsignacion' tendran el formato (In###) {ej: ES001, PE001, ME001, AD001, MO001, DI001}

create table Asignacion(cveAsignacion varchar(5) not null,
			cveUsuario varchar(5) not null,
			nombre varchar(50),
			constraint AsignacionPK primary key (cveAsignacion, cveUsuario),
			constraint AsignacionFK (cveUsuario) references Usuario (cveUsuario)
			);

create table Estudiante(noCont varchar(5) not null,
			cveAsignacion varchar(5) not null,
			cveUsuario  varchar(5) not null,
			cveCarrera varchar(5) not null,
			constraint EstudiantePK primary key (noCont, cveAsignacion, cveUsuario)
			constraint EstudianteFK1 (cveAsignacion, cveUsuario) 
						references Asignacion (cveAsignacion, cveUsuario),
			constraint EstudianteFK2 (cveCarrera) references Carrera (cveCarrera)
			);

create table Personal(noPersonal varchar(5) not null,
			cveAsignacion varchar(5) not null,
			cveUsuario  varchar(5) not null,
			cveDepartamento varchar(5) not null,
			constraint PersonalPK primary key (noPersonal, cveAsignacion, cveUsuario),
			constraint PersonalFK1 (cveAsignacion, cveUsuario)
						references Asignacion (cveAsignacion, cveUsuario),
			constraint PersonalFK2 (cveDepartamento) references Departamento (cveDepartamento)
			);

create table Medico(noCedula varchar(5) not null,
			cveAsignacion varchar(5) not null,
			cveUsuario  varchar(5) not null,
			constraint MedicoPK primary key (noCedula, cveAsignacion, cveUsuario),
			constraint MedicoFK(cveAsignacion, cveUsuario) 
						references Asignacion (cveAsignacion, cveUsuario)
			);

create table Administrador(noAdmin varchar(5) not null,
			cveAsignacion varchar(5) not null,
			cveUsuario  varchar(5) not null,
			constraint AdministradorPK primary key (noAdmin, cveAsignacion, cveUsuario),
			constraint AdministradorFK(cveAsignacion, cveUsuario) 
						references Asignacion (cveAsignacion, cveUsuario)
			);

create table Monitoreo(noMonitoreo varchar(5) not null,
			cveAsignacion varchar(5) not null,
			cveUsuario  varchar(5) not null,
			constraint MonitoreoPK primary key (noMonitoreo, cveAsignacion, cveUsuario),
			constraint MonitoreoFK(cveAsignacion, cveUsuario) 
						references Asignacion (cveAsignacion, cveUsuario)
			);

create table Directivo(noDirectivo varchar(5) not null,
			cveAsignacion varchar(5) not null,
			cveUsuario  varchar(5) not null,
			constraint DirectivoPK primary key (noDirectivo, cveAsignacion, cveUsuario),
			constraint DirectivoFK(cveAsignacion, cveUsuario) 
						references Asignacion (cveAsignacion, cveUsuario)
			);

# Tablas a partir de las solicitudes

create table Solicitud(noSolicitud int not null auto_increment,
			cveAsignacion varchar(5) not null,
			cveUsuario varchar(5) not null,
			estado varchar(),
			
			);