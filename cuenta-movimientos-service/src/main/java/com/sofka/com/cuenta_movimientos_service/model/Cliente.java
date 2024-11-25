package com.sofka.com.cuenta_movimientos_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cliente")

public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id")
    @JsonIgnore
    private Long id;

    @Column(name = "contrasena", nullable = false)
    @JsonIgnore
    private String contrasena;

    @Column(name = "estado", nullable = false)
    private Boolean estado;

    @OneToMany(mappedBy = "cliente",fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cuenta> cuentas = new ArrayList<>();


    public Long getId() {
        return id;
    }


    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

}
