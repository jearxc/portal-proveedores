package pe.edu.idat.portalproveedores.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pe.edu.idat.portalproveedores.dto.RegistroProveedorDTO;
import pe.edu.idat.portalproveedores.service.ProveedorService;
import org.springframework.web.client.RestTemplate;
import pe.edu.idat.portalproveedores.dto.RecaptchaResponse;


@Controller
@RequiredArgsConstructor
public class RegistroController {

    private final ProveedorService proveedorService;

    @Value("${recaptcha.site-key}")
    private String siteKey;
    @Value("${recaptcha.secret-key}")
    private String secretKey;

    @GetMapping("/registro")
    public String mostrarFormulario(Model model){

        model.addAttribute(
                "registroProveedorDTO",
                new RegistroProveedorDTO()
        );

        model.addAttribute(
                "siteKey",
                siteKey
        );

        return "registro";
    }

    @PostMapping("/registro")
    public String registrarProveedor(
            @Valid RegistroProveedorDTO dto,
            BindingResult result,

            @RequestParam(
                    name = "g-recaptcha-response",
                    required = false
            )
            String captcha,

            Model model){

        System.out.println("ENTRO AL POST");

        if(result.hasErrors()){

            System.out.println("HAY ERRORES");

            model.addAttribute(
                    "siteKey",
                    siteKey
            );

            return "registro";
        }

        if(captcha == null || captcha.isBlank()){

            model.addAttribute(
                    "errorCaptcha",
                    "Debe validar el captcha"
            );

            model.addAttribute(
                    "siteKey",
                    siteKey
            );

            return "registro";
        }

        String url =
                "https://www.google.com/recaptcha/api/siteverify"
                        + "?secret=" + secretKey
                        + "&response=" + captcha;

        RestTemplate restTemplate =
                new RestTemplate();

        RecaptchaResponse response =
                restTemplate.getForObject(
                        url,
                        RecaptchaResponse.class
                );

        if(response == null || !response.isSuccess()){

            model.addAttribute(
                    "errorCaptcha",
                    "Captcha inválido"
            );

            model.addAttribute(
                    "siteKey",
                    siteKey
            );

            return "registro";
        }

        proveedorService.registrarProveedor(dto);

        System.out.println("VALIDACION OK");

        model.addAttribute(
                "mensaje",
                "Registro validado correctamente"
        );

        model.addAttribute(
                "siteKey",
                siteKey
        );

        return "registro";
    }

}