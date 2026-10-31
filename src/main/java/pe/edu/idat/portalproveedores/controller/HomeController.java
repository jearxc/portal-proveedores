package pe.edu.idat.portalproveedores.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Authentication authentication){

        boolean esAdmin = authentication
                .getAuthorities()
                .stream()
                .anyMatch(a ->
                        a.getAuthority()
                                .equals("ROLE_ADMIN"));

        if(esAdmin){
            return "redirect:/admin/proveedores";
        }

        return "redirect:/proveedor/home";
    }
    @GetMapping("/error-403")
    public String accesoDenegado(){

        return "error-403";

    }

}