package com.example.springnativejdbctemplate.Logic.Prime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.sqrt;

@RestController
@RequestMapping("/prime")
public class PrimeController {

    @GetMapping("/{n}")
    public ResponseEntity<?> getNthPrimeNumbers(@PathVariable int n) {
        if (n > 10000000) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Cannot exceed the limit of 10 million");
        } else if (n < 0) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Cannot enter a negative number");
        } else {
            // AI
            // Start the stopwatch
            long startTime = System.nanoTime();

            List<Long> primeNumbers = listNthPrimeNumbers(n);

            // Stop the stopwatch
            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1000000; // Convert nanoseconds to milliseconds

            // Create response body
            Map<String, Object> response = new HashMap<>();
//            response.put({"primeNumbers"= primeNumbers});

ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonResponse = mapper.createObjectNode();
        jsonResponse.put("status", "success");
        jsonResponse.put("count", n);
        jsonResponse.put("result", primeNumbers.get(n-1));
        jsonResponse.put("elapsed_time", String.format("%.3f seconds", duration / 1000.0));
        jsonResponse.put("elapsed_millis", duration);
        
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(jsonResponse);

           
//            response.put("calculationTimeInMillis", duration);
//            return ResponseEntity.ok(response);
        }
    }

    List<Long> listNthPrimeNumbers(int n) {
        List<Long> result = new ArrayList<>();

        long pointer = 2L;
        while (result.size() < n) {
            if (isPrimeOptimized(pointer)) {
                result.add(pointer);
            }
            pointer++;
        }
        return result;
    }

    boolean isPrimeOptimized(long n) {
        if (n < 2) return false;
        if (n == 2 || n == 3) return true;

        for (long i = 2; i <= sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }
}
