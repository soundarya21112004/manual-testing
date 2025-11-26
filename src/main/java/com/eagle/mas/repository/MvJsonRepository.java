package com.eagle.mas.repository;

import com.eagle.mas.model.MvJson;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface MvJsonRepository  extends CrudRepository<MvJson, BigInteger> {
   /* @Query(value = "SELECT t1 from MvJson t1 where t1.regId=:probe and t1.matchedRefId=:candidate and t1.reqId=:requestId")
    public MvJson getJson(@Param("probe") String probe,@Param("candidate") String candidate,@Param("requestId")String requestId);

    @Query(value = "SELECT t1 from MvJson t1 where t1.regId=:probe and t1.matchedRefId=:probe and t1.reqId=:requestId")
    public MvJson getProbJson(@Param("probe") String probe,@Param("requestId")String requestId);*/

/*    @Query(value = "SELECT t1 from MvJson t1 where t1.regId=:probe and t1.matchedRefId=:candidate and (t1.mvReqJson<>'' and t1.mvReqJson is not null)")
    List<MvJson> getJson(@Param("probe") String probe, @Param("candidate") String candidate, Pageable pageable);

    @Query(value = "SELECT t1 from MvJson t1 where t1.regId=:probe and t1.matchedRefId=:probe and (t1.mvReqJson<>'' and t1.mvReqJson is not null)")
    List<MvJson> getProbJson(@Param("probe") String probe, Pageable pageable);*/

    @Query(value = "SELECT t1 from MvJson t1 where t1.matchedRefId=:rid and (t1.mvReqJson<>'' and t1.mvReqJson is not null)")
    List<MvJson> getJson( @Param("rid") String rid, Pageable pageable);

    @Query(value = "SELECT t1 from MvJson t1 where t1.matchedRefId=:probe and (t1.mvReqJson<>'' and t1.mvReqJson is not null)")
    List<MvJson> getProbJson(@Param("probe") String probe, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE MvJson SET mvReqJson = :mvJson WHERE matchedRefId = :m_rid")
    void saveMvJson(String mvJson, String m_rid);

    @Query(value = "select t1 from MvJson t1 where t1.matchedRefId=:rid ")
    MvJson getUpdateStatus(@Param("rid") String rid, Pageable pageable);
}

