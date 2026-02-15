package com.sistema.app;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.sistema.config.Conexion;

public class TestUsuarioSelect {

    public static void main(String[] args) {

        String sql = "SELECT id_usuario, nombre, apellido, email FROM usuario";

        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("📋 Lista de usuarios:");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id_usuario") + " | " +
                        rs.getString("nombre") + " | " +
                        rs.getString("apellido") + " | " +
                        rs.getString("email")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}