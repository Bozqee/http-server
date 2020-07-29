package xyz.bozqee.httpserver;

import com.sun.net.httpserver.HttpServer;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class HttpServerRun {

    private static Logger log = Logger.getLogger(HttpServerRun.class.getClass());

    private final static int PORT = 80;

    private final static String CONTEXT = "/myHttpServer";

    public static void main(String[] args) throws IOException {

        InetSocketAddress address = new InetSocketAddress(PORT);

        //绑定 PORT 端口，监听
        HttpServer httpServer = HttpServer.create(address, 0);

        log.info("80端口监听中...");

        //将路径为 CONTEXT 请求映射到MyHttpServerHandler处理器
//        httpServer.createContext(CONTEXT, new MyHttpServerHandler());
        httpServer.createContext(CONTEXT, new MyHandler());
        log.info(CONTEXT+"路径开启");

        //设置服务器的线程池对象
        httpServer.setExecutor(Executors.newCachedThreadPool());

        //启动服务器
        httpServer.start();

        log.info("服务器启动成功");
    }
}
