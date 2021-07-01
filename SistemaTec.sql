#
Elimina la base anterior en caso de existir

drop
database SistemaTec;

#
Creacion y uso la base de datos

create
database SistemaTec;
use
SistemaTec;

#
Creacion de las tablas Independientes

create table Usuario
(
    noUsuario  int         not null auto_increment,
    usuario    varchar(30) not null,
    contra     varchar(30) not null,
    nombres    varchar(50),
    apellidos  varchar(50),
    genero     varchar(1),
    correo     varchar(50),
    fechaNac   date,
    fotoPerfil longblob,
    constraint UsuarioPK primary key (noUsuario),
    constraint UsuarioCK check (genero = '' F '' or genero = '' M ''
)
    );

create table Carrera
(
    cveCarrera varchar(5) not null,
    nombre     varchar(50),
    constraint CarreraPK primary key (cveCarrera)
);

create table Departamento
(
    cveDepa varchar(5) not null,
    nombre  varchar(100),
    constraint DepartamentoPK primary key (cveDepa)
);

create table Prueba
(
    cvePrueba varchar(5) not null,
    tipo      varchar(100),
    constraint PruebaPK primary key (cvePrueba)
);

#
Creacion de tablas dependientes

create table Encuesta
(
    noEncuesta    int not null auto_increment,
    respuesta1    int(1),
    respuesta2    int(1),
    respuesta3    int(1),
    respuesta4    int(1),
    respuesta5    int(1),
    respuesta6    int(1),
    respuesta7    int(1),
    respuesta8    int(1),
    respuesta9    int(1),
    respuesta10   int(1),
    respuesta11   int(1),
    respuesta12   int(1),
    respuesta13   int(1),
    otrosSintomas varchar(100),
    noUsuario     int not null,
    constraint EncuestaPK primary key (noEncuesta),
    constraint EncuestaFK foreign key (noUsuario) references Usuario (noUsuario)
);

#
Las ''cveAsignacion'' tendran el formato (In###) {ej: ES001, PE001, ME001, AD001, MO001, DI001}

create table Asignacion
(
    cveAsignacion varchar(5) not null,
    noUsuario     int        not null,
    nombre        varchar(50),
    constraint AsignacionPK primary key (cveAsignacion, noUsuario),
    constraint AsignacionFK foreign key (noUsuario) references Usuario (noUsuario)
);

#
Las (cve''s) de los Usuarios tendran el formato (NIn##) {ej: NES01, NPE01, NME01, NAD01, NMO01, NAD01}

create table Estudiante
(
    noCont        varchar(5) not null,
    cveAsignacion varchar(5) not null,
    noUsuario     int        not null,
    cveCarrera    varchar(5) not null,
    constraint EstudiantePK primary key (noCont, cveAsignacion, noUsuario),
    constraint EstudianteFK1 foreign key (cveAsignacion, noUsuario)
        references Asignacion (cveAsignacion, noUsuario),
    constraint EstudianteFK2 foreign key (cveCarrera) references Carrera (cveCarrera)
);

create table Personal
(
    noPersonal    varchar(5) not null,
    cveAsignacion varchar(5) not null,
    noUsuario     int        not null,
    cveDepa       varchar(5) not null,
    constraint PersonalPK primary key (noPersonal, cveAsignacion, noUsuario),
    constraint PersonalFK1 foreign key (cveAsignacion, noUsuario)
        references Asignacion (cveAsignacion, noUsuario),
    constraint PersonalFK2 foreign key (cveDepa) references Departamento (cveDepa)
);

create table Medico
(
    noCedula      varchar(5) not null,
    cveAsignacion varchar(5) not null,
    noUsuario     int        not null,
    constraint MedicoPK primary key (noCedula),
    constraint MedicoFK foreign key (cveAsignacion, noUsuario)
        references Asignacion (cveAsignacion, noUsuario)
);

create table Administrador
(
    noAdmin       varchar(5) not null,
    cveAsignacion varchar(5) not null,
    noUsuario     int        not null,
    constraint AdministradorPK primary key (noAdmin),
    constraint AdministradorFK foreign key (cveAsignacion, noUsuario)
        references Asignacion (cveAsignacion, noUsuario)
);

create table Monitoreo
(
    noMonitoreo   varchar(5) not null,
    cveAsignacion varchar(5) not null,
    noUsuario     int        not null,
    constraint MonitoreoPK primary key (noMonitoreo),
    constraint MonitoreoFK foreign key (cveAsignacion, noUsuario)
        references Asignacion (cveAsignacion, noUsuario)
);

create table Directivo
(
    noDirectivo   varchar(5) not null,
    cveAsignacion varchar(5) not null,
    noUsuario     int        not null,
    constraint DirectivoPK primary key (noDirectivo),
    constraint DirectivoFK foreign key (cveAsignacion, noUsuario)
        references Asignacion (cveAsignacion, noUsuario)
);

#
Tablas a partir de las solicitudes

create table Solicitud
(
    noSolicitud   int        not null auto_increment,
    estado        varchar(30),
    tipo          varchar(30),
    cveAsignacion varchar(5) not null,
    noUsuario     int        not null,
    noCedula      varchar(5) not null,
    constraint SolicitudPK primary key (noSolicitud),
    constraint SolicitudFK1 foreign key (cveAsignacion, noUsuario)
        references Asignacion (cveAsignacion, noUsuario),
    constraint SolicitudFK2 foreign key (noCedula) references Medico (noCedula),
    constraint SolicitudCK1 check (tipo = '' Virtual '' or tipo = '' Presencial ''
) ,
			constraint SolicitudCK2 check (estado = ''Aceptado'' or estado = ''Rechazado'' or estado =''Espera'')
			);

create table Archivos
(
    cveArchivos varchar(5) not null,
    foto        longblob,
    video       longblob,
    noSolicitud int        not null,
    constraint ArchivosPK primary key (cveArchivos),
    constraint ArchivosFK foreign key (noSolicitud) references Solicitud (noSolicitud)
);

create table Consulta
(
    noConsulta    int        not null auto_increment,
    sintomas      varchar(100),
    fecha         date,
    hora          varchar(10),
    tipo          varchar(30),
    cveAsignacion varchar(5) not null,
    noUsuario     int        not null,
    noCedula      varchar(5) not null,
    constraint ConsultaPK primary key (noConsulta),
    constraint ConsultaFK1 foreign key (cveAsignacion, noUsuario)
        references Asignacion (cveAsignacion, noUsuario),
    constraint ConsultaFK2 foreign key (noCedula) references Medico (noCedula),
    constraint ConsultaCK1 check (tipo = '' Virtual '' or tipo = '' Presencial ''
)
    );

create table Receta
(
    noReceta     int not null auto_increment,
    indicaciones varchar(100),
    noConsulta   int not null,
    constraint RecetaPK primary key (noReceta),
    constraint RecetaFK foreign key (noConsulta) references Consulta (noConsulta)
);

create table Medicamento
(
    cveMedicamento varchar(5) not null,
    nombre         varchar(50),
    cantidad       int(5),
    noReceta       int        not null,
    constraint MedicamentoPK primary key (cveMedicamento),
    constraint MedicamentoFK foreign key (noReceta) references Receta (noReceta)
);

create table Orden
(
    noOrden    int        not null auto_increment,
    resultado  varchar(100),
    noConsulta int        not null,
    noCedula   varchar(5) not null,
    cvePrueba  varchar(5) not null,
    constraint primary key (noOrden),
    constraint OrdenFK1 foreign key (noConsulta) references Consulta (noConsulta),
    constraint OrdenFK2 foreign key (noCedula) references Medico (noCedula),
    constraint OrdenFK3 foreign key (cvePrueba) references Prueba (cvePrueba)
);

create table ContactoInterno
(
    noUsuario int not null,
    noOrden   int not null,
    constraint ContactoInternoPK primary key (noUsuario, noOrden),
    constraint ContactoInternoFK1 foreign key (noUsuario) references Usuario (noUsuario),
    constraint ContactoInternoFK2 foreign key (noOrden) references Orden (noOrden)
);

create table ContactoExterno
(
    cveExterno varchar(5),
    nombre     varchar(50),
    correo     varchar(50),
    noOrden    int not null,
    constraint ContactoExternoPK primary key (cveExterno),
    constraint ContactoExternoFK foreign key (noOrden)
        references Orden (noOrden)
);

create table Alerta
(
    noAlerta    int not null auto_increment,
    descripcion varchar(100),
    noOrden     int not null,
    constraint AlertaPK primary key (noAlerta),
    constraint AlertaFK foreign key (noOrden) references Orden (noOrden)
);

create table AlertaMonitoreada
(
    noAlertaMonitoreo int        not null auto_increment,
    descripcion       varchar(100),
    noOrden           int        not null,
    noMonitoreo       varchar(5) not null,
    constraint AlertaMonitoreadaPK primary key (noAlertaMonitoreo),
    constraint AlertaMonitoreadaFK1 foreign key (noOrden) references Orden (noOrden),
    constraint AlertaMonitoreadaFK2 foreign key (noMonitoreo) references Monitoreo (noMonitoreo)
);

#
Creacion de los registros de la tablas independientes

insert into Carrera values
			(''CA001'', ''Lic. Administracion''),
			(''CA002'', ''Ing. Ambiental''),
			(''CA003'', ''Ing. Bioquimica''),
			(''CA004'', ''Ing. En Electronica''),
			(''CA005'', ''Ing. Gestion Empresarial''),
			(''CA006'', ''Ing. Industrial''),
			(''CA007'', ''Ing. Mecanica''),
			(''CA008'', ''Ing. Mecatronica''),
			(''CA009'', ''Ing. Quimica''),
			(''CA010'', ''Ing. Sistemas computacionales'');

insert into Departamento
values (''DE001'', ''Actividades Extraescolares''),
       (''DE002'', ''Calidad''),
       (''DE003'', ''Centro de computo''),
       (''DE004'', ''Centro de informacion''),
       (''DE005'', ''Ciencias basicas''),
       (''DE006'', ''Comunicacion y Difusion''),
       (''DE007'', ''Departamento de Ciencias Economico Administrativas''),
       (''DE009'', ''Departamento de Ingenieria Bioquimica''),
       (''DE010'', ''Departamento de Ingenieria Electronica''),
       (''DE011'', ''Departamento de Ingenieria en Mecatronica''),
       (''DE012'', ''Departamento de Ingenieria en Sistemas Computacionales e Informatica''),
       (''DE013'', ''Departamento de Ingenieria Industrial''),
       (''DE014'', ''Departamento de Ingenieria Mecanica''),
       (''DE015'', ''Departamento de Ingenieria Quimica''),
       (''DE016'', ''Desarrollo Academico''),
       (''DE017'', ''Direccion''),
       (''DE018'', ''Division de Estudios de Posgrado e Investigacion''),
       (''DE019'', ''Division de Estudios Profesionales''),
       (''DE020'', ''Division de Estudios Profesionales / Propedeutico''),
       (''DE021'', ''Gestion Tecnologica y Vinculacion''),
       (''DE022'', ''Ingenieria Ambiental''),
       (''DE023'', ''Mantenimiento de Equipo''),
       (''DE024'', ''Planeacion, Programacion y Presupuestacion''),
       (''DE025'', ''Recursos Financieros''),
       (''DE026'', ''Recursos Humanos''),
       (''DE027'', ''Recursos Materiales y Servicios''),
       (''DE028'', ''Servicios Escolares''),
       (''DE029'', ''Sindicato''),
       (''DE030'', ''Subdireccion Academica''),
       (''DE031'', ''Subdireccion de Planeacion y Vinculacion''),
       (''DE032'', ''Subdireccion de Servicios Administrativos'');

insert into Prueba
values (''PR001'', ''Prueba rapida de sangre''),
       (''PR002'', ''Prueba rapida de antigeno''),
       (''PR003'', ''Prueba PCR (Reaccion en cadena de la polimerasa)'');

#
Creacion de los usuarios que no se pueden registar (tres de cada uno)

insert into Usuario (usuario, contra, nombres, apellidos, genero, correo, fechaNac) values
	(''medico1'', ''contraME1'', ''Aurelio'', ''Granjeno Luna'', ''M'', ''exampleME1@example.com'', ''1999-08-02''),
	(''medico2'', ''contraME2'', ''Maria Guadalupe'', ''Sanchez Campos'', ''F'', ''exampleME2@example.com'', ''1988-09-20''),
	(''medico3'', ''contraME3'', ''Juan'', ''Cruz Coyote'', ''M'', ''exampleME3@example.com'', ''1997-05-16''),
	(''admin1'', ''contraAD1'', ''Carlos Daniel'', ''Perez Ruiz'', ''M'', ''exampleAD1@example.com'', ''1999-08-02''),
	(''admin2'', ''contraAD2'', ''Sofia'', ''Montoya Zarate'', ''F'', ''exampleAD2@example.com'', ''1988-09-20''),
	(''admin3'', ''contraAD3'', ''Adan'', ''Cruz Tovar'', ''M'', ''exampleAD3@example.com'', ''1997-05-16''),
	(''monitoreo1'', ''contraMO1'', ''Pedro'', ''Palma Nuñez'', ''M'', ''exampleMO1@example.com'', ''1999-08-02''),
	(''monitoreo2'', ''contraMO2'', ''Jaime'', ''Aboytes Pizano'', ''M'', ''exampleMO2@example.com'', ''1988-09-20''),
	(''monitoreo3'', ''contraMO3'', ''Yolanda'', ''Banderas Jimenez'', ''F'', ''exampleMO3@example.com'', ''1997-05-16''),
	(''directivo1'', ''contraDI1'', ''Fausto'', ''Rocha Peralta'', ''M'', ''exampleDI1@example.com'', ''1999-08-02''),
	(''directivo2'', ''contraDI2'', ''Sarahi'', ''Hernandez Centeno'', ''F'', ''exampleDI2@example.com'', ''1988-09-20''),
	(''directivo3'', ''contraDI3'', ''Miguel Rodrigo'', ''Alvarez Quesada'', ''M'', ''exampleDI3@example.com'', ''1997-05-16'');

insert into Asignacion
values (''ME001'', 1, ''Medico''),
       (''ME002'', 2, ''Medico''),
       (''ME003'', 3, ''Medico''),
       (''AD001'', 4, ''Administrador''),
       (''AD002'', 5, ''Administrador''),
       (''AD003'', 6, ''Administrador''),
       (''MO001'', 7, ''Monitoreo''),
       (''MO002'', 8, ''Monitoreo''),
       (''MO003'', 9, ''Monitoreo''),
       (''DI001'', 10, ''Directivo''),
       (''DI002'', 11, ''Directivo''),
       (''DI003'', 12, ''Directivo'');

insert into Medico
values (''NME01'', ''ME001'', 1),
       (''NME02'', ''ME002'', 2),
       (''NME03'', ''ME003'', 3);

insert into Administrador
values (''NAD01'', ''AD001'', 4),
       (''NAD02'', ''AD002'', 5),
       (''NAD03'', ''AD003'', 6);

insert into Monitoreo
values (''NMO01'', ''MO001'', 7),
       (''NMO02'', ''MO002'', 8),
       (''NMO03'', ''MO003'', 9);

insert into Directivo
values (''NDI01'', ''DI001'', 10),
       (''NDI02'', ''DI002'', 11),
       (''NDI03'', ''DI003'', 12);

#
Se crearan algunos pacientes iniciales a manera de prueba en el sistema

insert into Usuario (usuario, contra, nombres, apellidos, genero, correo, fechaNac) values
	# Estudiantes ###,##,###
	# 13 - 27
	(''19010111'', ''contraES1'', ''Enrique'', ''Rangel Andrade'', ''M'', ''exampleES1@example.com'', ''2001-10-10''),
	(''19010112'', ''contraES2'', ''Uriel'', ''Garcia Almaraz'', ''M'', ''exampleES2@example.com'', ''2000-03-08''),
	(''19010113'', ''contraES3'', ''Ana Paula'', ''Castillo Cabrera'', ''F'', ''exampleES3@example.com'', ''2002-04-03''),
	(''19010114'', ''contraES4'', ''Clara'', ''Zavala Rodriguez'', ''F'', ''exampleES4@example.com'', ''2001-10-06''),
	(''19010115'', ''contraES5'', ''Oscar'', ''Cardenas Rojas'', ''M'', ''exampleES5@example.com'', ''2000-02-01''),
	(''19010116'', ''contraES6'', ''Camilo'', ''Cano Vasquez'', ''M'', ''exampleES6@example.com'', ''2001-11-10''),
    (''19010117'', ''contraES7'', ''Luis Angel'', ''Garcia Ramirez'', ''M'', ''exampleES7@example.com'', ''2002-04-08''),
    (''19010118'', ''contraES8'', ''Fatima'', ''Pineda Olivares'', ''F'', ''exampleES8@example.com'', ''2002-09-03''),
    (''19010119'', ''contraES9'', ''Angelica'', ''Godinez Lugo'', ''F'', ''exampleES9@example.com'', ''2001-12-06''),
    (''19010120'', ''contraES10'', ''Jairo Saul'', ''Quintanilla Cornejo'', ''M'', ''exampleES10@example.com'', ''2000-09-01''),
    (''19010121'', ''contraES11'', ''Moises'', ''Ochoa Uribe'', ''M'', ''exampleES11@example.com'', ''2001-11-10''),
    (''19010122'', ''contraES12'', ''Ramiro'', ''Escobar Delgado'', ''M'', ''exampleES12@example.com'', ''2000-04-08''),
    (''19010123'', ''contraES13'', ''Giselle'', ''Montes Galvan'', ''F'', ''exampleES13@example.com'', ''2002-05-03''),
    (''19010124'', ''contraES14'', ''Gabriela'', ''Higareda Guarnizo'', ''F'', ''exampleES14@example.com'', ''2001-12-06''),
    (''19010125'', ''contraES15'', ''Jesus Noe'', ''Contreras Villanuevas'', ''M'', ''exampleES15@example.com'', ''2000-01-01''),
	# Personal ###,##,###
	# 28 - 42
	(''29050111'', ''contraPE1'', ''Diana Camila'', ''Banda Gasca'', ''F'', ''examplePE1@example.com'', ''1975-12-04''),
	(''29050112'', ''contraPE2'', ''Ruth'', ''Paramo Nieto'', ''M'', ''examplePE2@example.com'', ''1980-03-03''),
	(''29050113'', ''contraPE3'', ''Alma'', ''Pantoja Ortiz'', ''F'', ''examplePE3@example.com'', ''1972-12-02''),
	(''29050114'', ''contraPE4'', ''Arturo Gabriel'', ''Vargas Mancera'', ''M'', ''examplePE4@example.com'', ''1970-04-01''),
	(''29050115'', ''contraPE5'', ''Tania'', ''Escobedo Conejo'', ''F'', ''examplePE5@example.com'', ''1968-12-04''),
	(''29050116'', ''contraPE6'', ''Sandra'', ''Moreno Gonzalez'', ''F'', ''examplePE6@example.com'', ''1962-11-06''),
    (''29050117'', ''contraPE7'', ''Nestor'', ''Salinas Najarro'', ''M'', ''examplePE7@example.com'', ''1961-04-10''),
    (''29050118'', ''contraPE8'', ''Elizabeth'', ''Molina Gomez'', ''F'', ''examplePE8@example.com'', ''1981-12-09''),
    (''29050119'', ''contraPE9'', ''Edgar Ignacio'', ''Esquivel'', ''M'', ''examplePE9@example.com'', ''1966-01-07''),
    (''29050120'', ''contraPE10'', ''Raquel'', ''Bravo Noguez'', ''F'', ''examplePE10@example.com'', ''1972-11-06''),
    (''29050121'', ''contraPE11'', ''Michelle'', ''Velazco Espinoza'', ''F'', ''examplePE11@example.com'', ''1962-12-04''),
    (''29050122'', ''contraPE12'', ''Yahir'', ''Romero Venegas'', ''M'', ''examplePE12@example.com'', ''1976-01-09''),
    (''29050123'', ''contraPE13'', ''Litzy'', ''Juarez Arellano'', ''F'', ''examplePE13@example.com'', ''1983-11-03''),
    (''29050124'', ''contraPE14'', ''Diego Leonardo'', ''Valadez Acosta'', ''M'', ''examplePE14@example.com'', ''1960-03-09''),
    (''29050125'', ''contraPE15'', ''Ximena'', ''Flores Estrada'', ''F'', ''examplePE15@example.com'', ''1982-11-02'');

insert into Asignacion
values
    # Estudiantes ###,##,###
	(''ES001'', 13, ''Estudiante''),
	(''ES002'', 14, ''Estudiante''),
	(''ES003'', 15, ''Estudiante''),
	(''ES004'', 16, ''Estudiante''),
	(''ES005'', 17, ''Estudiante''),
	(''ES006'', 18, ''Estudiante''),
	(''ES007'', 19, ''Estudiante''),
	(''ES008'', 20, ''Estudiante''),
	(''ES009'', 21, ''Estudiante''),
	(''ES010'', 22, ''Estudiante''),
	(''ES011'', 23, ''Estudiante''),
    (''ES012'', 24, ''Estudiante''),
    (''ES013'', 25, ''Estudiante''),
    (''ES014'', 26, ''Estudiante''),
    (''ES015'', 27, ''Estudiante''),
	# Personal ###,##,###
	(''PE001'', 28, ''Personal''),
	(''PE002'', 29, ''Personal''),
	(''PE003'', 30, ''Personal''),
	(''PE004'', 31, ''Personal''),
	(''PE005'', 32, ''Personal''),
	(''PE006'', 33, ''Personal''),
	(''PE007'', 34, ''Personal''),
	(''PE008'', 35, ''Personal''),
	(''PE009'', 36, ''Personal''),
	(''PE010'', 37, ''Personal''),
	(''PE011'', 38, ''Personal''),
    (''PE012'', 39, ''Personal''),
    (''PE013'', 40, ''Personal''),
    (''PE014'', 41, ''Personal''),
    (''PE015'', 42, ''Personal'');


insert into Estudiante
values (''10111'', ''ES001'', 13, ''CA001''),
       (''10112'', ''ES002'', 14, ''CA010''),
       (''10113'', ''ES003'', 15, ''CA007''),
       (''10114'', ''ES004'', 16, ''CA007''),
       (''10115'', ''ES005'', 17, ''CA008''),
       (''10116'', ''ES006'', 18, ''CA001''),
       (''10117'', ''ES007'', 19, ''CA002''),
       (''10118'', ''ES008'', 20, ''CA003''),
       (''10119'', ''ES009'', 21, ''CA005''),
       (''10120'', ''ES010'', 22, ''CA004''),
       (''10116'', ''ES011'', 23, ''CA010''),
       (''10117'', ''ES012'', 24, ''CA009''),
       (''10118'', ''ES013'', 25, ''CA004''),
       (''10119'', ''ES014'', 26, ''CA005''),
       (''10120'', ''ES015'', 27, ''CA006'');

insert into Personal
values (''50111'', ''PE001'', 28, ''DE001''),
       (''50112'', ''PE002'', 29, ''DE002''),
       (''50113'', ''PE003'', 30, ''DE014''),
       (''50114'', ''PE004'', 31, ''DE014''),
       (''50115'', ''PE005'', 32, ''DE005''),
       (''50116'', ''PE006'', 33, ''DE006''),
       (''50117'', ''PE007'', 34, ''DE012''),
       (''50118'', ''PE008'', 35, ''DE025''),
       (''50119'', ''PE009'', 36, ''DE024''),
       (''50120'', ''PE010'', 37, ''DE025''),
       (''50121'', ''PE011'', 38, ''DE001''),
       (''50122'', ''PE012'', 39, ''DE002''),
       (''50123'', ''PE013'', 40, ''DE013''),
       (''50124'', ''PE014'', 41, ''DE024''),
       (''50125'', ''PE015'', 42, ''DE005'');

#
Se crearan casos de prueba iniciales en el sistema

insert into Solicitud (estado, tipo, cveAsignacion, noUsuario, noCedula) values
	# Estudiantes
	(''Rechazado'', ''Virtual'', ''ES001'', 13, ''NME03''),
	(''Aceptado'', ''Presencial'', ''ES002'', 14, ''NME02''),
	(''Aceptado'', ''Virtual'', ''ES003'', 15, ''NME02''),
	(''Aceptado'', ''Presencial'', ''ES004'', 16, ''NME01''),
	(''Espera'', ''Virtual'', ''ES005'', 17, ''NME02''),
	(''Espera'', ''Virtual'', ''ES006'', 18, ''NME03''),
	(''Rechazado'', ''Presencial'', ''ES007'', 19, ''NME03''),
    (''Aceptado'', ''Virtual'', ''ES008'', 20, ''NME02''),
    (''Aceptado'', ''Virtual'', ''ES009'', 21, ''NME02''),
    (''Aceptado'', ''Presencial'', ''ES010'', 22, ''NME01''),
    (''Espera'', ''Presencial'', ''ES011'', 23, ''NME03''),
    (''Espera'', ''Virtual'', ''ES012'', 24, ''NME01''),
    (''Rechazado'', ''Virtual'', ''ES013'', 25, ''NME03''),
    (''Aceptado'', ''Presencial'', ''ES014'', 26, ''NME02''),
    (''Aceptado'', ''Virtual'', ''ES015'', 27, ''NME02''),
    (''Aceptado'', ''Virtual'', ''ES016'', 27, ''NME03''),
	# Personal
	(''Rechazado'', ''Presencial'', ''PE001'', 28, ''NME03''),
	(''Aceptado'', ''Presencial'', ''PE002'', 29, ''NME01''),
	(''Aceptado'', ''Virtual'', ''PE003'', 30, ''NME03''),
	(''Rechazado'', ''Presencial'', ''PE004'', 31, ''NME02''),
	(''Aceptado'', ''Presencial'', ''PE005'', 32, ''NME01''),
	(''Espera'', ''Virtual'', ''PE006'', 33, ''NME01''),
	(''Rechazado'', ''Presencial'', ''PE007'', 34, ''NME02''),
    (''Aceptado'', ''Presencial'', ''PE008'', 35, ''NME01''),
   	(''Aceptado'', ''Virtual'', ''PE009'', 36, ''NME02''),
   	(''Rechazado'', ''Presencial'', ''PE010'', 37, ''NME03''),
   	(''Aceptado'', ''Presencial'', ''PE011'', 38, ''NME01''),
   	(''Espera'', ''Virtual'', ''PE012'', 39, ''NME01''),
   	(''Rechazado'', ''Presencial'', ''PE013'', 40, ''NME03''),
   	(''Aceptado'', ''Presencial'', ''PE014'', 41, ''NME02''),
    (''Aceptado'', ''Virtual'', ''PE015'', 42, ''NME01'');


insert into Consulta (sintomas, fecha, hora, tipo, cveAsignacion, noUsuario, noCedula)
values
    # Estudiantes
	(''Dolor de Cabeza'', ''2021-10-01'', ''10:00'', ''Virtual'', ''ES001'', 13, ''NME01''),
	(''Fiebre y dolor'', ''2021-12-05'', ''12:00'', ''Virtual'', ''ES002'', 14, ''NME02''),
	(''Fiebre'', ''2021-12-05'', ''14:00'', ''Presencial'', ''ES003'', 15, ''NME03''),
	(''Dolor de Cabeza'', ''2021-10-01'', ''10:00'', ''Virtual'', ''ES004'', 16, ''NME01''),
    (''Fiebre y dolor'', ''2021-12-05'', ''12:00'', ''Virtual'', ''ES005'', 17, ''NME02''),
    (''Fiebre'', ''2021-12-05'', ''14:00'', ''Presencial'', ''ES006'', 18, ''NME03''),
    (''Dolor de Cabeza'', ''2021-10-01'', ''10:00'', ''Virtual'', ''ES007'', 19, ''NME01''),
    (''Fiebre y dolor'', ''2021-12-05'', ''12:00'', ''Virtual'', ''ES008'', 20, ''NME02''),
    (''Fiebre'', ''2021-12-05'', ''14:00'', ''Presencial'', ''ES009'', 21, ''NME03''),
    (''Dolor de Cabeza'', ''2021-10-01'', ''10:00'', ''Virtual'', ''ES010'', 22, ''NME01''),
    (''Fiebre y dolor'', ''2021-12-05'', ''12:00'', ''Virtual'', ''ES011'', 23, ''NME02''),
    (''Dolor de Cabeza'', ''2021-10-01'', ''10:00'', ''Virtual'', ''ES012'', 24, ''NME01''),
    (''Fiebre y dolor'', ''2021-12-05'', ''12:00'', ''Virtual'', ''ES013'', 25, ''NME02''),
    (''Fiebre'', ''2021-12-05'', ''14:00'', ''Presencial'', ''ES014'', 26, ''NME03''),
    (''Fiebre'', ''2021-12-05'', ''16:00'', ''Presencial'', ''ES015'', 27, ''NME02''),
	# Personal
	(''Nauseas'', ''2021-11-24'', ''16:00'', ''Presencial'', ''PE001'', 28, ''NME02''),
	(''Tos'', ''2021-11-15'', ''17:00'', ''Virtual'', ''PE001'', 28, ''NME02''),
	(''Dolor de Garganta'', ''2021-12-18'', ''18:00'', ''Presencial'', ''PE001'', 29, ''NME02''),
	(''Dolor de cabeza'', ''2021-12-18'', ''12:00'', ''Presencial'', ''PE002'', 30, ''NME02''),
	(''Nauseas'', ''2021-11-21'', ''13:00'', ''Presencial'', ''PE001'', 31, ''NME03''),
	(''Nauseas'', ''2021-10-22'', ''14:00'', ''Virtual'', ''PE001'', 30, ''NME03''),
	(''Nauseas'', ''2021-10-23'', ''15:00'', ''Presencial'', ''PE001'', 28, ''NME01''),
	(''Migraña'', ''2021-10-24'', ''18:00'', ''Virtual'', ''PE002'', 29, ''NME01''),
	(''Fiebre y vomito'', ''2021-11-12'', ''20:00'', ''Presencial'', ''PE003'', 30, ''NME03'');
#PersonalPelos
(''Nauseas'', ''2021-10-11'', ''16:00'', ''Presencial'', ''PE001'', 32, ''NME02''),
	(''Tos'', ''2021-09-11'', ''17:00'', ''Virtual'', ''PE001'', 33, ''NME03''),
	(''Dolor de Garganta'', ''2021-09-24'', ''18:00'', ''Virtual'', ''PE001'', 34, ''NME01''),
	(''Nauseas'', ''2021-10-12'', ''15:00'', ''Presencial'', ''PE001'', 38, ''NME01''),
	(''Migraña'', ''2021-10-12'', ''18:00'', ''Virtual'', ''PE002'', 39, ''NME01''),
	(''Dolor de cabeza'', ''2021-12-24'', ''12:00'', ''Presencial'', ''PE002'', 35, ''NME02''),
	(''Nauseas'', ''2021-11-12'', ''13:00'', ''Presencial'', ''PE001'', 36, ''NME03''),
	(''Nauseas'', ''2021-10-12'', ''14:00'', ''Virtual'', ''PE001'', 37, ''NME03''),
	(''Fiebre y vomito'', ''2021-11-12'', ''20:00'', ''Presencial'', ''PE003'', 40, ''NME03'');

insert into Receta (indicaciones, noConsulta)
values
    # Estudiantes
	(''Una pastilla cada 8 hrs'', 1),
	(''Tomar cuando haya dolor intenso'', 1),
	(''Dividir la pastilla en dos'', 2),
	(''Tomar en ayunas'', 3),
	# Personal
	(''Una pastilla cada 8 hrs'', 4),
	(''Tomar cuando siga el dolor'', 5),
	(''No tomar refrescos'', 5),
	(''Tomar en ayunas'', 6);
    ('' Tomar una caguama '', 7);
    ('' Tomar agua '', 8);
    ('' Tomar medicamento '', 9);
    ('' Tomar Flanax '', 10);
    ('' Reposo en cama '', 11);
    ('' Reposo leve '', 12);
    ('' Evitar comer grasas '', 13);
    ('' Evitar comer embutidos '', 14);
    ('' Tomar cada 16hrs '', 15);
    ('' Tomar cada 6hrs '', 16);

insert into Medicamento (cveMedicamento, nombre, cantidad, noReceta)
values (''00001'', ''Paracetamol'', 24, 1),
       (''00002'', ''Paracetamol'', 12, 2),
       (''00003'', ''Paracetamol'', 24, 3),
       (''00004'', ''Paracetamol'', 12, 4),
       (''00005'', ''Paracetamol'', 24, 5),
       (''00006'', ''Paracetamol'', 12, 6);
    (''00007'', '' Aspirina '', 24, 7)
    ,
	(''00008'', ''Aspirina '', 12, 8),
	(''00009'', ''Aspirina '', 24, 9),
	(''00010'', ''Aspirina '', 12, 10),
	(''00011'', ''Omeprazol '', 24, 11),
	(''00012'', ''Omeprazol  '', 12, 12);
    (''00013'', '' Omeprazol '', 24, 13)
    ,
	(''00014'', ''Omeprazol '', 12, 14),
	(''00015'', ''Amlodipina '', 24, 15),
	(''00016'', ''Amlodipina '', 12, 16),
	(''00017'', ''Amlodipina '', 24, 17),
	(''00018'', ''Amlodipina '', 12, 18);

insert into Orden (resultado, noConsulta, noCedula, cvePrueba)
values (''Contagiado'', 1, ''NME01'', ''PR001''),
       (''Contagiado'', 2, ''NME02'', ''PR002''),
       (''Contagiado'', 3, ''NME03'', ''PR003''),
       (''Contagiado'', 4, ''NME02'', ''PR003''),
       (''Contagiado'', 5, ''NME01'', ''PR002''),
       (''Contagiado'', 6, ''NME03'', ''PR001'');
    ('' Contagiado '', 7, '' NME01 '', '' PR001 '')
    ,
	(''Contagiado'', 8, ''NME02'', ''PR002''),
	(''Contagiado'', 9, ''NME03'', ''PR003''),
	(''Contagiado'', 10, ''NME02'', ''PR003''),
	(''Contagiado'', 11, ''NME01'', ''PR002''),
	(''Contagiado'', 12, ''NME03'', ''PR001'');
    ('' Contagiado '', 13, '' NME01 '', '' PR001 '')
    ,
	(''Contagiado'', 14, ''NME02'', ''PR002''),
	(''Contagiado'', 15, ''NME03'', ''PR003''),
	(''Contagiado'', 16, ''NME02'', ''PR003''),
	(''Contagiado'', 17, ''NME01'', ''PR002''),
	(''Contagiado'', 18, ''NME03'', ''PR001'');

insert into Alerta (descripcion, noOrden)
values (''Contagiado de Covid-19'', 1),
       (''Tiene todos los sintomas'', 2),
       (''Contagiado de Covid-19'', 3),
       (''Contagiado de Covid-19'', 4),
       (''Tiene todos los sintomas'', 5),
       (''Contagiado de Covid-19'', 6) (''Contagiado de Covid-19'', 7)
	(''Tiene todos los sintomas'', 8)
	(''Contagiado de Covid-19'', 9)
	(''Tiene todos los sintomas'', 10)
	(''Contagiado de Covid-19'', 11)
	(''Tiene todos los sintomas'', 12)
	(''Contagiado de Covid-19'', 13)
	(''Tiene todos los sintomas'', 14)
	(''Contagiado de Covid-19'', 15);

insert into AlertaMonitoreada (descripcion, noOrden, noMonitoreo)
values (''Estado de salud delicado'', 1, ''NMO01''),
       (''Sintomas contagiosos'', 2, ''NMO01''),
       (''Estado de salud delicado'', 3, ''NMO02''),
       (''C murio'', 3, ''NMO03''),
       (''Ce nos fue'', 2, ''NMO03''),
       (''Aun sobrevive'', 1, ''NMO02''),
       (''Salvo el semestre'', 2, ''NMO02''),
       (''Estado de salud delicado'', 4, ''NMO03'');

insert into Encuesta (respuesta1, respuesta2, respuesta3, respuesta4, respuesta5,
                      respuesta6, respuesta7, respuesta8, respuesta9, respuesta10, respuesta11,
                      respuesta12, respuesta13, otrosSintomas, noUsuario)
values (0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, ''Dolor de Cabeza'', 13),
       (0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, ''Fiebre y dolor'', 14),
       (0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, ''Fiebre'', 15),
       (0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, null, 16),
       (0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, null, 17);

#
Se usara una vista para generar reportes

create view Reporte1Estudiantes(Nombres, Apellidos, Fecha, Resultado, Carrera) as
select U.nombres, U.apellidos, C.fecha, O.resultado, C2.nombre
from Estudiante
         inner join Asignacion A on Estudiante.cveAsignacion = A.cveAsignacion
    and Estudiante.noUsuario = A.noUsuario
         inner join Consulta C on A.cveAsignacion = C.cveAsignacion
    and A.noUsuario = C.noUsuario
         inner join Orden O on C.noConsulta = O.noConsulta
         inner join Usuario U on A.noUsuario = U.noUsuario
         inner join Carrera C2 on Estudiante.cveCarrera = C2.cveCarrera
where O.resultado = ''Contagiado'';

create view Reporte2Personal(Nombres, Apellidos, Fecha, Resultado, Departamento) as
select U.nombres, U.apellidos, C.fecha, O.resultado, D.nombre
from Personal P
         inner join Asignacion A on P.cveAsignacion = A.cveAsignacion
    and P.noUsuario = A.noUsuario
         inner join Consulta C on A.cveAsignacion = C.cveAsignacion and A.noUsuario = C.noUsuario
         inner join Orden O on C.noConsulta = O.noConsulta
         inner join Usuario U on A.noUsuario = U.noUsuario
         inner join Departamento D on P.cveDepa = D.cveDepa
where O.resultado = ''Contagiado'';

create view Reporte3Carreras(Carrera, Contagiados) as
select C.nombre, count(E.cveCarrera) as contagiados
from Carrera C
         inner join Estudiante E on C.cveCarrera = E.cveCarrera
         inner join Asignacion A on E.cveAsignacion = A.cveAsignacion and E.noUsuario = A.noUsuario
         inner join Consulta C2 on A.cveAsignacion = C2.cveAsignacion and A.noUsuario = C2.noUsuario
         inner join Orden O on C2.noConsulta = O.noConsulta
where O.resultado = ''Contagiado''
			group by C.nombre;

create view Reporte4Departamentos(Departamento, Contagiados) as
select D.nombre, count(P.cveDepa) as contagiados
from Departamento D
         inner join Personal P on D.cveDepa = P.cveDepa
         inner join Asignacion A on P.cveAsignacion = A.cveAsignacion and P.noUsuario = A.noUsuario
         inner join Consulta C on A.cveAsignacion = C.cveAsignacion and A.noUsuario = C.noUsuario
         inner join Orden O on C.noConsulta = O.noConsulta
where O.resultado = ''Contagiado''
			group by D.nombre;

create view Grafica1(Medico, Consultas) as
select M.noCedula, count(C.noConsulta) as consultas
from Consulta C
         inner join Medico M on C.noCedula = M.noCedula
         inner join Usuario U on C.noUsuario = U.noUsuario
group by M.noCedula;

#
Crear conexion con el usuario de DataGrip

grant all privileges on SistemaTec.* to topicos_progra;

#
Exportar
-- mysqldump -u root -p SistemaTec > SistemaTec.sql
# Importar (debe estar creada la base de datos)
-- mysql -u root -p SistemaTec < SistemaTec.sql