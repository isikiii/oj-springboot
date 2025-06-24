package com.example.oj.judge;

import com.example.oj.dao.SubmissionDao;
import com.example.oj.model.Submission;
import com.example.oj.model.Problem;
import com.example.oj.dao.ProblemDao;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class JudgeService {

    private final SubmissionDao submissionDao;
    private final ProblemDao problemDao;

    public JudgeService(SubmissionDao submissionDao, ProblemDao problemDao) {
        this.submissionDao = submissionDao;
        this.problemDao = problemDao;
    }

    // 每隔 10 秒检查一次待评测代码
    @Scheduled(fixedRate = 10000)
    public void judgePending() throws IOException {
        List<Submission> pendingList = submissionDao.findPending();

        for (Submission sub : pendingList) {
            Problem problem = problemDao.findById(sub.getProblemId());
            String result = runAndCompare(sub.getCode(), problem.getInput(), problem.getOutput());
            submissionDao.updateStatus(sub.getId(), result);
        }
    }

    private String runAndCompare(String code, String input, String expectedOutput) {
        try {
            // 写入 Main.java
            File file = new File("Main.java");
            try (FileWriter fw = new FileWriter(file)) {
                fw.write(code);
            }

            // 编译
            Process compile = Runtime.getRuntime().exec("javac Main.java");
            compile.waitFor();

            if (compile.exitValue() != 0) {
                return "CE"; // Compilation Error
            }

            // 执行程序
            Process exec = Runtime.getRuntime().exec("java Main");
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(exec.getOutputStream()));
                 BufferedReader reader = new BufferedReader(new InputStreamReader(exec.getInputStream()))) {

                // 写入输入
                writer.write(input);
                writer.flush();
                writer.close();

                // 读取输出
                StringBuilder output = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }

                // 比较输出
                return output.toString().trim().equals(expectedOutput.trim()) ? "AC" : "WA";
            }

        } catch (Exception e) {
            return "RE"; // Runtime Error
        }
    }
}
