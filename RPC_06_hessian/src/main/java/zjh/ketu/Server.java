package zjh.ketu;

import com.caucho.hessian.io.Hessian2Input;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
        Hessian2Input h2i = new Hessian2Input(s.getInputStream());
        String className = h2i.readString();
        String methodName = h2i.readString();

        Object o1 = getInstanceByClassName(className);


        Class<?>[] parameterTypes = (Class<?>[]) h2i.readObject();
        Method method = o1.getClass().getMethod(methodName, parameterTypes);

        Object o = method.invoke(o1, (Object[]) h2i.readObject());

        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
        oos.writeObject(o);
        oos.flush();
    }

    /**
     * 根据类名 获取实例对象
     * todo 自动注入响应的对象
     *
     * @param className
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private static Object getInstanceByClassName(String className) throws InstantiationException, IllegalAccessException {
        Class<?> clazz;
        if (className.equals(IUserService.class.getName())) {
            clazz = UserServiceImpl.class;
        } else {
            clazz = ProductServiceImpl.class;
        }
        return clazz.newInstance();
    }
}
