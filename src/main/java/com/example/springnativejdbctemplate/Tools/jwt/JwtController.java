package com.example.springnativejdbctemplate.Tools.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/jwt")
public class JwtController {

    private final ResourceLoader resourceLoader;
    int numIters = 1000;

    public JwtController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/{n}")
    public ResponseEntity<?> benchmarkJwt(@PathVariable int n) throws IOException {
        Gson gson = new Gson();


        List<String> emails = new ArrayList<>();
        try {
            // Load the JSON file using ResourceLoader
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    resourceLoader.getResource("classpath:test-emails.json").getInputStream()));

            // Parse the JSON content
            JsonElement jsonElement = JsonParser.parseReader(reader);

            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                if (jsonObject.has("emails")) {
                    JsonArray jsonArray = jsonObject.getAsJsonArray("emails");
                    for (JsonElement element : jsonArray) {
                        emails.add(element.getAsString());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle file reading or parsing errors here
        }

        long startTS = System.currentTimeMillis();
//        String jwtSecret = System.getenv("JWT_SECRET"); //! replace this
        String jwtSecret = "fc81305cebbcd49877e5c624cded1f62506ad35d7273bc9a9e036c2215d042d1"; //! replace this

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
        jsonResponse.put("result", xToShow);
        jsonResponse.put("elapsed_time", String.format("%.3f seconds", diff / 1000.0));
        jsonResponse.put("elapsed_millis", diff);

        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(jsonResponse);
//        return "{\"timeInMillis\": " + diff + "}";
    }
}
