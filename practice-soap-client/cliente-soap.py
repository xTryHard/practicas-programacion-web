from zeep import Client
from tabulate import tabulate

client = Client('http://localhost:7000/ws/EstudianteWebServices?wsdl')

while True:
  print('\nElige una opción')
  print('1. Listar todos los estudiantes')
  print('2. Busca un estudiante por matrícula')
  print('3. Crear estudiante')
  print('4. Actualizar estudiante')
  print('5. Eliminar estudiante')
  print('6. Salir\n')

  option = input('Opción: ')
  if (option == '1'):
    students = client.service.getListaEstudiante()
    all_data = []
    for student in students:
      data = []
      data.append(student.matricula)
      data.append(student.nombre)
      data.append(student.carrera)
      all_data.append(data)
    print("\nLista estudiantes:")
    print(tabulate(all_data, headers=["Matricula","Nombre", "Carrera"]))
  elif (option == '2'):
    id = input('Ingrese la matrícula: ')

    try:
      id =  int(id)
      student = client.service.getEstudiante(id)
      if (student is None):
        print('\nNingún resultado obtenido')
      else:
        print("\n Estudiante:")
        all_data = []
        data = []
        data.append(student.matricula)
        data.append(student.nombre)
        data.append(student.carrera)
        all_data.append(data)
        print(tabulate(all_data, headers=["Matricula","Nombre", "Carrera"]))
    except ValueError:
      print('Matrícula debe ser numérica')
    
  elif (option == '3'):
    try:
      id = int(input('Ingrese matrícula: '))
      name = input('Ingrese nombre: ')
      career = input('Ingrese carrera: ')
      estudiante = {'matricula': id, 'nombre': name, 'carrera':career}
      result = client.service.crearEstudiante(estudiante)
      if (result):
        print('\nEstudiante creado correctamente')
      else:
        print('\nError al crear estudiante')
    except ValueError:
      print('\nMatrícula debe ser numérica')
    
  elif (option == '4'):
    try:
      id = int(input('Ingrese matrícula estudiante a editar: '))
      name = input('Ingrese nuevo nombre: ')
      career = input('Ingrese nueva carrera: ')
      estudiante = {'matricula': id, 'nombre': name, 'carrera':career}
      result = client.service.actualizarEstudiante(estudiante)
      if (result):
        print('\nEstudiante editado correctamente')
      else:
        print('\nError al editar estudiante')
    except:
      print('\nMatrícula debe ser numérica o usuario no existe')
  elif (option == '5'):
    try:
      id = int(input('Ingrese matrícula estudiante a eliminar: '))
      result = client.service.eliminarEstudiante(id)

      if(result):
        print('\nEstudiante eliminado correctamente')
      else:
        print('\nError al eliminar estudiante')
    except ValueError:
      print('\nMatrícula debe ser numérica.')
  elif (option == '6'):
    break
  else:
    print('No existe el servicio')

print('\n¡Hasta luego!')