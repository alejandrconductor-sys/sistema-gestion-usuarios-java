package com.sistema.app;
import java.sql.Connection;
import java.sql.SQLException;
import com.sistema.config.Conexion;

public class TestConexion {

    public static void main(String[] args) {

        try (Connection conn = Conexion.getConnection()) {

            System.out.println("✅ Conexión exitosa a la base de datos");

        } catch (SQLException e) {

            System.out.println("❌ Error al conectar con la base de datos");
            e.printStackTrace();
        }
    }
}