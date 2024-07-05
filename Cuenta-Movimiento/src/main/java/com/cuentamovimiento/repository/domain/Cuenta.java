package com.cuentamovimiento.repository.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "cuenta")
public class Cuenta {
    @Id
    @Column(name = "numero", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo")
    private Producto tipo;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "saldo", nullable = false)
    private Double saldo;

    @NotNull
    @ColumnDefault("1")
    @Column(name = "estado", nullable = false)
    private Integer estado;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente", nullable = false)
    private Cliente cliente;

}