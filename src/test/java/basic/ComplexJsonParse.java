package basic;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {
    public static void main(String[] args) {

        JsonPath js2 = new JsonPath(Payload.coursePrice());
        int courseSize = js2.getInt("courses.size()");
        System.out.println(courseSize);

        int purchaseAmount = js2.getInt("dashboard.purchaseAmount");
        System.out.println(purchaseAmount);

        String title = js2.getString("courses[0].title");
        System.out.println(title);

        //print all course and prices

        for (int i = 0; i<courseSize; i++){
            String courseTitle = js2.get("courses["+i+"].title");
            int prices = js2.get("courses["+i+"].price");
            System.out.println(courseTitle);
            System.out.println(prices);
        }


        //print number of copies sold by RPA

        System.out.println("print number of copies sold by RPA");

        for (int i = 0; i<courseSize; i++){
            String courseTitle = js2.get("courses["+i+"].title");
            if (courseTitle.equalsIgnoreCase("RPA")){
                int copies = js2.get("courses["+i+"].copies");
                System.out.println(copies);
                break;
            }
        }

        //verify all course price matches with the purchase amount

        System.out.println("verify all course price matches with the purchase amount");


       int total = 0;
        for (int i=0; i<courseSize; i++) {
            int price = js2.get("courses[" + i + "].price");
            int copies = js2.get("courses[" + i + "].copies");


            total = total + price * copies;
        }

            int totalPurchaseAmount = js2.get("dashboard.purchaseAmount");

            if (totalPurchaseAmount == total){
                System.out.println("Purchase amount matches with the total " + total + " " + totalPurchaseAmount);
            }
        }
    }



