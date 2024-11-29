package com.sofka.com.cliente_persona_service.model;
import com.sofka.com.cliente_persona_service.utils.TipoCuenta;
import jakarta.persistence.*;
@Entity
@Table(name = "cuenta")
public class Cuenta   {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cuenta_id")
    private Long id;

    @Column(name = "numero_cuenta", nullable = false)
    private long numeroCuenta;

    @Column(name = "tipoCuenta", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoCuenta tipoCuenta;

    @Column(name = "saldoInicial", nullable = false)
    private double saldoInicial;

    @Column(name = "estado", nullable = false)
    private boolean estado;

    @Column(name = "cliente_id", nullable = false)
    private long cliente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public double getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public long getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(long numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

}
