package com.example.oj.dao;

import com.example.oj.model.Submission;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SubmissionDaoImpl extends JdbcDaoSupport implements SubmissionDao {

    private final RowMapper<Submission> rowMapper = (rs, rowNum) -> {
        Submission s = new Submission();
        s.setId(rs.getInt("id"));
        s.setUsername(rs.getString("username"));
        s.setProblemId(rs.getInt("problem_id"));
        s.setLanguage(rs.getString("language"));
        s.setCode(rs.getString("code"));
        s.setStatus(rs.getString("status"));
        s.setSubmitTime(rs.getTimestamp("submit_time").toLocalDateTime());
        return s;
    };

    private final DataSource dataSource;

    public SubmissionDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    private void init() {
        setDataSource(dataSource);
    }

    @Override
    public void save(Submission s) {
        String sql = "INSERT INTO submissions (username, problem_id, language, code, status) VALUES (?, ?, ?, ?, ?)";
        getJdbcTemplate().update(sql,
                s.getUsername(), s.getProblemId(), s.getLanguage(), s.getCode(), s.getStatus());
    }

    @Override
    public List<Submission> findAllByUsername(String username) {
        String sql = "SELECT * FROM submissions WHERE username = ? ORDER BY id DESC";
        return getJdbcTemplate().query(sql, rowMapper, username);
    }

    @Override
    public List<Submission> findPending() {
        String sql = "SELECT * FROM submissions WHERE status = 'PENDING' ORDER BY submit_time";
        return getJdbcTemplate().query(sql, rowMapper);
    }

    @Override
    public void updateStatus(int id, String status) {
        String sql = "UPDATE submissions SET status = ? WHERE id = ?";
        getJdbcTemplate().update(sql, status, id);
    }
}
