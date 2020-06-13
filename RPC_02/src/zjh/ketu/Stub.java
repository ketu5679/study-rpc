package zjh.ketu;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Stub {
    public static IUserService getStub() {
        return new IUserService() {
            @Override
            public User getUserById(Integer id) throws IOException {
                Socket socket = new Socket("127.0.0.1", 8888);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(baos);
                dos.writeInt(1);

                socket.getOutputStream().write(baos.toByteArray());
                socket.getOutputStream().flush();

                DataInputStream dis = new DataInputStream(socket.getInputStream());
                int resId = dis.readInt();
                String name = dis.readUTF();
                User user = new User(resId, name);
                dos.close();
                socket.close();

                return user;
            }
        };
    }
}
