## rpc 学习记录
1 [资料来源 mashibing](https://www.ixigua.com/pseries/6837411755535106572_6837389894990430723/?logTag=eHDqXGMAI5QrVy3pUI-Ww)

## rpc的演化过程
- 从单机走向分布式，产生了很多分布式的通信方式
    - 最古老也是最有效，并且永不过时的， TCP/UDP的二进制传输。事实上所有的通信方式归根结底都是TCP/UDP 
    - CORBA Common Object Request Broker Architecture. 古老而复杂的。 支持面向对象的通信协议 
    - Web Service(SOA SOAP RDD  I WSDL ...)
    基于http+xml的标准化web API
    - RestFul (Representational State Transfer)
    回归简单本源的webAPI事实标准 http+json
    - RMI Remote Method Invocation
    Java内部的分布式通信协议
    - JMS Java Message Service
    JavaEE中的消息框架标准，为很多MQ所支持
    - RPC Remote Procedure Call
    远程方法调用，这只是一个统称，重点在于方法调用，具体实现甚至可以用RMI RestFul等去实现，但是一般不用，因为RMI不能跨语言，restful效率太低 多用于服务器集群间的通信因此常使用更加高效 短小精悍的传输模式以调高效率
 
 ## rpc序列化框架
 1. java.io.Serializable
 2. Hession
 3. Google protobuf
 4. facebook thrift
 5. kyro
 6 fst
 7. json序列化框架
    1. jackson
    2. GSON
    3. fastJson
 8. xmlRpc
 ...
 
## 代码演进
1. RPC_01 Client直接调用通过网络调用这个一个方法
2. RPC_02 Client 通过Stub调用方法
3. RPC_03 stub 动态代理IUserService
4. RPC_04 根据调用的，方法，方法入参，动态调用IUserService的方法，代理多个方法
5. RPC_05 动态代理所有接口的所有方法，server需注入要代理的对象可以结合spring
6. RPC_06 通过hessian序列化、反序列


# todo 
    - thrift/protobuf测试
