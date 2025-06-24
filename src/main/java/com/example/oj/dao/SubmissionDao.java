package com.example.oj.dao;

import com.example.oj.model.Submission;
import java.util.List;

public interface SubmissionDao {

    /** 保存一次提交 */
    void save(Submission s);

    /** 按用户名查询提交记录（倒序） */
    List<Submission> findAllByUsername(String username);

    /** 查询所有 Pending 提交（判题器使用） */
    List<Submission> findPending();

    /** 更新某条提交的评测结果 */
    void updateStatus(int id, String status);
}
