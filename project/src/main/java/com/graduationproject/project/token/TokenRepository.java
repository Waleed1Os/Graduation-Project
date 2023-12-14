package com.graduationproject.project.token;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token,Integer>{
    @Query("""
    SELECT t\s
    FROM Token t\s
    INNER JOIN User u\s
    ON t.user.username = u.username\s 
    WHERE u.username = :username AND (t.expired = FALSE OR t.revoked = FALSE)          
            """)
    public List<Token> findAllValidTokensByUser(String username);
    public Optional<Token> findByToken(String token);
}
