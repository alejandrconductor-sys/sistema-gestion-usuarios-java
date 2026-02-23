package com.sistema.config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL = "jdbc:mariadb://localhost:3306/sistema_gestion";
    private static final String USER = "SistemaRRHH";
    private static final String PASSWORD = "Rrhh12345";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}