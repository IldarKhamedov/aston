package ru.khamedov.ildar.aston.model.util;


import ru.khamedov.ildar.aston.model.Operation;

public class Withdraw implements Task{
    @Override
    public void execute(Operation operation) {
        minus(operation.getFrom(), operation.getAmount());
    }
}
