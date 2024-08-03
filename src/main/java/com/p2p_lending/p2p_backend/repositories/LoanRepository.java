package com.p2p_lending.p2p_backend.repositories;

import com.p2p_lending.p2p_backend.models.Loan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends MongoRepository<Loan, String> {
}
