package ec.edu.espe.JEQualityFootwear.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PromotionCalendarTest {

    private PromotionCalendar calendar;

    public PromotionCalendarTest() {
    }

    @BeforeEach
    public void setUp() {
        calendar = new PromotionCalendar("Sucursal Centro", "Av. Principal 123", "0991234567", 8, 20);
    }

    @Test
    public void testSetNegativeOpeningHourThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            calendar.setOpeningHour(-1);
        }, "La hora de apertura no puede ser negativa");
    }

    @Test
    public void testSetClosingHourOutOfRangeThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            calendar.setClosingHour(25);
        }, "La hora de cierre no puede superar las 24 horas");
    }

    @Test
    public void testSetClosingHourBeforeOpeningHourThrowsException() {
        calendar.setOpeningHour(10);
        assertThrows(IllegalArgumentException.class, () -> {
            calendar.setClosingHour(8);
        }, "La hora de cierre no debe ser menor o igual a la hora de apertura");
    }

    @Test
    public void testAddPromotionWithEmptyDescriptionThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            calendar.addPromotion("01 al 15 de Noviembre", "   ", "Botas de cuero");
        }, "No se debe permitir registrar una promocion con descripcion vacia");
    }

    @Test
    public void testAddPromotionWithNullDateRangeThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            calendar.addPromotion(null, "20% de descuento", "Calzado formal");
        }, "El rango de fechas de una promocion no puede ser nulo");
    }

    @Test
    public void testAddPromotionWithEmptyModelsThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            calendar.addPromotion("10 al 20 de Diciembre", "Liquidacion", "");
        }, "Se debe especificar al menos un modelo participante en la promocion");
    }

    @Test
    public void testSetInvalidContactPhoneWithLettersThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            calendar.setContactPhone("099ABCDEFG");
        }, "El telefono de contacto de la sucursal solo debe aceptar digitos numericos");
    }

    @Test
    public void testSetEmptyBranchNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            calendar.setBranchName("");
        }, "El nombre de la sucursal no puede quedar vacio");
    }

    @Test
    public void testSetNullScheduledPromotionsThrowsException() {
        assertThrows(NullPointerException.class, () -> {
            calendar.setScheduledPromotions(null);
        }, "La lista de promociones programadas no debe ser reemplazada por null");
    }

    @Test
    public void testCreateCalendarWithNullAddressThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PromotionCalendar("Sucursal Norte", null, "0991234567", 9, 19);
        }, "No se debe permitir crear una sucursal con direccion nula");
    }
}