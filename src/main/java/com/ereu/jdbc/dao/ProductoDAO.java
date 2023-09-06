package com.ereu.jdbc.dao;

import com.ereu.jdbc.factory.ConnectionFactory;
import com.ereu.jdbc.modelo.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ProductoDAO {

    final private Connection con;

    public ProductoDAO(Connection con){
        this.con = con;
    }

    public  void guardar(Producto producto)  {

        try(con){

            PreparedStatement statement = con.prepareStatement("INSERT INTO PRODUCTO(nombre,descripcion,cantidad,categoria_id)"
                            + "VALUES(?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            try(statement){
                statement.setString(1, producto.getNombre());
                statement.setString(2, producto.getDescripcion());
                statement.setInt(3, producto.getCantidad());
                statement.setInt(4, producto.getCategoriaId());
                statement.execute();

                final ResultSet resultSet = statement.getGeneratedKeys();

                try (resultSet){
                    while (resultSet.next()) {
                        producto.setId(resultSet.getInt(1));
                        System.out.println(String.format
                                ("Fue insertado el producto de %s ", producto));
                    }
                }
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Producto> listar(){
        List<Producto> resultado = new ArrayList<>();
        final Connection con = new ConnectionFactory().recuperaConexion();

        try (con) {

            final PreparedStatement statement = con.prepareStatement("SELECT ID,NOMBRE,DESCRIPCION,CANTIDAD FROM PRODUCTO");

            try(statement) {
                statement.execute();
                final ResultSet resultSet = statement.getResultSet();

                try (resultSet){
                    while (resultSet.next()) {
                        Producto fila = new Producto(resultSet.getInt("ID"),
                                resultSet.getString("Nombre"),
                                resultSet.getString("DESCRIPCION"),
                                resultSet.getInt("CANTIDAD"));

                        resultado.add(fila);
                    }
                }
            }
            return resultado;
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }

    public int eliminar(Integer id) {
        try {
            final PreparedStatement statement = con.prepareStatement("DELETE FROM PRODUCTO WHERE ID = ?");

            try (statement) {
                statement.setInt(1, id);
                statement.execute();

                int updateCount = statement.getUpdateCount();

                return updateCount;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) {
        try {
            final PreparedStatement statement = con.prepareStatement(
                    "UPDATE PRODUCTO SET "
                            + " NOMBRE = ?, "
                            + " DESCRIPCION = ?,"
                            + " CANTIDAD = ?"
                            + " WHERE ID = ?");

            try (statement) {
                statement.setString(1, nombre);
                statement.setString(2, descripcion);
                statement.setInt(3, cantidad);
                statement.setInt(4, id);
                statement.execute();

                int updateCount = statement.getUpdateCount();

                return updateCount;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Producto> listar(Integer categoriaId){
        List<Producto> resultado = new ArrayList<>();
        final Connection con = new ConnectionFactory().recuperaConexion();

        try (con) {
            var querySelect = "SELECT ID,NOMBRE,DESCRIPCION,CANTIDAD "
                    +"FROM PRODUCTO"
                    +" WHERE categoria_id = ?";
            System.out.println(querySelect);

            final PreparedStatement statement = con.prepareStatement(querySelect);

            try(statement) {
                statement.setInt(1,categoriaId);
                statement.execute();
                final ResultSet resultSet = statement.getResultSet();

                try (resultSet){
                    while (resultSet.next()) {
                        Producto fila = new Producto(resultSet.getInt("ID"),
                                resultSet.getString("Nombre"),
                                resultSet.getString("DESCRIPCION"),
                                resultSet.getInt("CANTIDAD"));

                        resultado.add(fila);
                    }
                }
            }
            return resultado;
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }
}
