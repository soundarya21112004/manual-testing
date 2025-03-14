package com.eagle.mas.repository;

import com.eagle.mas.model.TokenDetails;
import com.eagle.mas.model.Userdetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<TokenDetails,Long> {
    Optional<TokenDetails> findByUserdetailsAndExpiredAndRevoked(Userdetails userdetails, boolean expired, boolean revoked);
    TokenDetails findByToken(String token);

}
