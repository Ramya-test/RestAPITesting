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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.example.banking.constants.BankingConstants.bankAPIURL;

public class APIExercise1 {
    String accessToken;
    Integer userId;
    Double accountBalance1;
    Double accountBalance2;

    @DataProvider(name = "AccountDetails")
    public String[][] testProvider() {
        return new String[][]{{
                "0600140000007"
        },
                {
                        "0600140000009"
                },
        };
    }

    @Test(enabled = false)
    public void createUser() throws InterruptedException {
        Header header = new Header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJLY1B6c3dVN0JtWDBpdkZUNjlIYTh6RUV2V2NTTHViYTk3c3djUFFta1IwIn0.eyJleHAiOjE3NjM2NjUzNzgsImlhdCI6MTc2MzY0NzM3OCwianRpIjoiZWRmOTQ0ODctMjYyNi00MjM2LWIyZDUtN2YwMTM4MWY5ZWVmIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4NTcxL3JlYWxtcy9iYW5raW5nLXNlcnZpY2UiLCJhdWQiOlsicmVhbG0tbWFuYWdlbWVudCIsImJyb2tlciIsImFjY291bnQiXSwic3ViIjoiYmRkYmQ5MWUtNWZkMS00ZjRlLTk0ZjItNTU0YjAxMmU1ZDgwIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYmFua2luZy1zZXJ2aWNlLWNsaWVudCIsInNpZCI6IjIzNWRhYjEzLWI3ODAtNDIwOC1iMjc3LTk5OGI3MzY4OWI0NSIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy1iYW5raW5nLXNlcnZpY2UiLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsicmVhbG0tbWFuYWdlbWVudCI6eyJyb2xlcyI6WyJtYW5hZ2UtZXZlbnRzIiwibWFuYWdlLXJlYWxtIiwibWFuYWdlLWlkZW50aXR5LXByb3ZpZGVycyIsImltcGVyc29uYXRpb24iLCJjcmVhdGUtY2xpZW50IiwibWFuYWdlLXVzZXJzIiwibWFuYWdlLWF1dGhvcml6YXRpb24iLCJtYW5hZ2UtY2xpZW50cyIsInF1ZXJ5LWNsaWVudHMiLCJxdWVyeS1ncm91cHMiXX0sImJhbmtpbmctc2VydmljZS1jbGllbnQiOnsicm9sZXMiOlsidW1hX3Byb3RlY3Rpb24iXX0sImJyb2tlciI6eyJyb2xlcyI6WyJyZWFkLXRva2VuIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50Iiwidmlldy1hcHBsaWNhdGlvbnMiLCJ2aWV3LWNvbnNlbnQiLCJ2aWV3LWdyb3VwcyIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwibWFuYWdlLWNvbnNlbnQiLCJkZWxldGUtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIGVtYWlsIHByb2ZpbGUgb2ZmbGluZV9hY2Nlc3MiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6IlJha2VzaCBEIEthYXRrYXIiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJzdXBlcl91c2VyIiwiZ2l2ZW5fbmFtZSI6IlJha2VzaCBEIiwiZmFtaWx5X25hbWUiOiJLYWF0a2FyIiwiZW1haWwiOiJyYWtlc2hka2FhdGthckBnbWFpbC5jb20ifQ.qR_wPoNR0PEzIVIvhL6LrIE9qwlArrZaZ6zoYNyS8Kkn6gyYtpSGaILTjv7Vm2sKKnqE3z6TZIM3n8UOK_CjoOhHhvkv9Jmp4DeXg8hWXNyeZ1wagGAYOztZOklMM4sDu09T1KwlmmqvLHTVYs36lwJywzCXkFphR1kyhfIQPQOmoC-VuqR0zBaRaN37eOdHpw5sHOgSjkTsUXpfPkagiioA9KR3YbUK1FOmD93rLxdBp_XICAk89wV-Lv51VX7gSE0ZfNCZatOFcfiM7hEUA6iCqfpfMPIXx4gnNuVQDM0WMd0aST_ersjQrAPZfAOJMH1P27cMwB1OkMNr8wrhsw");
        RequestSpecification reqSpcObj = RestAssured.given();
        reqSpcObj.baseUri(bankAPIURL);
        reqSpcObj.header(header);
        reqSpcObj.contentType(ContentType.JSON);

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("firstName", "Anush");
        jsonObj.put("lastName", "Kaatkar");
        jsonObj.put("emailId", "Ananya2Rakesh@gmail.com");
        jsonObj.put("contactNumber", "8981950067");
        jsonObj.put("password", "Adam@1234");

        reqSpcObj.body(jsonObj.toString());
        reqSpcObj.log().all();

        ResponseSpecBuilder resObj = new ResponseSpecBuilder();
        resObj.expectStatusCode(200);
        resObj.expectContentType(ContentType.JSON);

        reqSpcObj.then().spec(resObj.build());
        Response res = reqSpcObj.post("/api/users/register");
        JsonPath jsonpathObj = res.jsonPath();
        res.then().log().all();
        Assert.assertNotNull(jsonpathObj.get("userId"));
        userId = jsonpathObj.get("userId");
        Thread.sleep(5000);

    }

    @Test(enabled = false)
    public void updateUser() throws InterruptedException {
        Header header = new Header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJLY1B6c3dVN0JtWDBpdkZUNjlIYTh6RUV2V2NTTHViYTk3c3djUFFta1IwIn0.eyJleHAiOjE3NjM2NjUzNzgsImlhdCI6MTc2MzY0NzM3OCwianRpIjoiZWRmOTQ0ODctMjYyNi00MjM2LWIyZDUtN2YwMTM4MWY5ZWVmIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4NTcxL3JlYWxtcy9iYW5raW5nLXNlcnZpY2UiLCJhdWQiOlsicmVhbG0tbWFuYWdlbWVudCIsImJyb2tlciIsImFjY291bnQiXSwic3ViIjoiYmRkYmQ5MWUtNWZkMS00ZjRlLTk0ZjItNTU0YjAxMmU1ZDgwIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYmFua2luZy1zZXJ2aWNlLWNsaWVudCIsInNpZCI6IjIzNWRhYjEzLWI3ODAtNDIwOC1iMjc3LTk5OGI3MzY4OWI0NSIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy1iYW5raW5nLXNlcnZpY2UiLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsicmVhbG0tbWFuYWdlbWVudCI6eyJyb2xlcyI6WyJtYW5hZ2UtZXZlbnRzIiwibWFuYWdlLXJlYWxtIiwibWFuYWdlLWlkZW50aXR5LXByb3ZpZGVycyIsImltcGVyc29uYXRpb24iLCJjcmVhdGUtY2xpZW50IiwibWFuYWdlLXVzZXJzIiwibWFuYWdlLWF1dGhvcml6YXRpb24iLCJtYW5hZ2UtY2xpZW50cyIsInF1ZXJ5LWNsaWVudHMiLCJxdWVyeS1ncm91cHMiXX0sImJhbmtpbmctc2VydmljZS1jbGllbnQiOnsicm9sZXMiOlsidW1hX3Byb3RlY3Rpb24iXX0sImJyb2tlciI6eyJyb2xlcyI6WyJyZWFkLXRva2VuIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50Iiwidmlldy1hcHBsaWNhdGlvbnMiLCJ2aWV3LWNvbnNlbnQiLCJ2aWV3LWdyb3VwcyIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwibWFuYWdlLWNvbnNlbnQiLCJkZWxldGUtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIGVtYWlsIHByb2ZpbGUgb2ZmbGluZV9hY2Nlc3MiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6IlJha2VzaCBEIEthYXRrYXIiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJzdXBlcl91c2VyIiwiZ2l2ZW5fbmFtZSI6IlJha2VzaCBEIiwiZmFtaWx5X25hbWUiOiJLYWF0a2FyIiwiZW1haWwiOiJyYWtlc2hka2FhdGthckBnbWFpbC5jb20ifQ.qR_wPoNR0PEzIVIvhL6LrIE9qwlArrZaZ6zoYNyS8Kkn6gyYtpSGaILTjv7Vm2sKKnqE3z6TZIM3n8UOK_CjoOhHhvkv9Jmp4DeXg8hWXNyeZ1wagGAYOztZOklMM4sDu09T1KwlmmqvLHTVYs36lwJywzCXkFphR1kyhfIQPQOmoC-VuqR0zBaRaN37eOdHpw5sHOgSjkTsUXpfPkagiioA9KR3YbUK1FOmD93rLxdBp_XICAk89wV-Lv51VX7gSE0ZfNCZatOFcfiM7hEUA6iCqfpfMPIXx4gnNuVQDM0WMd0aST_ersjQrAPZfAOJMH1P27cMwB1OkMNr8wrhsw");

        RequestSpecification reqSpcObj = RestAssured.given();
        reqSpcObj.baseUri(bankAPIURL);
        reqSpcObj.header(header);
        reqSpcObj.contentType(ContentType.JSON);

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("firstName", "Anush");
        jsonObj.put("lastName", "Kaatkar");
        jsonObj.put("emailId", "Ananya2Rakesh@gmail.com");
        jsonObj.put("contactNumber", "8981950067");
        jsonObj.put("password", "Adam@1234");
        jsonObj.put("address", "RailwayLout");
        jsonObj.put("gender", "Male");
        jsonObj.put("occupation", "Engineer");
        jsonObj.put("martialStatus", "Single");
        jsonObj.put("nationality", "Indian");

        reqSpcObj.body(jsonObj.toString());
        reqSpcObj.then().log().all();

        ResponseSpecBuilder resObj = new ResponseSpecBuilder();
       /* resObj.expectStatusCode(200);
        resObj.expectContentType(ContentType.JSON);*/

        reqSpcObj.then().spec(resObj.build());

        Response res = reqSpcObj.put("/api/users/" + userId);
        JsonPath jsonpathObj = res.jsonPath();
        Assert.assertEquals(jsonpathObj.get("responseMessage"), "user updated successfully");
        Thread.sleep(6000);

    }

    @Test(enabled = false)
    public void updateUserStatus() {
        Header header = new Header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJLY1B6c3dVN0JtWDBpdkZUNjlIYTh6RUV2V2NTTHViYTk3c3djUFFta1IwIn0.eyJleHAiOjE3NjM2NjUzNzgsImlhdCI6MTc2MzY0NzM3OCwianRpIjoiZWRmOTQ0ODctMjYyNi00MjM2LWIyZDUtN2YwMTM4MWY5ZWVmIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4NTcxL3JlYWxtcy9iYW5raW5nLXNlcnZpY2UiLCJhdWQiOlsicmVhbG0tbWFuYWdlbWVudCIsImJyb2tlciIsImFjY291bnQiXSwic3ViIjoiYmRkYmQ5MWUtNWZkMS00ZjRlLTk0ZjItNTU0YjAxMmU1ZDgwIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYmFua2luZy1zZXJ2aWNlLWNsaWVudCIsInNpZCI6IjIzNWRhYjEzLWI3ODAtNDIwOC1iMjc3LTk5OGI3MzY4OWI0NSIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy1iYW5raW5nLXNlcnZpY2UiLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsicmVhbG0tbWFuYWdlbWVudCI6eyJyb2xlcyI6WyJtYW5hZ2UtZXZlbnRzIiwibWFuYWdlLXJlYWxtIiwibWFuYWdlLWlkZW50aXR5LXByb3ZpZGVycyIsImltcGVyc29uYXRpb24iLCJjcmVhdGUtY2xpZW50IiwibWFuYWdlLXVzZXJzIiwibWFuYWdlLWF1dGhvcml6YXRpb24iLCJtYW5hZ2UtY2xpZW50cyIsInF1ZXJ5LWNsaWVudHMiLCJxdWVyeS1ncm91cHMiXX0sImJhbmtpbmctc2VydmljZS1jbGllbnQiOnsicm9sZXMiOlsidW1hX3Byb3RlY3Rpb24iXX0sImJyb2tlciI6eyJyb2xlcyI6WyJyZWFkLXRva2VuIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50Iiwidmlldy1hcHBsaWNhdGlvbnMiLCJ2aWV3LWNvbnNlbnQiLCJ2aWV3LWdyb3VwcyIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwibWFuYWdlLWNvbnNlbnQiLCJkZWxldGUtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIGVtYWlsIHByb2ZpbGUgb2ZmbGluZV9hY2Nlc3MiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6IlJha2VzaCBEIEthYXRrYXIiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJzdXBlcl91c2VyIiwiZ2l2ZW5fbmFtZSI6IlJha2VzaCBEIiwiZmFtaWx5X25hbWUiOiJLYWF0a2FyIiwiZW1haWwiOiJyYWtlc2hka2FhdGthckBnbWFpbC5jb20ifQ.qR_wPoNR0PEzIVIvhL6LrIE9qwlArrZaZ6zoYNyS8Kkn6gyYtpSGaILTjv7Vm2sKKnqE3z6TZIM3n8UOK_CjoOhHhvkv9Jmp4DeXg8hWXNyeZ1wagGAYOztZOklMM4sDu09T1KwlmmqvLHTVYs36lwJywzCXkFphR1kyhfIQPQOmoC-VuqR0zBaRaN37eOdHpw5sHOgSjkTsUXpfPkagiioA9KR3YbUK1FOmD93rLxdBp_XICAk89wV-Lv51VX7gSE0ZfNCZatOFcfiM7hEUA6iCqfpfMPIXx4gnNuVQDM0WMd0aST_ersjQrAPZfAOJMH1P27cMwB1OkMNr8wrhsw");
        RequestSpecification reqspObj = RestAssured.given();
        reqspObj.baseUri(bankAPIURL);
        reqspObj.contentType(ContentType.JSON);
        reqspObj.header(header);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("status", "APPROVED");
        reqspObj.body(jsonObj.toString());
        reqspObj.log().all();

        ResponseSpecBuilder resObj = new ResponseSpecBuilder();
        /*resObj.expectStatusCode(200);
        resObj.expectContentType(ContentType.JSON);*/

        reqspObj.then().spec(resObj.build());

        Response res = reqspObj.patch("/api/users/" + userId);
        JsonPath jsonpathObj = res.jsonPath();
        res.then().log().all();
        Assert.assertEquals(jsonpathObj.get("ResponseMessage"), "User updated successfully");

    }

    @Test(enabled = false)
    public void readUserById() {
        Header header = new Header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJLY1B6c3dVN0JtWDBpdkZUNjlIYTh6RUV2V2NTTHViYTk3c3djUFFta1IwIn0.eyJleHAiOjE3NjM2NjUzNzgsImlhdCI6MTc2MzY0NzM3OCwianRpIjoiZWRmOTQ0ODctMjYyNi00MjM2LWIyZDUtN2YwMTM4MWY5ZWVmIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4NTcxL3JlYWxtcy9iYW5raW5nLXNlcnZpY2UiLCJhdWQiOlsicmVhbG0tbWFuYWdlbWVudCIsImJyb2tlciIsImFjY291bnQiXSwic3ViIjoiYmRkYmQ5MWUtNWZkMS00ZjRlLTk0ZjItNTU0YjAxMmU1ZDgwIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYmFua2luZy1zZXJ2aWNlLWNsaWVudCIsInNpZCI6IjIzNWRhYjEzLWI3ODAtNDIwOC1iMjc3LTk5OGI3MzY4OWI0NSIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy1iYW5raW5nLXNlcnZpY2UiLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsicmVhbG0tbWFuYWdlbWVudCI6eyJyb2xlcyI6WyJtYW5hZ2UtZXZlbnRzIiwibWFuYWdlLXJlYWxtIiwibWFuYWdlLWlkZW50aXR5LXByb3ZpZGVycyIsImltcGVyc29uYXRpb24iLCJjcmVhdGUtY2xpZW50IiwibWFuYWdlLXVzZXJzIiwibWFuYWdlLWF1dGhvcml6YXRpb24iLCJtYW5hZ2UtY2xpZW50cyIsInF1ZXJ5LWNsaWVudHMiLCJxdWVyeS1ncm91cHMiXX0sImJhbmtpbmctc2VydmljZS1jbGllbnQiOnsicm9sZXMiOlsidW1hX3Byb3RlY3Rpb24iXX0sImJyb2tlciI6eyJyb2xlcyI6WyJyZWFkLXRva2VuIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50Iiwidmlldy1hcHBsaWNhdGlvbnMiLCJ2aWV3LWNvbnNlbnQiLCJ2aWV3LWdyb3VwcyIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwibWFuYWdlLWNvbnNlbnQiLCJkZWxldGUtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIGVtYWlsIHByb2ZpbGUgb2ZmbGluZV9hY2Nlc3MiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6IlJha2VzaCBEIEthYXRrYXIiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJzdXBlcl91c2VyIiwiZ2l2ZW5fbmFtZSI6IlJha2VzaCBEIiwiZmFtaWx5X25hbWUiOiJLYWF0a2FyIiwiZW1haWwiOiJyYWtlc2hka2FhdGthckBnbWFpbC5jb20ifQ.qR_wPoNR0PEzIVIvhL6LrIE9qwlArrZaZ6zoYNyS8Kkn6gyYtpSGaILTjv7Vm2sKKnqE3z6TZIM3n8UOK_CjoOhHhvkv9Jmp4DeXg8hWXNyeZ1wagGAYOztZOklMM4sDu09T1KwlmmqvLHTVYs36lwJywzCXkFphR1kyhfIQPQOmoC-VuqR0zBaRaN37eOdHpw5sHOgSjkTsUXpfPkagiioA9KR3YbUK1FOmD93rLxdBp_XICAk89wV-Lv51VX7gSE0ZfNCZatOFcfiM7hEUA6iCqfpfMPIXx4gnNuVQDM0WMd0aST_ersjQrAPZfAOJMH1P27cMwB1OkMNr8wrhsw");

        RequestSpecification reqSpObj = RestAssured.given();
        reqSpObj.header(header);
        reqSpObj.baseUri(bankAPIURL);
        reqSpObj.contentType(ContentType.JSON);
        reqSpObj.log().all();

        ResponseSpecBuilder resSpcObj = new ResponseSpecBuilder();
        resSpcObj.expectStatusCode(200);
        resSpcObj.expectContentType(ContentType.JSON);

        reqSpObj.then().spec(resSpcObj.build());

        Response res = reqSpObj.get("/api/users/" + userId);
        JsonPath jsonpathObj = res.jsonPath();
        res.then().log().all();
        Assert.assertEquals(jsonpathObj.get("userId"), userId);
        Assert.assertEquals(jsonpathObj.get("userProfileDto.firstName"), "Anush");

    }



    @Test(dependsOnMethods = "getAccountDetails", enabled = false)
    public void fundTransfer() {
        Header header = new Header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJLY1B6c3dVN0JtWDBpdkZUNjlIYTh6RUV2V2NTTHViYTk3c3djUFFta1IwIn0.eyJleHAiOjE3NjM4NDI0OTYsImlhdCI6MTc2MzgyNDQ5NiwianRpIjoiYzZhOThmMWEtMzA0ZC00Y2I3LThjZDQtMWYzMDVjZjdjMDBkIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4NTcxL3JlYWxtcy9iYW5raW5nLXNlcnZpY2UiLCJhdWQiOlsicmVhbG0tbWFuYWdlbWVudCIsImJyb2tlciIsImFjY291bnQiXSwic3ViIjoiYmRkYmQ5MWUtNWZkMS00ZjRlLTk0ZjItNTU0YjAxMmU1ZDgwIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYmFua2luZy1zZXJ2aWNlLWNsaWVudCIsInNpZCI6ImY2ZGM0MWIwLTI1OGEtNDA1OC04YTg4LTkzMjMxM2ZhZGJmNiIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy1iYW5raW5nLXNlcnZpY2UiLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsicmVhbG0tbWFuYWdlbWVudCI6eyJyb2xlcyI6WyJtYW5hZ2UtZXZlbnRzIiwibWFuYWdlLXJlYWxtIiwibWFuYWdlLWlkZW50aXR5LXByb3ZpZGVycyIsImltcGVyc29uYXRpb24iLCJjcmVhdGUtY2xpZW50IiwibWFuYWdlLXVzZXJzIiwibWFuYWdlLWF1dGhvcml6YXRpb24iLCJtYW5hZ2UtY2xpZW50cyIsInF1ZXJ5LWNsaWVudHMiLCJxdWVyeS1ncm91cHMiXX0sImJhbmtpbmctc2VydmljZS1jbGllbnQiOnsicm9sZXMiOlsidW1hX3Byb3RlY3Rpb24iXX0sImJyb2tlciI6eyJyb2xlcyI6WyJyZWFkLXRva2VuIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50Iiwidmlldy1hcHBsaWNhdGlvbnMiLCJ2aWV3LWNvbnNlbnQiLCJ2aWV3LWdyb3VwcyIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwibWFuYWdlLWNvbnNlbnQiLCJkZWxldGUtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIGVtYWlsIHByb2ZpbGUgb2ZmbGluZV9hY2Nlc3MiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6IlJha2VzaCBEIEthYXRrYXIiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJzdXBlcl91c2VyIiwiZ2l2ZW5fbmFtZSI6IlJha2VzaCBEIiwiZmFtaWx5X25hbWUiOiJLYWF0a2FyIiwiZW1haWwiOiJyYWtlc2hka2FhdGthckBnbWFpbC5jb20ifQ.TyKOpoooLGw3Q668NYxhf9XuX1JdHdfxWCvaXwVmPBE4meIVfDcQEDGSzS0bpJ1FgWIiu7TgfqyE5Ng9NNeSBzBjt0JgQg3pk4Mz4Kg2Q6angvxDKJK3xDqaSKju2u4ZlTypYc_j7-gTbc2acHZou0Xm7MshAKI-Blbyo2t3hsVNmFky5gRkqupydp9pjCMmznSxGLJyb22_rizZi-SuFm9IImxy5X7bpZZetEqWDzRpAtXbhZQUbDP1Pwmo9dC7DKHbjmbfFXmEpV-mbagtV9gL3-s3CDHuOR8s47APDpKonfOnlNqPoT6k5E36Kc8r2QrxYYsogYbI0GbxaQBdQQ");
        RequestSpecification reqSpcObj = RestAssured.given();
        reqSpcObj.header(header);
        reqSpcObj.baseUri(bankAPIURL);
        reqSpcObj.contentType(ContentType.JSON);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("fromAccount", "0600140000007");
        jsonObj.put("toAccount", "0600140000008");
        jsonObj.put("amount", "100");
        reqSpcObj.body(jsonObj.toString());
        reqSpcObj.log().all();

        ResponseSpecBuilder resSpcObj = new ResponseSpecBuilder();
        resSpcObj.expectStatusCode(201);
        resSpcObj.expectContentType(ContentType.JSON);

        reqSpcObj.then().spec(resSpcObj.build());

        Response res = reqSpcObj.post("/fund-transfers");
        JsonPath jsonpathObj = res.jsonPath();
        res.then().log().all();
        Assert.assertEquals(jsonpathObj.get("message"), "Fund transfer was successful");
        accountBalance1 = accountBalance1 - 100;

    }

    @Test(dependsOnMethods = "fundTransfer", enabled = false)
    public void getAccountDetailsAfterTransfer() {
        Header header = new Header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJLY1B6c3dVN0JtWDBpdkZUNjlIYTh6RUV2V2NTTHViYTk3c3djUFFta1IwIn0.eyJleHAiOjE3NjM4NDI0OTYsImlhdCI6MTc2MzgyNDQ5NiwianRpIjoiYzZhOThmMWEtMzA0ZC00Y2I3LThjZDQtMWYzMDVjZjdjMDBkIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4NTcxL3JlYWxtcy9iYW5raW5nLXNlcnZpY2UiLCJhdWQiOlsicmVhbG0tbWFuYWdlbWVudCIsImJyb2tlciIsImFjY291bnQiXSwic3ViIjoiYmRkYmQ5MWUtNWZkMS00ZjRlLTk0ZjItNTU0YjAxMmU1ZDgwIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYmFua2luZy1zZXJ2aWNlLWNsaWVudCIsInNpZCI6ImY2ZGM0MWIwLTI1OGEtNDA1OC04YTg4LTkzMjMxM2ZhZGJmNiIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy1iYW5raW5nLXNlcnZpY2UiLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsicmVhbG0tbWFuYWdlbWVudCI6eyJyb2xlcyI6WyJtYW5hZ2UtZXZlbnRzIiwibWFuYWdlLXJlYWxtIiwibWFuYWdlLWlkZW50aXR5LXByb3ZpZGVycyIsImltcGVyc29uYXRpb24iLCJjcmVhdGUtY2xpZW50IiwibWFuYWdlLXVzZXJzIiwibWFuYWdlLWF1dGhvcml6YXRpb24iLCJtYW5hZ2UtY2xpZW50cyIsInF1ZXJ5LWNsaWVudHMiLCJxdWVyeS1ncm91cHMiXX0sImJhbmtpbmctc2VydmljZS1jbGllbnQiOnsicm9sZXMiOlsidW1hX3Byb3RlY3Rpb24iXX0sImJyb2tlciI6eyJyb2xlcyI6WyJyZWFkLXRva2VuIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50Iiwidmlldy1hcHBsaWNhdGlvbnMiLCJ2aWV3LWNvbnNlbnQiLCJ2aWV3LWdyb3VwcyIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwibWFuYWdlLWNvbnNlbnQiLCJkZWxldGUtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIGVtYWlsIHByb2ZpbGUgb2ZmbGluZV9hY2Nlc3MiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6IlJha2VzaCBEIEthYXRrYXIiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJzdXBlcl91c2VyIiwiZ2l2ZW5fbmFtZSI6IlJha2VzaCBEIiwiZmFtaWx5X25hbWUiOiJLYWF0a2FyIiwiZW1haWwiOiJyYWtlc2hka2FhdGthckBnbWFpbC5jb20ifQ.TyKOpoooLGw3Q668NYxhf9XuX1JdHdfxWCvaXwVmPBE4meIVfDcQEDGSzS0bpJ1FgWIiu7TgfqyE5Ng9NNeSBzBjt0JgQg3pk4Mz4Kg2Q6angvxDKJK3xDqaSKju2u4ZlTypYc_j7-gTbc2acHZou0Xm7MshAKI-Blbyo2t3hsVNmFky5gRkqupydp9pjCMmznSxGLJyb22_rizZi-SuFm9IImxy5X7bpZZetEqWDzRpAtXbhZQUbDP1Pwmo9dC7DKHbjmbfFXmEpV-mbagtV9gL3-s3CDHuOR8s47APDpKonfOnlNqPoT6k5E36Kc8r2QrxYYsogYbI0GbxaQBdQQ");
        RequestSpecification reqSpcObj = RestAssured.given();
        reqSpcObj.header(header);
        reqSpcObj.baseUri(bankAPIURL);

        reqSpcObj.log().all();

        ResponseSpecBuilder resSpcObj = new ResponseSpecBuilder();
        resSpcObj.expectStatusCode(200);
        resSpcObj.expectContentType(ContentType.JSON);

        reqSpcObj.then().spec(resSpcObj.build());

        reqSpcObj.queryParam("accountNumber", "0600140000007");
        Response res = reqSpcObj.get("/accounts");
        JsonPath jsonpathObj = res.jsonPath();
        res.then().log().all();
        Double delta = 0.0;
        Assert.assertEquals(jsonpathObj.getDouble("availableBalance"), accountBalance1, delta);

    }


    @Test(dependsOnMethods = "depositAmount",enabled = false)
    public void getAccountDetailsAfterDeposit() {
        Header header = new Header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJLY1B6c3dVN0JtWDBpdkZUNjlIYTh6RUV2V2NTTHViYTk3c3djUFFta1IwIn0.eyJleHAiOjE3NjQwMTE0NzEsImlhdCI6MTc2Mzk5MzQ3MSwianRpIjoiMDFmY2YwMjItNmUwYy00ZDI1LWFlNDEtOTg4MTI0Y2RhOWZiIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4NTcxL3JlYWxtcy9iYW5raW5nLXNlcnZpY2UiLCJhdWQiOlsicmVhbG0tbWFuYWdlbWVudCIsImJyb2tlciIsImFjY291bnQiXSwic3ViIjoiYmRkYmQ5MWUtNWZkMS00ZjRlLTk0ZjItNTU0YjAxMmU1ZDgwIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYmFua2luZy1zZXJ2aWNlLWNsaWVudCIsInNpZCI6ImY2OGY5YTY2LWFiZTEtNDRjMy04ZTVlLWMwMjFkZjhiY2M2NyIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy1iYW5raW5nLXNlcnZpY2UiLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsicmVhbG0tbWFuYWdlbWVudCI6eyJyb2xlcyI6WyJtYW5hZ2UtZXZlbnRzIiwibWFuYWdlLXJlYWxtIiwibWFuYWdlLWlkZW50aXR5LXByb3ZpZGVycyIsImltcGVyc29uYXRpb24iLCJjcmVhdGUtY2xpZW50IiwibWFuYWdlLXVzZXJzIiwibWFuYWdlLWF1dGhvcml6YXRpb24iLCJtYW5hZ2UtY2xpZW50cyIsInF1ZXJ5LWNsaWVudHMiLCJxdWVyeS1ncm91cHMiXX0sImJhbmtpbmctc2VydmljZS1jbGllbnQiOnsicm9sZXMiOlsidW1hX3Byb3RlY3Rpb24iXX0sImJyb2tlciI6eyJyb2xlcyI6WyJyZWFkLXRva2VuIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50Iiwidmlldy1hcHBsaWNhdGlvbnMiLCJ2aWV3LWNvbnNlbnQiLCJ2aWV3LWdyb3VwcyIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwibWFuYWdlLWNvbnNlbnQiLCJkZWxldGUtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIGVtYWlsIHByb2ZpbGUgb2ZmbGluZV9hY2Nlc3MiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6IlJha2VzaCBEIEthYXRrYXIiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJzdXBlcl91c2VyIiwiZ2l2ZW5fbmFtZSI6IlJha2VzaCBEIiwiZmFtaWx5X25hbWUiOiJLYWF0a2FyIiwiZW1haWwiOiJyYWtlc2hka2FhdGthckBnbWFpbC5jb20ifQ.fB0mDQm4MjDAYXnT_II9P2PMR3LatMaTZdhQFoUSbKIjYSrD9CZYOkYNW7T78jLcQqMYzI4uGT2tJuSxNWeRiOOvjukY57Y7RkCxzMx1-1zXS2F012l3G97CGGPetbOSM26dCPaSFVyCXqwezYijBpHslyDmamwis4s_fxXf8WZZxyoikTZHEfy7XoirLi8nYopLgkF39iDWyk-evwRSmesG-GWD4S3xpnD8n5mqz4d62l-xM-sMw4mrOGiuDiDGL8GLy6_YEUx9SMm88podVJ78Y8s0uvjBkR1JRJrDDBHYqqlyTE-FyK7fW1j-Ugv6Jv7E06gsMmbdbzACVrbXHw");
        RequestSpecification reqSpcObj = RestAssured.given();
        reqSpcObj.header(header);
        reqSpcObj.baseUri(bankAPIURL);

        reqSpcObj.log().all();

        ResponseSpecBuilder resSpcObj = new ResponseSpecBuilder();
        resSpcObj.expectStatusCode(200);
        resSpcObj.expectContentType(ContentType.JSON);

        reqSpcObj.then().spec(resSpcObj.build());

        reqSpcObj.queryParam("accountNumber", "0600140000007");
        Response res = reqSpcObj.get("/accounts");
        JsonPath jsonpathObj = res.jsonPath();
        res.then().log().all();
        Double delta = 0.0;
        Assert.assertEquals(jsonpathObj.getDouble("availableBalance"), accountBalance1, delta);

    }

    @Test(dataProvider = "AccountDetails",priority = 1)
    public void getAccountDetails(String accountNumber) {
        Header header = new Header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJLY1B6c3dVN0JtWDBpdkZUNjlIYTh6RUV2V2NTTHViYTk3c3djUFFta1IwIn0.eyJleHAiOjE3NjQ2Mjk1MzUsImlhdCI6MTc2NDYxMTUzNSwianRpIjoiOTE0MjQ5NTItMzFkNS00ZDg5LThhNTItMTA5NWZlZWZhNDQxIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4NTcxL3JlYWxtcy9iYW5raW5nLXNlcnZpY2UiLCJhdWQiOlsicmVhbG0tbWFuYWdlbWVudCIsImJyb2tlciIsImFjY291bnQiXSwic3ViIjoiYmRkYmQ5MWUtNWZkMS00ZjRlLTk0ZjItNTU0YjAxMmU1ZDgwIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYmFua2luZy1zZXJ2aWNlLWNsaWVudCIsInNpZCI6ImVhMDFmZWJmLTllNWYtNDU2Zi1iNWQ0LWRmOWFmZDk3ZTIwNCIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy1iYW5raW5nLXNlcnZpY2UiLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsicmVhbG0tbWFuYWdlbWVudCI6eyJyb2xlcyI6WyJtYW5hZ2UtZXZlbnRzIiwibWFuYWdlLXJlYWxtIiwibWFuYWdlLWlkZW50aXR5LXByb3ZpZGVycyIsImltcGVyc29uYXRpb24iLCJjcmVhdGUtY2xpZW50IiwibWFuYWdlLXVzZXJzIiwibWFuYWdlLWF1dGhvcml6YXRpb24iLCJtYW5hZ2UtY2xpZW50cyIsInF1ZXJ5LWNsaWVudHMiLCJxdWVyeS1ncm91cHMiXX0sImJhbmtpbmctc2VydmljZS1jbGllbnQiOnsicm9sZXMiOlsidW1hX3Byb3RlY3Rpb24iXX0sImJyb2tlciI6eyJyb2xlcyI6WyJyZWFkLXRva2VuIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50Iiwidmlldy1hcHBsaWNhdGlvbnMiLCJ2aWV3LWNvbnNlbnQiLCJ2aWV3LWdyb3VwcyIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwibWFuYWdlLWNvbnNlbnQiLCJkZWxldGUtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIGVtYWlsIHByb2ZpbGUgb2ZmbGluZV9hY2Nlc3MiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6IlJha2VzaCBEIEthYXRrYXIiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJzdXBlcl91c2VyIiwiZ2l2ZW5fbmFtZSI6IlJha2VzaCBEIiwiZmFtaWx5X25hbWUiOiJLYWF0a2FyIiwiZW1haWwiOiJyYWtlc2hka2FhdGthckBnbWFpbC5jb20ifQ.NZrtxFXoZjbCQvCq7SztRSlEa74btZZzZ-BCgHeEUmim0TA6cjNbZI9hKtiDC-rpz2rfLDD6Hp1irE9ZqE0qwgb7bFCr_I-9MCzLwQ1moTWXxXBgJytOAjLBbp9ieCY9Gfy8MhIpX6MuZhNGo5OP7MaVm-XYu3SBcN0lBAWuiTuWQEc-junWTvNS3I3sL2vikKkIaAkaNw87WBkbquLOzOTxlAtnhZ7z90DSa1Z6j78dDxQ0mxXnnchmQgOsTruBxK6aflqkTqN-fx6p1HSGFl-gihfu7IiEFHdXsggwN1-SmVpaQtDPTuqRCO4mc_i76gjh4loHo0aRFZ113GHi8Q");
        RequestSpecification reqSpcObj = RestAssured.given();
        reqSpcObj.header(header);
        reqSpcObj.baseUri(bankAPIURL);

        reqSpcObj.log().all();

        ResponseSpecBuilder resSpcObj = new ResponseSpecBuilder();
        resSpcObj.expectStatusCode(200);
        resSpcObj.expectContentType(ContentType.JSON);

        reqSpcObj.then().spec(resSpcObj.build());

        reqSpcObj.queryParam("accountNumber", accountNumber);
        Response res = reqSpcObj.get("/accounts");
        JsonPath jsonpathObj = res.jsonPath();
        res.then().log().all();
        if(accountNumber=="0600140000007"){
            accountBalance1 = jsonpathObj.getDouble("availableBalance");
        } else if (accountNumber=="0600140000009") {
            accountBalance2 = jsonpathObj.getDouble("availableBalance");
        }

    }
    
// dated 30/11/2025
    @Test(dependsOnMethods = "getAccountDetails")
    public void transactionFundTranferDepositWithdrawal() {
        Header header = new Header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJLY1B6c3dVN0JtWDBpdkZUNjlIYTh6RUV2V2NTTHViYTk3c3djUFFta1IwIn0.eyJleHAiOjE3NjQ2Mjk1MzUsImlhdCI6MTc2NDYxMTUzNSwianRpIjoiOTE0MjQ5NTItMzFkNS00ZDg5LThhNTItMTA5NWZlZWZhNDQxIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4NTcxL3JlYWxtcy9iYW5raW5nLXNlcnZpY2UiLCJhdWQiOlsicmVhbG0tbWFuYWdlbWVudCIsImJyb2tlciIsImFjY291bnQiXSwic3ViIjoiYmRkYmQ5MWUtNWZkMS00ZjRlLTk0ZjItNTU0YjAxMmU1ZDgwIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYmFua2luZy1zZXJ2aWNlLWNsaWVudCIsInNpZCI6ImVhMDFmZWJmLTllNWYtNDU2Zi1iNWQ0LWRmOWFmZDk3ZTIwNCIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy1iYW5raW5nLXNlcnZpY2UiLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsicmVhbG0tbWFuYWdlbWVudCI6eyJyb2xlcyI6WyJtYW5hZ2UtZXZlbnRzIiwibWFuYWdlLXJlYWxtIiwibWFuYWdlLWlkZW50aXR5LXByb3ZpZGVycyIsImltcGVyc29uYXRpb24iLCJjcmVhdGUtY2xpZW50IiwibWFuYWdlLXVzZXJzIiwibWFuYWdlLWF1dGhvcml6YXRpb24iLCJtYW5hZ2UtY2xpZW50cyIsInF1ZXJ5LWNsaWVudHMiLCJxdWVyeS1ncm91cHMiXX0sImJhbmtpbmctc2VydmljZS1jbGllbnQiOnsicm9sZXMiOlsidW1hX3Byb3RlY3Rpb24iXX0sImJyb2tlciI6eyJyb2xlcyI6WyJyZWFkLXRva2VuIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50Iiwidmlldy1hcHBsaWNhdGlvbnMiLCJ2aWV3LWNvbnNlbnQiLCJ2aWV3LWdyb3VwcyIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwibWFuYWdlLWNvbnNlbnQiLCJkZWxldGUtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIGVtYWlsIHByb2ZpbGUgb2ZmbGluZV9hY2Nlc3MiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6IlJha2VzaCBEIEthYXRrYXIiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJzdXBlcl91c2VyIiwiZ2l2ZW5fbmFtZSI6IlJha2VzaCBEIiwiZmFtaWx5X25hbWUiOiJLYWF0a2FyIiwiZW1haWwiOiJyYWtlc2hka2FhdGthckBnbWFpbC5jb20ifQ.NZrtxFXoZjbCQvCq7SztRSlEa74btZZzZ-BCgHeEUmim0TA6cjNbZI9hKtiDC-rpz2rfLDD6Hp1irE9ZqE0qwgb7bFCr_I-9MCzLwQ1moTWXxXBgJytOAjLBbp9ieCY9Gfy8MhIpX6MuZhNGo5OP7MaVm-XYu3SBcN0lBAWuiTuWQEc-junWTvNS3I3sL2vikKkIaAkaNw87WBkbquLOzOTxlAtnhZ7z90DSa1Z6j78dDxQ0mxXnnchmQgOsTruBxK6aflqkTqN-fx6p1HSGFl-gihfu7IiEFHdXsggwN1-SmVpaQtDPTuqRCO4mc_i76gjh4loHo0aRFZ113GHi8Q");
        RequestSpecification reqSpcObj = RestAssured.given();
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("fromAccount", "0600140000007");
        jsonObj.put("toAccount", "0600140000009");
        jsonObj.put("amount", "1000");
        reqSpcObj.baseUri(bankAPIURL);
        reqSpcObj.header(header);
        reqSpcObj.contentType(ContentType.JSON);
        reqSpcObj.body(jsonObj.toString());
        reqSpcObj.log().all();

        ResponseSpecBuilder resSpcObj = new ResponseSpecBuilder();
        resSpcObj.expectContentType(ContentType.JSON);
        resSpcObj.expectStatusCode(201);
        reqSpcObj.then().spec(resSpcObj.build());

        Response res = reqSpcObj.post("/fund-transfers");
        JsonPath jsonpathObj = res.jsonPath();
        res.then().log().all();

        Assert.assertEquals(jsonpathObj.getString("message"), "Fund transfer was successful");

        //Deposit workflow


        RequestSpecification reqSpcObj1 = RestAssured.given();
        reqSpcObj1.header(header);
        reqSpcObj1.baseUri(bankAPIURL);
        reqSpcObj1.contentType(ContentType.JSON);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("accountId", "0600140000007");
        jsonObject.put("transactionType", "DEPOSIT");
        jsonObject.put("amount", "1000");
        jsonObject.put("description", "1000 has successfully credited to account");
        reqSpcObj1.body(jsonObject.toString());
        reqSpcObj1.log().all();

        ResponseSpecBuilder resSpcObj1 = new ResponseSpecBuilder();
        resSpcObj1.expectStatusCode(200);
        resSpcObj1.expectContentType(ContentType.JSON);

        reqSpcObj1.then().spec(resSpcObj1.build());

        Response res1 = reqSpcObj1.post("/transactions");
        JsonPath jsonPathObj = res1.jsonPath();
        res1.then().log().all();
        Assert.assertEquals(jsonPathObj.getString("status"), "COMPLETED");
        Assert.assertEquals(jsonPathObj.getString("comments"), "1000 has successfully credited to account");
        accountBalance1 = accountBalance1 + 1000;

        //Withdrawal workflow

        RequestSpecification reqSpcObj2 = RestAssured.given();
        reqSpcObj2.header(header);
        reqSpcObj2.baseUri(bankAPIURL);
        reqSpcObj2.contentType(ContentType.JSON);
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("accountId", "0600140000009");
        jsonObject1.put("transactionType", "WITHDRAWAL");
        jsonObject1.put("amount", "1000");
        jsonObject1.put("description", "1000 has successfully debited to account");
        reqSpcObj2.body(jsonObject1.toString());
        reqSpcObj2.log().all();

        ResponseSpecBuilder resSpcObj2 = new ResponseSpecBuilder();
        resSpcObj2.expectStatusCode(200);
        resSpcObj2.expectContentType(ContentType.JSON);

        reqSpcObj2.then().spec(resSpcObj2.build());

        Response res2 = reqSpcObj2.post("/transactions");
        JsonPath jsonPathObj1 = res2.jsonPath();
        res1.then().log().all();
        Assert.assertEquals(jsonPathObj1.getString("status"), "COMPLETED");
        Assert.assertEquals(jsonPathObj1.getString("comments"), "1000 has successfully debited to account");
        accountBalance2 = accountBalance2 - 1000;

    }

    @Test(dependsOnMethods = "getAccountDetails",enabled = false)
    public void depositAmount() {
        Header header = new Header("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJLY1B6c3dVN0JtWDBpdkZUNjlIYTh6RUV2V2NTTHViYTk3c3djUFFta1IwIn0.eyJleHAiOjE3NjQwMTE0NzEsImlhdCI6MTc2Mzk5MzQ3MSwianRpIjoiMDFmY2YwMjItNmUwYy00ZDI1LWFlNDEtOTg4MTI0Y2RhOWZiIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4NTcxL3JlYWxtcy9iYW5raW5nLXNlcnZpY2UiLCJhdWQiOlsicmVhbG0tbWFuYWdlbWVudCIsImJyb2tlciIsImFjY291bnQiXSwic3ViIjoiYmRkYmQ5MWUtNWZkMS00ZjRlLTk0ZjItNTU0YjAxMmU1ZDgwIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYmFua2luZy1zZXJ2aWNlLWNsaWVudCIsInNpZCI6ImY2OGY5YTY2LWFiZTEtNDRjMy04ZTVlLWMwMjFkZjhiY2M2NyIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy1iYW5raW5nLXNlcnZpY2UiLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsicmVhbG0tbWFuYWdlbWVudCI6eyJyb2xlcyI6WyJtYW5hZ2UtZXZlbnRzIiwibWFuYWdlLXJlYWxtIiwibWFuYWdlLWlkZW50aXR5LXByb3ZpZGVycyIsImltcGVyc29uYXRpb24iLCJjcmVhdGUtY2xpZW50IiwibWFuYWdlLXVzZXJzIiwibWFuYWdlLWF1dGhvcml6YXRpb24iLCJtYW5hZ2UtY2xpZW50cyIsInF1ZXJ5LWNsaWVudHMiLCJxdWVyeS1ncm91cHMiXX0sImJhbmtpbmctc2VydmljZS1jbGllbnQiOnsicm9sZXMiOlsidW1hX3Byb3RlY3Rpb24iXX0sImJyb2tlciI6eyJyb2xlcyI6WyJyZWFkLXRva2VuIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50Iiwidmlldy1hcHBsaWNhdGlvbnMiLCJ2aWV3LWNvbnNlbnQiLCJ2aWV3LWdyb3VwcyIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwibWFuYWdlLWNvbnNlbnQiLCJkZWxldGUtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIGVtYWlsIHByb2ZpbGUgb2ZmbGluZV9hY2Nlc3MiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6IlJha2VzaCBEIEthYXRrYXIiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJzdXBlcl91c2VyIiwiZ2l2ZW5fbmFtZSI6IlJha2VzaCBEIiwiZmFtaWx5X25hbWUiOiJLYWF0a2FyIiwiZW1haWwiOiJyYWtlc2hka2FhdGthckBnbWFpbC5jb20ifQ.fB0mDQm4MjDAYXnT_II9P2PMR3LatMaTZdhQFoUSbKIjYSrD9CZYOkYNW7T78jLcQqMYzI4uGT2tJuSxNWeRiOOvjukY57Y7RkCxzMx1-1zXS2F012l3G97CGGPetbOSM26dCPaSFVyCXqwezYijBpHslyDmamwis4s_fxXf8WZZxyoikTZHEfy7XoirLi8nYopLgkF39iDWyk-evwRSmesG-GWD4S3xpnD8n5mqz4d62l-xM-sMw4mrOGiuDiDGL8GLy6_YEUx9SMm88podVJ78Y8s0uvjBkR1JRJrDDBHYqqlyTE-FyK7fW1j-Ugv6Jv7E06gsMmbdbzACVrbXHw");
        RequestSpecification reqSpcObj = RestAssured.given();
        reqSpcObj.header(header);
        reqSpcObj.baseUri(bankAPIURL);
        reqSpcObj.contentType(ContentType.JSON);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("accountId", "0600140000007");
        jsonObject.put("transactionType", "DEPOSIT");
        jsonObject.put("amount", "10000");
        jsonObject.put("description", "10000 has successfully credited to account");
        reqSpcObj.body(jsonObject.toString());
        reqSpcObj.log().all();

        ResponseSpecBuilder resSpcObj = new ResponseSpecBuilder();
        resSpcObj.expectStatusCode(200);
        resSpcObj.expectContentType(ContentType.JSON);

        reqSpcObj.then().spec(resSpcObj.build());

        Response res = reqSpcObj.post("/transactions");
        JsonPath jsonPathObj = res.jsonPath();
        res.then().log().all();
        Assert.assertEquals(jsonPathObj.getString("status"), "COMPLETED");
        Assert.assertEquals(jsonPathObj.getString("comments"), "10000 has successfully credited to account");
        accountBalance1 = accountBalance1 + 10000;

    }



}
