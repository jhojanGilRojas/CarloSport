package db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import oracle.jdbc.internal.OracleTypes;
import java.sql.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;

public class Oracle {

    public static void cambiarEstadoDespacho(int idDespacho, String nuevoEstado) throws SQLException {

        Connection connection = null;
        CallableStatement statement = null;

        try {
            connection = ConexionBD.getInstance().getConnection();

            String sql = "{ call cambiar_estado_despacho(?, ?) }";
            statement = connection.prepareCall(sql);

            statement.setInt(1, idDespacho);
            statement.setString(2, nuevoEstado);


            statement.execute();

            System.out.println("El estado del despacho con ID " + idDespacho + " ha sido cambiado a " + nuevoEstado + " exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al cambiar el estado del despacho.");
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static String[] Login(String user, String password)throws SQLException {

        Connection connection = null;
        CallableStatement statement = null;

        try {
            connection = ConexionBD.getInstance().getConnection();


            String sql = "{? = call login_usuario(?, ?)}";
            statement = connection.prepareCall(sql);
            statement.registerOutParameter(1, Types.VARCHAR);
            statement.setString(2, user);
            statement.setString(3, password);

            statement.execute();

            String result = statement.getString(1);

            if (result != null) {
                String[] usuarioData = result.split(",");
                return usuarioData;
            }
        } catch (SQLException e) {
            System.out.println("El usuario no existe");
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static ObservableList<String[]> obtenerTodasVentas()throws SQLException {
        ObservableList<String[]> data = FXCollections.observableArrayList();

        String sql = "{? = call obtener_ventas_productos()}";

        try (Connection connection = ConexionBD.getInstance().getConnection();
             CallableStatement statement = connection.prepareCall(sql)) {

            // Registrar el parámetro de salida como un cursor
            statement.registerOutParameter(1, Types.REF_CURSOR);

            // Ejecutar la función
            statement.execute();

            // Obtener el cursor
            ResultSet resultSet = (ResultSet) statement.getObject(1);

            while (resultSet.next()) {
                String[] row = {
                        resultSet.getString("id_venta"),
                        resultSet.getString("fecha_venta"),
                        resultSet.getString("total_venta"),
                        resultSet.getString("estado"),
                        resultSet.getString("productos")
                };
                data.add(row);
            }

            return data;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static ObservableList<String[]> obtenerVentasUsuario(int idAfiliado)throws SQLException {
        ObservableList<String[]> data = FXCollections.observableArrayList();
        System.out.println(idAfiliado);
        String sql = "{? = call obtener_ventas_por_usuario(?)}";

        try (Connection connection = ConexionBD.getInstance().getConnection();
             CallableStatement statement = connection.prepareCall(sql)) {

            statement.registerOutParameter(1, OracleTypes.CURSOR);
            statement.setInt(2, idAfiliado);
            statement.execute();
            ResultSet resultSet = (ResultSet) statement.getObject(1);

            while (resultSet.next()) {
                String[] row = {
                        resultSet.getString("id_venta"),
                        resultSet.getDate("fecha_venta").toString(),
                        resultSet.getString("total_venta"),
                        resultSet.getString("estado"),
                        resultSet.getString("productos")
                };
                data.add(row);

            }

            return data;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("error aqui"+idAfiliado);
        return null;
    }


    public static void crearVenta(String idSeleccion, ObservableList<String[]> productos) throws SQLException {
        // Crear un Map para consolidar productos únicos
        Map<String, String[]> productosUnicos = new HashMap<>();
        idSeleccion = String.valueOf(2);  // Este valor debe ser dinámico según la selección del usuario

        // Consolidar productos
        for (String[] producto : productos) {
            String idProducto = producto[0]; // ID del producto
            int cantidad = Integer.parseInt(producto[5]); // Cantidad
            double precioUnitario = Double.parseDouble(producto[3]); // Precio unitario

            if (productosUnicos.containsKey(idProducto)) {
                String[] productoExistente = productosUnicos.get(idProducto);
                int cantidadExistente = Integer.parseInt(productoExistente[1]);
                productoExistente[1] = String.valueOf(cantidadExistente + cantidad); // Actualizar cantidad
            } else {
                productosUnicos.put(idProducto, new String[]{idProducto, String.valueOf(cantidad), String.valueOf(precioUnitario)});
            }
        }

        // Convertir el Map a una lista
        List<String[]> productosFinal = new ArrayList<>(productosUnicos.values());

        // Preparar la conexión
        Connection connection = null;
        CallableStatement statement = null;
        ResultSet productosCursor = null;

        try {
            connection = ConexionBD.getInstance().getConnection();
            connection.setAutoCommit(false);  // Desactivar auto-commit para controlar manualmente la transacción

            // Paso 1: Crear la venta
            String crearVentaSql = "BEGIN crear_venta(?); END;";
            statement = connection.prepareCall(crearVentaSql);
            statement.setInt(1, Integer.parseInt(idSeleccion)); // ID del afiliado
            statement.execute();

            // Obtener el id de la venta generada
            String obtenerIdVentaSql = "SELECT venta_seq.CURRVAL AS id_venta FROM dual";
            PreparedStatement ps = connection.prepareStatement(obtenerIdVentaSql);
            ResultSet rs = ps.executeQuery();
            int idVenta = 0;
            if (rs.next()) {
                idVenta = rs.getInt("id_venta");
            }

            // Paso 2: Agregar los productos a la venta
            for (String[] producto : productosFinal) {
                String idProducto = producto[0];
                int cantidad = Integer.parseInt(producto[1]);
                double precioUnitario = Double.parseDouble(producto[2]);

                String agregarProductoSql = "BEGIN agregar_productos(?, ?, ?, ?); END;";
                statement = connection.prepareCall(agregarProductoSql);
                statement.setInt(1, idVenta);  // ID de la venta
                statement.setInt(2, Integer.parseInt(idProducto));  // ID del producto
                statement.setInt(3, cantidad);  // Cantidad
                statement.setDouble(4, precioUnitario);  // Precio unitario
                statement.execute();
            }

            // Confirmar la transacción
            connection.commit();
            System.out.println("Venta creada y productos agregados exitosamente.");

        } catch (SQLException e) {
            // Manejo de excepciones
            if (connection != null) {
                connection.rollback();  // Revertir cambios en caso de error
            }
            e.printStackTrace();
        } finally {
            // Asegurarse de cerrar todos los recursos
            try {
                if (productosCursor != null) productosCursor.close();
                if (statement != null) statement.close();
                if (connection != null) {
                    connection.setAutoCommit(true); // Restaurar auto-commit
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }


    public static ObservableList<String[]> obtenerProductoId(int ventaSeleccionada){

        ObservableList<String[]> productos = FXCollections.observableArrayList();
        String sql = "{? = call obtener_productos_por_venta(?)}";

        try (Connection connection = ConexionBD.getInstance().getConnection();
             CallableStatement statement = connection.prepareCall(sql)) {

            statement.registerOutParameter(1, OracleTypes.CURSOR);
            statement.setInt(2, ventaSeleccionada);
            statement.execute();

            ResultSet resultSet = (ResultSet) statement.getObject(1);

            while (resultSet.next()) {
                String[] producto = {
                        resultSet.getString("id_producto"),
                        resultSet.getString("nombre"),  // Nombre del producto
                        resultSet.getString("descripcion"),  // Descripción del producto
                        resultSet.getString("precio"),
                        resultSet.getString("stock"),
                        resultSet.getString("cantidad")
                };
                productos.add(producto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }

    public static ObservableList<String[]> obtenerAllProductos() {

        ObservableList<String[]> productos = FXCollections.observableArrayList();
        String sql = "{? = call obtener_all_productos()}";
        try (Connection connection = ConexionBD.getInstance().getConnection();
             CallableStatement statement = connection.prepareCall(sql)) {

            statement.registerOutParameter(1, OracleTypes.CURSOR);
            statement.execute();
            ResultSet resultSet = (ResultSet) statement.getObject(1);

            while (resultSet.next()) {
                String[] producto = {
                        resultSet.getString("id_producto"),
                        resultSet.getString("nombre"),
                        resultSet.getString("descripcion"),
                        resultSet.getString("precio"),
                        resultSet.getString("stock"),
                        "0"
                };
                productos.add(producto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

}
