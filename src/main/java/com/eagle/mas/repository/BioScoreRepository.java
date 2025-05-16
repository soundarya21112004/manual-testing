package com.eagle.mas.repository;

import com.eagle.mas.model.BioScore;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BioScoreRepository extends CrudRepository<BioScore,String> {

//    @Query(value = "select b from BioScore b where b.regID =: reg_id and b.matchedRefId =: matched_refID ORDER BY b.regID desc limit 1")
    public BioScore findFirstByRegIDAndMatchedRefIdAndResponseTextNotNullOrderByCrTimesRegIdDesc(String reg_id,String matched_refID);

    public BioScore findFirstByMatchedRefIdAndBioRefIdIsNotNull(String matched_refID);

}
