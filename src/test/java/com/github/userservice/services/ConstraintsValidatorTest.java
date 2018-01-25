package com.github.userservice.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.github.userservice.helpers.StubBuilder;
import com.github.userservice.views.UserRequest;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Map;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class ConstraintsValidatorTest {

    @Mock
    private Validator validator;

    @Mock
    private ConstraintViolation<UserRequest> constraintViolation;

    @InjectMocks
    private ConstraintsValidator constraintsValidator;

    @Test
    public void validateMap_shouldThrowException_whenViolationOccurs() {
        // GIVEN
        String fieldName = "email";
        String fieldValue = "incorrectEmail.co.uk";
        Map<String, Object> request = ImmutableMap.of(fieldName, fieldValue);
        Set<ConstraintViolation<UserRequest>> violations = ImmutableSet.of(constraintViolation);

        given(validator.validateValue(UserRequest.class, fieldName, fieldValue)).willReturn(violations);
        given(constraintViolation.getPropertyPath()).willReturn(PathImpl.createPathFromString("email"));
        given(constraintViolation.getMessage()).willReturn("must not be blank");

        // WHEN
        Throwable actual = catchThrowable(() -> constraintsValidator.validate(UserRequest.class, request));

        // THEN
        assertThat(actual).isInstanceOf(ConstraintViolationException.class)
                .hasMessage("email: must not be blank");
        verify(validator).validateValue(UserRequest.class, fieldName, fieldValue);
    }

    @Test
    public void validateBean_shouldThrowException_whenViolationOccurs() {
        // GIVEN
        UserRequest userRequest = StubBuilder.userRequest();
        userRequest.setEmail(null);

        Set<ConstraintViolation<UserRequest>> violations = ImmutableSet.of(constraintViolation);
        given(validator.validate(userRequest)).willReturn(violations);
        given(constraintViolation.getPropertyPath()).willReturn(PathImpl.createPathFromString("email"));
        given(constraintViolation.getMessage()).willReturn("must not be blank");

        // WHEN
        Throwable actual = catchThrowable(() -> constraintsValidator.validate(userRequest));

        // THEN
        assertThat(actual).isInstanceOf(ConstraintViolationException.class)
                .hasMessage("email: must not be blank");
        verify(validator).validate(userRequest);
    }
}