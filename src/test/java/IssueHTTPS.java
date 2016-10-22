import apis.IssueAPI;
import fixtures.JiraJSONFixture;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.RequestSenderHTTPS;

import java.util.List;

import static com.thoughtworks.selenium.SeleneseTestNgHelper.assertEquals;
import static io.restassured.path.json.JsonPath.from;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class IssueHTTPS {

//
//    public static String sessionID;
//    public static String ATLASSIAN_TOKEN;
//    public static String STUDIO_TOKEN;


    @Test(groups = {"Issue", "comment", "search"})
    public void login(){

        String sessionID;
        String ATLASSIAN_TOKEN;
        String STUDIO_TOKEN;
        RequestSenderHTTPS requestSenderHTTPS = new RequestSenderHTTPS();
        requestSenderHTTPS.authenticate();
        sessionID = requestSenderHTTPS.extractResponseByPath("session.value");
        ATLASSIAN_TOKEN = requestSenderHTTPS.response.getCookie("atlassian.xsrf.token");
        STUDIO_TOKEN = requestSenderHTTPS.response.getCookie("studio.crowd.tokenkey");

        //check
        assertNotNull(sessionID);
        assertNotNull(ATLASSIAN_TOKEN);
        assertNotNull(STUDIO_TOKEN);

//        assertTrue(String.valueOf(requestSenderHTTPS.response.getStatusCode()).equals("200"));
    }

    @Test (groups = {"Issue"}, dependsOnMethods = {"login"} )
    public void createIssuePositive201HTTPS(){

        JiraJSONFixture jiraJSONFixture = new JiraJSONFixture();
        String issue = jiraJSONFixture.generateJSONForSampleIssueHTTPS();

        IssueAPI issueAPI = new IssueAPI();

        RequestSenderHTTPS requestSenderHTTPS = issueAPI.createIsueHTTPS(issue);

        assertEquals(requestSenderHTTPS.response.statusCode(),201);
        assertTrue(requestSenderHTTPS.response.getBody().jsonPath().get("key").toString().contains("TES-"));
        assertTrue(requestSenderHTTPS.response.getBody().jsonPath().get("self").toString().contains("https://forapitest.atlassian.net"));
    }

    @Test (groups = {"comment"}, dependsOnMethods = {"login"})
    public void CommentHTTPS(){
        JiraJSONFixture jiraJSONFixture = new JiraJSONFixture();
        String issue = jiraJSONFixture.generateJSONForSampleIssueHTTPS();

        IssueAPI issueAPI = new IssueAPI();

        RequestSenderHTTPS requestSenderHTTPS = issueAPI.createIsueHTTPS(issue);

        String id = "10056";
//        String id =requestSenderHTTPS.response.getBody().jsonPath().get("id").toString();

        String comment = jiraJSONFixture.generateJSONForCommentHTTPS();
        System.out.println(id);

        RequestSenderHTTPS requestSenderHTTPS1 = issueAPI.addCommentHTTPS(comment, id);

        Assert.assertEquals(requestSenderHTTPS1.response.getStatusCode(), 201);
        assertTrue(requestSenderHTTPS1.response.getBody().jsonPath().get("body").toString().equals( "REST API comment via RESTASSURED"));
    }


    @Test (groups = {"search"}, dependsOnMethods = {"login"})
    public void SearchIssueHTTPS(){
        JiraJSONFixture jiraJSONFixture = new JiraJSONFixture();
        String searchIssue = jiraJSONFixture.generateJSONforSearchHTTPS();

        IssueAPI issueAPI = new IssueAPI();

        RequestSenderHTTPS requestSenderHTTPS = issueAPI.searchHTTPS(searchIssue);

        System.out.println(requestSenderHTTPS.response.getStatusCode());
        System.out.println(requestSenderHTTPS.response.getBody().jsonPath());

        System.out.println(requestSenderHTTPS.response.asString());

        List<String> stringList = null;
        String rez = requestSenderHTTPS.response.asString();

        stringList = from(rez).getList("issues.key");

        for (String s2 : stringList) {
            System.out.println(s2);
        }

        Assert.assertEquals(requestSenderHTTPS.response.getStatusCode(),200);
        Assert.assertTrue(requestSenderHTTPS.response.getBody().jsonPath().get("issues.key").toString().contains("TES-"));
    }


    @Test (groups = {"Issue"}, dependsOnMethods = {"createIssuePositive201HTTPS"})
    public void deleteIssue(){
        JiraJSONFixture jiraJSONFixture = new JiraJSONFixture();
        String issue = jiraJSONFixture.generateJSONForSampleIssueHTTPS();

        IssueAPI issueAPI = new IssueAPI();

        RequestSenderHTTPS requestSenderHTTPS = issueAPI.createIsueHTTPS(issue);

//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        String key = requestSenderHTTPS.response.getBody().jsonPath().get("key").toString();
        System.out.println(key);
//
        RequestSenderHTTPS requestSenderHTTPS1 = issueAPI.deleteIssueHTTPS(key);

        Assert.assertEquals(requestSenderHTTPS1.response.getStatusCode(), 204);
    }

}
