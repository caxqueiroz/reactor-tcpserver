package io.cax.debs2016;


import org.springframework.beans.factory.annotation.Autowired;
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
public class Debs2016Application implements CommandLineRunner {

    @Autowired
    public EventBus eventBus;

	public static void main(String[] args) {
		SpringApplication.run(Debs2016Application.class, args);
	}


    @Override
    public void run(String... strings) throws Exception {


        TcpServer<String,String> tcpServer = NetStreams.<String, String>tcpServer(spec ->
                spec
                        .codec(StandardCodecs.STRING_CODEC)
                        .listen(3000)
                        .dispatcher(Environment.cachedDispatcher()));

       tcpServer.start(c -> {
           c.consume(s -> eventBus.notify("debs2016.topic",Event.wrap(s)));
           return Streams.never();
       }).await();

    }
}
