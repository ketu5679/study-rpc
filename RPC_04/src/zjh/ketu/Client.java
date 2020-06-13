package zjh.ketu;

import java.io.IOException;

public class Client {
    public static void main(String[] args) throws IOException {
        IUserService stub = Stub.getStub();
        System.out.println(stub.getUserById(1));
        System.out.println(stub.getNameByPrefix("fa"));
    }
}
