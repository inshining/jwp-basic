package next.dao;

import core.jdbc.ConnectionManager;
import next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class SelectJdbcTemplate {

    public <T> List<T> query(String sql) throws SQLException {

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);

            rs = pstmt.executeQuery();

            List<T> users = new ArrayList<T>();
            while (rs.next()) {
                users.add((T) mapRow(rs));
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


    public Object queryForObject(String sql) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            setValues(pstmt);
            rs = pstmt.executeQuery();
            User user = null;
            if (rs.next()) {
                user = (User) mapRow(rs);
            }

            return user;
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
    public abstract void setValues(PreparedStatement preparedStatement) throws SQLException;
    public abstract Object mapRow(ResultSet rs) throws SQLException;
}
