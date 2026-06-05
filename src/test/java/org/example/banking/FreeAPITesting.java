package org.example.banking;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.example.banking.constants.BankingConstants.FreeAPIURL;

public class FreeAPITesting {
    String id;
    @Test(enabled = false)
    public void getAPIMethod(){
        RequestSpecification reqSpcObj = RestAssured.given();
        reqSpcObj.baseUri(FreeAPIURL);
        reqSpcObj.log().all();

        ResponseSpecBuilder resObj = new ResponseSpecBuilder();
        resObj.expectStatusCode(200);
        reqSpcObj.then().spec(resObj.build());


       Response responseObj = reqSpcObj.get("/objects/1");
       responseObj.then().log().all();
       JsonPath jsonPathObj =responseObj.jsonPath();
       Assert.assertEquals(jsonPathObj.get("name"),"Google Pixel 6 Pro");
    }

    @Test
    public void postFreeAPIMethod(){
        RequestSpecification reqSpcObj1 = RestAssured.given();// initialize req spc obj- Changes in develop branch
        reqSpcObj1.baseUri(FreeAPIURL);

        Header header = new Header("Content-Type","application/json");
        reqSpcObj1.header(header);

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("name","One Plus 14");
        JSONObject data = new JSONObject();
        //data.put("color","Cloudy White");
        //data.put("capacity","128 GB");
        data.put("year", 2019);
        data.put("CPU model", "Intel Core i9");
        data.put("Hard disk size","1 TB");
        data.put("price", 1849.99);
        jsonObj.put("data",data);

        reqSpcObj1.body(jsonObj.toString());
        reqSpcObj1.log().all();

        ResponseSpecBuilder resspecObj1 = new ResponseSpecBuilder();
        resspecObj1.expectStatusCode(200);

        reqSpcObj1.then().spec(resspecObj1.build());

        Response responseObj1 = reqSpcObj1.post("/objects");
        JsonPath jsonpathObj1 = responseObj1.jsonPath();
        responseObj1.then().log().all();
        Assert.assertEquals(jsonpathObj1.get("name"),"One Plus 14");
         id = jsonpathObj1.get("id");
    }

    @Test(dependsOnMethods = "postFreeAPIMethod")
    public void putFreeAPIMethod(){
        RequestSpecification reqspcObj = RestAssured.given();
        reqspcObj.baseUri(FreeAPIURL);

        Header header= new Header("Content-Type","application/json");
        reqspcObj.header(header);

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("name","One Plus 14_Updated");
        JSONObject data = new JSONObject();
        data.put("year", 2025);
        jsonObj.put("data",data);
        reqspcObj.body(jsonObj.toString());

        reqspcObj.log().all();

        ResponseSpecBuilder resObj = new ResponseSpecBuilder();
        resObj.expectStatusCode(200);
        reqspcObj.then().spec(resObj.build());

        Response response= reqspcObj.put("objects/"+id);
        response.then().log().all();
        JsonPath jsonpathObj = response.jsonPath();
        Assert.assertEquals(jsonpathObj.get("name"),"One Plus 14_Updated");
    }

    @Test(dependsOnMethods = "putFreeAPIMethod" )
    public void getAPINewID(){
        RequestSpecification reqSpcObj2 = RestAssured.given();
        reqSpcObj2.baseUri(FreeAPIURL);

        reqSpcObj2.log().all();

        ResponseSpecBuilder resSpecObj2 = new ResponseSpecBuilder();
        resSpecObj2.expectStatusCode(200);

        reqSpcObj2.then().spec(resSpecObj2.build());

         Response responseObj1 = reqSpcObj2.get("/objects/"+id);
         responseObj1.then().log().all();
         JsonPath jsonpathObj2 = responseObj1.jsonPath();
         Assert.assertEquals(jsonpathObj2.get("name"),"One Plus 14_Updated");
    }
}
