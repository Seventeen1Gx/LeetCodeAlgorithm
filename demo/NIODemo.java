package demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NIODemo {
    static final ExecutorService threadPool = Executors.newCachedThreadPool();

    public static class Client {
        public static void main(String[] args) {
            Runnable task = () -> {
                SocketChannel channel = null;
                Selector selector = null;
                try {
                    channel = SocketChannel.open();
                    channel.configureBlocking(false);

                    selector = Selector.open();
                    channel.register(selector, SelectionKey.OP_CONNECT);
                    // 跟服务端发起三次握手
                    channel.connect(new InetSocketAddress(9000));

                    while (true) {
                        selector.select();
                        Iterator<SelectionKey> keysIterator = selector.selectedKeys().iterator();
                        while (keysIterator.hasNext()) {
                            SelectionKey key = keysIterator.next();
                            keysIterator.remove();

                            if (key.isConnectable()) {
                                // 服务器连接成功时，来到这里
                                System.out.println();
                                channel = (SocketChannel) key.channel();

                                if (channel.isConnectionPending()) {
                                    channel.finishConnect();

                                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                                    buffer.put("你好".getBytes());
                                    buffer.flip();
                                    channel.write(buffer);
                                }

                                channel.register(selector, SelectionKey.OP_READ);
                            }

                            if (key.isReadable()) {
                                channel = (SocketChannel) key.channel();
                                ByteBuffer buffer = ByteBuffer.allocate(1024);
                                int len = channel.read(buffer);
                                if (len > 0) {
                                    System.out.println("[" + Thread.currentThread().getName()
                                            + "]收到响应：" + new String(buffer.array(), 0, len));
                                    Thread.sleep(5000);
                                    channel.register(selector, SelectionKey.OP_WRITE);
                                }
                            }

                            if (key.isWritable()) {
                                ByteBuffer buffer = ByteBuffer.allocate(1024);
                                buffer.put("你好".getBytes());
                                buffer.flip();

                                channel = (SocketChannel) key.channel();
                                channel.write(buffer);
                                channel.register(selector, SelectionKey.OP_READ);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (channel != null) {
                        try {
                            channel.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (selector != null) {
                        try {
                            selector.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            threadPool.execute(task);
        }
    }

    public static class Server {

        public static void main(String[] args) {
            init();
            listen();
        }

        private static Selector selector;

        private static void init() {
            ServerSocketChannel serverSocketChannel = null;

            try {
                selector = Selector.open();

                serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.configureBlocking(false);
                serverSocketChannel.socket().bind(new InetSocketAddress(9000));
                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
                System.out.println("NioServer 启动完成");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static void listen() {
            while (true) {
                try {
                    selector.select();
                    Iterator<SelectionKey> keysIterator = selector.selectedKeys().iterator();
                    while (keysIterator.hasNext()) {
                        SelectionKey key = keysIterator.next();
                        keysIterator.remove();
                        handleRequest(key);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private static void handleRequest(SelectionKey key) throws IOException {
            SocketChannel channel = null;
            if (key.isAcceptable()) {
                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                channel = serverSocketChannel.accept();
                channel.configureBlocking(false);
                System.out.println("接收新的 Channel");
                channel.register(selector, SelectionKey.OP_READ);
            }
            if (key.isReadable()) {
                channel = (SocketChannel) key.channel();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                int len = channel.read(buffer);
                if (len > 0) {
                    System.out.println("服务器接收请求：" + new String(buffer.array(), 0, len));
                    channel.register(selector, SelectionKey.OP_WRITE);
                }
            }
            if (key.isWritable()) {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                buffer.put("收到".getBytes());
                buffer.flip();

                channel = (SocketChannel) key.channel();
                channel.write(buffer);
                channel.register(selector, SelectionKey.OP_READ);
            }
        }
    }

}
