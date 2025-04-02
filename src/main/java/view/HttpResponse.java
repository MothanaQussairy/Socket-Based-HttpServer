package view;

public class HttpResponse {
    private  String body;
    private  byte[] bodyBytes;
    public HttpResponse(String body) {
        this.body = body;
        bodyBytes = body.getBytes();
    }
    public  String generateResponse() {
         return "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: " + bodyBytes.length + "\r\n" +
                "\r\n" + body;
    }
}
