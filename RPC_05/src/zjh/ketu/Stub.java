package zjh.ketu;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

public class Stub {
    public static Object getStub(Class clazz) {
        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket socket = new Socket("127.0.0.1", 8888);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                /*
                根据调用的，方法，方法入参，动态调用IUserService指定方法
                 */
                String className = clazz.getName();
                String methodName = method.getName();
                Class<?>[] parameterTypes = method.getParameterTypes();
                oos.writeUTF(className);
                oos.writeUTF(methodName);
                oos.writeObject(parameterTypes);
                oos.writeObject(args);
                oos.flush();

                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Object o = ois.readObject();
                socket.close();

                return o;
            }
        };
        return Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class[]{clazz},
                invocationHandler);
    }
}
