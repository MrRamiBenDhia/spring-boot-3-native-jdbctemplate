package com.example.springnativejdbctemplate.Logic.Fibonacci;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/fibonacci")
@Controller
public class FibonacciController {
    private final Map<Integer, Integer> memo = new HashMap<>();

    @GetMapping("/{n}")
    public ResponseEntity<?> getNthFibonacci(@PathVariable int n) {
        if (n > 1000000) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Cannot exceed the limit of 1 million");
        } else if (n < 0) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Cannot enter a negative number");
        } else {

            // AI
            // Start the stopwatch
            long startTime = System.nanoTime();

            Long FibonacciResult = FibonacciSeq(n);

            // Stop the stopwatch
            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1000000; // Convert nanoseconds to milliseconds


ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonResponse = mapper.createObjectNode();
        jsonResponse.put("status", "success");
        jsonResponse.put("count", n);
        jsonResponse.put("result", FibonacciResult);
        jsonResponse.put("elapsed_time", String.format("%.3f seconds", duration / 1000.0));
        jsonResponse.put("elapsed_millis", duration);
        
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(jsonResponse);
        }
    }

    public Long FibonacciSeq(int n) {
        switch (n) {
            case 0:
            case 1:
                return 1L;
            default:
                return FibonacciSeq(n - 2) + FibonacciSeq(n - 1);
        }
    }

    private int FibonacciSeqWithMemo(int n) {
        if (n <= 1) {
            return 1;
        }
        if (memo.containsKey(n)) {
            return memo.get(n);
        }
        int result = FibonacciSeqWithMemo(n - 1) + FibonacciSeqWithMemo(n - 2);
        memo.put(n, result);
        return result;
    }

    public int FibonacciSeqLoop(int n) {
        if (n <= 1) {
            return 1;
        }
        int prev = 1;
        int current = 1;
        for (int i = 2; i <= n; i++) {
            int next = prev + current;
            prev = current;
            current = next;
        }
        return current;
    }
}
