package ru.khamedov.ildar.aston.controller;

import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.khamedov.ildar.aston.dto.OperationDTO;
import ru.khamedov.ildar.aston.service.OperationService;

@RestController
@RequestMapping("/api/v1/aston/operation")
public class OperationRestController {

    @Resource
    private OperationService operationService;

    @PostMapping("/execute")
    public ResponseEntity executeOperation(@RequestBody OperationDTO operationDTO){
        return new ResponseEntity(operationService.executeOperation(operationDTO), HttpStatus.OK);
    }
}
