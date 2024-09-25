package com.example;

import static io.restassured.RestAssured.*;
import io.restassured.response.Response;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PetStoreTest {

    private static PetStorePage petStorePage;

    @BeforeAll
    public static void setup() {
        petStorePage = new PetStorePage(); 
    }

    //This test case retrieves the details of a pet by its ID (in this case, 1).
    @Test
    public void testGetPetById() {
        Response response1 = petStorePage.getPet(1);
        
        response1.then().statusCode(200)
                .body("name", equalTo("Cat 1"))
                .body("status", equalTo("available"));
    }
    //It verifies that the response status code is 200 OK and checks that the name and status of the pet match the expected values ("Cat 1" for name and "available" for status).

    // This is a negative test case that attempts to retrieve a pet using an ID that does not exist (in this case, 999).
    @Test
    public void testGetPetByIdNotFounded() {
        Response response1 = petStorePage.getPet(999);
        response1.then().statusCode(404);
    }
    //It asserts that the response status code is 404 Not Found, indicating that the pet with the specified ID could not be found.

    // This test case creates a new pet by sending a POST request with the pet's ID, name, and status.
    @Test
    public void testCreatePet() {
        // Makes the POST request and receives the response
        Response response = petStorePage.createPet(123, "Bobby", "available");

        // Verifies the name in the response
        String name = response.jsonPath().getString("name");
        assertEquals("Bobby", name, "The name of the pet should be 'Bobby'");
    }
    //After the creation request, it verifies that the name of the pet returned in the response matches the expected name ("Bobby"). This ensures that the pet was created successfully with the correct details.

    // This test case updates the details of an existing pet by sending a PUT request with updated values (changing the name to "Bobby Updated" and status to "sold").
    @Test
    public void testUpdatePetDetails() {

        // Makes the PUT request to update the pet
        Response response = petStorePage.updatePet(123, "Bobby Updated", "sold");
        
        // Verifies the changes in the response
        String updatedName = response.jsonPath().getString("name");
        String updatedStatus = response.jsonPath().getString("status");

        assertEquals("Bobby Updated", updatedName, "The name should be 'Bobby Updated'");
        assertEquals("sold", updatedStatus, "The status should be 'sold'");
    }
    //It checks that the response contains the updated name and status, asserting that the changes were applied correctly.

    // This test case deletes a pet using its ID (123). It first makes a DELETE request to remove the pet.
    @Test
    public void testDeletePet() {
        int petId = 123;

        // Makes the DELETE request to remove the pet
        Response deleteResponse = petStorePage.deletePet(petId);

        // Verifies that the deletion was successful (200 OK)
        assertEquals(200, deleteResponse.statusCode(), "The pet should be deleted successfully.");

        // Verifies the response message
        String message = deleteResponse.getBody().asString();
        assertEquals("Pet deleted", message, "The message should indicate that the pet was deleted");

        // Verifies that the pet no longer exists
        Response getResponse = petStorePage.getPet(petId);
        assertEquals(404, getResponse.statusCode(), "The pet should not exist (404 Not Found).");
    }
    // It verifies that the deletion was successful by checking for a 200 OK status. It also checks the response message to confirm that it indicates the pet was deleted. Finally, it attempts to retrieve the pet again to ensure it no longer exists, expecting a 404 Not Found status.
}