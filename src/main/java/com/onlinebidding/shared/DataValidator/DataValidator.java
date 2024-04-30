package com.onlinebidding.shared.DataValidator;

import org.springframework.validation.BindingResult;

import java.util.Map;

public interface DataValidator {
    Map<String, String> validateInputData(BindingResult result);
}
