package ru.khamedov.ildar.aston.controller;

import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.khamedov.ildar.aston.dto.SecretAccountDTO;
import ru.khamedov.ildar.aston.service.AccountService;

@RestController
@RequestMapping("/api/v1/aston/account")
public class AccountRestController {

    @Resource
    private AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity createAccount(@RequestBody SecretAccountDTO accountDTO){
        return new ResponseEntity<>(accountService.createAccount(accountDTO), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity createAccount(){
        return new ResponseEntity<>(accountService.getAccountDTOList(), HttpStatus.OK);
    }
}
