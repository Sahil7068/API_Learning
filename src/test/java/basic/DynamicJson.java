package basic;

import files.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DynamicJson {

    @Test(dataProvider = "data")
    public void addBook(String isbn, String aisle){
        RestAssured.baseURI = "http://216.10.245.166";

        String response = given().header("Content-Type", "application/json").
                body(Payload.addNewBook(isbn, aisle)).
                when().
                post("/Library/Addbook.php").
                then().log().all().assertThat().
                statusCode(200).extract().response().asString();

        JsonPath js = new JsonPath(response);
        String bookId = js.get("ID");
        System.out.println(bookId);
    }

    @DataProvider(name = "data")
    public Object[][] getData(){
        return new Object[][]{{"bdddop12", "24322222"}, {"asdd888", "9798797"}, {"lsdl88", "929339"}};
    }
}
