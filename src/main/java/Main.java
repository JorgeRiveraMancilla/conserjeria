import cl.ucn.disc.as.grpc.PersonaGrpcServiceImpl;
import cl.ucn.disc.as.ui.ApiRestServer;
import cl.ucn.disc.as.ui.WebController;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.javalin.Javalin;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;


@Slf4j
public class Main {
    public static void main(String[] args) {
        log.debug("Starting Main with library path: {}.", System.getProperty("java.library.path"));

        // Start the API Rest server
        log.debug("Starting the API Rest server ..");
        Javalin app = ApiRestServer.start(7070, new WebController());

        // Stop the API Rest server
        // app.stop();

        // Start the gRPC server
        log.debug("Starting the gRPC server ..");
        Server server = ServerBuilder
                .forPort(50123)
                .addService(new PersonaGrpcServiceImpl())
                .build();

        server.start();

        // shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));

        // wait for the stop
        server.awaitTermination();

        log.debug("Done.");
    }
}