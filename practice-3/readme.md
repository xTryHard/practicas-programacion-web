# Carrito de Compras - Práctica 2

## Desarrollo

Para tener una manejo completo del proceso de petición y respuesta del protocolo HTTP 
e integrándolo con las tecnología de HTML, CSS y JS, vamos a implementar una 
aplicación que simule un Carro de Compras utilizando el contexto de sesión y aplicación
para almacenar os productos y la selección por parte de los clientes. La aplicación debe
implementar los siguientes requerimientos:

### Requerimientos

1. Debe estar disponible una vista para gestionar (crear, visualizar, actualizar y borrar)
los productos que serán disponible en el sistema. Dicha vista debe ser accesible
para el usuario administrador.Crear un usuario admin con contraseña admin por
defecto.
1. Los usuarios se estarán almacenando en una colección visible para todo el
sistema.
1. Los productos se estarán almacenando en una colección visible para todo el
sistema.
1. Los usuarios no autenticados, es decir, los usuarios directos, tendrán acceso a la
lista de producto como pagina principal para seleccionar e indicar la cantidad y el
productos que estarán comprando.
1. Los productos y la cantidad seleccionada deben ser agregados y almacenados en
una colección que representa el carro de compras en el contexto de sesión.
1. Debe visualizarse la cantidad de elementos que el usuario tiene agregado en el
carro de compras en todo momento en la vista.
1. Debe existir una vista donde se muestren los productos seleccionado con su
cantidad y precio, presentar el total y un botón para Procesar Compra.
1. En la vista para visualizar el carro de compra, debe existir la funcionalidad de
eliminar un elemento.
1. Procesar Compra limpia la colección almacenada en la sesión y lo guarda en una
colección que incluye el nombre del cliente.
1. Debe existir una vista para visualizar todas las compras realizadas. El acceso es
exclusivo para los administradores.
1. Aplicación debe estar desarrollada utilizando el framework Javalin.
1. Deben implementar un sistema de plantilla responsiva (Bootstrap o Material).

