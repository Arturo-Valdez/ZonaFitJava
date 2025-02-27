package zona_fit.presentacion;

import zona_fit.datos.ClienteDAO;
import zona_fit.datos.IClienteDAO;
import zona_fit.dominio.Cliente;

import java.util.Scanner;

public class AppZonaFit {
    public static void main(String[] args) {
        zonaFitApp();
    }

    private static void zonaFitApp() {
        Scanner consola = new Scanner(System.in);
        var salir = false;

        //Creanis un objeto de la clase clienteDao
        IClienteDAO clienteDao = new ClienteDAO();

        while (!salir) {
            try {
                var opcion = mostrarMenu(consola);
                salir = ejecuarOpciones(consola, opcion, clienteDao);



            } catch (Exception e) {
                System.out.println("Operacion incorrecta, seleccione correspondiente al menu: " + e.getMessage());
            }
            System.out.println();

        }
    }

    private static int mostrarMenu(Scanner consola) {
        System.out.print("""
                        *** Bienvenidos a Zona Fit (GYM) ***
                        - MENU -:
                        1. Listar Clientes
                        2. Buscar Cliente
                        3. Agregar Cliente
                        4. Modificar Cliente
                        5. Eliminar Cliente
                        6. Salir
                    
                        Seleccione la operacion que desea aplicar:\s""");
        var operacion = Integer.parseInt(consola.nextLine());
        return operacion;
    }

    private static boolean ejecuarOpciones(Scanner consola, int opcion, IClienteDAO clienteDao) {
        var salir = false;
        switch (opcion){
            case 1 ->{ // 1. Listar clientes
                System.out.println("\n--- Listado de Clientes ---");
                var clientes = clienteDao.listarClientes();
                clientes.forEach(System.out::println);
            }
            case 2 -> {// 2. Buscar cliente por id
                System.out.print("Introduce el id del Cliente a buscar: ");
                var idCliente = Integer.parseInt(consola.nextLine());
                var cliente = new Cliente(idCliente);
                var encontrado = clienteDao.buscarClientePorId(cliente);
                if(encontrado){
                    System.out.println("Cliente encontrado: " + cliente);
                }else{
                    System.out.println("Cliente NO encontrado: " + cliente);
                }
            }
            case 3 -> {// Agregar cliente
                System.out.println("--- Agregar Cliente ---");
                System.out.print("Nombre: ");
                var nombre = consola.nextLine();
                System.out.print("Apellido: ");
                var apellido = consola.nextLine();
                System.out.print("Membresia: ");
                var membresia = Integer.parseInt(consola.nextLine());
                //Creamos el objeto cliente (sin el id ya que es incrementable)
                var cliente = new Cliente(nombre, apellido, membresia);
                var agregado = clienteDao.agregarCliente(cliente);
                if(agregado){
                    System.out.println("Cliente agregado correctamente: " + cliente);
                }else{
                    System.out.println("Fallo al agregar Cliente: " + cliente);
                }
            }
        }
        return salir;
    }
}

