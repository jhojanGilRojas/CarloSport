package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static ConexionBD instancia;
    private String url = "jdbc:oracle:thin:@localhost:1521:XE";
    private String usuario = "CARLO";
    private String contraseña = "0223";

    private ConexionBD() {
        // Constructor privado para evitar instanciación
    }

    public static ConexionBD getInstance() {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    public Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            // Registra el driver de Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Establece la conexión
            connection = DriverManager.getConnection(url, usuario, contraseña);
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se pudo cargar el driver de Oracle");
            e.printStackTrace();
        }
        return connection;
    }
}
