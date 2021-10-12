package com.sventas.sventas.repository;

import com.sventas.sventas.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User,String> {

    boolean existsByIdAndDateOffIsNull(String id);

    User findByIdAndDateOffIsNull(String id);

    List<User> findAllByDateOffIsNullOrderByLastNameAsc();

    List<User> findByNameIgnoreCaseAndLastNameIgnoreCaseAndDateOffIsNullOrderByLastNameDesc(String name,String lastName);

    List<User> findBySalaryAndDateOffIsNullOrderByLastNameAsc(Double salary);

    List<User> findAllByDateOffIsNullOrderBySalaryDesc();

    List<User> findAllByDateOffIsNullOrderByNameAsc();

    List<User> findAllByDateOffIsNullOrderByNameDesc();
}
