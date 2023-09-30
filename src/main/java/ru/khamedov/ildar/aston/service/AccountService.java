package ru.khamedov.ildar.aston.service;

import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import ru.khamedov.ildar.aston.dto.OperationDTO;
import ru.khamedov.ildar.aston.dto.PublicAccountDTO;
import ru.khamedov.ildar.aston.dto.SecretAccountDTO;
import ru.khamedov.ildar.aston.model.Account;
import ru.khamedov.ildar.aston.model.Operation;
import ru.khamedov.ildar.aston.model.OperationType;
import ru.khamedov.ildar.aston.repository.AccountRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {


    private static final String NAME_IS_USED="Name is already in use";

    @Resource
    private AccountRepository accountRepository;

    @Resource
    private ModelMapperService modelMapperService;

    public Long createAccount(SecretAccountDTO accountDTO){
        if(prohibitionName(accountDTO.getName())){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,NAME_IS_USED);
        }
        Account account=modelMapperService.convertToAccount(accountDTO);
        accountRepository.save(account);
        return account.getId();
    }

    public List<PublicAccountDTO> getAccountDTOList(){
        return accountRepository.findNotBlocked().stream().map(a->modelMapperService.convertToPublicAccountDTO(a)).collect(Collectors.toList());
    }

    private boolean prohibitionName(String name){
        return accountRepository.findByName(name) != null;
    }

    public Account getSender(OperationDTO operationDTO){
        return accountRepository.findByNameAndPin(operationDTO.getNameFrom(),operationDTO.getPin());
    }
    public Account getReceiver(String name){
        return accountRepository.findByName(name);
    }

    public void saveAccounts(Operation operation){
        Account from= operation.getFrom();
        Account to=operation.getTo();
        if(from!=null){
            accountRepository.save(from);
        }
        if(to!=null){
            accountRepository.save(to);
        }
    }
}
