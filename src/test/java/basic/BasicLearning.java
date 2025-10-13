package basic;

import files.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * BasicLearning class demonstrates basic REST API testing using RestAssured library.
 * This class contains examples of making API requests and validating responses.
 */
public class BasicLearning {

    /**
     * Main method that demonstrates a basic API test flow:
     * 1. Sets up the base URI
     * 2. Sends a POST request to add a place
     * 3. Validates the response
     * 4. Extracts and prints the place ID from the response
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // REST Assured follows a BDD (Behavior-Driven Development) style with given-when-then methods:
        // given - Set up request specification including headers, query params, and body
        // when - Specify the HTTP method and endpoint
        // then - Validate the response

        // Set the base URI for all requests
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        
        // Send POST request to add a new place and validate the response
        String response = given()
                .log().all()  // Log all request details
                .queryParam("key", "qaclick123")  // Add query parameter
                .header("Content-Type", "application/json")  // Set content type header
                .body(Payload.addPlace())  // Set request body using the payload
            .when()
                .post("/maps/api/place/add/json")  // Specify HTTP method and endpoint
            .then()
                .statusCode(200)  // Verify status code is 200 OK
                .body("scope", equalTo("APP"))  // Validate response body
                .header("Server", "Apache/2.4.52 (Ubuntu)")  // Validate response header
                .extract().response().asString();  // Extract response as string

        // Print the complete JSON response
        System.out.println("API Response: " + response);
        
        // Parse the JSON response to extract specific values
        JsonPath js = new JsonPath(response);
        String placeId = js.getString("place_id");
        
        // Print the extracted place ID
        System.out.println("Extracted Place ID: " + placeId);

        //update place

        String newAddress = "punjab";

        given().log().all().queryParam("key", "qaclick123").
                body("{\n" +
                        "\"place_id\":\""+placeId+"\",\n" +
                        "\"address\":\""+newAddress+"\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}\n").when().
                put("/maps/api/place/update/json").then().statusCode(200).
                body("msg", equalTo("Address successfully updated"));

        //get the place

        String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").
                queryParam("place_id", placeId)
                .when().
                get("/maps/api/place/get/json").then().statusCode(200).extract().response().asString();

        JsonPath js1 = new JsonPath(getPlaceResponse);
        String address = js1.getString("address");
        System.out.println("The extracted address is " + address);
    }
}
