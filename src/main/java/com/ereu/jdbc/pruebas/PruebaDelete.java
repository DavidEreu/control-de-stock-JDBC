package com.ereu.jdbc.pruebas;

import com.ereu.jdbc.factory.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class PruebaDelete {

    public static void main(String[] args) throws SQLException {

        Connection con = new ConnectionFactory().recuperaConexion();

        Statement statement = con.createStatement();

        statement.execute("DELETE FROM  PRODUCTO WHERE ID = 99" );

        System.out.println(statement.getUpdateCount());

    }
}
