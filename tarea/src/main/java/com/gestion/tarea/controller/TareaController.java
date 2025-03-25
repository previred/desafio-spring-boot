package com.gestion.tarea.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.gestion.tarea.dto.TareaDto;
import com.gestion.tarea.repository.TareaRepository;

@Controller
@RequestMapping("api/tareas")
public class TareaController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TareaRepository tareaRepository;

    @PostMapping(path = "/insert",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*")
    public @ResponseBody ResponseEntity<TareaDto> insertTarea(@RequestBody TareaDto tareaDto) {
        try {
            TareaDto resp = tareaRepository.save(tareaDto);
            return new ResponseEntity<TareaDto>(resp, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity("Error al crear tarea",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/update", produces = "application/json")
    @CrossOrigin(origins = "*")
    public @ResponseBody
    ResponseEntity<TareaDto> updateTarea(@RequestBody TareaDto newTarea) {
        TareaDto resp = tareaRepository.save(newTarea);
        return new ResponseEntity<TareaDto>(resp, HttpStatus.OK);

    }

    @DeleteMapping(path = "/delete", produces = "application/json")
    @CrossOrigin(origins = "*")
    public @ResponseBody
    ResponseEntity<TareaDto> deleteTarea(@RequestBody TareaDto newTarea) {
        tareaRepository.delete(newTarea);
        return new ResponseEntity<TareaDto>(HttpStatus.OK);

    }

    @GetMapping(path = "/id/{id}", produces = "application/json")
    @CrossOrigin(origins = "*")
    public @ResponseBody
    ResponseEntity getTareaId(@PathVariable long id) {
        try {
            LOGGER.info("get tarea por id");
            Optional<TareaDto> tareaDto = tareaRepository.findById(id);
            return new ResponseEntity(tareaDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Error Interno al buscar tarea", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/all", produces = "application/json")
    @CrossOrigin(origins = "*")
    public @ResponseBody
    ResponseEntity getAllTareas() {
        try {
            LOGGER.info("getAll tareas");
            List<TareaDto> tareasDto = tareaRepository.findAll();
            return new ResponseEntity(tareasDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Error Interno al buscar las tareas", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
