package com.ereu.jdbc.dao;

import com.ereu.jdbc.modelo.Categoria;
import com.ereu.jdbc.modelo.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    private Connection con;
    public CategoriaDAO(Connection con) {
        this.con=con;
    }

    public List<Categoria> listar() {
        List<Categoria> resultado =  new ArrayList<>();
        try {
            var querySelect = "SELECT id,nombre FROM categoria";
            System.out.println(querySelect);

            final PreparedStatement statement =  con.prepareStatement(querySelect);

            try (statement){

                final ResultSet resultSet = statement.executeQuery();

                try(resultSet){
                    while (resultSet.next()){
                        var categoria = new Categoria(resultSet.getInt("id"),resultSet.getString("nombre"));

                        resultado.add(categoria);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }


    public List<Categoria> listarConProductos() {
        List<Categoria> resultado = new ArrayList<>();

        try {
            String sql = "SELECT C.ID, C.NOMBRE, P.ID, P.NOMBRE, P.CANTIDAD "
                    + " FROM CATEGORIA C INNER JOIN PRODUCTO P "
                    + " ON C.ID = P.CATEGORIA_ID";

            System.out.println(sql);

            final PreparedStatement statement = con
                    .prepareStatement(sql);

            try (statement) {
                final ResultSet resultSet = statement.executeQuery();

                try (resultSet) {
                    while (resultSet.next()) {
                        int categoriaId = resultSet.getInt("C.ID");
                        String categoriaNombre = resultSet.getString("C.NOMBRE");

                        Categoria categoria = resultado
                                .stream()
                                .filter(cat -> cat.getId().equals(categoriaId))
                                .findAny().orElseGet(() -> {
                                    Categoria cat = new Categoria(
                                            categoriaId, categoriaNombre);
                                    resultado.add(cat);

                                    return cat;
                                });

                        Producto producto = new Producto(
                                resultSet.getInt("P.ID"),
                                resultSet.getString("P.NOMBRE"),
                                resultSet.getInt("P.CANTIDAD"));

                        categoria.agregar(producto);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultado;
    }
}
