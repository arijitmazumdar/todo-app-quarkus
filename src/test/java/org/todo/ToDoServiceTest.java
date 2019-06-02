package org.todo;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
public class ToDoServiceTest {

    @Test
    public void testToDoListEndpoint() {
        given()
          .when().get("/todos")
          .then()
             .statusCode(200)
             .body(containsString("42") );
    }

    @Test
    public void testToDoCompleteEndpoint() {
        given()
          .when().get("/todos?complete=true")
          .then()
             .statusCode(200)
             .body(containsString("42") );
    }

    @Test
    public void testToDoCompleteEndpoint() {
        given()
          .parameters("task","sometask","complete", true).post("/todos")
          .then()
             .statusCode(200)
             .body(containsString("sometask") );
    }
}
