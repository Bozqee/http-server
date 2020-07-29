package xyz.bozqee.httpserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class MyHandler implements HttpHandler {

    private static Logger log = Logger.getLogger(MyHandler.class.getClass());

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        StringBuilder request = new StringBuilder();

        request.append("请求方式：").append(httpExchange.getRequestMethod()).append("<br/>");
        //get请求方式
        if (httpExchange.getRequestMethod().equals("GET")) {
            request.append("参数：").append(httpExchange.getRequestURI().getQuery()).append("<br/>");
        } else if (httpExchange.getRequestMethod().equals("POST")) {  //Post
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody(), "utf-8"));
            StringBuilder requestBodyContent = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                requestBodyContent.append(line);
            }
            request.append("参数：").append(requestBodyContent.toString()).append("<br/>");
        }

        log.info(request.toString());

        //处理响应
        StringBuilder response = new StringBuilder();
        response.append("<html>")
                .append("<body>")
                .append(request.toString())
                .append("</body>")
                .append("</html>");
        String responseStr = response.toString();
        byte[] responseContentByte = responseStr.getBytes("utf-8");

        httpExchange.getResponseHeaders().add("Content-Type:", "text/html;charset=utf-8");
        httpExchange.sendResponseHeaders(200, responseContentByte.length);

        OutputStream out = httpExchange.getResponseBody();
        out.write(responseContentByte);
        out.flush();
        out.close();
    }

}
