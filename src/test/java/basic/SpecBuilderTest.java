package basic;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.LongLat;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SpecBuilderTest {
    public static void main(String[] args) {

//        RestAssured.baseURI = "https://rahulshettyacademy.com";

        AddPlace addPlace = new AddPlace();
        addPlace.setAccuracy(50);
        addPlace.setAddress("dumka");
        addPlace.setLanguage("French-IN");
        addPlace.setName("Frontline house");
        addPlace.setPhone_number("9876543210");
        addPlace.setWebsite("http://google.com");

        List<String> types = new ArrayList<>();
        types.add("shoe park");
        types.add("shop");
        addPlace.setTypes(types);

        LongLat longLat = new LongLat();
        longLat.setLat(-38.383494);
        longLat.setLng(33.427362);
        addPlace.setLocation(longLat);

        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addQueryParam("key", "qaclick123").build();

        ResponseSpecification resSpec = new ResponseSpecBuilder().expectStatusCode(200).build();

        RequestSpecification res = given().log().all().spec(req).
        body(addPlace);


                Response response =res.when().post("/maps/api/place/add/json")
                .then().spec(resSpec).extract().response();

        System.out.println(response.asString());
    }
}
