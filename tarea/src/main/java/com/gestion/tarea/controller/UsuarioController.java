package com.gestion.tarea.controller;

import com.gestion.tarea.dto.UsuarioDto;
import com.gestion.tarea.repository.UsuariosRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("api/usuario")
public class UsuarioController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UsuariosRepository usuariosRepository;

    @Autowired
    PasswordEncoder encoder;

    @PostMapping(path = "/insert",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*")
    public @ResponseBody ResponseEntity<UsuarioDto> insertUser(@RequestBody UsuarioDto usuarioDto) {
        try {
            usuarioDto.setPass(encoder.encode(usuarioDto.getPass()));
            UsuarioDto resp = usuariosRepository.save(usuarioDto);
            return new ResponseEntity<UsuarioDto>(resp, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity("Error al crear usuario",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/update", produces = "application/json")
    @CrossOrigin(origins = "*")
    public @ResponseBody
    ResponseEntity<UsuarioDto> updateUser(@RequestBody UsuarioDto newUsuario) {
        newUsuario.setPass(encoder.encode(newUsuario.getPass()));
        UsuarioDto resp = usuariosRepository.save(newUsuario);
        return new ResponseEntity<UsuarioDto>(resp, HttpStatus.OK);

    }

    @DeleteMapping(path = "/delete", produces = "application/json")
    @CrossOrigin(origins = "*")
    public @ResponseBody
    ResponseEntity<UsuarioDto> deleteUser(@RequestBody UsuarioDto newUsuario) {
        usuariosRepository.delete(newUsuario);
        return new ResponseEntity<UsuarioDto>(HttpStatus.OK);

    }

    @GetMapping(path = "/id/{id}", produces = "application/json")
    @CrossOrigin(origins = "*")
    public @ResponseBody
    ResponseEntity getUsuariosId(@PathVariable long id) {
        try {
            LOGGER.info("get usuario por nombre");
            Optional<UsuarioDto> usuarioDto = usuariosRepository.findById(id);
            return new ResponseEntity(usuarioDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Error Interno al buscar usuario", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/all", produces = "application/json")
    @CrossOrigin(origins = "*")
    public @ResponseBody
    ResponseEntity getAllUsuarios() {
        try {
            LOGGER.info("getAll usuarios");
            List<UsuarioDto> usuarioDtos = usuariosRepository.findAll();
            return new ResponseEntity(usuarioDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Error Interno al buscar los usuarios", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
