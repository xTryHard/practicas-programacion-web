# Carrito de Compras con ORM - Práctica 4

## Desarrollo

Utilizando como base el proyecto anterior “Carro de Compra con JDBC”, vamos a
realizar las siguientes modificaciones para agregar la funcionalidad de persistencia
utilizando ORM y habilitando el proyecto en . La aplicación debe agregar los siguientes
requerimientos:

### Requerimientos

1. Para la persistencia de la información utilizar el motor de base de datos H2 con
ORM, JPA con Hibernate como motor de persistencia.
1. El sistema debe arrancar la base de datos en modo servidor, habilitar la interfaz
web de consulta y crear toda la información base de manera automática.
1. Los modelos de datos utilizado deberán estar relacionada a una entidad de la base
de datos.
1. En el CRUD de Productos, debemos permitir almacenar una o varias imágenes del
producto que estamos creando, así como una descripción. La imagen debe ser
almacenada en base 64. La imagen es obligatoria para el registro de un producto.
Ver el ejemplo en el proyecto Demostración ORM-JPA. En la visualización del
Equipo debe mostrarse dicha imagen.
1. En la vista de Selección del Producto para agregar al Carrito de Compra, vamos a
incluir una vista para visualizar al descripción del producto, las imágenes
agregadas en la creación del producto y permitir a los usuarios agregar comentario.
1. Los usuarios administradores pueden eliminar comentarios que consideren
ofensivos.
1. En la vista de Selección del Producto para agregar al Carrito de Compra, vamos a
implementar un sistema de paginación que muestre 10 productos por pagina. La
Paginación es una técnica que nos permite ser eficientes con la información que
accedemos de la base de datos y presentamos en la vista. Ver ejemplo para
implementar en Hibernate aquí y más detalle sobre el concepto aquí (lectura
rápida).
1. Nuestra práctica será publicada en un servidor en La Nube, con acceso mediante
una IP pública, pueden utilizar los servicios de AWS, Digital Ocean y Azure.
