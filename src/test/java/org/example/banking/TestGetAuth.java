package org.example.banking;

import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;



import static org.example.banking.constants.BankingConstants.bankAPIURL;

//@FixMethodOrder
@Epic("REST API Regression Testing using TestNG")
@Feature("Verify Bank Transaction services")
public class TestGetAuth {
    String token;
    int userId;

    @DataProvider(name = "Userdetails")
    public String[][] getUserDetails(){
        return new String[][]{
                {
                        "super_user", "Ganesha@1992"
                },
                {
                        "super_user1", "Ganesha@1992"
                }
        };
    }


    @Test(description = "Login to Bank Application", priority = 1, invocationCount = 4, enabled = true, timeOut = 10l, dataProvider = "Userdetails")
    @Story("POST Request")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description : Verify Login to Bank Application")
    public void authToken(String username, String password) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("emailId", username);
        jsonObject.put("password", password);
        //given().baseUri(bankAPIURL).contentType(ContentType.JSON).when().body(jsonObject.toString()).post("/api/users/login").then().statusCode(200);
        RequestSpecification rsObj = RestAssured.given();
        rsObj.filter(new AllureRestAssured());
        rsObj.baseUri(bankAPIURL);
        rsObj.contentType(ContentType.JSON);
        rsObj.when();
        rsObj.body(jsonObject.toString());
        //rsObj.post("/api/users/login");
        ResponseSpecBuilder rpObj = new ResponseSpecBuilder();
        rpObj.expectStatusCode(200);
        rpObj.expectContentType(ContentType.JSON);
        rsObj.then().spec(rpObj.build());
        Response res = rsObj.post("/api/users/login");// click on SEND button in postman
        JsonPath jsonpath = res.jsonPath();
        Assert.assertFalse(jsonpath.getString("access_token").isBlank());
        token = jsonpath.getString("access_token");
    }


    /*@Test(description = "Get user records",priority = 2)
    public void getUsers() {

        Header headerObj = new Header("Authorization", "Bearer " + token);
        RequestSpecification reqspeObj = RestAssured.given();
        reqspeObj.baseUri(bankAPIURL);
        reqspeObj.header(headerObj);
        reqspeObj.log().all();
        reqspeObj.when();
        ResponseSpecBuilder rpObj = new ResponseSpecBuilder();
        rpObj.expectStatusCode(200);
        rpObj.expectContentType(ContentType.JSON);
        reqspeObj.then().spec(rpObj.build());
        Response res = reqspeObj.get("/api/users");// click on SEND button in postman
        res.then().log().all();
        JsonPath jspathObj = res.jsonPath();
        Assert.assertEquals(jspathObj.getInt("userId[0]"), 1);
    }


    @Test
    public void getUserbyId() {
        Header headerObj = new Header("Authorization", "Bearer " + token);
        RequestSpecification reqspeObj = RestAssured.given();
        reqspeObj.baseUri(bankAPIURL);
        reqspeObj.header(headerObj);
        reqspeObj.log().all();
        reqspeObj.when();
        ResponseSpecBuilder rpObj = new ResponseSpecBuilder();
        rpObj.expectContentType(ContentType.JSON);
        rpObj.expectStatusCode(200);
        reqspeObj.then().spec(rpObj.build());
        Response res = reqspeObj.get("/api/users/" + userId);
        res.then().log().all();
        JsonPath jspathObj = res.jsonPath();
        Assert.assertEquals(jspathObj.getInt("userId"), userId);
        Assert.assertEquals(jspathObj.getString("emailId"), "Rajesh7@gmail.com");
        Assert.assertEquals(jspathObj.getString("status"), "APPROVED");
        Assert.assertEquals(jspathObj.getString("userProfileDto.firstName"), "Arpitha Rakesh");
        Assert.assertEquals(jspathObj.getString("userProfileDto.lastName"), "Kaatkar");
    }


    @Test
    public void createABUser() {
        Header headerObj = new Header("Authorization", "Bearer " + token);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("lastName", "Kaatkar");
        jsonObject.put("emailId", "Rajesh7@gmail.com");
        jsonObject.put("contactNumber", "8981009267");
        jsonObject.put("password", "Adam@1234");
        RequestSpecification reqspeObj = RestAssured.given();
        reqspeObj.baseUri(bankAPIURL);
        reqspeObj.header(headerObj);
        reqspeObj.contentType(ContentType.JSON);
        reqspeObj.body(jsonObject.toString());
        reqspeObj.log().all();
        reqspeObj.when();
        ResponseSpecBuilder resObj = new ResponseSpecBuilder();
        resObj.expectStatusCode(200);
        resObj.expectContentType(ContentType.JSON);
        reqspeObj.then().spec(resObj.build());
        Response res = reqspeObj.post("/api/users/register");
        res.then().log().all();
        JsonPath jsonObj = res.jsonPath();
        userId = jsonObj.getInt("userId");
        //Assert.assertEquals(jsonObj.getInt("userId"), 44);
        Assert.assertEquals(jsonObj.getString("emailId"), "Rajesh7@gmail.com");
        Assert.assertEquals(jsonObj.getString("contactNo"), "8981009267");
    }


    @Test
    public void updateUser() {
        Header headerObj = new Header("Authorization", "Bearer " + token);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("firstName", "Anu Gakesh");
        jsonObject.put("lastName", "Gopal");
        jsonObject.put("contactNo", "9098989098");
        jsonObject.put("address", "Mandya");
        jsonObject.put("gender", "Female");
        jsonObject.put("occupation", "Farmer");
        jsonObject.put("martialStatus", "Single");
        jsonObject.put("nationality", "Indian");
        RequestSpecification reqObj = RestAssured.given();
        reqObj.contentType(ContentType.JSON);
        reqObj.body(jsonObject.toString());
        reqObj.header(headerObj);
        reqObj.baseUri(bankAPIURL);
        reqObj.then().log().all();
        ResponseSpecBuilder resObj = new ResponseSpecBuilder();
        resObj.expectStatusCode(200);
        resObj.expectContentType(ContentType.JSON);
        reqObj.then().spec(resObj.build());
        Response response = reqObj.put("/api/users/40");
        JsonPath jsonpath = response.jsonPath();
        Assert.assertEquals(jsonpath.getString("responseCode"), "200");
        Assert.assertEquals(jsonpath.getString("responseMessage"), "user updated successfully");

    }


    @Test
    public void fundTransfer() {
        Header header = new Header("Authorization", "Bearer " + token);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fromAccount", "0600140000017");
        jsonObject.put("toAccount", "0600140000018");
        jsonObject.put("amount", "100");
        RequestSpecification reqspecObj = RestAssured.given();
        reqspecObj.baseUri(bankAPIURL);
        reqspecObj.header(header);
        reqspecObj.contentType(ContentType.JSON);
        reqspecObj.body(jsonObject.toString());
        ResponseSpecBuilder respObj = new ResponseSpecBuilder();
        respObj.expectStatusCode(201);
        respObj.expectContentType(ContentType.JSON);
        reqspecObj.then().spec(respObj.build());
        Response res = reqspecObj.post("/fund-transfers");
        JsonPath jsonpath = res.jsonPath();
        Assert.assertEquals(jsonpath.getString("message"), "Fund transfer was successful");
    }


    @Test
    public void fundTransferNegativeScenario() {
        Header header = new Header("Authorization", "Bearer " + token);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fromAccount", "0600140000017");
        jsonObject.put("toAccount", "0600140000018");
        jsonObject.put("amount", "-1");
        RequestSpecification reqspecObj = RestAssured.given();
        reqspecObj.baseUri(bankAPIURL);
        reqspecObj.header(header);
        reqspecObj.contentType(ContentType.JSON);
        reqspecObj.body(jsonObject.toString());
        ResponseSpecBuilder respObj = new ResponseSpecBuilder();
        respObj.expectStatusCode(400);
        respObj.expectContentType(ContentType.JSON);
        reqspecObj.then().spec(respObj.build());
        Response res = reqspecObj.post("/fund-transfers");
        JsonPath jsonpath = res.jsonPath();
        Assert.assertEquals(jsonpath.getString("errorCode"), "Amount cannot be negative");
    }


    @Test
    public void depositAmount() {
        Header header = new Header("Authorization", "Bearer " + token);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("accountId", "0600140000017");
        jsonObject.put("transactionType", "DEPOSIT");
        jsonObject.put("amount", "250");
        jsonObject.put("description", "250 has successfully credited to account");
        RequestSpecification reqObj = RestAssured.given();
        reqObj.baseUri(bankAPIURL);
        reqObj.header(header);
        reqObj.body(jsonObject.toString());
        reqObj.contentType(ContentType.JSON);
        ResponseSpecBuilder responseObj = new ResponseSpecBuilder();
        responseObj.expectStatusCode(200);
        responseObj.expectContentType(ContentType.JSON);
        reqObj.then().spec(responseObj.build());
        Response resp = reqObj.post("/transactions");
        JsonPath jsonpath = resp.jsonPath();
        Assert.assertEquals(jsonpath.getString("accountId"), "0600140000017");
        Assert.assertEquals(jsonpath.getInt("amount"), 250);
        Assert.assertEquals(jsonpath.getString("status"), "COMPLETED");
        Assert.assertEquals(jsonpath.getString("comments"), "250 has successfully credited to account");
    }


    @Test
    public void withdrawAmount() {
        Header header = new Header("Authorization", "Bearer " + token);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("accountId", "0600140000017");
        jsonObject.put("transactionType", "WITHDRAWAL");
        jsonObject.put("amount", "200");
        jsonObject.put("description", "200 has successfully debited from account");
        RequestSpecification reqObj = RestAssured.given();
        reqObj.header(header);
        reqObj.body(jsonObject.toString());
        reqObj.baseUri(bankAPIURL);
        reqObj.contentType(ContentType.JSON);
        ResponseSpecBuilder resObj = new ResponseSpecBuilder();
        resObj.expectStatusCode(200);
        resObj.expectContentType(ContentType.JSON);
        reqObj.then().spec(resObj.build());
        Response resp = reqObj.post("/transactions");
        JsonPath jsonpath = resp.jsonPath();
        Assert.assertEquals(jsonpath.getString("accountId"), "0600140000017");
        Assert.assertEquals(jsonpath.getInt("amount"), -200);
        Assert.assertEquals(jsonpath.getString("status"), "COMPLETED");
        Assert.assertEquals(jsonpath.getString("comments"), "200 has successfully debited from account");
    }


    @Test
    public void createAccount() {
        Header header = new Header("Authorization", "Bearer " + token);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("accountType", "SAVINGS_ACCOUNT");
        jsonObject.put("userId", userId);
        RequestSpecification reqObj = RestAssured.given();
        reqObj.baseUri(bankAPIURL);
        reqObj.contentType(ContentType.JSON);
        reqObj.header(header);
        reqObj.then().log().all();
        reqObj.body(jsonObject.toString());
        ResponseSpecBuilder resObj = new ResponseSpecBuilder();
        resObj.expectStatusCode(201);
        resObj.expectContentType(ContentType.JSON);
        reqObj.then().spec(resObj.build());
        Response response = reqObj.post("/accounts");
        JsonPath jsonpath = response.jsonPath();
        Assert.assertFalse(jsonpath.getString("message").isBlank());
        Assert.assertEquals(jsonpath.getString("responseCode"), "200");
    }

    @Test
    public void updateAccountStatus()
    {
        Header header = new Header("Authorization","Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJLY1B6c3dVN0JtWDBpdkZUNjlIYTh6RUV2V2NTTHViYTk3c3djUFFta1IwIn0.eyJleHAiOjE3NTMxMjkxNDIsImlhdCI6MTc1MzExMTE0MiwianRpIjoiMDdmMTQ1M2MtOTE3MS00MDAzLWJjY2MtYWVhYmM2ODBiMjU4IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4NTcxL3JlYWxtcy9iYW5raW5nLXNlcnZpY2UiLCJhdWQiOlsicmVhbG0tbWFuYWdlbWVudCIsImJyb2tlciIsImFjY291bnQiXSwic3ViIjoiYmRkYmQ5MWUtNWZkMS00ZjRlLTk0ZjItNTU0YjAxMmU1ZDgwIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYmFua2luZy1zZXJ2aWNlLWNsaWVudCIsInNpZCI6IjE5N2EyMmM2LWFlZDMtNGI5MS1iNzQzLTA0ZDRkMDMxZGE0NyIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy1iYW5raW5nLXNlcnZpY2UiLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsicmVhbG0tbWFuYWdlbWVudCI6eyJyb2xlcyI6WyJtYW5hZ2UtZXZlbnRzIiwibWFuYWdlLXJlYWxtIiwibWFuYWdlLWlkZW50aXR5LXByb3ZpZGVycyIsImltcGVyc29uYXRpb24iLCJjcmVhdGUtY2xpZW50IiwibWFuYWdlLXVzZXJzIiwibWFuYWdlLWF1dGhvcml6YXRpb24iLCJtYW5hZ2UtY2xpZW50cyIsInF1ZXJ5LWNsaWVudHMiLCJxdWVyeS1ncm91cHMiXX0sImJhbmtpbmctc2VydmljZS1jbGllbnQiOnsicm9sZXMiOlsidW1hX3Byb3RlY3Rpb24iXX0sImJyb2tlciI6eyJyb2xlcyI6WyJyZWFkLXRva2VuIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50Iiwidmlldy1hcHBsaWNhdGlvbnMiLCJ2aWV3LWNvbnNlbnQiLCJ2aWV3LWdyb3VwcyIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwibWFuYWdlLWNvbnNlbnQiLCJkZWxldGUtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIGVtYWlsIHByb2ZpbGUgb2ZmbGluZV9hY2Nlc3MiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6IlJha2VzaCBEIEthYXRrYXIiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJzdXBlcl91c2VyIiwiZ2l2ZW5fbmFtZSI6IlJha2VzaCBEIiwiZmFtaWx5X25hbWUiOiJLYWF0a2FyIiwiZW1haWwiOiJyYWtlc2hka2FhdGthckBnbWFpbC5jb20ifQ.EzLf0OwYm6LUM8tajOxd6PPG5fDBRjVTsXpHiN3QjT3btxdwzznBb2IAeuwFeZJUC1gxAgw8NLYhKUlsOgHkFZbFVDUaPkiioKAC_snQRUoQ326VrGcycoXFQdcXSlLUkIxn2vDuE-RvpevSQivf9yX7ryp1jD3OCL9ncEEdShpmgfxVjvr9T183Q3GxePOsrrX20qQgsQIK4WhgSvUmm0IMkAKYtXjpD9J-VEMvMk0EpjceKPgXu0-3WsHgP4HanifCNmv6qtmN6diVBCpGZpiESyJPSj9FywNqgHRk5jaW8Eg3m298zbPNaXIftlrb0eAOJh5jEosGpjOyr6XO4g");
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("accountStatus","ACTIVE");
        RequestSpecification reqObj = RestAssured.given();
        reqObj.header(header);
        reqObj.contentType(ContentType.JSON);
        reqObj.body(jsonObj.toString());
        reqObj.baseUri(bankAPIURL);
        reqObj.queryParam("accountNumber","0600140000033");
        reqObj.log().all();
        ResponseSpecBuilder resObj = new ResponseSpecBuilder();
        resObj.expectStatusCode(200);
        resObj.expectContentType(ContentType.JSON);
        reqObj.then().spec(resObj.build());
        Response res=reqObj.patch("/accounts");
        res.then().log().all();
        JsonPath jsonPathObj=res.jsonPath();
        Assert.assertEquals("Account updated successfully",jsonPathObj.getString("message"));
   }

   @Test
    public void getTransactionFromAccountID()
   {
       Header header = new Header("Authorization","Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJLY1B6c3dVN0JtWDBpdkZUNjlIYTh6RUV2V2NTTHViYTk3c3djUFFta1IwIn0.eyJleHAiOjE3NTMyMTU3MTYsImlhdCI6MTc1MzE5NzcxNiwianRpIjoiYmY5ZTZhNzEtM2RiZi00M2VlLWIzYjItMDc3OTA3ZDc0ZGY0IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4NTcxL3JlYWxtcy9iYW5raW5nLXNlcnZpY2UiLCJhdWQiOlsicmVhbG0tbWFuYWdlbWVudCIsImJyb2tlciIsImFjY291bnQiXSwic3ViIjoiMjBmOTRiNjEtYWY2ZC00YThiLTk4YmItMWRhODJkMzIwYjcyIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYmFua2luZy1zZXJ2aWNlLWNsaWVudCIsInNpZCI6ImZkOGI4MDljLWQ5MGUtNDY2ZS1iNDk1LTM4Yzc3YWEzZGQwZSIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy1iYW5raW5nLXNlcnZpY2UiLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsicmVhbG0tbWFuYWdlbWVudCI6eyJyb2xlcyI6WyJtYW5hZ2UtZXZlbnRzIiwibWFuYWdlLXJlYWxtIiwibWFuYWdlLWlkZW50aXR5LXByb3ZpZGVycyIsImltcGVyc29uYXRpb24iLCJjcmVhdGUtY2xpZW50IiwibWFuYWdlLXVzZXJzIiwibWFuYWdlLWF1dGhvcml6YXRpb24iLCJtYW5hZ2UtY2xpZW50cyIsInF1ZXJ5LWNsaWVudHMiLCJxdWVyeS1ncm91cHMiXX0sImJhbmtpbmctc2VydmljZS1jbGllbnQiOnsicm9sZXMiOlsidW1hX3Byb3RlY3Rpb24iXX0sImJyb2tlciI6eyJyb2xlcyI6WyJyZWFkLXRva2VuIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50Iiwidmlldy1hcHBsaWNhdGlvbnMiLCJ2aWV3LWNvbnNlbnQiLCJ2aWV3LWdyb3VwcyIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwibWFuYWdlLWNvbnNlbnQiLCJkZWxldGUtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIGVtYWlsIHByb2ZpbGUgb2ZmbGluZV9hY2Nlc3MiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6IklzaGEgUmFrZXNoIEthYXRrYXIiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJpc2hhcmFrZXNoQGdtYWlsLmNvbSIsImdpdmVuX25hbWUiOiJJc2hhIFJha2VzaCIsImZhbWlseV9uYW1lIjoiS2FhdGthciIsImVtYWlsIjoiaXNoYXJha2VzaEBnbWFpbC5jb20ifQ.JkV1WuGCtKZMXL77_kdNCmJZz8lqzd_hnhF48HJGmQzPdwpTFOoS1-QqKnXLK4bTcKcWtwd-JxAoWpJDEtdLi_X5cj8dQutr3RlUk9NdTu7HgNkYFF5h4Ysa_pN3u2UUb7gYEzeeHqt52pJaHMOfHR_4372wdThMpQPCg-NlFYifsgoZfgcncFh1vzDaPVKBpklX7xac4s7tJmbsBTulxviLe5wiCVm1wWdRHIYclJhEx1OGuucSzJVkAFWjMLRfDbhP9G7KCSSx6k-tVXbwyr6WQQXyYN2t0RJ3omh-aTLUQiYQ8vYmDYB2z0mRG0Dl2um7G3HjiT1z1e-_HUmNbg");
       RequestSpecification reqObj = RestAssured.given();
       reqObj.header(header);
       reqObj.baseUri(bankAPIURL);
       reqObj.log().all();
       ResponseSpecBuilder resObj = new ResponseSpecBuilder();
       resObj.expectStatusCode(200);
       resObj.expectContentType(ContentType.JSON);
       reqObj.then().spec(resObj.build());
       reqObj.queryParam("accountId","0600140000013");
       Response res=reqObj.get("/transactions");
       JsonPath jsonpathObj = res.jsonPath();
       res.then().log().all();
       Assert.assertEquals("0600140000013",jsonpathObj.getString("accountId[0]"));

   }

    @Test
    public void fundTransferandverifyAccountBalance() {
        Header header = new Header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJLY1B6c3dVN0JtWDBpdkZUNjlIYTh6RUV2V2NTTHViYTk3c3djUFFta1IwIn0.eyJleHAiOjE3NTMzODQ5NTcsImlhdCI6MTc1MzM2Njk1NywianRpIjoiNzExMTU5NTctODEwYi00M2UyLTgwODQtYzRlOGNiNjMzOTQ0IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4NTcxL3JlYWxtcy9iYW5raW5nLXNlcnZpY2UiLCJhdWQiOlsicmVhbG0tbWFuYWdlbWVudCIsImJyb2tlciIsImFjY291bnQiXSwic3ViIjoiYmRkYmQ5MWUtNWZkMS00ZjRlLTk0ZjItNTU0YjAxMmU1ZDgwIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYmFua2luZy1zZXJ2aWNlLWNsaWVudCIsInNpZCI6ImJmZjI2NTAzLWVjYWEtNDVhOC1iMTBlLTYzYmM0NDgxNDZlMCIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy1iYW5raW5nLXNlcnZpY2UiLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsicmVhbG0tbWFuYWdlbWVudCI6eyJyb2xlcyI6WyJtYW5hZ2UtZXZlbnRzIiwibWFuYWdlLXJlYWxtIiwibWFuYWdlLWlkZW50aXR5LXByb3ZpZGVycyIsImltcGVyc29uYXRpb24iLCJjcmVhdGUtY2xpZW50IiwibWFuYWdlLXVzZXJzIiwibWFuYWdlLWF1dGhvcml6YXRpb24iLCJtYW5hZ2UtY2xpZW50cyIsInF1ZXJ5LWNsaWVudHMiLCJxdWVyeS1ncm91cHMiXX0sImJhbmtpbmctc2VydmljZS1jbGllbnQiOnsicm9sZXMiOlsidW1hX3Byb3RlY3Rpb24iXX0sImJyb2tlciI6eyJyb2xlcyI6WyJyZWFkLXRva2VuIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50Iiwidmlldy1hcHBsaWNhdGlvbnMiLCJ2aWV3LWNvbnNlbnQiLCJ2aWV3LWdyb3VwcyIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwibWFuYWdlLWNvbnNlbnQiLCJkZWxldGUtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIGVtYWlsIHByb2ZpbGUgb2ZmbGluZV9hY2Nlc3MiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6IlJha2VzaCBEIEthYXRrYXIiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJzdXBlcl91c2VyIiwiZ2l2ZW5fbmFtZSI6IlJha2VzaCBEIiwiZmFtaWx5X25hbWUiOiJLYWF0a2FyIiwiZW1haWwiOiJyYWtlc2hka2FhdGthckBnbWFpbC5jb20ifQ.dh8rmUeUjQZfnw98W7Jqxgs513Jtz7S1qqDq-yFtMCYjyxxS4yqdYJNFZUSUKf73CiRDra6WG-_0ts8p9-mW-xDmPKoPWkg-YUdWyVa4Shd6_f8l43_LuEWoEG0mBtMDR5OLawDl0RT_xEcaI_C2eZs71WpwyPiSxxG96LdPZHsJCK_yn3g2HaLsak3AgjGm7WJhu767FCrf97M9wVL8tFjXEVsf5IYPWxVyhGsCwsQQqESEfV8RQPI4kiVcfw8lx3Q_v5x6fstomBEMy0swugBsWRhqBJftgX9Md_N-8Dj76FEl9qHCEWFzY5lgTc0XkKCjR2Cw7qBXlDTxScq52w");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fromAccount", "0600140000018");
        jsonObject.put("toAccount", "0600140000017");
        jsonObject.put("amount", "1000");
        RequestSpecification reqspecObj = RestAssured.given();
        reqspecObj.baseUri(bankAPIURL);
        reqspecObj.header(header);
        reqspecObj.contentType(ContentType.JSON);
        reqspecObj.body(jsonObject.toString());
        reqspecObj.log().all();
        ResponseSpecBuilder respObj = new ResponseSpecBuilder();
        //respObj.expectStatusCode(201);
        respObj.expectContentType(ContentType.JSON);
        reqspecObj.then().spec(respObj.build());
        Response res = reqspecObj.post("/fund-transfers");
        JsonPath jsonpath = res.jsonPath();
        res.then().log().all();

        RequestSpecification reqspecObj1 = RestAssured.given();
        reqspecObj1.baseUri(bankAPIURL);
        reqspecObj1.header(header);
        reqspecObj1.contentType(ContentType.JSON);
        ResponseSpecBuilder respObj1 = new ResponseSpecBuilder();
        respObj1.expectStatusCode(200);
        respObj1.expectContentType(ContentType.JSON);
        reqspecObj1.then().spec(respObj1.build());
        reqspecObj1.queryParam("accountNumber","0600140000018");
        reqspecObj1.log().all();
        Response responseacct1 =reqspecObj1.get("/accounts");
        JsonPath jsonpathObj1 = responseacct1.jsonPath();
        Assert.assertEquals(500.00,jsonpathObj1.getDouble("availableBalance"),0.0);
        responseacct1.then().log().all();

        RequestSpecification reqspecObj2 = RestAssured.given();
        reqspecObj2.baseUri(bankAPIURL);
        reqspecObj2.header(header);
        reqspecObj2.contentType(ContentType.JSON);
        ResponseSpecBuilder respObj2 = new ResponseSpecBuilder();
        respObj2.expectStatusCode(200);
        respObj2.expectContentType(ContentType.JSON);
        reqspecObj2.then().spec(respObj2.build());
        reqspecObj2.queryParam("accountNumber","0600140000017");
        reqspecObj2.log().all();
        Response responseacct2 =reqspecObj2.get("/accounts");
        JsonPath jsonpathObj2 = responseacct2.jsonPath();
        Assert.assertEquals(1350.00,jsonpathObj2.getDouble("availableBalance"),0.0);
        responseacct2.then().log().all();
    }

    @Test
    public void verifycurrentBalanceandDepositToThatAccount()
    {
        // flow for Read account by sending account number
        Header header = new Header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJLY1B6c3dVN0JtWDBpdkZUNjlIYTh6RUV2V2NTTHViYTk3c3djUFFta1IwIn0.eyJleHAiOjE3NTMzODQ5NTcsImlhdCI6MTc1MzM2Njk1NywianRpIjoiNzExMTU5NTctODEwYi00M2UyLTgwODQtYzRlOGNiNjMzOTQ0IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4NTcxL3JlYWxtcy9iYW5raW5nLXNlcnZpY2UiLCJhdWQiOlsicmVhbG0tbWFuYWdlbWVudCIsImJyb2tlciIsImFjY291bnQiXSwic3ViIjoiYmRkYmQ5MWUtNWZkMS00ZjRlLTk0ZjItNTU0YjAxMmU1ZDgwIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYmFua2luZy1zZXJ2aWNlLWNsaWVudCIsInNpZCI6ImJmZjI2NTAzLWVjYWEtNDVhOC1iMTBlLTYzYmM0NDgxNDZlMCIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy1iYW5raW5nLXNlcnZpY2UiLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsicmVhbG0tbWFuYWdlbWVudCI6eyJyb2xlcyI6WyJtYW5hZ2UtZXZlbnRzIiwibWFuYWdlLXJlYWxtIiwibWFuYWdlLWlkZW50aXR5LXByb3ZpZGVycyIsImltcGVyc29uYXRpb24iLCJjcmVhdGUtY2xpZW50IiwibWFuYWdlLXVzZXJzIiwibWFuYWdlLWF1dGhvcml6YXRpb24iLCJtYW5hZ2UtY2xpZW50cyIsInF1ZXJ5LWNsaWVudHMiLCJxdWVyeS1ncm91cHMiXX0sImJhbmtpbmctc2VydmljZS1jbGllbnQiOnsicm9sZXMiOlsidW1hX3Byb3RlY3Rpb24iXX0sImJyb2tlciI6eyJyb2xlcyI6WyJyZWFkLXRva2VuIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50Iiwidmlldy1hcHBsaWNhdGlvbnMiLCJ2aWV3LWNvbnNlbnQiLCJ2aWV3LWdyb3VwcyIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwibWFuYWdlLWNvbnNlbnQiLCJkZWxldGUtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIGVtYWlsIHByb2ZpbGUgb2ZmbGluZV9hY2Nlc3MiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6IlJha2VzaCBEIEthYXRrYXIiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJzdXBlcl91c2VyIiwiZ2l2ZW5fbmFtZSI6IlJha2VzaCBEIiwiZmFtaWx5X25hbWUiOiJLYWF0a2FyIiwiZW1haWwiOiJyYWtlc2hka2FhdGthckBnbWFpbC5jb20ifQ.dh8rmUeUjQZfnw98W7Jqxgs513Jtz7S1qqDq-yFtMCYjyxxS4yqdYJNFZUSUKf73CiRDra6WG-_0ts8p9-mW-xDmPKoPWkg-YUdWyVa4Shd6_f8l43_LuEWoEG0mBtMDR5OLawDl0RT_xEcaI_C2eZs71WpwyPiSxxG96LdPZHsJCK_yn3g2HaLsak3AgjGm7WJhu767FCrf97M9wVL8tFjXEVsf5IYPWxVyhGsCwsQQqESEfV8RQPI4kiVcfw8lx3Q_v5x6fstomBEMy0swugBsWRhqBJftgX9Md_N-8Dj76FEl9qHCEWFzY5lgTc0XkKCjR2Cw7qBXlDTxScq52w");
        RequestSpecification reqspeObj = RestAssured.given();
        reqspeObj.baseUri(bankAPIURL);
        reqspeObj.header(header);
        reqspeObj.log().all();
        reqspeObj.queryParam("accountNumber","0600140000019");

        ResponseSpecBuilder resObj = new ResponseSpecBuilder();
        resObj.expectContentType(ContentType.JSON);
        resObj.expectStatusCode(200);
        reqspeObj.then().spec(resObj.build());
        Response res = reqspeObj.get("/accounts");
        JsonPath jsonPathObj =res.jsonPath();
        Double currentBalance=jsonPathObj.getDouble("availableBalance");


        // flow to perform amount deposit transaction
        RequestSpecification reqSpecObj1 = RestAssured.given();
        reqSpecObj1.baseUri(bankAPIURL);
        reqSpecObj1.header(header);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("accountId","0600140000019");
        jsonObj.put("transactionType","DEPOSIT");
        jsonObj.put("amount","1000");
        jsonObj.put("description","1000 has successfully credited to account");
        reqSpecObj1.body(jsonObj.toString());
        reqSpecObj1.contentType(ContentType.JSON);
        reqSpecObj1.log().all();

        ResponseSpecBuilder resObj1 = new ResponseSpecBuilder();
        resObj1.expectStatusCode(200);
        resObj1.expectContentType(ContentType.JSON);
        reqSpecObj1.then().spec(resObj1.build());
        Response res1=reqSpecObj1.post("/transactions");
        JsonPath jsonpth1=res1.jsonPath();
        Integer newlyAddedbalanceAmount=jsonpth1.getInt("amount");
        res1.then().log().all();

        //flow for Read account by sending account number
        Response res2 = reqspeObj.get("/accounts");
        JsonPath jsonpathObj2 = res2.jsonPath();
        Double finalAmount=jsonpathObj2.getDouble("availableBalance");
        res2.then().log().all();

        Double calculatedAmount = currentBalance + newlyAddedbalanceAmount;
        Assert.assertEquals(finalAmount,calculatedAmount);
    }

    @Test
    //create user -store userid from response ,update user, and get the user using userid and compare the user data
    public void createUserandUpdateUser(){

        // Create new user
        Header header = new Header("Authorization","Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJLY1B6c3dVN0JtWDBpdkZUNjlIYTh6RUV2V2NTTHViYTk3c3djUFFta1IwIn0.eyJleHAiOjE3NTM0NzM1MTksImlhdCI6MTc1MzQ1NTUxOSwianRpIjoiMmU4ZjU0YWMtMDBkNC00OGJiLTk2YjgtODRhNTg5OTQ0Njg1IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4NTcxL3JlYWxtcy9iYW5raW5nLXNlcnZpY2UiLCJhdWQiOlsicmVhbG0tbWFuYWdlbWVudCIsImJyb2tlciIsImFjY291bnQiXSwic3ViIjoiYmRkYmQ5MWUtNWZkMS00ZjRlLTk0ZjItNTU0YjAxMmU1ZDgwIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYmFua2luZy1zZXJ2aWNlLWNsaWVudCIsInNpZCI6IjEyZjMwMzY4LTdlNmUtNDQ4ZS04ODhhLTEzNjliMjU0M2QwNyIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy1iYW5raW5nLXNlcnZpY2UiLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsicmVhbG0tbWFuYWdlbWVudCI6eyJyb2xlcyI6WyJtYW5hZ2UtZXZlbnRzIiwibWFuYWdlLXJlYWxtIiwibWFuYWdlLWlkZW50aXR5LXByb3ZpZGVycyIsImltcGVyc29uYXRpb24iLCJjcmVhdGUtY2xpZW50IiwibWFuYWdlLXVzZXJzIiwibWFuYWdlLWF1dGhvcml6YXRpb24iLCJtYW5hZ2UtY2xpZW50cyIsInF1ZXJ5LWNsaWVudHMiLCJxdWVyeS1ncm91cHMiXX0sImJhbmtpbmctc2VydmljZS1jbGllbnQiOnsicm9sZXMiOlsidW1hX3Byb3RlY3Rpb24iXX0sImJyb2tlciI6eyJyb2xlcyI6WyJyZWFkLXRva2VuIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50Iiwidmlldy1hcHBsaWNhdGlvbnMiLCJ2aWV3LWNvbnNlbnQiLCJ2aWV3LWdyb3VwcyIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwibWFuYWdlLWNvbnNlbnQiLCJkZWxldGUtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIGVtYWlsIHByb2ZpbGUgb2ZmbGluZV9hY2Nlc3MiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6IlJha2VzaCBEIEthYXRrYXIiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJzdXBlcl91c2VyIiwiZ2l2ZW5fbmFtZSI6IlJha2VzaCBEIiwiZmFtaWx5X25hbWUiOiJLYWF0a2FyIiwiZW1haWwiOiJyYWtlc2hka2FhdGthckBnbWFpbC5jb20ifQ.dhSw1V8uYj78fQ_Rvt1lzGf4H3pamwHX9GWBc8xglVsT5dA_t7TuK0a7mRIELBpkLBnbiIj0rJMe0qxOdrbNQ_GzohYkp-CyfJhMJBOZ784oILNuu0kTGX7YidMYmmGVxzIfVPlEEsxjIt4DfnTGv1WCeDHCuxGfxvX5FFAfFaWCcFevVKpW6qSSdUnsN3Aee8zTlqV6T7L_tcP3zOCnkTtke2hq4065GzzAiUPknHg7ye2iZZK5nuZcOCaxQWSlVxcnHMdm0afykQPHdPHIshrHf0d8ro9LXVUQNCtZrrh0CpwAAQaqLWUQ4iqX3QL5pKqIqOkndQCTX_PPY0NYaA");
        JSONObject jsonOBject = new JSONObject();
        jsonOBject.put("lastName","Sharma");
        jsonOBject.put("emailId","Dhruvi@gmail.com");
        jsonOBject.put("contactNumber","8981959267");
        jsonOBject.put("password","Adam@1234");
        RequestSpecification reqObj = RestAssured.given();
        reqObj.header(header);
        reqObj.baseUri(bankAPIURL);
        reqObj.body(jsonOBject.toString());
        reqObj.contentType(ContentType.JSON);
        reqObj.log().all();
        ResponseSpecBuilder resObj = new ResponseSpecBuilder();
        resObj.expectContentType(ContentType.JSON);
        resObj.expectStatusCode(200);
        reqObj.then().spec(resObj.build());
        Response res = reqObj.post("/api/users/register");
        JsonPath jsonpathObj = res.jsonPath();
        res.then().log().all();
        Integer userID = jsonpathObj.getInt("userId");

        //Update the details of created user
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("firstName","Dhruvi");
        jsonObject2.put("lastName","Sharma");
        jsonObject2.put("contactNo","9082048579");
        jsonObject2.put("address","HSR Layout, KRS road");
        jsonObject2.put("gender","Female");
        jsonObject2.put("occupation","Dentist");
        jsonObject2.put("martialStatus","Married");
        jsonObject2.put("nationality","Indian");
        RequestSpecification reqObj1 = RestAssured.given();
        reqObj1.baseUri(bankAPIURL);
        reqObj1.header(header);
        reqObj1.body(jsonObject2.toString());
        reqObj1.contentType(ContentType.JSON);
        reqObj1.log().all();
        ResponseSpecBuilder resObj2 = new ResponseSpecBuilder();
        resObj2.expectStatusCode(200);
        resObj2.expectContentType(ContentType.JSON);
        reqObj1.then().spec(resObj2.build());
        Response res1=reqObj1.put("/api/users/"+userID);
        JsonPath jsonpath2=res1.jsonPath();
        Assert.assertEquals("user updated successfully",jsonpath2.getString("responseMessage"));
        res1.then().log().all();

        //get the user using User ID and compare the results

        RequestSpecification reqObj2 = RestAssured.given();
        reqObj2.header(header);
        reqObj2.baseUri(bankAPIURL);
        reqObj2.log().all();
        ResponseSpecBuilder resObj3 = new ResponseSpecBuilder();
        resObj3.expectContentType(ContentType.JSON);
        resObj3.expectStatusCode(200);
        reqObj2.then().spec(resObj3.build());
        Response res3 = reqObj2.get("api/users/"+userID);
        JsonPath jsonpath3 = res3.jsonPath();
        res3.then().log().all();
       // Assert.assertEquals(""+userID,jsonpath3.getInt("userId"));// Converted int to String
        Assert.assertEquals(userID.toString(),jsonpath3.getInt("userId")+""); // Converted int to String
        Assert.assertEquals("Dhruvi",jsonpath3.getString("userProfileDto.firstName"));
    }
     @Test
    public void createAccountandDepositAmount(){
        Header header = new Header("Authorization","Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJLY1B6c3dVN0JtWDBpdkZUNjlIYTh6RUV2V2NTTHViYTk3c3djUFFta1IwIn0.eyJleHAiOjE3NTM1NjA2ODYsImlhdCI6MTc1MzU0MjY4NiwianRpIjoiOWZjOTRhOTktMjA5OC00ZjAzLWFmMmEtN2M1YThiNWM1YzRjIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4NTcxL3JlYWxtcy9iYW5raW5nLXNlcnZpY2UiLCJhdWQiOlsicmVhbG0tbWFuYWdlbWVudCIsImJyb2tlciIsImFjY291bnQiXSwic3ViIjoiYmRkYmQ5MWUtNWZkMS00ZjRlLTk0ZjItNTU0YjAxMmU1ZDgwIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYmFua2luZy1zZXJ2aWNlLWNsaWVudCIsInNpZCI6ImEzYmM3OGNkLTIxYWMtNDJkNi1hYjRiLTBmYjE5YzYxOTI2ZCIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy1iYW5raW5nLXNlcnZpY2UiLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsicmVhbG0tbWFuYWdlbWVudCI6eyJyb2xlcyI6WyJtYW5hZ2UtZXZlbnRzIiwibWFuYWdlLXJlYWxtIiwibWFuYWdlLWlkZW50aXR5LXByb3ZpZGVycyIsImltcGVyc29uYXRpb24iLCJjcmVhdGUtY2xpZW50IiwibWFuYWdlLXVzZXJzIiwibWFuYWdlLWF1dGhvcml6YXRpb24iLCJtYW5hZ2UtY2xpZW50cyIsInF1ZXJ5LWNsaWVudHMiLCJxdWVyeS1ncm91cHMiXX0sImJhbmtpbmctc2VydmljZS1jbGllbnQiOnsicm9sZXMiOlsidW1hX3Byb3RlY3Rpb24iXX0sImJyb2tlciI6eyJyb2xlcyI6WyJyZWFkLXRva2VuIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50Iiwidmlldy1hcHBsaWNhdGlvbnMiLCJ2aWV3LWNvbnNlbnQiLCJ2aWV3LWdyb3VwcyIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwibWFuYWdlLWNvbnNlbnQiLCJkZWxldGUtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIGVtYWlsIHByb2ZpbGUgb2ZmbGluZV9hY2Nlc3MiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6IlJha2VzaCBEIEthYXRrYXIiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJzdXBlcl91c2VyIiwiZ2l2ZW5fbmFtZSI6IlJha2VzaCBEIiwiZmFtaWx5X25hbWUiOiJLYWF0a2FyIiwiZW1haWwiOiJyYWtlc2hka2FhdGthckBnbWFpbC5jb20ifQ.apdrLvwu_raTjk3ndaOhjac9sUj5fvJhlj7F5t3t3w4Nr2bbpGnXHRjqGRrOqFq0mRfx6Vr9MuRnZ4acTRSGdCxdiTVjS0ljQD-Pkw_KnBJfpHBC81L1-_qU6ekoiChu6r31ePq5_WzjAjnwkgQD9Z_8LLOCWmU2FuSarogxFbSegXCS467b-sRk9RswkakiXv7HFHMZyEkZr0bbs53jE4jeH2t1KQgHPPjJquGRraW0n_4ACKO9F0wqNEX64hNTYQmEiwyKH-GRSOOgGg6BiJhFVO4oUD11CRalhgadMOYglGve8rsjVdohTdfRNDootARHevj6eiJOU2BpYCGLnw");
        JSONObject jsonObject = new JSONObject();
         jsonObject.put("accountType","SAVINGS_ACCOUNT");
         jsonObject.put("userId","41");
         RequestSpecification reqObj = RestAssured.given();
         reqObj.baseUri(bankAPIURL);
         reqObj.header(header);
         reqObj.body(jsonObject.toString());
         reqObj.contentType(ContentType.JSON);
         reqObj.log().all();

         ResponseSpecBuilder resObj =  new ResponseSpecBuilder();
         resObj.expectStatusCode(201);
         resObj.expectContentType(ContentType.JSON);
         reqObj.then().spec(resObj.build());
         Response res = reqObj.post("/accounts");
         JsonPath jsonpathObj = res.jsonPath();
         String accountNumber = jsonpathObj.getString("message");
         res.then().log().all();

         //update account
         JSONObject jsonObject1 = new JSONObject();
         jsonObject1.put("accountStatus","ACTIVE");
         RequestSpecification reqObj1 = RestAssured.given();
         reqObj1.baseUri(bankAPIURL);
         reqObj1.header(header);
         reqObj1.body(jsonObject.toString());
         reqObj1.contentType(ContentType.JSON);
         reqObj1.log().all();

         ResponseSpecBuilder resObj1 =  new ResponseSpecBuilder();
         resObj1.expectStatusCode(400);
         resObj1.expectContentType(ContentType.JSON);
         reqObj1.then().spec(resObj1.build());
         reqObj1.queryParam("accountNumber",accountNumber);
         Response res1 = reqObj1.patch("/accounts");
         JsonPath jsonpathObj1 = res1.jsonPath();
         Assert.assertEquals("Minimum balance of Rs.1000 is required",jsonpathObj1.getString("errorCode"));
         res1.then().log().all();

         //deposit amount
         JSONObject jsonObject2 = new JSONObject();
         jsonObject2.put("accountId",accountNumber);
         jsonObject2.put("transactionType","DEPOSIT");
         jsonObject2.put("amount","2000");
         jsonObject2.put("description","2000 has successfully credited to account");
         RequestSpecification reqObj2 = RestAssured.given();
         reqObj2.baseUri(bankAPIURL);
         reqObj2.header(header);
         reqObj2.body(jsonObject2.toString());
         reqObj2.contentType(ContentType.JSON);
         reqObj2.log().all();

         ResponseSpecBuilder resObj2 =  new ResponseSpecBuilder();
         resObj2.expectStatusCode(200);
         resObj2.expectContentType(ContentType.JSON);
         reqObj2.then().spec(resObj2.build());
         Response res2 = reqObj2.post("/transactions");
         JsonPath jsonpathObj2= res2.jsonPath();
         res2.then().log().all();
         Assert.assertEquals(accountNumber,jsonpathObj2.getString("accountId"));


         //update account after deposit
         ResponseSpecBuilder resObj3 =  new ResponseSpecBuilder();
         resObj3.expectStatusCode(200);
         resObj3.expectContentType(ContentType.JSON);
         reqObj1.then().spec(resObj3.build());
         Response res3 = reqObj1.patch("/accounts");
         JsonPath jsonpathObj3 = res3.jsonPath();
         res3.then().log().all();
         Assert.assertEquals("Account updated successfully",jsonpathObj3.getString("message"));
     }

     @Test
    public void closeAccount()
    {
        //Read account
        Header header = new Header("Authorization","Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJLY1B6c3dVN0JtWDBpdkZUNjlIYTh6RUV2V2NTTHViYTk3c3djUFFta1IwIn0.eyJleHAiOjE3NTM2NTMxMDgsImlhdCI6MTc1MzYzNTEwOCwianRpIjoiYjBmMjFlOWUtZTc2YS00NGJkLTkzYzUtZGMwZjkxNWUwZTk3IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4NTcxL3JlYWxtcy9iYW5raW5nLXNlcnZpY2UiLCJhdWQiOlsicmVhbG0tbWFuYWdlbWVudCIsImJyb2tlciIsImFjY291bnQiXSwic3ViIjoiYmRkYmQ5MWUtNWZkMS00ZjRlLTk0ZjItNTU0YjAxMmU1ZDgwIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYmFua2luZy1zZXJ2aWNlLWNsaWVudCIsInNpZCI6IjdkMGY4NTE4LTFmYzItNDEyYS1iMjNmLTNjNTMxNWNiNTczZSIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy1iYW5raW5nLXNlcnZpY2UiLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsicmVhbG0tbWFuYWdlbWVudCI6eyJyb2xlcyI6WyJtYW5hZ2UtZXZlbnRzIiwibWFuYWdlLXJlYWxtIiwibWFuYWdlLWlkZW50aXR5LXByb3ZpZGVycyIsImltcGVyc29uYXRpb24iLCJjcmVhdGUtY2xpZW50IiwibWFuYWdlLXVzZXJzIiwibWFuYWdlLWF1dGhvcml6YXRpb24iLCJtYW5hZ2UtY2xpZW50cyIsInF1ZXJ5LWNsaWVudHMiLCJxdWVyeS1ncm91cHMiXX0sImJhbmtpbmctc2VydmljZS1jbGllbnQiOnsicm9sZXMiOlsidW1hX3Byb3RlY3Rpb24iXX0sImJyb2tlciI6eyJyb2xlcyI6WyJyZWFkLXRva2VuIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50Iiwidmlldy1hcHBsaWNhdGlvbnMiLCJ2aWV3LWNvbnNlbnQiLCJ2aWV3LWdyb3VwcyIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwibWFuYWdlLWNvbnNlbnQiLCJkZWxldGUtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIGVtYWlsIHByb2ZpbGUgb2ZmbGluZV9hY2Nlc3MiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6IlJha2VzaCBEIEthYXRrYXIiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJzdXBlcl91c2VyIiwiZ2l2ZW5fbmFtZSI6IlJha2VzaCBEIiwiZmFtaWx5X25hbWUiOiJLYWF0a2FyIiwiZW1haWwiOiJyYWtlc2hka2FhdGthckBnbWFpbC5jb20ifQ.j51WbiKxiHxVv3g3FP6dPBjxBTlUahQoLR5Paygh-soOr2SsxZEgp3P90KdHKLJV4zE1tIpUKmyuep5Mr5sGzIN_trvl1EWimDhag-kUqpKRj_sO5I5slngf6ksTyeNKGEsL67gGdk9-k-PPZUTgbI5gimfoAq5D6H5wPzsrRqsRqximOtNYjg2XTd5bNvMelYbxJRNLu1ocpOqpX8A0Lkqz6u1iAmiyOREBdO4V7TTaqBLHk7ULlt4tMaf_hpB0NdRNU5yeJJjtxQoMYL00x2YeqzOPLJv-5tjmaizE2lFoysyTdhpiUQlSUWPWoNCn9rlDg0CKkNL9rfAf2fYCog");
        RequestSpecification reqObj = RestAssured.given();
        reqObj.baseUri(bankAPIURL);
        reqObj.header(header);
        reqObj.log().all();
        ResponseSpecBuilder resObj = new ResponseSpecBuilder();
        resObj.expectContentType(ContentType.JSON);
        resObj.expectStatusCode(200);
        reqObj.then().spec(resObj.build());
        reqObj.queryParam("accountNumber","0600140000040");
        Response res =reqObj.get("/accounts");
        JsonPath jsonpathObj = res.jsonPath();
        res.then().log().all();
        String acctNum = jsonpathObj.getString("accountNumber");
        Double acctBalance = jsonpathObj.getDouble("availableBalance");

        //Withdraw amount
        RequestSpecification reqOb1= RestAssured.given();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("accountId",acctNum);
        jsonObject.put("transactionType","WITHDRAWAL");
        jsonObject.put("amount",acctBalance);
        jsonObject.put("description",acctBalance+ "has been successfully debited from your account");
        reqOb1.header(header);
        reqOb1.baseUri(bankAPIURL);
        reqOb1.body(jsonObject.toString());
        reqOb1.contentType(ContentType.JSON);
        reqOb1.log().all();
        ResponseSpecBuilder resObj1 = new ResponseSpecBuilder();
        resObj1.expectStatusCode(200);
        resObj1.expectContentType(ContentType.JSON);
        reqOb1.then().spec(resObj1.build());
        Response res1=reqOb1.post("/transactions");
        res1.then().log().all();

        //Close account
        RequestSpecification reqObj2 = RestAssured.given();
        reqObj2.baseUri(bankAPIURL);
        reqObj2.header(header);
        reqObj2.log().all();
        ResponseSpecBuilder resObj2 = new ResponseSpecBuilder();
        resObj2.expectStatusCode(200);
        resObj2.expectContentType(ContentType.JSON);
        reqObj2.then().spec(resObj2.build());
        reqObj2.queryParam("accountNumber",acctNum);
        Response res2=reqObj2.put("/accounts/closure");
        res2.then().log().all();

        //Check if account is closed
        Response res3 = reqObj.get("/accounts");
        res3.then().log().all();
        JsonPath jsonpathObj3 = res3.jsonPath();
        Assert.assertEquals("CLOSED",jsonpathObj3.getString("accountStatus"));
        Assert.assertEquals(0.00,jsonpathObj3.getDouble("availableBalance"),0.00);
    }

    @Test
    public void FundTransferNegativeScenarios()
    {
        // Fund transfer when there is insufficient amount
        Header header = new Header("Authorization","Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJLY1B6c3dVN0JtWDBpdkZUNjlIYTh6RUV2V2NTTHViYTk3c3djUFFta1IwIn0.eyJleHAiOjE3NTM5MDYzNDIsImlhdCI6MTc1Mzg4ODM0MiwianRpIjoiNGM3MjNkYjEtNzRiYy00N2ZiLWE4MzAtYzQzMzljZDVlZDAyIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4NTcxL3JlYWxtcy9iYW5raW5nLXNlcnZpY2UiLCJhdWQiOlsicmVhbG0tbWFuYWdlbWVudCIsImJyb2tlciIsImFjY291bnQiXSwic3ViIjoiYmRkYmQ5MWUtNWZkMS00ZjRlLTk0ZjItNTU0YjAxMmU1ZDgwIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYmFua2luZy1zZXJ2aWNlLWNsaWVudCIsInNpZCI6ImJlMTE3MGZjLWQ4YTAtNDA2ZC1iNTYwLTQ5MDEzNDFlZmRmZiIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy1iYW5raW5nLXNlcnZpY2UiLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsicmVhbG0tbWFuYWdlbWVudCI6eyJyb2xlcyI6WyJtYW5hZ2UtZXZlbnRzIiwibWFuYWdlLXJlYWxtIiwibWFuYWdlLWlkZW50aXR5LXByb3ZpZGVycyIsImltcGVyc29uYXRpb24iLCJjcmVhdGUtY2xpZW50IiwibWFuYWdlLXVzZXJzIiwibWFuYWdlLWF1dGhvcml6YXRpb24iLCJtYW5hZ2UtY2xpZW50cyIsInF1ZXJ5LWNsaWVudHMiLCJxdWVyeS1ncm91cHMiXX0sImJhbmtpbmctc2VydmljZS1jbGllbnQiOnsicm9sZXMiOlsidW1hX3Byb3RlY3Rpb24iXX0sImJyb2tlciI6eyJyb2xlcyI6WyJyZWFkLXRva2VuIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50Iiwidmlldy1hcHBsaWNhdGlvbnMiLCJ2aWV3LWNvbnNlbnQiLCJ2aWV3LWdyb3VwcyIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwibWFuYWdlLWNvbnNlbnQiLCJkZWxldGUtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIGVtYWlsIHByb2ZpbGUgb2ZmbGluZV9hY2Nlc3MiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6IlJha2VzaCBEIEthYXRrYXIiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJzdXBlcl91c2VyIiwiZ2l2ZW5fbmFtZSI6IlJha2VzaCBEIiwiZmFtaWx5X25hbWUiOiJLYWF0a2FyIiwiZW1haWwiOiJyYWtlc2hka2FhdGthckBnbWFpbC5jb20ifQ.m7Mrn6R-2sDeYaBlUhgZkB6kijjsNL8_r9x3uoSQu903OltSQvQN1CAT5ayEaNhGYZL9nBsQNCedkWMUs99qcFKO0qQBzot4qGA4RahRZeCLnDmU0S6Zp8IMnK_Zc6rPDPAEcXH8pcBQZbjJcHXoWfmAN442ZKDHFRlrovuPbgsFg4B6D-XgrajW8upg0lVGcrgvfrwx-5xPgq858KfnanWt3lrILkl6BdFUkWOp8t0dVGus-_dllQG9AfYnaxMnMzGv2Gs7XOdXSu-JE6m_6chvH1m_-GU9ymtwfWbWiUHs1eudsZCrx5iOzp__CfoUm2b-MBP6I26ajamq7wut_Q");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fromAccount","0600140000007");
        jsonObject.put("toAccount","0600140000013");
        jsonObject.put("amount","60000.00");
        RequestSpecification reqObj = RestAssured.given();
        reqObj.header(header);
        reqObj.baseUri(bankAPIURL);
        reqObj.body(jsonObject.toString());
        reqObj.contentType(ContentType.JSON);
        reqObj.log().all();
        ResponseSpecBuilder resObj= new ResponseSpecBuilder();
        resObj.expectStatusCode(400);
        resObj.expectContentType(ContentType.JSON);
        reqObj.then().spec(resObj.build());
        Response res =reqObj.post("/fund-transfers");
        res.then().log().all();
        JsonPath jsonpathObj = res.jsonPath();
        Assert.assertEquals("requested amount is not available",jsonpathObj.getString("errorCode"));


        //Fund transfer with negative amount
      JSONObject jsonObj =  new JSONObject();
      jsonObj.put("fromAccount","0600140000007");
      jsonObj.put("toAccount","0600140000013");
      jsonObj.put("amount","-100.00");
      RequestSpecification reqOb1 = RestAssured.given();
      reqOb1.baseUri(bankAPIURL);
      reqOb1.header(header);
      reqOb1.body(jsonObj.toString());
      reqOb1.contentType(ContentType.JSON);
      reqOb1.log().all();
      ResponseSpecBuilder resObj1 = new ResponseSpecBuilder();
      resObj1.expectStatusCode(400);
      resObj1.expectContentType(ContentType.JSON);
      Response res1=reqOb1.post("/fund-transfers");
      JsonPath jsonpath1=res1.jsonPath();
      res1.then().log().all();
      Assert.assertEquals("Amount cannot be negative",jsonpath1.getString("errorCode"));


        //Fund transfer when account number is invalid
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("fromAccount","0600140000003");
        jsonObject2.put("toAccount","0600140000013");
        jsonObject2.put("amount","100.00");
        RequestSpecification reqObj2 = RestAssured.given();
        reqObj2.baseUri(bankAPIURL);
        reqObj2.header(header);
        reqObj2.body(jsonObject2.toString());
        reqObj2.contentType(ContentType.JSON);
        reqObj2.log().all();
        ResponseSpecBuilder resObj2 = new ResponseSpecBuilder();
        resObj2.expectStatusCode(400);
        resObj2.expectContentType(ContentType.JSON);
        Response res2=reqObj2.post("/fund-transfers");
        JsonPath jsonpath2 = res2.jsonPath();
        res2.then().log().all();
        Assert.assertEquals("Resource not found on the server",jsonpath2.getString("errorCode"));
    }
*/

}
