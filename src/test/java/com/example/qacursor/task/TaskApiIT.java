package com.example.qacursor.task;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskApiIT {

    @LocalServerPort
    int port;

    @BeforeAll
    static void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void createAndListTasks() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\"title\":\"API Task\",\"description\":\"via RestAssured\"}")
                .post("http://localhost:" + port + "/api/tasks")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("title", equalTo("API Task"));

        RestAssured.get("http://localhost:" + port + "/api/tasks")
                .then()
                .statusCode(200)
                .body("title", hasItem("API Task"));
    }

    @Test
    void createTask_shouldValidate() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\"title\":\" \"}")
                .post("http://localhost:" + port + "/api/tasks")
                .then()
                .statusCode(anyOf(is(400), is(500))); // depends on validation exception mapping
    }
}


