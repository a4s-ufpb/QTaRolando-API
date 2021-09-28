package br.ufpb.dcx.apps4society.qtarolando.api.controller;

import br.ufpb.dcx.apps4society.qtarolando.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/login")
public class UsuarioController {
    @Autowired
    private UsuarioService service;

    @GetMapping("/login-by-email/{email}")
    @ResponseBody
    public void loginPorEmail(@PathVariable("email") String email){
        service.loadUserByUsername(email);
    }
}
