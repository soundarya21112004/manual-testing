package com.eagle.mas.regproc.repo;

import com.eagle.mas.regproc.model.AbisResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AbisResponseRepo extends JpaRepository<AbisResponse,String>{
    @Query(value = "select t1 from AbisResponse t1 where t1.abisRequest =:id order by t1.crDtimes desc")
    AbisResponse findReqIdById(String id);
}
