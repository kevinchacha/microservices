package com.sofka.com.cuenta_movimientos_service.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sofka.com.cuenta_movimientos_service.utils.TipoCuenta;
import jakarta.persistence.*;
import java.util.List;
@Entity
@Table(name = "cuenta")
public class Cuenta  {
    @Id
    @JsonIgnore
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

    @Column(name = "primerMonto", nullable = false)
    private double primerMonto;


    @Column(name = "estado", nullable = false)
    private boolean estado;


    @JsonIgnore
    @Column(name = "cliente_id", nullable = false)
    private long cliente;


    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Movimiento> movimientos;



    public double getPrimerMonto() {
        return primerMonto;
    }

    public void setPrimerMonto(double primerMonto) {
        this.primerMonto = primerMonto;
    }

    public void setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public long getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(long numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

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

    public long getCliente() {
        return cliente;
    }

    public void setCliente(long cliente) {
        this.cliente = cliente;
    }

}
