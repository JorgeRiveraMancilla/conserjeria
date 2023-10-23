package cl.ucn.disc.as.services;

import cl.ucn.disc.as.exceptions.SistemaException;
import cl.ucn.disc.as.model.*;
import io.ebean.Database;
import lombok.Builder;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.List;

public class SistemaImpl implements Sistema {
    @NotNull
    private Database database;

    @Builder
    public SistemaImpl(@NotNull Database database) {
        this.database = database;
    }

    @Override
    public Edificio add(@NonNull Edificio edificio) {
        database.save(edificio);
        return edificio;
    }

    @Override
    public Persona add(@NonNull Persona persona) {
        database.save(persona);
        return persona;
    }

    @Override
    public Departamento addDepartamento(
            @NonNull Departamento departamento,
            @NonNull Edificio edificio) {
        edificio.add(departamento);
        database.save(edificio);
        return departamento;
    }

    @Override
    public Departamento addDepartamento(
            @NonNull Departamento departamento,
            @NonNull Long idEdificio) {
        Edificio edificio = database.find(Edificio.class, idEdificio);
        if (edificio == null) {
            throw new SistemaException(String.format("No existe algún edificio con ID %o", idEdificio));
        }
        return this.addDepartamento(departamento, edificio);
    }

    @Override
    public Contrato realizarContrato(
            @NonNull Persona duenio,
            @NonNull Departamento departamento,
            @NonNull Instant fechaPago) {
        Contrato contrato = Contrato.builder()
                .departamento(departamento)
                .persona(duenio)
                .fechaPago(fechaPago)
                .build();
        database.save(contrato);
        return contrato;
    }

    @Override
    public Contrato realizarContrato(
            @NonNull Long idDuenio,
            @NonNull Long idDepartamento,
            @NonNull Instant fechaPago) {
        Departamento departamento = database.find(Departamento.class, idDepartamento);
        if (departamento == null) {
            throw new SistemaException(String.format("No existe algún departamento con ID %o", idDepartamento));
        }
        Persona duenio = database.find(Persona.class, idDuenio);
        if (duenio == null) {
            throw new SistemaException(String.format("No existe alguna persona con ID %o", idDuenio));
        }
        return this.realizarContrato(duenio, departamento, fechaPago);
    }

    @Override
    public List<Contrato> getContratos() {
        return database.find(Contrato.class).findList();
    }

    @Override
    public List<Persona> getPersonas() {
        return database.find(Persona.class).findList();
    }

    @Override
    public List<Pago> getPagos(String rut) {
        return database.find(Pago.class).findList();
    }
}
