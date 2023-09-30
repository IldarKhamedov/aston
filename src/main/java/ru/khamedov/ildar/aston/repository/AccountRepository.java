package ru.khamedov.ildar.aston.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.khamedov.ildar.aston.model.Account;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    @Query(" SELECT a FROM Account a WHERE a.blocked=FALSE ")
    List<Account> findNotBlocked();

    @Query(" SELECT a FROM Account a WHERE a.blocked=FALSE AND a.name=:name ")
    Account findByName(@Param("name")String name);

    @Query(" SELECT a FROM Account a WHERE a.blocked=FALSE AND a.name=:name AND a.pin=:pin")
    Account findByNameAndPin(@Param("name")String name,@Param("pin")int pin);

}
