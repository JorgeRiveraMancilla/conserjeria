package cl.ucn.disc.as.ui;

import cl.ucn.disc.as.model.Persona;
import cl.ucn.disc.as.services.Sistema;
import cl.ucn.disc.as.services.SistemaImpl;
import io.ebean.DB;
import io.javalin.Javalin;
import io.javalin.http.NotFoundResponse;

import java.util.Optional;

public final class WebController implements RouterConfigurator {
    private final Sistema sistema;

    public WebController() {
        this.sistema = new SistemaImpl(DB.getDefault());
        this.sistema.populate();
    }

    @Override
    public void configure(final Javalin app) {
        app.get("/", ctx -> {
            ctx.result("Welcome to Conserjería API REST");
        });

        app.get("/personas", ctx -> {
            ctx.json(this.sistema.getPersonas());
        });

        app.get("/personas/{rut}", ctx -> {
            String rut = ctx.pathParam("rut");
            Optional<Persona> persona = this.sistema.getPersona(rut);
            if (persona.isPresent()) {
                ctx.json(persona.get());
            } else {
                throw new NotFoundResponse("No existe la persona con RUT " + rut);
            }
        });
    }
}
