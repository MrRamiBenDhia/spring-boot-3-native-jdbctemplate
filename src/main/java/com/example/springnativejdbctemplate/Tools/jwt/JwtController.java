package com.example.springnativejdbctemplate.Tools.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/jwt")
public class JwtController {

    int numIters = 1000;

    @GetMapping("/{n}")
    public ResponseEntity<?> benchmarkJwt(@PathVariable int n) throws IOException {
        Gson gson = new Gson();
        String content = new String(Files.readAllBytes(Paths.get("./test-data/test-emails.json")));
        List<String> emails = new ArrayList<>();

        JsonElement jsonElement = JsonParser.parseString(content);
        if (jsonElement.isJsonObject()) {
            JsonArray jsonArray = jsonElement.getAsJsonObject().getAsJsonArray("emails");
            for (JsonElement element : jsonArray) {
                emails.add(element.getAsString());
            }
        }

        long startTS = System.currentTimeMillis();
//        String jwtSecret = System.getenv("JWT_SECRET"); //! replace this
        String jwtSecret = "JWT_SECRET_na3n_din_zebyyyyyyyyyyyyyyyyyyyyyyyyyyyy"; //! replace this

        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        String xToShow = "";

        for (int i = 0; i < n; i++) {
            String email = emails.get(i % emails.size());
            String jwt = Jwts.builder()
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 2 * 60 * 60 * 1000))
                    .setSubject(email)
                    .signWith(key)
                    .compact();
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
            if (!email.equals(claims.getSubject())) {
                throw new IllegalStateException("JWT verification failed");
            }
            if (i == 1) xToShow = jwt;
        }

        long endTS = System.currentTimeMillis();
        long diff = endTS - startTS;


        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonResponse = mapper.createObjectNode();
        jsonResponse.put("status", "success");
        jsonResponse.put("count", n);
        jsonResponse.put("result",xToShow);
        jsonResponse.put("elapsed_time", String.format("%.3f seconds", diff / 1000.0));
        jsonResponse.put("elapsed_millis", diff);

        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(jsonResponse);
//        return "{\"timeInMillis\": " + diff + "}";
    }
}
