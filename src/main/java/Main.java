import cl.ucn.disc.as.ui.ApiRestServer;
import cl.ucn.disc.as.ui.WebController;
import io.javalin.Javalin;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class Main {
    public static void main(String[] args) {
        log.debug("Starting Main ..");
        log.debug("Library path: {}.", System.getProperty("java.library.path"));

        // Start the API Rest server
        Javalin app = ApiRestServer.start(7070, new WebController());

//        log.debug("Stopping ..");
//
//        app.stop();
//
//        log.debug("Done. :)");
    }
}