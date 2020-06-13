package zjh.ketu;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static boolean running = true;
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8888);
        while (running) {
            Socket accept = ss.accept();
            process(accept);
            accept.close();
        }
        ss.close();
    }

    private static void process(Socket accept) throws IOException {
        InputStream in = accept.getInputStream();
        OutputStream out = accept.getOutputStream();
        DataInputStream dis = new DataInputStream(in);
        DataOutputStream dos = new DataOutputStream(out);

        int id = dis.readInt();
        IUserService service = new UserServiceImpl();
        User user = service.getUserById(id);
        // 最基本的方式，写入基础类型
        dos.writeInt(user.getId());
        dos.writeUTF(user.getName());
        dos.flush();
    }
}
