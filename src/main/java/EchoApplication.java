import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EchoApplication {

    public static void main(String[] args) throws IOException, InterruptedException {

        final int listenPort = 12345;

        // 启动服务端
         EchoServer server = new EchoServer(listenPort);
        server.startService();

        // 启动客户端
        EchoClient client = new EchoClient(listenPort);
        client.startService();

        // 在控制台输入，服务端直接原文返回输入信息
        // 控制台结果示例：
        /**
            2020-03-31 16:58:44.049 - Welcome to My Echo Server.(from SERVER)

            Enter: hello!
            2020-03-31 16:58:55.452 - hello!(from SERVER)

            Enter: this is koal.
            2020-03-31 16:59:06.565 - this is koal.(from SERVER)

            Enter: what can i do for you?
            2020-03-31 16:59:12.828 - what can i do for you?(from SERVER)

            Enter: bye!
            2020-03-31 16:59:16.502 - Bye bye!(from SERVER)
         */
    }

}
class EchoServer {
    int port;
    private static DateTimeFormatter formatter=DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss:SSS");
    public EchoServer(int listenPort) {
        port = listenPort;
    }

    public void startService() throws IOException {
        ServerSocket server = new ServerSocket(port);
        new Thread(()->{
            try{
                Socket client = server.accept();
                PrintWriter out = new PrintWriter(client.getOutputStream(),true);
                out.println(LocalDateTime.now().format(formatter)+" - Welcome to My Echo Server.(from SERVER)");
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String s = "";
                while(!s.equals("bye")) {
                    s = reader.readLine();
                    out.println(LocalDateTime.now().format(formatter)+" " + s+  " - (from SERVER)");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // TODO
}
class EchoClient {
    int port;
    public EchoClient(int listenPort) {
        port =listenPort;
    }

    public void startService() throws IOException {
        Socket client = new Socket();
        client.connect(new InetSocketAddress("localhost",port));
        BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        System.out.println(reader.readLine());
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(client.getOutputStream(),true);
        String s = "";
        while (!s.equals("bye")){
            s = consoleReader.readLine();
            out.println(s);
            System.out.println(reader.readLine());
        }
    }
    // TODO
}
