package ru.khamedov.ildar.aston.service;

import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import ru.khamedov.ildar.aston.dto.OperationDTO;
import ru.khamedov.ildar.aston.model.Account;
import ru.khamedov.ildar.aston.model.Operation;
import ru.khamedov.ildar.aston.model.OperationType;
import org.springframework.util.StringUtils;
import ru.khamedov.ildar.aston.repository.OperationRepository;

@Service
public class OperationService {

    private static final String AUTHENTICATION="Incorrect name or PIN code";

    private static final String ADD_RECEIVER ="Specify receiver";

    private static final String RECEIVER_NOT_EXISTS ="Receiver is not exists";

    private static final String BALANCE="Amount exceeds balance";

    @Resource
    private AccountService accountService;

    @Resource
    private OperationRepository operationRepository;

    public Long executeOperation(OperationDTO operationDTO){
        Operation operation=checkOperation(operationDTO);
        accountService.updateBalance(operation);
        operationRepository.save(operation);
        return operation.getId();
    }

    private Operation checkOperation(OperationDTO operationDTO){
        Operation operation=new Operation();
        Account accountFrom=accountService.getSender(operationDTO);
        if(accountFrom==null){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,AUTHENTICATION);
        }
        if(operationDTO.getOperationType()== OperationType.TRANSFER && (!StringUtils.hasText(operationDTO.getNameTo()))){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, ADD_RECEIVER);
        }
        if((operationDTO.getOperationType()==OperationType.WITHDRAW ||operationDTO.getOperationType()==OperationType.TRANSFER)
                && accountFrom.getBalance() < operationDTO.getAmount()){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, BALANCE);
        }
        Account accountTo=null;
        if(operationDTO.getOperationType()==OperationType.TRANSFER){
            accountTo=accountService.getReceiver(operationDTO);
        }
        if(operationDTO.getOperationType()==OperationType.TRANSFER && accountTo==null){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,RECEIVER_NOT_EXISTS);
        }
        operation.setFrom(accountFrom);
        operation.setTo(accountTo);
        operation.setAmount(operationDTO.getAmount());
        operation.setOperationType(operationDTO.getOperationType());
        return operation;
    }


}
