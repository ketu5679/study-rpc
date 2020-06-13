package zjh.ketu;

import com.caucho.hessian.io.Hessian2Output;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

public class Stub {
    public static Object getStub(final Class clazz) {
        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket socket = new Socket("127.0.0.1", 8888);
                Hessian2Output h2o = new Hessian2Output(socket.getOutputStream());
                /*
                根据调用的，方法，方法入参，动态调用IUserService指定方法
                 */
                String className = clazz.getName();
                String methodName = method.getName();
                Class<?>[] parameterTypes = method.getParameterTypes();
                h2o.writeString(className);
                h2o.writeString(methodName);
                h2o.writeObject(parameterTypes);
                h2o.writeObject(args);
                h2o.flush();

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
