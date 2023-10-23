package cl.ucn.disc.as.model;

import io.ebean.annotation.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.persistence.Entity;

@ToString(callSuper = true)
@Entity
public class Departamento extends BaseModel {
    @NotNull
    @Getter
    private Integer numero;

    @NotNull
    @Getter
    private Integer piso;

    @Builder
    public Departamento(
            @NonNull Integer numero,
            @NonNull Integer piso) {
        this.numero = numero;
        this.piso = piso;
    }
}
