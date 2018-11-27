package com.chrl.bs.sfs;

import com.chrl.bs.sfs.util.FileList;
import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

/**
 * 主类
 *
 * @author
 * @version
 * @since 1.0
 */
public class Server {

    private final int port;

    public static Properties CONFIG = new Properties();

    private static Logger log = Logger.getLogger(Server.class);

    public static final Map<String,Object> DATA_MAP = new ConcurrentHashMap<String, Object>();

    public Server(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 11600;
        }

        log.info("loading config.properties...");
        InputStream objIS = Server.class.getResourceAsStream("/config.properties");
        try {
            CONFIG.load(objIS);
            objIS.close();
        } catch (IOException e) {
            log.error("CAN NOT locate config.properties in classpath ROOT");
            return;
        }
        try {

            FileList.initData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Server(port).run();
    }

    public void run() {
        ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
        bootstrap.setPipelineFactory(new PipelineFactory());
        bootstrap.bind(new InetSocketAddress(port));
        log.info("Server is listening on localhost:"+port);
    }
}