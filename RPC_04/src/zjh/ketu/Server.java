package zjh.ketu;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static boolean running = true;
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(8888);
        while (running) {
            Socket accept = ss.accept();
            process(accept);
            accept.close();
        }
        ss.close();
    }

    private static void process(Socket s) throws Exception {
        ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
        String methodName = ois.readUTF();
        Class<?>[] parameterTypes = (Class<?>[])ois.readObject();
        UserServiceImpl service = new UserServiceImpl();
        Method method = service.getClass().getMethod(methodName, parameterTypes);

        Object o = method.invoke(service, (Object[]) ois.readObject());

        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
        oos.writeObject(o);
        oos.flush();
    }
}
