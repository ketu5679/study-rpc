package zjh.ketu;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;

import java.io.*;

public class HelloHessian {
    public static void main(String[] args) throws Exception {
        User user = new User(122, "ff");
        System.out.println("init user=" + user.toString());
        byte[] hessianBytes = serialize(user);
        System.out.println("hessianBytes use Hessian length=" + hessianBytes.length);
        System.out.println("hessian deserialize" + deserialize(hessianBytes));

        byte[] jdkBytes = jdkSerialize(user);
        System.out.println("hessianBytes use jdk length=" + jdkBytes.length);
        System.out.println("dsk deserialize" + jdkDeserialize(jdkBytes));

        int times = 100000;
        long s;
        s = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            serialize(user);
        }
        System.out.println("hession serialize use=" + (System.currentTimeMillis()-s));
        s = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            deserialize(hessianBytes);
        }
        System.out.println("hession deserialize use=" + (System.currentTimeMillis()-s));

        s = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            jdkSerialize(user);
        }
        System.out.println("jdk serialize use=" + (System.currentTimeMillis()-s));
        s = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            jdkDeserialize(jdkBytes);
        }
        System.out.println("jdk deserialize use=" + (System.currentTimeMillis()-s));

        s = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            jdkSerialize(user);
        }
        System.out.println("jdk serialize use=" + (System.currentTimeMillis()-s));
        s = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            jdkDeserialize(jdkBytes);
        }
        System.out.println("jdk deserialize use=" + (System.currentTimeMillis()-s));
    }


    public static byte[] serialize(Object o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Hessian2Output output = new Hessian2Output(baos);
        output.writeObject(o);
        output.flush();

        byte[] bytes = baos.toByteArray();
        baos.close();
        output.close();
        return bytes;
    }

    public static Object deserialize(byte[] bytes) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        Hessian2Input input = new Hessian2Input(bais);
        Object o = input.readObject();
        bais.close();
        input.close();
        return o;
    }

    public static byte[] jdkSerialize(Object o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream output = new ObjectOutputStream(baos);
        output.writeObject(o);
        output.flush();

        byte[] bytes = baos.toByteArray();
        baos.close();
        output.close();
        return bytes;
    }

    public static Object jdkDeserialize(byte[] bytes) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream input = new ObjectInputStream(bais);
        Object o = input.readObject();
        bais.close();
        input.close();
        return o;
    }
}
