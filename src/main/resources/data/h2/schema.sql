CREATE SEQUENCE "PERSONA_SEQ"
	MINVALUE 1
	MAXVALUE 999999999
	INCREMENT BY 50
	START WITH 5000
	NOCACHE
	NOCYCLE;

CREATE SEQUENCE "PRODUCTO_SEQ"
	MINVALUE 1 
    MAXVALUE 999999999 
    INCREMENT BY 50 
    START WITH 5000 
    NOCACHE 
    NOCYCLE;
    
CREATE SEQUENCE "ESTABLECIMIENTO_SEQ"
	MINVALUE 1 
    MAXVALUE 999999999 
    INCREMENT BY 50 
    START WITH 5000 
    NOCACHE 
    NOCYCLE;
    
CREATE SEQUENCE "PEDIDO_SEQ"
	MINVALUE 1 
    MAXVALUE 999999999 
    INCREMENT BY 50 
    START WITH 5000 
    NOCACHE 
    NOCYCLE;
    
CREATE SEQUENCE "CATEGORIA_SEQ"
	MINVALUE 1 
    MAXVALUE 999999999 
    INCREMENT BY 50 
    START WITH 5000 
    NOCACHE 
    NOCYCLE;

CREATE TABLE CATEGORIAS(
	ID				BIGINT			NOT NULL,
	NAME			VARCHAR(100)	,
	PRIMARY KEY(ID)
);    
    
CREATE TABLE PRODUCTOS(
	CODIGO							BIGINT			NOT NULL,
	NOMBRE							VARCHAR(50)		,
	DESCRIPCION						VARCHAR(150)	,
	FECHA_ALTA						DATE			,
	PRECIO							DOUBLE			,
	ID_CATEGORIA					BIGINT			,
	DESCATALOGADO					BOOLEAN			,
	PRIMARY KEY(CODIGO),
	FOREIGN KEY (ID_CATEGORIA) REFERENCES CATEGORIAS (ID)
);

CREATE TABLE ESTABLECIMIENTOS(
	CODIGO							BIGINT			NOT NULL,
	NOMBRE_COMERCIAL				VARCHAR(100)	,
	FECHA_INAUGURACION				DATE			,
	DIRECCION						VARCHAR(100)	,
	POBLACION						VARCHAR(100)	,
	CODIGO_POSTAL					VARCHAR(10)		,
	PROVINCIA						VARCHAR(100)	,
	PAIS							VARCHAR(100)	,
	TELEFONO						VARCHAR(25)		,
	FAX								VARCHAR(25)		,
	EMAIL							VARCHAR(250)	,
	PRIMARY KEY (CODIGO)
);

CREATE TABLE PERSONAS(
	CODIGO							BIGINT			NOT NULL,
	DNI								VARCHAR(20)		,
	NOMBRE							VARCHAR(100)	,
	APELLIDO1						VARCHAR(100)	,
	APELLIDO2						VARCHAR(100)	,
	DIRECCION						VARCHAR(100)	,
	POBLACION						VARCHAR(100)	,
	CODIGO_POSTAL					VARCHAR(10)		,
	PROVINCIA						VARCHAR(100)	,
	PAIS							VARCHAR(100)	,
	TELEFONO						VARCHAR(25)		,
	FAX								VARCHAR(25)		,
	EMAIL							VARCHAR(250)	,
	PRIMARY KEY(CODIGO)
);

CREATE TABLE CLIENTES(
	CODIGO_CLIENTE					BIGINT			NOT NULL,
	GOLD							BOOLEAN			,
	PRIMARY KEY(CODIGO_CLIENTE),
	FOREIGN KEY(CODIGO_CLIENTE) REFERENCES PERSONAS(CODIGO)
);

CREATE TABLE CAMAREROS(
	CODIGO_CAMARERO					BIGINT			NOT NULL,
	LICENCIA_MANIPULADOR_ALIMENTOS	VARCHAR(10)		,
	PRIMARY KEY(CODIGO_CAMARERO),
	FOREIGN KEY(CODIGO_CAMARERO) REFERENCES PERSONAS(CODIGO)
);

CREATE TABLE PEDIDOS(
	CODIGO							BIGINT			NOT NULL,
	FECHA_HORA						TIMESTAMP		NOT NULL,
	CODIGO_CAMARERO					BIGINT			,
	CODIGO_CLIENTE		    		BIGINT			,
	CODIGO_ESTABLECIMIENTO			BIGINT			NOT NULL,
	ESTADO							VARCHAR(20)		NOT NULL,
	COMENTARIO                      VARCHAR(250)    ,
	PRIMARY KEY (CODIGO),
	FOREIGN KEY (CODIGO_CAMARERO) REFERENCES CAMAREROS (CODIGO_CAMARERO),
	FOREIGN KEY (CODIGO_CLIENTE) REFERENCES CLIENTES (CODIGO_CLIENTE),
	FOREIGN KEY (CODIGO_ESTABLECIMIENTO) REFERENCES ESTABLECIMIENTOS (CODIGO)
);

CREATE TABLE LINEAS_PEDIDO(
	CODIGO_PEDIDO					BIGINT			NOT NULL,
	CODIGO_PRODUCTO					BIGINT			NOT NULL,
	CANTIDAD						INTEGER			NOT NULL,
	PRECIO							DOUBLE			,
	FOREIGN KEY (CODIGO_PEDIDO) REFERENCES PEDIDOS (CODIGO),
	FOREIGN KEY (CODIGO_PRODUCTO) REFERENCES PRODUCTOS (CODIGO)
);
