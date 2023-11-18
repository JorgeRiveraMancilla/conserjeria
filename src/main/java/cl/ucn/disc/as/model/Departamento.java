package cl.ucn.disc.as.model;

import io.ebean.annotation.NotNull;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@ToString(callSuper = true)
@Entity
public class Departamento extends BaseModel {
    @NotNull
    @Getter
    private Integer numero;

    @NotNull
    @Getter
    private Integer piso;

    @ManyToOne
    @Getter
    @Setter
    private Edificio edificio;

    @Builder
    public Departamento(
            @NonNull Integer numero,
            @NonNull Integer piso) {
        this.numero = numero;
        this.piso = piso;
    }
}
