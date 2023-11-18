package cl.ucn.disc.as.services;

import cl.ucn.disc.as.exceptions.SistemaException;
import cl.ucn.disc.as.model.*;
import cl.ucn.disc.as.utils.ValidationUtils;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import io.ebean.Database;
import lombok.Builder;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.PersistenceException;
import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Slf4j
public class SistemaImpl implements Sistema {
    @NotNull
    private final Database database;

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

    @Override
    public Optional<Persona> getPersona(String rut) {
        return Optional.ofNullable(
                database.find(Persona.class)
                        .where()
                        .eq("rut", rut)
                        .findOne());
    }

    @Override
    public void populate() {
        Locale locale = new Locale("es-CL");
        FakeValuesService fvs = new FakeValuesService(locale, new RandomService());
        Faker faker = new Faker(locale);

        for (int i = 0; i < 100; i++) {
            String rut = fvs.bothify("########");
            String dv = ValidationUtils.dv(rut);
            String name = faker.name().firstName();
            String lastName = faker.name().lastName();
            Persona persona = Persona.builder()
                    .rut(rut + "-" + dv)
                    .nombre(name)
                    .apellidos(lastName)
                    .email(name.toLowerCase() + "." + lastName.toLowerCase() + fvs.bothify("###@gmail.com"))
                    .telefono(fvs.bothify("+569########"))
                    .build();
            this.add(persona);
        }
    }
}
