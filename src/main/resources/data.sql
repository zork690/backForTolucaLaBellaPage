CREATE TABLE imagenes (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    imagen LONGBLOB NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    precio DOUBLE NOT NULL,
    estatus BOOL NOT NULL DEFAULT 1,
    registro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  
) ROW_FORMAT=COMPRESSED;

ALTER TABLE imagenes
  MODIFY imagen LONGBLOB NOT NULL;

ALTER TABLE imagenes
  MODIFY registro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE imagenes
  MODIFY estatus BOOL NOT NULL DEFAULT 1;