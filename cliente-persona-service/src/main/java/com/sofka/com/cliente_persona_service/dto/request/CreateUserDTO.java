package com.sofka.com.cliente_persona_service.dto.request;
import jakarta.validation.constraints.*;

public record CreateUserDTO(
        @NotNull(message = "El nombre no puede estar vacío.")
        @Size(min = 1, max = 100, message = "El nombre debe tener entre 1 y 100 caracteres.")
        String nombre,

        @NotNull(message = "El género no puede estar vacío.")
        @Size(min = 1, max = 50, message = "El género debe tener entre 1 y 50 caracteres.")
        String genero,

        @NotNull(message = "La edad no puede ser nula.")
        @Min(value = 0, message = "La edad no puede ser negativa.")
        @Max(value = 98, message = "La edad no puede ser mayor a 98 años.")
        Integer edad,

        @NotNull(message = "La identificación no puede ser nula.")
        Long identificacion,

        @Size(max = 255, message = "La dirección no puede exceder los 255 caracteres.")
        String direccion,


        @NotNull(message = "El telefono no puede ser nulo.")
        @Pattern(regexp = "^[0-9]{10}$", message = "El teléfono debe tener 10 caracteres numéricos.")
        String telefono,

        @NotNull(message = "La contraseña no puede estar vacía.")
        @Size(min = 4, max = 20, message = "La contraseña debe tener entre 4 y 20 caracteres.")
        String contrasena,

        Boolean estado
) {
}
