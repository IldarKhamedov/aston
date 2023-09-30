package ru.khamedov.ildar.aston.model.util;

import ru.khamedov.ildar.aston.model.Account;
import ru.khamedov.ildar.aston.model.Operation;

public interface Task {

    public void execute(Operation operation);

    public default void plus(Account account,Long amount){
        account.setBalance(account.getBalance()+amount);
    }
    public default void minus(Account account,Long amount){
        account.setBalance(account.getBalance()-amount);
    }
}
