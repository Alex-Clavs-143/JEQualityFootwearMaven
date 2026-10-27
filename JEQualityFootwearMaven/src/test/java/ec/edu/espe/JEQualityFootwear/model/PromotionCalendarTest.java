package ec.edu.espe.JEQualityFootwear.model;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PromotionCalendarTest {

    private PromotionCalendar promotionCalendar;

    public PromotionCalendarTest() {
    }

    @BeforeEach
    public void setUp() {
        promotionCalendar = new PromotionCalendar("Matriz Quito", "Av. Amazonas y Naciones Unidas", "0991234567", 8, 20);
    }

    @Test
    public void testAddPromotion() {
        promotionCalendar.addPromotion("01 al 15 de Noviembre", "20% de descuento", "ZAP-001");
        assertEquals(1, promotionCalendar.getScheduledPromotions().size(), "La promocion debe agregarse correctamente a la lista");
    }

    @Test
    public void testIsStoreOpen() {
        promotionCalendar.setOpeningHour(0);
        promotionCalendar.setClosingHour(24);
        boolean expResult = true;
        boolean result = promotionCalendar.isStoreOpen();
        assertEquals(expResult, result, "La tienda debe reportarse abierta dentro del horario establecido");
    }

    @Test
    public void testGetBranchName() {
        String expResult = "Matriz Quito";
        String result = promotionCalendar.getBranchName();
        assertEquals(expResult, result, "El nombre de la sucursal debe coincidir");
    }

    @Test
    public void testSetBranchName() {
        String branchName = "Sucursal Sur";
        promotionCalendar.setBranchName(branchName);
        assertEquals(branchName, promotionCalendar.getBranchName(), "El nombre de la sucursal debe actualizarse");
    }

    @Test
    public void testGetScheduledPromotions() {
        List<String> result = promotionCalendar.getScheduledPromotions();
        assertNotNull(result, "La lista de promociones no debe ser null");
    }

    @Test
    public void testToString() {
        String result = promotionCalendar.toString();
        assertNotNull(result, "La representacion en texto no debe ser null");
        assertTrue(result.contains("Matriz Quito"), "Debe contener el nombre de la sucursal");
    }

    @Test
    public void testSetNegativeOpeningHourThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            promotionCalendar.setOpeningHour(-1);
        }, "No se debe permitir una hora de apertura negativa");
    }

    @Test
    public void testSetInvalidClosingHourThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            promotionCalendar.setClosingHour(25);
        }, "No se debe permitir una hora de cierre mayor a 24");
    }

    @Test
    public void testSetEmptyBranchNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            promotionCalendar.setBranchName("");
        }, "No se debe permitir un nombre de sucursal vacio");
    }

    @Test
    public void testSetInvalidContactPhoneThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            promotionCalendar.setContactPhone("ABC");
        }, "No se debe permitir un telefono con caracteres no numericos");
    }
}