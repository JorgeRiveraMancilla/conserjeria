package cl.ucn.disc.as.ui;

import io.javalin.Javalin;

public interface RoutesConfigurator {
    void configure(Javalin javalin);
}
