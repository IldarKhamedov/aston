package ru.khamedov.ildar.aston.model;

import ru.khamedov.ildar.aston.model.util.Deposit;
import ru.khamedov.ildar.aston.model.util.Task;
import ru.khamedov.ildar.aston.model.util.Transfer;
import ru.khamedov.ildar.aston.model.util.Withdraw;

public enum OperationType {
    DEPOSIT(new Deposit()),
    WITHDRAW(new Withdraw()),
    TRANSFER(new Transfer());

    private Task task;

    OperationType(Task task) {
        this.task = task;
    }

    public void executeTask(Operation operation){
        task.execute(operation);
    }
}
