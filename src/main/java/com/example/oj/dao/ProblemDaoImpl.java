package com.example.oj.dao;

import com.example.oj.model.Problem;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProblemDaoImpl extends JdbcDaoSupport implements ProblemDao {

    private final DataSource dataSource;

    public ProblemDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    private void init() {
        setDataSource(dataSource);
    }

    private final RowMapper<Problem> rowMapper = new RowMapper<>() {
        public Problem mapRow(ResultSet rs, int rowNum) throws SQLException {
            Problem p = new Problem();
            p.setId(rs.getInt("id"));
            p.setTitle(rs.getString("title"));
            p.setDescription(rs.getString("description"));
            p.setTimeLimit(rs.getInt("time_limit"));
            p.setMemoryLimit(rs.getInt("memory_limit"));
            p.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            return p;
        }
    };

    @Override
    public void save(Problem p) {
        String sql = "INSERT INTO problems (title, description, time_limit, memory_limit) VALUES (?, ?, ?, ?)";
        getJdbcTemplate().update(sql, p.getTitle(), p.getDescription(), p.getTimeLimit(), p.getMemoryLimit());
    }

    @Override
    public List<Problem> findAll() {
        return getJdbcTemplate().query("SELECT * FROM problems ORDER BY id", rowMapper);
    }

    @Override
    public Problem findById(int id) {
        return getJdbcTemplate().queryForObject("SELECT * FROM problems WHERE id = ?", rowMapper, id);
    }
}
