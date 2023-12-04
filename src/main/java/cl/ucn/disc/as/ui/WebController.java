package cl.ucn.disc.as.ui;

import cl.ucn.disc.as.grpc.PersonaGrpc;
import cl.ucn.disc.as.grpc.PersonaGrpcRequest;
import cl.ucn.disc.as.grpc.PersonaGrpcResponse;
import cl.ucn.disc.as.grpc.PersonaGrpcServiceGrpc;
import cl.ucn.disc.as.model.Persona;
import cl.ucn.disc.as.services.Sistema;
import cl.ucn.disc.as.services.SistemaImpl;
import io.ebean.DB;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.javalin.Javalin;
import io.javalin.http.NotFoundResponse;

import java.util.Optional;

public final class WebController implements RoutesConfigurator {
    private final Sistema sistema;

    public WebController() {
        this.sistema = new SistemaImpl(DB.getDefault());
        this.sistema.populate();
    }

    @Override
    public void configure(final Javalin app) {
        app.get("/api", ctx -> {
            ctx.result("Welcome to Conserjería API REST");
        });

        app.get("/api/personas", ctx -> {
            ctx.json(this.sistema.getPersonas());
        });

        app.get("/api/personas/{rut}", ctx -> {
            String rut = ctx.pathParam("rut");
            Optional<Persona> persona = this.sistema.getPersona(rut);
            if (persona.isPresent()) {
                ctx.json(persona.get());
            } else {
                throw new NotFoundResponse("No existe la persona con RUT " + rut);
            }
        });

        app.get("/grpc/personas/rut/{rut}", ctx -> {
            String rut = ctx.pathParam("rut");

            // channel
            ManagedChannel channel = ManagedChannelBuilder
                    .forAddress("127.0.0.1", 50123)
                    .usePlaintext()
                    .build();

            // stub
            PersonaGrpcServiceGrpc.PersonaGrpcServiceBlockingStub stub =
                    PersonaGrpcServiceGrpc.newBlockingStub(channel);

            // call the grpc
            PersonaGrpcResponse response = stub.retrieve(PersonaGrpcRequest
                    .newBuilder()
                    .setRut("204166994")
                    .build());

            // get the response
            PersonaGrpc personaGrpc = response.getPersona();

            // return the persona
            Optional<Persona> oPersona = Optional.of(Persona.builder()
                    .rut(personaGrpc.getRut())
                    .nombre(personaGrpc.getNombre())
                    .apellidos(personaGrpc.getApellidos())
                    .email(personaGrpc.getEmail())
                    .telefono(personaGrpc.getTelefono())
                    .build());

            ctx.json(oPersona.orElseThrow(() -> new NotFoundResponse("Can't find Persona with rut: " + rut)));
        });
    }
}
