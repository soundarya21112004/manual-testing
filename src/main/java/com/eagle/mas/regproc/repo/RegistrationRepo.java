package com.eagle.mas.regproc.repo;

import com.eagle.mas.regproc.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepo extends JpaRepository<Registration, String> {

    Registration findByRegId(String rid);
}
