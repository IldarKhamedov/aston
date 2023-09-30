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

    private static final String AMOUNT="Amount must be positive";

    private static final String OPERATION_TYPE="Select type operation";

    private static final String SELF_OPERATION="Operation with your account, delete the receiver";

    @Resource
    private AccountService accountService;

    @Resource
    private OperationRepository operationRepository;

    public Long executeOperation(OperationDTO operationDTO){
        checkOperationDTO(operationDTO);
        Operation operation=createOperation(operationDTO);
        checkOperation(operation);
        operation.getOperationType().executeTask(operation);
        accountService.saveAccounts(operation);
        operationRepository.save(operation);
        return operation.getId();
    }

    private void checkOperation(Operation operation){
        if(operation.getFrom()==null){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,AUTHENTICATION);
        }
        if((!(operation.getOperationType()==OperationType.DEPOSIT))
                && operation.getFrom().getBalance() <operation.getAmount()){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, BALANCE);
        }
        if(operation.getOperationType()==OperationType.TRANSFER && operation.getTo()==null){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,RECEIVER_NOT_EXISTS);
        }
    }

    private void checkOperationDTO(OperationDTO operationDTO){
        if(operationDTO.getAmount() <= 0){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,AMOUNT);
        }
        if(operationDTO.getOperationType()==null){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,OPERATION_TYPE);
        }
        if(operationDTO.getOperationType()== OperationType.TRANSFER && (!StringUtils.hasText(operationDTO.getNameTo()))){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, ADD_RECEIVER);
        }
        if((!(operationDTO.getOperationType()== OperationType.TRANSFER)) && StringUtils.hasText(operationDTO.getNameTo())){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, SELF_OPERATION);
        }
    }

    private Operation createOperation(OperationDTO operationDTO){
        Operation operation=new Operation();
        Account accountFrom=accountService.getSender(operationDTO);
        operation.setFrom(accountFrom);
        operation.setAmount(operationDTO.getAmount());
        operation.setOperationType(operationDTO.getOperationType());
        addAccountTo(operation,operationDTO.getNameTo());
        return operation;
    }

    private void addAccountTo(Operation operation,String name){
        if(operation.getOperationType()==OperationType.TRANSFER){
            Account accountTo= accountService.getReceiver(name);
            operation.setTo(accountTo);
        }
    }


}
