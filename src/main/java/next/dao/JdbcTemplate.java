package next.dao;

import core.jdbc.ConnectionManager;
import next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {
    public void update(String query, Object... parameter) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = query;
            pstmt = con.prepareStatement(sql);
            for (int i =0; i < parameter.length; i++) {
                pstmt.setObject(i+1, parameter[i]);
            }
            pstmt.executeUpdate();

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }
    }
    public <T> List<T> query(String sql, RowMapper<T> rm) throws SQLException {

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);

            rs = pstmt.executeQuery();

            List<T> users = new ArrayList<T>();
            while (rs.next()) {
                users.add(rm.mapRow(rs));
            }
            return users;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }


    public <T> T queryForObject(String sql, RowMapper<T> rm, Object... parameter) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            for (int i = 0; i < parameter.length; i++) {
                pstmt.setObject(i + 1, parameter[i]);
            }
            rs = pstmt.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return rm.mapRow(rs);

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

}
