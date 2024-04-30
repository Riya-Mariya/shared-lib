package com.onlinebidding.shared.DataValidator.impl;

import com.onlinebidding.shared.DataValidator.DataValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
@Component
public class DataValidatorImpl implements DataValidator {

    public Map<String, String> validateInputData(BindingResult result) {
        return result.getAllErrors().stream()
                    .map(error -> (FieldError) error)
                    .collect(Collectors.toMap(FieldError::getField, error -> error.getDefaultMessage() != null ? error.getDefaultMessage() : ""));

    }
}