package com.meli.mutantes.challenge.model.entity;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class MutanteTests {

	private Mutantes mutantes;

    @BeforeEach
    void setUp() {
    	mutantes = new Mutantes();
    }
    
    @Test
    @DisplayName("Should instance F1()")
    void Formularios1Constructor() {
    	mutantes = new Mutantes();
        assertNotNull(mutantes, "Should instance Mutantes()");
    }

    @Test
    @DisplayName("Should get field adn properly")
    void getAdn() throws NoSuchFieldException, IllegalAccessException {
        final java.lang.reflect.Field field = mutantes.getClass().getDeclaredField("adn");
        final String fieldValue = "TTTTTT";
        field.setAccessible(true);
        field.set(mutantes, fieldValue);
        final String result = mutantes.getAdn();

        assertEquals(fieldValue, result, "Field adn wasn't retrieve properly");
    }
    
    @Test
    @DisplayName("Should set field adn properly")
    void setAdn() throws NoSuchFieldException, IllegalAccessException {
        final String fieldValue = "TTTTTT";
        mutantes.setAdn(fieldValue);
        final java.lang.reflect.Field field = mutantes.getClass().getDeclaredField("adn");
        field.setAccessible(true);

        assertEquals(fieldValue, field.get(mutantes), "Fields adn didn't match");
    }
}
