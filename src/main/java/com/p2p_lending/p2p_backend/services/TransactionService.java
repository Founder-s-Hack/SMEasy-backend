package com.p2p_lending.p2p_backend.services;

import com.p2p_lending.p2p_backend.models.Loan;
import com.p2p_lending.p2p_backend.models.Transaction;
import com.p2p_lending.p2p_backend.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {

    private final LoanRepository loanRepository;

    @Autowired
    public TransactionService (LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public String processTransaction(Transaction transaction) {

        Optional<Loan> optionalLoan = this.loanRepository.findById(transaction.getUserId());

        if (optionalLoan.isEmpty()) {
            return "Loan not found.";
        }

        Loan loan = optionalLoan.get();

        if (loan.getPaidOff()) {
            return "Your loan has been fully repaid.";
        }

        float repayAmount = transaction.getTransactionAmount() * transaction.getCommissionCharged();
        float repayBalance = loan.getBalance();

        if (repayBalance - repayAmount <= 0) {
            loan.setBalance(0);
            loan.setPaidOff(true);
        } else {
            float newRepayBalance = repayBalance - repayAmount;
            loan.setBalance(newRepayBalance);
        }

        this.loanRepository.save(loan);

        return "Balance to repay: $" + loan.getBalance();
    }

}
