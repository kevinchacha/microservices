package com.sofka.com.cliente_persona_service.utils;
import com.sofka.com.cliente_persona_service.model.ManageResponse;
import org.springframework.stereotype.Component;

@Component
public class CommonUtils {
    public ManageResponse createResponse(String errorCode, String errorDescription, Object detalle) {
        ManageResponse responseDTO = new ManageResponse();
        responseDTO.setTipo(errorCode);
        responseDTO.setMensaje(errorDescription);
        responseDTO.setDetalle(detalle);
        return responseDTO;
    }
}
