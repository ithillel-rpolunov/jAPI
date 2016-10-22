package apis;

public enum ApiUrls {

    LOGIN("/rest/auth/latest/session"),
    ISSUE("/rest/api/latest/issue"),
    SEARCH("/rest/api/latest/search");

    private String uri;

    ApiUrls(String url) {
        this.uri = url;
    }

    public String getUri() {
        return this.uri;
    }

    public String getUri(String suffix) {
//        System.out.println(this.uri + "/" + suffix);
        return this.uri + "/" + suffix;
    }

}