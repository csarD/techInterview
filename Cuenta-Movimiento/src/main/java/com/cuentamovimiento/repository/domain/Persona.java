package com.cuentamovimiento.repository.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "persona")
public class Persona {
    @Id
    @Size(max = 10)
    @Column(name = "identificacion", nullable = false, length = 10)
    private String identificacion;

    @Size(max = 100)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Size(max = 1)
    @NotNull
    @Column(name = "genero", nullable = false, length = 1)
    private String genero;

    @NotNull
    @Column(name = "edad", nullable = false)
    private Integer edad;

    @Size(max = 500)
    @NotNull
    @Column(name = "direccion", nullable = false, length = 500)
    private String direccion;

    @NotNull
    @Column(name = "telefono", nullable = false)
    private Integer telefono;

}