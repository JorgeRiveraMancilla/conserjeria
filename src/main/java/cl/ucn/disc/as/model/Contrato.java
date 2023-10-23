package cl.ucn.disc.as.model;

import io.ebean.annotation.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.AllArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ToString(callSuper = true)
@AllArgsConstructor
@Entity
public class Contrato extends BaseModel {
    @NotNull
    private Instant fechaPago;

    @NotNull
    @ManyToOne
    @Getter
    private Persona persona;

    @NotNull
    @ManyToOne
    @Getter
    private Departamento departamento;

    @NotNull
    @OneToMany(mappedBy = "contrato")
    @Getter
    private List<Pago> pagos;

    @Builder
    public Contrato(@NonNull Instant fechaPago,
                    @NonNull Persona persona,
                    @NonNull Departamento departamento) {
        this.fechaPago = fechaPago;
        this.persona = persona;
        this.departamento = departamento;
        this.pagos = new ArrayList<>();
    }
}