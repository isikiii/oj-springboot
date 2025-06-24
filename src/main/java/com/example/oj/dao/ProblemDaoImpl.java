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

    private final RowMapper<Problem> rowMapper = (rs, rowNum) -> {
        Problem p = new Problem();
        p.setId(rs.getInt("id"));
        p.setTitle(rs.getString("title"));
        p.setDescription(rs.getString("description"));
        p.setTimeLimit(rs.getInt("time_limit"));
        p.setMemoryLimit(rs.getInt("memory_limit"));
        p.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        p.setInput(rs.getString("input"));
        p.setOutput(rs.getString("output"));
        return p;
    };

    private final DataSource dataSource;

    public ProblemDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    private void init() {
        setDataSource(dataSource);
    }

    @Override
    public void save(Problem p) {
        String sql = "INSERT INTO problems (title, description, time_limit, memory_limit) VALUES (?, ?, ?, ?)";
        getJdbcTemplate().update(sql, p.getTitle(), p.getDescription(), p.getTimeLimit(), p.getMemoryLimit());
    }

    @Override
    public List<Problem> findAll() {
        String sql = "SELECT * FROM problems ORDER BY id";
        return getJdbcTemplate().query(sql, rowMapper);
    }

    @Override
    public Problem findById(int id) {
        String sql = "SELECT * FROM problems WHERE id = ?";
        return getJdbcTemplate().queryForObject(sql, rowMapper, id);
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM problems WHERE id = ?";
        getJdbcTemplate().update(sql, id);
    }

    @Override
    public void update(Problem p) {
        String sql = "UPDATE problems SET title = ?, description = ?, time_limit = ?, memory_limit = ? WHERE id = ?";
        getJdbcTemplate().update(sql,
                p.getTitle(), p.getDescription(), p.getTimeLimit(), p.getMemoryLimit(), p.getId());
    }

    @Override
    public List<Problem> findPage(int offset, int size) {
        String sql = "SELECT * FROM problems ORDER BY id LIMIT ? OFFSET ?";
        return getJdbcTemplate().query(sql, rowMapper, size, offset);
    }
}
