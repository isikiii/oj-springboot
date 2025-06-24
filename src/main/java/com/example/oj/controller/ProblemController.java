package com.example.oj.controller;

import com.example.oj.dao.ProblemDao;
import com.example.oj.model.Problem;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/problems")
public class ProblemController {

    private final ProblemDao problemDao;

    public ProblemController(ProblemDao problemDao) {
        this.problemDao = problemDao;
    }

    @PostMapping
    public String create(@RequestBody Problem problem) {
        problemDao.save(problem);
        return "题目添加成功";
    }

    @GetMapping
    public List<Problem> listAll() {
        return problemDao.findAll();
    }

    @GetMapping("/{id}")
    public Problem getById(@PathVariable int id) {
        return problemDao.findById(id);
    }
}