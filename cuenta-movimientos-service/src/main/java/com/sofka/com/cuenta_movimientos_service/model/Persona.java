package com.sofka.com.cuenta_movimientos_service.model;
import jakarta.persistence.*;

@Entity
@Table(name = "persona")
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre", nullable = false)
    @NotNull(message = "El nombre no puede ser nulo.")
    @Size(min = 1, max = 100, message = "El nombre debe tener entre 1 y 100 caracteres.")
    private String nombre;

    @Column(name = "genero", nullable = false, length = 50)
    @NotNull(message = "El género no puede ser nulo.")
    @Size(min = 1, max = 50, message = "El género debe tener entre 1 y 50 caracteres.")
    private String genero;

    @Column(name = "edad", nullable = false)
    @NotNull(message = "La edad no puede ser nula.")
    @Min(value = 0, message = "La edad no puede ser negativa.")
    @Max(value = 98, message = "La edad no puede ser mayor a 98 años.")
    private Integer edad;

    @Column(name = "identificacion", unique = true, nullable = false)
    @Pattern(regexp = "^[0-9]{10}$", message = "La identificación debe tener 10 caracteres numéricos.")
    private String identificacion;

    @Column(name = "direccion")
    @Size(max = 255, message = "La dirección no puede exceder los 255 caracteres.")
    private String direccion;

    @Column(name = "telefono")
    @Pattern(regexp = "^[0-9]{10}$", message = "El teléfono debe tener 10 caracteres numéricos.")
    private String telefono;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}