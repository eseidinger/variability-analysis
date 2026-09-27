package de.eseidinger.variabilityanalysis;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class StatusResourceTest {

    @Test
    void returnsApiStatus() {
        given()
          .when().get("/api/status")
          .then()
             .statusCode(200)
             .contentType("text/plain")
             .body(is("Variability Analysis API is reachable"));
    }
}
