package next.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import next.model.User;

public class UserDao {
    public void insert(User user) throws SQLException {
        JdbcTemplate insertJdbcTemplate = new JdbcTemplate() {

            @Override
            public void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getUserId());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getName());
                pstmt.setString(4, user.getEmail());
                pstmt.executeUpdate();
            }
        };
        insertJdbcTemplate.update("INSERT INTO USERS VALUES (?, ?, ?, ?)");
    }

    public void update(User user) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {
            @Override
            public void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getPassword());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getEmail());
                pstmt.setString(4, user.getUserId());
                pstmt.executeUpdate();
            }
        };
        jdbcTemplate.update("UPDATE USERS SET password=?, name=?, email=? WHERE userid=?");
    }

    public List<User> findAll() throws SQLException {

        SelectJdbcTemplate selectJdbcTemplate = new SelectJdbcTemplate() {
            @Override
            public void setValues(PreparedStatement preparedStatement) {

            }

            @Override
            public Object mapRow(ResultSet rs) throws SQLException {
                return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"), rs.getString("email"));
            }
        };
        return selectJdbcTemplate.query("SELECT userId, password, name, email FROM USERS");

    }

    public User findByUserId(String userId) throws SQLException {
        SelectJdbcTemplate selectJdbcTemplate = new SelectJdbcTemplate() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, userId);

            }

            @Override
            public Object mapRow(ResultSet rs) throws SQLException {
                return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"), rs.getString("email"));
            }
        };
        return (User) selectJdbcTemplate.queryForObject("SELECT userId, password, name, email FROM USERS WHERE userid=?");
    }
}
