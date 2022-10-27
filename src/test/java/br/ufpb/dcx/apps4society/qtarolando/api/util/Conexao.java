package br.ufpb.dcx.apps4society.qtarolando.api.util;

import java.sql.*;

public class Conexao {

    public static Connection getConexao(){
        String url = "jdbc:postgresql://localhost:5441/qtarolando";
        String user = "postgres";
        String password = "postgres";

        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void close(Connection connection){
        try {
            if(connection != null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(Connection connection, Statement stnt){
        close(connection);
        try {
            if(stnt != null)
                stnt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void close(Connection connection, Statement stnt, ResultSet rs){
        close(connection, stnt);
        try {
            if(rs != null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
