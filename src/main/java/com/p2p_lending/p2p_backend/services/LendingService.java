package com.p2p_lending.p2p_backend.services;

import com.p2p_lending.p2p_backend.models.*;
import com.p2p_lending.p2p_backend.repositories.LoanApplicationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LendingService {

    private final LoanApplicationRequestRepository loanApplicationRequestRepository;

    @Autowired
    public LendingService(LoanApplicationRequestRepository loanApplicationRequestRepository) {
        this.loanApplicationRequestRepository = loanApplicationRequestRepository;
    }

    public List<String> processRequest(LoanApplicationRequest request) {

        List<String> errors = new ArrayList<>();

        if (request.getPastBusinesses()) {
            validatePastBusinesses(request.getPastBusinessData(), errors);
        }
        validateCurrentBusiness(request, errors);
        validateAge(request.getAge(), errors);

        if (errors.isEmpty()) {
            this.loanApplicationRequestRepository.save(request);
        }

        return errors;
    }

    private void validatePastBusinesses(List<BusinessData> pastBusinesses, List<String> errors) {
        if (pastBusinesses == null) {
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

    private void validateCurrentBusiness(LoanApplicationRequest request, List<String> errors) {
        if (request.getBusinessStatus().equals(BusinessStatus.EXISTING)) {
            validateDuration(request.getCurrentBusinessData(), errors);
            if (request.getCurrentBusinessData().getMonthlyRevenue() == null) {
                errors.add("An existing business must provide its previous monthly revenue.");
            }
            else{
                validateMonthlyRevenue(request.getCurrentBusinessData(), errors);
            }
        } else if (request.getBusinessStatus().equals(BusinessStatus.NEW)) {
            if (request.getProjectedRevenue() <= 0) {
                errors.add("Invalid projected revenue.");
            }
        }
        validateAnzsicCode(request.getCurrentBusinessData(), errors);
        validateAbn(request.getCurrentBusinessData(), errors);
        validateAddress(request.getCurrentBusinessData(),errors);
    }

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
            case 1:
                if (!code.matches("[A-S]")) {
                    errors.add(business.getBusinessName() + " : Invalid ANZSIC code: a single letter must be between A and S.");
                }
                break;
            case 2:
                if (!code.matches("0[1-9]|[1-8][0-9]|9[0-6]")) {
                    errors.add(business.getBusinessName() + " : Invalid ANZSIC code: two digits must be between 01 and 96.");
                }
                break;
            case 3:
                if (!code.matches("0[1-9][1-9]|[1-8][0-9][0-9]|9[0-5][0-9]|960")) {
                    errors.add(business.getBusinessName() + " : Invalid ANZSIC code: three digits must be between 011 and 960.");
                }
                break;
            case 4:
                if (!code.matches("0[1-9][1-9][1-9]|[1-8][0-9][0-9][0-9]|9[0-5][0-9][0-9]|960[0-3]")) {
                    errors.add(business.getBusinessName() + " : Invalid ANZSIC code: four digits must be between 0111 and 9603.");
                }
                break;
            default:
                errors.add(business.getBusinessName() + " : Invalid ANZSIC code length: must be 1 to 4 characters.");
        }
    }

    private void validateAbn(BusinessData business, List<String> errors) {
        if (!business.getAbn().matches("^(\\d *?){11}$")) {
            errors.add(business.getBusinessName() + " : Invalid abn");
        }
    }

    private void validateMonthlyRevenue(BusinessData business, List<String> errors) {
        if (business.getMonthlyRevenue().size() > 12) {
            errors.add(business.getBusinessName() + " : Only the most recent 12 months of monthly revenue data is required.");
        }
    }

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
