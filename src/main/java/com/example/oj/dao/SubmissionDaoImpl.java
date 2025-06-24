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

    private final DataSource dataSource;

    public SubmissionDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    private void init() {
        setDataSource(dataSource);
    }

    @Override
    public void save(Submission submission) {
        String sql = "INSERT INTO submissions (username, problem_id, language, code, status) VALUES (?, ?, ?, ?, ?)";
        getJdbcTemplate().update(sql,
                submission.getUsername(),
                submission.getProblemId(),
                submission.getLanguage(),
                submission.getCode(),
                submission.getStatus());
    }

    @Override
    public List<Submission> findAllByUsername(String username) {
        String sql = "SELECT * FROM submissions WHERE username = ? ORDER BY submit_time DESC";
        return getJdbcTemplate().query(sql, new SubmissionRowMapper(), username);
    }

    private static class SubmissionRowMapper implements RowMapper<Submission> {
        @Override
        public Submission mapRow(ResultSet rs, int rowNum) throws SQLException {
            Submission s = new Submission();
            s.setId(rs.getInt("id"));
            s.setUsername(rs.getString("username"));
            s.setProblemId(rs.getInt("problem_id"));
            s.setLanguage(rs.getString("language"));
            s.setCode(rs.getString("code"));
            s.setStatus(rs.getString("status"));
            s.setSubmitTime(rs.getTimestamp("submit_time").toLocalDateTime());
            return s;
        }
    }
}
