package com.app.elista.registration;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;


class RegistrationRestControllerTest {

    @Test
    void register() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime now = LocalDateTime.now();

        System.out.println(dtf.format(now));

    }
}