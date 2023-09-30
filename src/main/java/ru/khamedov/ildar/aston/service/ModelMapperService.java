package ru.khamedov.ildar.aston.service;

import jakarta.annotation.Resource;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.khamedov.ildar.aston.dto.PublicAccountDTO;
import ru.khamedov.ildar.aston.dto.SecretAccountDTO;
import ru.khamedov.ildar.aston.model.Account;

@Service
public class ModelMapperService {

    @Resource
    private ModelMapper modelMapper;

    public Account convertToAccount(SecretAccountDTO accountDTO){
        return modelMapper.map(accountDTO,Account.class);
    }

    public PublicAccountDTO convertToPublicAccountDTO(Account account){
        return modelMapper.map(account,PublicAccountDTO.class);
    }
}
