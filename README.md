- com.cyt.netty
    - test01: 
        -【标签】netty-服务端 + http；
        -【实现】浏览器或 curl 发送请求： http://locolhost:8899，在当前页面显示或返回 hello world； 
        -【学习】1）http 和 curl 的区别 2）channel 的生命周期：Added、Registered、Active、执行自定义handler 中的 channelRead0、in_active、unregistered；
    - test02: 
        -【标签】netty-服务端&客户端 基于 tcp 的双向通信【Socket】；
        -【实现】服务端向客户端传 uuid，客户端向服务端传当前系统时间；
        -【学习】一个 netty 的使用范本，注意 serverInitializer 和 clientInitializer 的 handler 对应一致；
    - test03: 
        -【标签】netty-服务端&客户端 基于 tcp 的双向通信【Socket】强化版；
        -【实现】聊天室-一个服务端可以连接多个客户端，客户端可以向服务端发送数据，服务端可以将从客户端收到的数据以及客户端的加入与退出情况广播到每个客户端；
        -【学习】ChannelGroup，inActive 会自动实现 ChannelGroup 的 remove 功能；
    - test04: 
        -【标签】netty-服务端 + tcp 或长连接 + 心跳检测；
        -【实现】IdleStateHandler：空闲状态检查，如果读的时间，写的时间，或读写加起来的时间超过指定参数，就会调用该方法；
        -【学习】可以通过定时发送心跳包的方式对网络连接状态进行检测；
    - test05: 
        -【标签】netty-服务端 （webSocket）+ webapp/test.html-客户端；
        -【实现】客户端感知连接的开启和中断，客户端向服务端发送消息，服务端 console 输出消息，并返回一个系统时间给客户端；
        -【学习】1）webSocket 客户端和服务端的数据传输代码；
                 2）webSocket 可以建立真正意义上的长连接，且通信双方是一种对等关系（全双工）；
                 3）webSocket 协议是基于 http 协议的，在首次建立连接的时候通过 http 协议在 header 中注入 webSocket 信息，在服务端接收到后会将 http 升级为 webSocket，之后只需要传输数据既可；
    - test06: 
        -【标签】（其他 RPC 框架）netty 对 Protobuf 的支持；
        -【实现】protobuf 集成 netty 实现多协议消息传递：protobuf/Person.proto 定义了一个主 message，包含三个 dataType，
                 客户端一旦与服务端建立连接，就会根据随机生成的数字来生成一个 MyMessage 的实例，并将其返回给服务端，服务端
                 通过类型判断，打印出具体内容；
        -【学习】protobuf 的 message 定义，encoder 和 decoder 机制，一个通道连接如何传输多种类型的 message；   
- com.cyt.thrift: 
    -【标签】（其他 RPC 框架）netty 对 Thrift 的支持（IDL（Interface Description Language））；
    -【实现】客户端调用 getPersonByUserName 和 savePerson 方法，服务端打印出从客户端获取的对象参数；
    -【学习】1）Thrift 的定义格式，包括命名空间、struct、exception 和 service； 2）Thrift 的传输协议及原理； 3）支持的服务模型；             
         
               