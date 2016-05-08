package io.cax.reactor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.Environment;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.io.codec.StandardCodecs;
import reactor.io.net.NetStreams;
import reactor.io.net.tcp.TcpServer;
import reactor.rx.Streams;


@SpringBootApplication
public class ServerApp implements CommandLineRunner {

    @Autowired
    private EventBus eventBus;

    @Value("${server.tcp.port}")
    private int tcpPort;

    @Value("${topic.name}")
    private String topicName;

	public static void main(String[] args) {
		SpringApplication.run(ServerApp.class, args);
	}


    @Override
    public void run(String... strings) throws Exception {


        TcpServer<String,String> tcpServer = NetStreams.<String, String>tcpServer(spec ->
                spec
                        .codec(StandardCodecs.STRING_CODEC)
                        .listen(tcpPort)
                        .dispatcher(Environment.cachedDispatcher()));

       tcpServer.start(c -> {
           c.consume(s -> eventBus.notify(topicName,Event.wrap(s)));
           return Streams.never();
       }).await();

    }
}
