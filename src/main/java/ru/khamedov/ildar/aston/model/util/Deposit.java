package ru.khamedov.ildar.aston.model.util;


import ru.khamedov.ildar.aston.model.Operation;

public class Deposit implements Task{
    @Override
    public void execute(Operation operation) {
        plus(operation.getFrom(), operation.getAmount());
    }
}
