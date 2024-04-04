package com.example.springnativejdbctemplate.Tools.csv;


import com.example.springnativejdbctemplate.model.User;
import com.example.springnativejdbctemplate.repository.UserRepo;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Book;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/csv")
public class csvController {
//    @Autowired
    UserRepo userRepo;


    void read_write() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, CsvValidationException {

        //Reading a CSV file

        Reader reader = new FileReader("example.csv");

        CSVReader csvReader = new CSVReader(reader);
        String[] line = csvReader.readNext();

        //Writing into a CSV file

        Writer writer = new FileWriter("example.csv");

        CSVWriter csvWriter = new CSVWriter(writer);
        csvWriter.writeNext(new String[]{"id", "name"});
        csvWriter.writeNext(new String[]{"1", "abc"});
    }

    void read_write_beanBased() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {   //Bean based reading
        Reader reader = new FileReader("example.csv");

        CsvToBean<Book> cb = new CsvToBeanBuilder<Book>(reader).withType(Book.class).build();
        List<Book> book = cb.parse();

        //Bean based writing
        Writer writer2 = new FileWriter("example.csv");
        List<Book> books = null;
        new StatefulBeanToCsvBuilder<Book>(writer2).build().write(books);

    }


    @PostMapping
    public ResponseEntity<?> createUsersFromCSV() {
        long startTime = System.currentTimeMillis(); // Start measuring time

        List<User> users = readUsersFromCSV("Rust_User_3.csv");
        if (users == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to read CSV file");
        }

        addUserToDatabase(users);
//        if (count == -1) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add users to database");
//        }

        long elapsedTime = System.currentTimeMillis() - startTime;
        return ResponseEntity.ok("Users added successfully from CSV. Time taken: " + elapsedTime + " milliseconds" + "List size = " + users.size());
    }    @PostMapping("/{filename}")
    public ResponseEntity<?> createUsersFromCSVWithFileNameInParam(@PathVariable String filename) {
        long startTime = System.currentTimeMillis(); // Start measuring time

        List<User> users = readUsersFromCSV(filename+".csv");
        if (users == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to read CSV file");
        }

        addUserToDatabase(users);
//        if (count == -1) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add users to database");
//        }

        long elapsedTime = System.currentTimeMillis() - startTime;
        return ResponseEntity.ok("Users added successfully from CSV. Time taken: " + elapsedTime + " milliseconds" + "List size = " + users.size());
    }

    // AI
    private List<User> readUsersFromCSV(String filename) {
        List<User> users = new ArrayList<>();
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            boolean isFirstLine = true; // Flag to skip the first line
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip the first line
                }
                String[] data = line.split(","); // Assuming CSV format: name,email,age,...
                // Create a User object from data and add it to the list

                User x = new User(Long.parseLong(data[0]), data[1], data[2], data[3], data[4], data[5], data[6]);
                x.setCreatedAt(new Date());
                x.setUpdatedAt(new Date());

                users.add(x);
            } br.close();
        } catch (IOException | ArrayIndexOutOfBoundsException | NumberFormatException e) {
            e.printStackTrace();
            return null; // Return null if any exception occurs
        } return users;
    }


    private void addUserToDatabase(List<User> users) {

        userRepo.saveAll(users);

    }
}