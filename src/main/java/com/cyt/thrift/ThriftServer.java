package com.cyt.thrift;

import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import thrift.generated.PersonService;

public class ThriftServer {

    public static void main(String[] args) throws TTransportException {
        //多线程服务模型，使用非阻塞式IO（需使用TFramedTransport数据传输方式）
        TNonblockingServerSocket socket = new TNonblockingServerSocket(8899);
        THsHaServer.Args arg = new THsHaServer.Args(socket).minWorkerThreads(2).maxWorkerThreads(4);
        PersonService.Processor<PersonServiceImpl> processor = new PersonService.Processor<>(new PersonServiceImpl());

        arg.protocolFactory(new TCompactProtocol.Factory());//协议层：压缩格式；
        arg.transportFactory(new TFramedTransport.Factory());//传输层：以frame为单位进行传输，非阻塞式服务中使用；
        arg.processorFactory(new TProcessorFactory(processor));//与服务相关的processor实现由编译器产生，Processor封装了从输入数据流中读数据和向数据数据流中写数据的操作

        //服务层
        TServer server = new THsHaServer(arg);
        System.out.println("Thrift Server Started");
        server.serve();
    }
}
