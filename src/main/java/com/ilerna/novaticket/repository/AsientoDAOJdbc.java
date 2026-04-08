package com.ilerna.novaticket.repository;

import com.ilerna.novaticket.connection.Conexion;
import com.ilerna.novaticket.model.Asiento;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Qualifier("asientoDAOJdbc")
public class AsientoDAOJdbc implements AsientoDAO{

    private Connection getConnection() {
        return Conexion.getInstancia().getConnection();
    }

    @Override
    public void guardar(Asiento asiento) {
        Connection conn = getConnection();
        if (conn == null) {
            System.err.println("No se pudo obtener conexion a la base de datos.");
            return;
        }

        String sql = "INSERT INTO asiento (id_lugar, fila, numero_asiento, zona) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, asiento.getId_lugar());
            pstmt.setString(2, asiento.getFila());
            pstmt.setInt(3, asiento.getNumero_asiento());
            pstmt.setString(4, asiento.getZona());

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        asiento.setId(rs.getInt(1));
                    }
                }
                System.out.println("Asiento guardado correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar el asiento.");
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Asiento asiento) {

        Connection conn = getConnection();
        if (conn == null) {
            System.err.println("❌ No se pudo obtener conexión a la base de datos.");
            return;
        }

        String sql = "UPDATE asiento SET id_lugar = ?, fila = ?, numero_asiento = ?, zona = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, asiento.getId_lugar());
            pstmt.setString(2, asiento.getFila());
            pstmt.setInt(3, asiento.getNumero_asiento());
            pstmt.setString(4, asiento.getZona());
            pstmt.setInt(5, asiento.getId());

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Asiento actualizado correctamente.");
            } else {
                System.err.println("❌ No se encontró el asiento para actualizar.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar el asiento.");
            e.printStackTrace();
        }

    }

    @Override
    public void eliminar(int id) {

        Connection conn = getConnection();
        if (conn == null) {
            System.err.println("❌ No se pudo obtener conexión a la base de datos.");
            return;
        }

        String sql = "DELETE FROM asiento WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Evento eliminado correctamente.");
            } else {
                System.err.println("❌ No se encontró el evento para eliminar.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar el evento.");
            e.printStackTrace();
        }

    }

    @Override
    public Asiento obtenerPorId(int id) {

        Asiento asiento = null;
        String sql = "SELECT * FROM asiento WHERE id = ?";

        Connection conn = getConnection();
        if (conn == null) {
            System.err.println("❌ No se pudo obtener conexión a la base de datos.");
            return asiento;
        }

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    asiento = mapearAsiento(rs);
                }
            }
            System.out.println("✅ Asiento obtenido correctamente.");
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener el asiento.");
            e.printStackTrace();
        }
        return asiento;
    }

    @Override
    public List<Asiento> listarTodos() {

        List<Asiento> asientos = new ArrayList<>();
        String sql = "SELECT * FROM asiento";

        Connection conn = getConnection();
        if (conn == null) {
            System.err.println("❌ No se pudo obtener conexión a la base de datos.");
            return asientos;
        }

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Asiento asiento = mapearAsiento(rs);
                asientos.add(asiento);
            }
            System.out.println("✅ Listado de asientos recuperado correctamente.");
        } catch (SQLException e) {
            System.err.println("❌ Error al listar los asientos.");
            e.printStackTrace();
        }
        return asientos;
    }



    private Asiento mapearAsiento(ResultSet rs) throws SQLException {
        Asiento asiento = new Asiento();
        asiento.setId(rs.getInt("id"));
        asiento.setId_lugar(rs.getInt("id_lugar"));
        asiento.setFila(rs.getString("fila"));
        asiento.setNumero_asiento(rs.getInt("numero_asiento"));
        asiento.setZona(rs.getString("zona"));
        return asiento;
    }

}
