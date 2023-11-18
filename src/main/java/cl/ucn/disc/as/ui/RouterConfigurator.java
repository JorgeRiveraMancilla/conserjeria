package cl.ucn.disc.as.ui;

import io.javalin.Javalin;

public interface RouterConfigurator {
    void configure(Javalin javalin);
}
