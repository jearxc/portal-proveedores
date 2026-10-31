package pe.edu.idat.portalproveedores.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsuarioDuplicadoException.class)
    public String usuarioDuplicado(
            UsuarioDuplicadoException ex,
            Model model){

        model.addAttribute(
                "error",
                ex.getMessage()
        );

        return "error-negocio";
    }

    @ExceptionHandler(CorreoDuplicadoException.class)
    public String correoDuplicado(
            CorreoDuplicadoException ex,
            Model model){

        model.addAttribute(
                "error",
                ex.getMessage()
        );

        return "error-negocio";
    }

    @ExceptionHandler(RucDuplicadoException.class)
    public String rucDuplicado(
            RucDuplicadoException ex,
            Model model){

        model.addAttribute(
                "error",
                ex.getMessage()
        );

        return "error-negocio";
    }
}