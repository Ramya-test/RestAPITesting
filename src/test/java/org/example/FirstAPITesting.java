package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.request.model.Loginrequest;
import org.hamcrest.core.Is;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


public class FirstAPITesting {

    @Test
    public void testGetRequest() {
        // Define the base URI of the API
        RestAssured.baseURI = "https://prepared-accurately-worm.ngrok-free.app";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("emailId" , "super_user");
        jsonObject.put("password" , "Ganesha@1992");
        Loginrequest Login=new Loginrequest("super_user","Ganesha@1992");
       /* Login.setEmailId("super_user");
        Login.setPassword("Ganesha@1992");*/
        Response tokenresponse=given().accept(ContentType.JSON).contentType(ContentType.JSON).body(jsonObject.toString()).
                when().post("/api/users/login");
        tokenresponse.then().log().all();
        // Send a GET request to /posts endpoint
       /*Response response = given().header("Authorization","Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc0MDgyNjY4NiwiZXhwIjoxNzQwODMwMjg2fQ.hGr3wYTXHaR1S8wheCglM3Hxle-SOQ5QhvSEGMztRUWertpXSXNi6GtA6wImZKKAF8uQGJMThaUn_g6mY-roXw")
                .when()
                .get("/api/members/19");

        response.then().assertThat().body("name", Is.is("Asha Kaatkar"));

        response.then().log().all();*/
        // Print the response body for debugging
        System.out.println("Response Body:");
        System.out.println(tokenresponse.getBody().asPrettyString());

        // Assert the status code
        int statusCode = tokenresponse.getStatusCode();
        Assert.assertEquals(statusCode, 200, "Status code should be 200");
    }
}

