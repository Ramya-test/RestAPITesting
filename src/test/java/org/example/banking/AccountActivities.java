package org.example.banking;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.spi.ResourceBundleProvider;

import static org.example.banking.constants.BankingConstants.bankAPIURL;

public class AccountActivities {
String accessToken = "Bearer ";
String AccountID;
String transactionID;

@DataProvider(name="InputFileValues")
public String[][] inputFileValues(){
    return new String[][]{
            {
                "super_user","Ganesha@1992","SAVINGS_ACCOUNT","56","DEPOSIT","900000","900000 is deposited successfully"
            }

             };
}
@DataProvider(name ="providerforFundTransfer")
public String[][] inputFileforFundTransfer(){
    return new String[][] {
            {
              "0600140000007","0600140000008","4000"
            }
    };
}

    @Test(dataProvider = "InputFileValues",priority = 0)
    public void adminLogin(String emailId, String password, String accountType , String userid, String TransactionType, String Amount, String Desc)
    {
        RequestSpecification reqspcObj = RestAssured.given();
        reqspcObj.baseUri(bankAPIURL);
        reqspcObj.contentType(ContentType.JSON);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("emailId",emailId);
        jsonObj.put("password",password);
        reqspcObj.body(jsonObj.toString());
        reqspcObj.log().all();
        ResponseSpecBuilder resObj = new ResponseSpecBuilder();
        resObj.expectStatusCode(200);
        resObj.expectContentType(ContentType.JSON);
        reqspcObj.then().spec(resObj.build());
        Response res = reqspcObj.post("/api/users/login");
        JsonPath jsonpathObj = res.jsonPath();
        res.then().log().all();
        accessToken = accessToken + jsonpathObj.getString("access_token");
        Assert.assertFalse(accessToken.isEmpty());
    }

    @Test(dataProvider = "InputFileValues",dependsOnMethods = "adminLogin",priority = 1,enabled = false)
    public void createAccount(String emailId, String password, String accountType , String userid, String TransactionType, String Amount, String Desc)
    {
    Header header = new Header("Authorization", accessToken);
    RequestSpecification reqSpcObj = RestAssured.given();
    reqSpcObj.baseUri(bankAPIURL);
    reqSpcObj.header(header);
    reqSpcObj.contentType(ContentType.JSON);
    JSONObject jsonObj = new JSONObject();
    jsonObj.put("accountType",accountType);
    jsonObj.put("userId",userid);
    reqSpcObj.body(jsonObj.toString());
    reqSpcObj.log().all();
    ResponseSpecBuilder resSpcObj = new ResponseSpecBuilder();
    resSpcObj.expectStatusCode(201);
    resSpcObj.expectContentType(ContentType.JSON);
    Response resObj = reqSpcObj.post("/accounts");
    resObj.then().log().all();
    JsonPath jsonpathObj = resObj.jsonPath();
    AccountID = jsonpathObj.getString("message");
    Assert.assertFalse(AccountID.isEmpty());
    }

    @Test(dataProvider = "InputFileValues",dependsOnMethods = "createAccount", priority = 2,enabled = false)
    public void makeTransaction(String emailId, String password, String accountType , String userid, String TransactionType, String Amount, String Desc){
    Header header = new Header("Authorization",accessToken);
    JSONObject jsonObj = new JSONObject();
    RequestSpecification reqSpcObj = RestAssured.given();
    jsonObj.put("accountId",AccountID);
    jsonObj.put("transactionType",TransactionType);
    jsonObj.put("amount",Amount);
    jsonObj.put("description",Desc);
    reqSpcObj.baseUri(bankAPIURL);
    reqSpcObj.header(header);
    reqSpcObj.contentType(ContentType.JSON);
    reqSpcObj.body(jsonObj.toString());
    reqSpcObj.log().all();
    ResponseSpecBuilder resSpcObj = new ResponseSpecBuilder();
    resSpcObj.expectStatusCode(200);
    resSpcObj.expectContentType(ContentType.JSON);
    reqSpcObj.then().spec(resSpcObj.build());
    Response res = reqSpcObj.post("/transactions");
    res.then().log().all();
    JsonPath jsonpathObj = res.jsonPath();
    Assert.assertEquals(AccountID, jsonpathObj.getString("accountId"));
    Assert.assertEquals(Amount,jsonpathObj.getString("amount"));
    Assert.assertEquals("COMPLETED",jsonpathObj.getString("status"));
    Assert.assertEquals(Desc,jsonpathObj.getString("comments"));
        }

     @Test(dataProvider = "providerforFundTransfer",priority=3)
    public void fundTransfer(String FromAccount, String ToAccount , String Amount){
      Header header = new Header("Authorization" , accessToken);
      JSONObject jsonObj = new JSONObject();
      RequestSpecification reqSpcObj = RestAssured.given();
      reqSpcObj.baseUri(bankAPIURL);
      reqSpcObj.header(header);
      jsonObj.put("fromAccount",FromAccount);
      jsonObj.put("toAccount",ToAccount);
      jsonObj.put("amount",Amount);
      reqSpcObj.body(jsonObj.toString());
      reqSpcObj.contentType(ContentType.JSON);
      reqSpcObj.log().all();
      ResponseSpecBuilder resSpcObj = new ResponseSpecBuilder();
      resSpcObj.expectStatusCode(201);
      resSpcObj.expectContentType(ContentType.JSON);
      reqSpcObj.then().spec(resSpcObj.build());
     Response res = reqSpcObj.post("/fund-transfers");
     res.then().log().all();
     JsonPath jsonPathObj = res.jsonPath();
     Assert.assertEquals("Fund transfer was successful",jsonPathObj.getString("message"));
       transactionID = jsonPathObj.getString("transactionId");
              }

    @Test(dataProvider ="providerforFundTransfer" ,dependsOnMethods = "fundTransfer")
    public void getDetailsOfTransferByReferenceID(String FromAccount, String ToAccount , String Amount)
    {
        Header header = new Header("Authorization", accessToken);
        RequestSpecification reqSpObj = RestAssured.given();
        reqSpObj.baseUri(bankAPIURL);
        reqSpObj.header(header);
        reqSpObj.log().all();
        ResponseSpecBuilder resSpcObj = new ResponseSpecBuilder();
        resSpcObj.expectStatusCode(200);
        resSpcObj.expectContentType(ContentType.JSON);
        reqSpObj.then().spec(resSpcObj.build());
        Response res = reqSpObj.get("/fund-transfers/" +transactionID);
        res.then().log().all();
        JsonPath jsonpathObj = res.jsonPath();
        Assert.assertEquals(transactionID,jsonpathObj.getString("transactionReference"));
        Assert.assertEquals(Integer.parseInt(Amount),jsonpathObj.getString("amount"));
        Assert.assertEquals(FromAccount,jsonpathObj.getString("fromAccount"));
        Assert.assertEquals(ToAccount,jsonpathObj.getString("toAccount"));
    }

}
