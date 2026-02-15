package com.sistema.app;
import java.sql.Connection;
import java.sql.SQLException;
import com.sistema.config.Conexion;

public class TestConexion {

    public static void main(String[] args) {

        Connection conn = null;

        try {
            conn = Conexion.getConnection();
            System.out.println("✅ Conexión exitosa a la base de datos");

        } catch (SQLException e) {
            System.out.println("❌ Error al conectar con la base de datos");
            e.printStackTrace();

        } finally {
            try {
                if (conn != null) {
                    conn.close();
                    System.out.println("🔒 Conexión cerrada correctamente");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}