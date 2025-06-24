package com.example.oj.dao;

import com.example.oj.model.Problem;
import java.util.List;

public interface ProblemDao {
    void save(Problem problem);
    List<Problem> findAll();
    Problem findById(int id);
}
