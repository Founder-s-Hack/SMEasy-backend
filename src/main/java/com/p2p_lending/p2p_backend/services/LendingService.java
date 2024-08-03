package com.p2p_lending.p2p_backend.services;

import com.p2p_lending.p2p_backend.models.*;
import com.p2p_lending.p2p_backend.repositories.LoanApplicationRequestRepository;
import com.p2p_lending.p2p_backend.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LendingService {

    private final LoanApplicationRequestRepository loanApplicationRequestRepository;
    private final LoanRepository loanRepository;

    @Autowired
    public LendingService(
            LoanApplicationRequestRepository loanApplicationRequestRepository,
            LoanRepository loanRepository
    ) {
        this.loanApplicationRequestRepository = loanApplicationRequestRepository;
        this.loanRepository = loanRepository;
    }

    /**
     * Processes a loan application by validating past and current business data, and applicant's age.
     * Saves the application if no errors are found and creates a new loan record.
     */
    public List<String> processRequest(LoanApplicationRequest request) {

        List<String> errors = new ArrayList<>(); // to store all the errors message of the application

        if (request.getPastBusinesses()) { // getPastBusinesses is a boolean indicating if past businesses exist
            validatePastBusinesses(request.getPastBusinessData(), errors); // only if there are past businesses only have to validate the data
        }
        validateCurrentBusiness(request, errors);
        validateAge(request.getAge(), errors);

        if (errors.isEmpty()) { // application will be accepted if no errors are occurred and to be fit into ML model later
            this.loanApplicationRequestRepository.save(request);

            // CALL ML

            Loan loan = new Loan();
            loan.setUserId(request.getUserId());
            loan.setAmount(request.getLoanAmount());
            loan.setBalance(request.getLoanAmount());
            loan.setPaidOff(false);
            this.loanRepository.insert(loan);
        }

        return errors;
    }

    /**
     * Requires at least one past business record if the applicant has past businesses and validates each past business data.
     */
    private void validatePastBusinesses(List<BusinessData> pastBusinesses, List<String> errors) {
        if (pastBusinesses == null || pastBusinesses.isEmpty()) {
            errors.add("Past business history is required for existing businesses.");
        }
        else {
            for (BusinessData business : pastBusinesses) {
                validateAddress(business, errors);
                validateAnzsicCode(business, errors);
                validateAbn(business, errors);
                validateDuration(business, errors);
                validateMonthlyRevenue(business, errors);
            }
        }
    }

    /**
     * Validates current business data in the loan application.
     * For existing businesses, checks that the duration and monthly revenue are provided.
     * For new businesses, ensures projected revenue is positive.
     */
    private void validateCurrentBusiness(LoanApplicationRequest request, List<String> errors) {
        if (!request.getIsNewBusiness()) {
            validateDuration(request.getCurrentBusinessData(), errors);
            if (request.getCurrentBusinessData().getMonthlyRevenue() == null || request.getCurrentBusinessData().getMonthlyRevenue().isEmpty()) {
                errors.add("An existing business must provide its previous monthly revenue.");
            }
            else{
                validateMonthlyRevenue(request.getCurrentBusinessData(), errors);
            }
        } else {
            if (request.getProjectedRevenue() <= 0) {
                errors.add("Invalid projected revenue.");
            }
        }
        validateAnzsicCode(request.getCurrentBusinessData(), errors);
        validateAbn(request.getCurrentBusinessData(), errors);
        validateAddress(request.getCurrentBusinessData(),errors);
    }

    /**
     * Only accepts new businesses located in Australia and requires information only for past businesses
     * that were located in Australia.
     */
    private void validateAddress(BusinessData business, List<String> errors) {
        if (!"Australia".equalsIgnoreCase(business.getAddress().getCountry())) {
            errors.add("Business location must be in Australia.");
        }
    }

    private void validateAnzsicCode(BusinessData business, List<String> errors) {
        String code = business.getAnzsicCode().trim();
        if (code.isEmpty()) {
            errors.add(business.getBusinessName() + " : ANZSIC code is required.");
            return;
        }

        switch (code.length()) {
            case 1 -> {
                if (!code.matches("[A-S]")) {
                    errors.add(business.getBusinessName() + " : Invalid ANZSIC code: a single letter must be between A and S.");
                }
            }
            case 2 -> {
                if (!code.matches("0[1-9]|[1-8][0-9]|9[0-6]")) {
                    errors.add(business.getBusinessName() + " : Invalid ANZSIC code: two digits must be between 01 and 96.");
                }
            }
            case 3 -> {
                if (!code.matches("0[1-9][1-9]|[1-8][0-9][0-9]|9[0-5][0-9]|960")) {
                    errors.add(business.getBusinessName() + " : Invalid ANZSIC code: three digits must be between 011 and 960.");
                }
            }
            case 4 -> {
                if (!code.matches("0[1-9][1-9][1-9]|[1-8][0-9][0-9][0-9]|9[0-5][0-9][0-9]|960[0-3]")) {
                    errors.add(business.getBusinessName() + " : Invalid ANZSIC code: four digits must be between 0111 and 9603.");
                }
            }
            default ->
                    errors.add(business.getBusinessName() + " : Invalid ANZSIC code length: must be 1 to 4 characters.");
        }
    }

    private void validateAbn(BusinessData business, List<String> errors) {
        if (!business.getAbn().matches("^(\\d *?){11}$")) {
            errors.add(business.getBusinessName() + " : Invalid abn");
        }
    }

    /**
     Validates the monthly revenue data for a business by ensuring it contains data for no more than the
     * most recent 12 months. This ensures that only the most recent and relevant revenue data is considered
     * for the business evaluation.
     */
    private void validateMonthlyRevenue(BusinessData business, List<String> errors) {
        if (business.getMonthlyRevenue().size() > 12) {
            errors.add(business.getBusinessName() + " : Only the most recent 12 months of monthly revenue data is required.");
        }
    }

    /**
     * Validates the duration of operation for a past / exisitng business, ensuring it has been in operation for at least 1 month.
     */
    private void validateDuration(BusinessData business, List<String> errors) {
        if (business.getYearsInOperation().getYears() <= 0 && business.getYearsInOperation().getMonths()<=0) {
            errors.add(business.getBusinessName() + " : Invalid years in operation.");
        }
    }

    private void validateAge(int age, List<String> errors) {
        if (age < 18) {
            errors.add("Age must be above 18.");
        }
    }

}
