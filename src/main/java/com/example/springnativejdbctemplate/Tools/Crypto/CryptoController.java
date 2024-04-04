package com.example.springnativejdbctemplate.Tools.Crypto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/crypto")
public class CryptoController {

    @GetMapping("/md5/{iterations}")
    @ResponseBody
    public ResponseEntity<?> hashMd5(@PathVariable int iterations) {
        long startTime = System.currentTimeMillis();

        List<String> result = new ArrayList<>() ;
        for (int i = 0; i < iterations; i++) {
            result.add(CryptoService.hashMd5(CryptoService.SAMPLE_TEXT));
        }
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonResponse = mapper.createObjectNode();
        jsonResponse.put("status", "success");
        jsonResponse.put("count", iterations);
        jsonResponse.put("result", result.get(0));
        jsonResponse.put("elapsed_time", String.format("%.3f seconds", duration / 1000.0));
        jsonResponse.put("elapsed_millis", duration);
        
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(jsonResponse);
//        return "MD5 hashing completed " + iterations + " times.\nTime taken (ms): " + duration;
    }

    @GetMapping("/sha256/{iterations}")
    @ResponseBody
    public ResponseEntity<?> hashSha256(@PathVariable int iterations) {
        long startTime = System.currentTimeMillis();
        List<String> result = new ArrayList<>() ;
        for (int i = 0; i < iterations; i++) {
            result.add(CryptoService.hashSha256(CryptoService.SAMPLE_TEXT));
        }
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonResponse = mapper.createObjectNode();
        jsonResponse.put("status", "success");
        jsonResponse.put("count", iterations);
        jsonResponse.put("result", result.get(0));
        jsonResponse.put("elapsed_time", String.format("%.3f seconds", duration / 1000.0));
        jsonResponse.put("elapsed_millis", duration);

        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(jsonResponse);
//        return "SHA-256 hashing completed " + iterations + " times.\nTime taken (ms): " + duration;
    }
}
