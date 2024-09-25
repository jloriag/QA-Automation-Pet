package com.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PetStorePage {

    public PetStorePage() {
        RestAssured.baseURI = "http://localhost:8080/api/v3"; // Sets the base URL
    }

    public Response deletePet(int petId) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/pet/" + petId)
                .then()
                .extract().response();
    }

    public Response getPet(int petId) {
        return RestAssured.given()
                .when()
                .get("/pet/" + petId)
                .then()
                .extract().response();
    }

    public Response createPet(int id, String name, String status) {
        // Body of the JSON that we will send in the POST request
        String requestBody = String.format("{\n" +
            "  \"id\": %d,\n" +
            "  \"name\": \"%s\",\n" +
            "  \"status\": \"%s\"\n" +
            "}", id, name, status);
            
        // Makes the POST request and returns the response
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/pet")
                .then()
                .extract().response();
    }

    public Response updatePet(int id, String name, String status) {
        // Body of the JSON that we will send in the PUT request
        String updatedPetBody = String.format("{\n" +
                "  \"id\": %d,\n" +
                "  \"name\": \"%s\",\n" +
                "  \"status\": \"%s\"\n" +
                "}", id, name, status);

        // Makes the PUT request and returns the response
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(updatedPetBody)
                .when()
                .put("/pet")
                .then()
                .extract().response();
    }
}