# Carrito de Compras con JDBC - Práctica 3

## Desarrollo

Utilizando como base el proyecto anterior “Carro de Compra en Sesión”, vamos a
realizar las siguientes modificaciones para agregar la funcionalidad de persistencia a
nuestra aplicación. La aplicación debe agregar los siguientes requerimientos:

### Requerimientos

1. Para la persistencia de la información utilizar el motor de base de datos H2 con
JDBC o una librería envoltorio (sql2o).
1. El sistema debe arrancar la base de datos en modo servidor, habilitar la interfaz
web de consulta y ejecutar el script de creación de todas las tablas e información
necesaria para el arranque del sistema.
1. Los modelos de datos utilizado deberán estar relacionada a una entidad de la base
de datos.
1. Los productos y la cantidad seleccionada por el usuario seguirán siendo agregados
y almacenados en una colección que representa su el carro de compras en el
contexto de sesión. Los demás procesos se estarán persistiendo en la base de
datos.
1. A la hora de un usuario autenticarse debe existir la opción de recordar usuario, el
cual si es marcada, debe crear una cookie en el cliente con una duración de una
semana con la información encriptada que permita a dicho acceder al sistema sin
necesidad de realizar el proceso de autenticación, no debe enviar información
sensible al cliente en el cookie. De realizar un logout, se invalida dicha cookie. Ver
uso de la librería http://www.jasypt.org/
