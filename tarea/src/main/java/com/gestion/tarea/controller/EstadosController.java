package com.gestion.tarea.controller;

import com.gestion.tarea.dto.EstadosTareaDto;
import com.gestion.tarea.repository.EstadoTareaRepository;
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
@RequestMapping("api/estados")
public class EstadosController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EstadoTareaRepository estadoTareaRepository;

    @PostMapping(path = "/insert",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*")
    public @ResponseBody ResponseEntity<EstadosTareaDto> insertEstadoTarea(@RequestBody EstadosTareaDto estadosTareaDto) {
        try {
            EstadosTareaDto resp = estadoTareaRepository.save(estadosTareaDto);
            return new ResponseEntity<EstadosTareaDto>(resp, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity("Error al crear estado tarea",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/update", produces = "application/json")
    @CrossOrigin(origins = "*")
    public @ResponseBody
    ResponseEntity<EstadosTareaDto> updateEstadoTarea(@RequestBody EstadosTareaDto newEstadoTarea) {
        EstadosTareaDto resp = estadoTareaRepository.save(newEstadoTarea);
        return new ResponseEntity<EstadosTareaDto>(resp, HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete", produces = "application/json")
    @CrossOrigin(origins = "*")
    public @ResponseBody
    ResponseEntity<EstadosTareaDto> deleteEstadoTarea(@RequestBody EstadosTareaDto newEstadoTarea) {
        estadoTareaRepository.delete(newEstadoTarea);
        return new ResponseEntity<EstadosTareaDto>(HttpStatus.OK);
    }

    @GetMapping(path = "/id/{id}", produces = "application/json")
    @CrossOrigin(origins = "*")
    public @ResponseBody
    ResponseEntity getEstadoTareaId(@PathVariable long id) {
        try {
            LOGGER.info("get estado tarea por id");
            Optional<EstadosTareaDto> estadosTareaDto = estadoTareaRepository.findById(id);
            return new ResponseEntity(estadosTareaDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Error Interno al buscar estadoTarea", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/all", produces = "application/json")
    @CrossOrigin(origins = "*")
    public @ResponseBody
    ResponseEntity getAllEstados() {
        try {
            LOGGER.info("getAll estados");
            List<EstadosTareaDto> estadosTareaDtoList=estadoTareaRepository.findAll();

            return new ResponseEntity(estadosTareaDtoList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("Error Interno al buscar todos los estados", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
