package basic;

import io.restassured.RestAssured;
import pojo.AddPlace;
import pojo.LongLat;

import java.util.*;

import static io.restassured.RestAssured.given;

public class SerializeTest {
    public static void main(String[] args) {

        RestAssured.baseURI = "https://rahulshettyacademy.com";

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

        String res = given().log().all().queryParam("key", "qaclick123").
        body(addPlace).when().post("/maps/api/place/add/json")
                .then().assertThat().statusCode(200).extract().response().asString();

        System.out.println(res);
    }
}
