package zjh.ketu;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        IUserService stub = Stub.getStub();
        System.out.println(stub.getUserById(1));
    }
}
