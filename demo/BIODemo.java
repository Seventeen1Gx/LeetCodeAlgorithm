package demo;

import java.io.*;
import java.net.*;

public class BIODemo {
    public static class Server {

        public static void main(String[] args) throws IOException {
            TCP();
        }

        public static void TCP() throws IOException {
            // new 的同时绑定端口
            ServerSocket serverSocket = new ServerSocket(10001);

            // 无限循环
            // 负责接收连接
            // 每收到一个连接，就处理（一般是开线程去处理）
            while (true) {
                // 三次握手成功，拿到连接
                Socket socket = serverSocket.accept();

                // 读取数据并返回响应
                int len;
                byte[] data = new byte[1024];
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                while ((len = inputStream.read(data)) != -1) {
                    String s = new String(data, 0, len);
                    System.out.println("收到的消息是：" + s);
                    outputStream.write(("服务器收到了：" + s).getBytes());
                }
            }
        }


        public static void UDP() throws IOException {
            // new 的同时绑定接口
            DatagramSocket ds = new DatagramSocket(7831);

            while (true) {
                // 用来接收
                byte[] data = new byte[1024];
                // 包装成数据包
                DatagramPacket dp = new DatagramPacket(data, data.length);

                ds.receive(dp);

                System.out.println("我收到了：" + new String(data, 0, data.length));
            }
        }

    }

    public static class Client {
        public static void main(String[] args) throws IOException {
            TCP();
        }

        public static void TCP() throws IOException {
            // 默认会给客户端 socket 绑定一个接口，但这个接口并不重要
            Socket socket = new Socket();

            // 服务器上我想绑定的那个 ServerSocket 的端口号
            // 发起三次握手
            socket.connect(new InetSocketAddress(10001));

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            String s;
            while ((s = br.readLine()) != null) {
                outputStream.write(s.getBytes());
                byte[] data = new byte[1024];
                int len = inputStream.read(data);
                System.out.println("服务器的响应是 [" + new String(data, 0, len) + "]");
            }
        }

        public static void UDP() throws IOException {
            // 默认会给 ds 绑定一个端口号，但不重要
            DatagramSocket ds = new DatagramSocket();

            DatagramPacket dp;

            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
            String s;
            while ((s = bf.readLine()) != null) {
                byte[] data = s.getBytes();
                // 数据包中指定连接
                dp = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), 7831);
                ds.send(dp);
            }
        }
    }
}
