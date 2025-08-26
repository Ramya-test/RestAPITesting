package org.example.banking;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestProvider {

    @DataProvider(name="GetDatafromInputFile")
    public String[][] testProvider(){
            return new String[][]{
            {
                "user","pass"
            },
                    {
                            "user1","pass1"
                    }
        };
    }

    @Test(dataProvider = "GetDatafromInputFile")
    public void testPractice(String username,String password){
        Header headerobj = new Header("Authorization", "Basic dXNlcjpwYXNz" );
        RequestSpecification reqSpeObj = RestAssured.given();
        reqSpeObj.baseUri("https://httpbin.org/basic-auth");
        reqSpeObj.header(headerobj);
        reqSpeObj.log().all();
        reqSpeObj.when();
        ResponseSpecBuilder resSpcObj = new ResponseSpecBuilder();
        resSpcObj.expectStatusCode(200);
        resSpcObj.expectContentType(ContentType.JSON);
        reqSpeObj.then().spec(resSpcObj.build());
        Response res = reqSpeObj.get("/"+username +"/" +password);
        res.then().log().all();
        JsonPath jsonpathObj = res.jsonPath();
        Assert.assertEquals(true,jsonpathObj.getBoolean("authenticated"));
        Assert.assertEquals("user",jsonpathObj.getString("user"));



    }




}
