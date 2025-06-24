package com.example.oj.dao;

import com.example.oj.model.Submission;

import java.util.List;

public interface SubmissionDao {
    void save(Submission submission);
    List<Submission> findAllByUsername(String username);
}

@Override
public List<Submission> findPending() {
    String sql = "SELECT * FROM submissions WHERE status = 'Pending'";
    return getJdbcTemplate().query(sql, new SubmissionRowMapper());
}

@Override
public void updateStatus(int id, String status) {
    String sql = "UPDATE submissions SET status = ? WHERE id = ?";
    getJdbcTemplate().update(sql, status, id);
}
