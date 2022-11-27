package br.ufpb.dcx.apps4society.qtarolando.api.util;

import br.ufpb.dcx.apps4society.qtarolando.api.model.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class EventDAO {

    public static void inserir(Event event) {
        String sql = "INSERT INTO event (event_id, title, subtitle, category_id, description, initial_date, final_date, image_path, " +
                "event_modality_id, location, phone, site ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

        Timestamp initialDateConverted = Timestamp.valueOf(event.getInitialDate());
        Timestamp finalDateConverted = Timestamp.valueOf(event.getFinalDate());

        try (Connection con = Conexao.getConexao()){
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, event.getId());
            ps.setString(2, event.getTitle());
            ps.setString(3, event.getSubtitle());
            ps.setInt(4, event.getCategoryId());
            ps.setString(5, event.getDescription());
            ps.setTimestamp(6, initialDateConverted);
            ps.setTimestamp(7, finalDateConverted);
            ps.setString(8, event.getImagePath());
            ps.setInt(9, event.getEventModalityId());
            ps.setString(10, event.getLocation());
            ps.setString(11, event.getPhone());
            ps.setString(12, event.getSite());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
