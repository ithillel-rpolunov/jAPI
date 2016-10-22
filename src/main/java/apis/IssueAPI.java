package apis;

import utils.RequestSenderHTTPS;

public class IssueAPI {

    public RequestSenderHTTPS createIsueHTTPS(String body){
        RequestSenderHTTPS requestSender = new RequestSenderHTTPS();
        requestSender
                .createRequestHTTPS(body)
                .post(ApiUrls.ISSUE.getUri());
        return requestSender;
    }

    public RequestSenderHTTPS addCommentHTTPS(String body, String key){
        RequestSenderHTTPS requestSender = new RequestSenderHTTPS();
        requestSender
                .createRequestHTTPS(body)
                .post(ApiUrls.ISSUE.getUri( key + "/comment"));
        return requestSender;
    }

    public RequestSenderHTTPS deleteIssueHTTPS(String key){
        RequestSenderHTTPS requestSender = new RequestSenderHTTPS();
        requestSender
                .createRequestWithoutBodyHTTPS()
                .delete(ApiUrls.ISSUE.getUri(key));
        return requestSender;
    }

    public RequestSenderHTTPS searchHTTPS(String body){
        RequestSenderHTTPS requestSender = new RequestSenderHTTPS();
        requestSender
                .createRequestHTTPS(body)
                .post(ApiUrls.SEARCH.getUri());
        return requestSender;
    }

}
