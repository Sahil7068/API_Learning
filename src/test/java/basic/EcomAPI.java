package basic;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class EcomAPI {

    public static void main(String[] args) {

       RequestSpecification res = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
               .setContentType(ContentType.JSON).build();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail("slworld@gmail.com");
        loginRequest.setUserPassword("Test@1234567");

       RequestSpecification reqLogin = given().spec(res).body(loginRequest);

       LoginResponse loginResponse = reqLogin.when().post("/api/ecom/auth/login")
               .then().extract().response().as(LoginResponse.class);

       System.out.println(loginResponse.getToken());
       String token = loginResponse.getToken();
       System.out.println(loginResponse.getUserId());
       String userId = loginResponse.getUserId();
       System.out.println(loginResponse.getMessage());



       //Add product

        RequestSpecification reqAddProduct = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                        .addHeader("Authorization", token).build();
        RequestSpecification reqAddProductres = given().spec(reqAddProduct).param("productName", "qwerty")
                .param("productAddedBy", userId).param("productCategory", "fashion")
                .param("productSubCategory", "shoes")
                .param("productPrice", "100").param("productDescription", "Addias Originals")
                .param("productFor", "women")
                .multiPart("productImage", new File("C:\\Users\\mdsah\\Downloads\\Screenshot 2025-10-17 175513.png"));

        String addProductResponse = reqAddProductres.when().post("/api/ecom/product/add-product")
                .then().log().all().extract().response().asString();

        JsonPath jsonPath = new JsonPath(addProductResponse);
        String productId = jsonPath.getString("productId");
        System.out.println("The product id is "  + productId);

        //create order

        RequestSpecification reqCreateOrder = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON)
                .addHeader("Authorization", token).build();

        OrdersDetail ordersDetail = new OrdersDetail();
        ordersDetail.setCountry("india");
        ordersDetail.setProductOrderedId(productId);

        List<OrdersDetail> ordersDetailList = new ArrayList<>();
        ordersDetailList.add(ordersDetail);

        Orders orders = new Orders();
        orders.setOrders(ordersDetailList);

        RequestSpecification responseCreate = given().spec(reqCreateOrder).body(orders);


         String orderResponse = responseCreate.when().post("/api/ecom/order/create-order")
                .then().log().all().extract().response().asString();

         System.out.println(orderResponse);


         //Delete product

        RequestSpecification deleteAddProduct = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization", token).build();

        RequestSpecification deleteAddProductResponse = given().spec(deleteAddProduct)
                .pathParams("productId", productId);

        String deleteResponse = deleteAddProductResponse.when().delete("/api/ecom/product/delete-product/{productId}")
                .then().log().all().extract().response().asString();

        System.out.println(deleteResponse);

    }
}
