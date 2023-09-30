package ru.khamedov.ildar.aston.dto;

import lombok.Getter;
import lombok.Setter;
import ru.khamedov.ildar.aston.model.OperationType;

@Getter
@Setter
public class OperationDTO {

    private String nameFrom;

    private int pin;

    private String nameTo;

    private long amount;

    private OperationType operationType;
}
