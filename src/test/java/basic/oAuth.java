package basic;

import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;

import java.util.List;

import static io.restassured.RestAssured.given;

public class oAuth {
    public static void main(String[] args) throws InterruptedException {

// TODO Auto-generated method stub

        String response =

                given()
                        .formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")

                        .formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")

                        .formParams("grant_type", "client_credentials")

                        .formParams("scope", "trust")

                        .when().log().all()

                        .post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();

        System.out.println(response);

        JsonPath jsonPath = new JsonPath(response);

        String accessToken = jsonPath.getString("access_token");

        System.out.println(accessToken);

        GetCourse getCourse = given()

                .queryParams("access_token", accessToken)

                .when()

                .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")

                .as(GetCourse.class);

        System.out.println("The linkedin url is " + getCourse.getLinkedIn());

        System.out.println(getCourse.getCourses().getApi().get(0).getCourseTitle());

        List<Api> l = getCourse.getCourses().getApi();

        l.stream().filter(s -> s.getCourseTitle().
                equalsIgnoreCase("Rest Assured Automation using Java")).
                forEach(s -> System.out.println(s.getCourseTitle() + " " + s.getPrice()));

        List<WebAutomation> webAutomation = getCourse.getCourses().getWebAutomation();

        webAutomation.stream().
                forEach(s -> System.out.println("All course title are " + s.getCourseTitle()));





    }
}
