package cl.ucn.disc.as.model;

import io.ebean.annotation.NotNull;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@ToString(callSuper = true)
@AllArgsConstructor
@Entity
public class Edificio extends BaseModel {
    @NotNull
    @Getter
    private String nombre;

    @NotNull
    @Getter
    private String direccion;

    @OneToMany(mappedBy = "edificio", cascade = CascadeType.ALL)
    @Getter
    private List<Departamento> departamentos;

    @Builder
    public Edificio(
            @NonNull String nombre,
            @NonNull String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.departamentos = new ArrayList<>();
    }

    public void add(@NonNull Departamento departamento) {
        this.departamentos.add(departamento);
    }
}
