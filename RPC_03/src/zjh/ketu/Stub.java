package zjh.ketu;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

public class Stub {
    public static IUserService getStub() {
        /**
         * 采用动态代理，但是参数还是只是一个int
         */
        return (IUserService) Proxy.newProxyInstance(
                IUserService.class.getClassLoader(),
                new Class[]{IUserService.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
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
                });
    }
}
