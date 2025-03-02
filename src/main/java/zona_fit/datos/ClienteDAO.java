package zona_fit.datos;

import zona_fit.conexion.Conexion;
import zona_fit.dominio.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO implements IClienteDAO{


    @Override
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        PreparedStatement ps;
        //Resive la informacion de la consulta
        ResultSet rs;
        Connection con = Conexion.getConexion();
        var sql = "SELECT * FROM cliente ORDER BY id";
        try{
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                var cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setMembresia(rs.getInt("membresia"));
                clientes.add(cliente);
            }
        } catch (Exception e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
        }finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar conexion: " + e.getMessage());
            }
        }
        return clientes;
    }

    @Override
    public boolean buscarClientePorId(Cliente cliente) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = Conexion.getConexion();
        var sql = "SELECT * FROM cliente WHERE id = ?";
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1, cliente.getId());
            rs = ps.executeQuery();
            if(rs.next()){
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setMembresia(rs.getInt("membresia"));
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error al recuperar cliente por id: " + e.getMessage());
        }finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar conexion");
            }
        }
        return false;
    }

    @Override
    public boolean agregarCliente(Cliente cliente) {
        PreparedStatement ps;
        Connection con = Conexion.getConexion();
        String sql = "INSERT INTO cliente(nombre, apellido, membresia)"
                + " VALUES(?, ?, ?)";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setInt(3, cliente.getMembresia());
            ps.execute();
            return true;

        } catch (Exception e) {
            System.out.println("Error al agregar cliente: " + e.getMessage());
        }finally {
            try{
                con.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar conexion");
            }
        }
        return false;
    }

    @Override
    public boolean modificarCliente(Cliente cliente) {
        PreparedStatement ps;
        Connection con = Conexion.getConexion();
        var sql = "UPDATE cliente Set nombre=?, apellido=?, membresia=? " +
                "WHERE id=?";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setInt(3, cliente.getMembresia());
            ps.setInt(4, cliente.getId());
            ps.execute();
            return true;
        } catch (Exception e) {
            System.out.println("Error al modificar Cliente" + e.getMessage());
        }finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar conexion: " + e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean eliminarCliente(Cliente cliente) {
        PreparedStatement ps;
        Connection con = Conexion.getConexion();
        var sql = "DELETE FROM cliente WHERE id=?";
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1, cliente.getId());
            ps.execute();
            return true;
        } catch (Exception e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
        }finally {
            try{
                con.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar conexion: "+ e.getMessage());
            }
        }
        return false;
    }

    public static void main(String[] args) {
//        //Listar clientes
//        System.out.println("*** Listar clientes*** ");
        IClienteDAO clientesDao = new ClienteDAO();
//        var clientes = clientesDao.listarClientes();
//        clientes.forEach(System.out::println);

        /// //////////////////////////////////////////////////////////////////////////
        //Buscar por id
//        var cliente1 = new Cliente(1);
//        System.out.println("Cliente antes de la busqueda: " + cliente1);
//        var encontrado = clientesDao.buscarClientePorId(cliente1);
//        if(encontrado){
//            System.out.println("Cliente encontrado: " + cliente1);
//        }else{
//            System.out.println("No se encontro cliente: " + cliente1.getId());
//        }

        /// ///////////////////////////////////////////////////////////////////////
//        //Agregar cliente
//        var nuevoCliente = new Cliente("Santiago", "Valdez", 27);
//        var agregado = clientesDao.agregarCliente(nuevoCliente);
//        if(agregado)
//            System.out.println("Cliente agregado: " + nuevoCliente);
//        else
//            System.out.println("No se agrego ciente: " + nuevoCliente);


//        //Modificar cliente
//        var modificarCliente = new Cliente(4, "Santiago Arturo", "Valdez", 27);
//        var modificado = clientesDao.modificarCliente(modificarCliente);
//        if(modificado){
//            System.out.println("Cliente modificado: " + modificado);
//        }else{
//            System.out.println("Error al modificar cliente: " + modificado);
//        }


        //Eliminar cliente
        var eliminaCliente = new Cliente(3);
        var eliminado = clientesDao.eliminarCliente(eliminaCliente);
        if(eliminado){
            System.out.println("Se elimino cliente: " + eliminado);
        }else{
            System.out.println("No se elimino cliente: " + eliminado);
        }

        //Listar clientes
        System.out.println("*** Listar clientes*** ");
        var clientes = clientesDao.listarClientes();
        clientes.forEach(System.out::println);
    }
}
