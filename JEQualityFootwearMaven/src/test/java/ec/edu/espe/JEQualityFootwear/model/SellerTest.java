package ec.edu.espe.JEQualityFootwear.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SellerTest {

    private Seller seller;

    public SellerTest() {
    }

    @BeforeEach
    public void setUp() {
        seller = new Seller(201, 460.0f, "1711223344", "Carlos Perez",
                "0987654321", "carlos@jequality.com", "carlos_v", "pass123");
    }

    @Test
    public void testSell() {
        int initialCount = seller.getCompletedSalesCount();
        seller.sell();

        assertEquals(initialCount + 1, seller.getCompletedSalesCount(),
                "El contador de ventas realizadas debe incrementarse en uno");
    }

    @Test
    public void testAddCommission() {
        float commissionAmount = 25.50f;
        seller.addCommission(commissionAmount);

        assertEquals(commissionAmount, seller.getAccumulatedCommission(), 0.001f,
                "La comision acumulada debe reflejar el monto agregado");
    }

    @Test
    public void testCalculateSalary() {
        seller.addCommission(50.0f);
        float expectedSalary = 460.0f + 50.0f;

        assertEquals(expectedSalary, seller.calculateSalary(), 0.001f,
                "El sueldo total debe ser la suma del sueldo base y las comisiones acumuladas");
    }

    @Test
    public void testGetCompletedSalesCount() {
        assertEquals(0, seller.getCompletedSalesCount(),
                "El contador de ventas debe iniciar en cero");
    }

    @Test
    public void testToString() {
        String result = seller.toString();

        assertNotNull(result, "La descripcion del vendedor no debe ser null");
        assertTrue(result.contains("Carlos Perez"),
                "La descripcion debe contener el nombre del vendedor");
        assertTrue(result.contains("201"),
                "La descripcion debe contener el codigo de empleado");
    }

    @Test
    public void testAddNegativeCommissionThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            seller.addCommission(-10.0f);
        }, "No se debe permitir agregar comisiones negativas");
    }

    @Test
    public void testSetNegativeAccumulatedCommissionThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            seller.setAccumulatedCommission(-50.0f);
        }, "La comision acumulada no puede ser negativa");
    }

    @Test
    public void testSetNegativeCompletedSalesCountThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            seller.setCompletedSalesCount(-5);
        }, "El contador de ventas realizadas no puede ser negativo");
    }

    @Test
    public void testSetNegativeSalaryThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            seller.setSalary(-400.0f);
        }, "El sueldo base del vendedor no puede ser negativo");
    }

    @Test
    public void testCreateSellerWithNullNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Seller(202, 460.0f, "1700000000", null,
                    "0999999999", "test@test.com", "user", "pass");
        }, "No se debe permitir crear un vendedor sin nombre");
    }
}