package com.ereu.jdbc.factory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactory {

    private DataSource dataSource;

    public ConnectionFactory(){
        var pooledDataSource = new ComboPooledDataSource();

        pooledDataSource.setJdbcUrl("jdbc:mysql://localhost/control_de_stock?useTimeZone=true&sercerTimeZone=UTC");
        pooledDataSource.setUser("root");
        pooledDataSource.setPassword("123456");
        pooledDataSource.setMaxPoolSize(10);

        this.dataSource=pooledDataSource;
    }
    public Connection recuperaConexion() {
        try {
            return this.dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        /*
        return DriverManager.getConnection(
                "jdbc:mysql://localhost/control_de_stock?useTimeZone=true&sercerTimeZone=UTC",
                "root",
                "123456"
        );
         */
    }
}
