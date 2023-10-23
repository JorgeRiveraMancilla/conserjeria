package cl.ucn.disc.as.model;

import io.ebean.annotation.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.Getter;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.Instant;

@ToString(callSuper = true)
@AllArgsConstructor
@Entity
public class Pago extends BaseModel {
    @NotNull
    @Getter
    private Instant fechaPago;

    @NotNull
    @Getter
    private Integer monto;

    @ManyToOne
    private Contrato contrato;

    @Builder
    public Pago(
            @NonNull Instant fechaPago,
            @NonNull Integer monto) {
        this.fechaPago = fechaPago;
        this.monto = monto;
    }
}
