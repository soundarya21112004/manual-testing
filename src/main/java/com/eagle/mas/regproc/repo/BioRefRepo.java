package com.eagle.mas.regproc.repo;

import com.eagle.mas.regproc.model.RegBioRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BioRefRepo extends JpaRepository<RegBioRef, String> {
        RegBioRef findFirstByRegIdAndBioRefIdIsNotNull(String candidate);
}
