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
        seller = new Seller(201, 460.0f, "1712345678", "Carlos Perez", "0991234567", "carlos@jequality.com", "seller_carlos", "pass123");
    }

    @Test
    public void testAddNegativeCommissionThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            seller.addCommission(-25.0f);
        }, "No se debe permitir agregar un monto de comision negativo");
    }

    @Test
    public void testSetNegativeAccumulatedCommissionThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            seller.setAccumulatedCommission(-100.0f);
        }, "La comision acumulada no puede establecerse en un valor negativo");
    }

    @Test
    public void testSetNegativeCompletedSalesCountThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            seller.setCompletedSalesCount(-5);
        }, "El contador de ventas realizadas no puede ser negativo");
    }

    @Test
    public void testCalculateSalaryWithMultipleCommissions() {
        seller.addCommission(15.50f);
        seller.addCommission(24.50f);
        assertEquals(500.0f, seller.calculateSalary(), 0.001, "El sueldo total debe sumar exactamente el sueldo base mas las comisiones acumuladas");
    }

    @Test
    public void testSellIncrementsCompletedSalesCount() {
        seller.sell();
        seller.sell();
        assertEquals(2, seller.getCompletedSalesCount(), "Cada llamada al metodo sell debe incrementar en 1 las ventas cerradas");
    }

    @Test
    public void testSetNegativeBaseSalaryThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            seller.setSalary(-460.0f);
        }, "El sueldo base heredado no debe aceptar valores negativos");
    }

    @Test
    public void testSetInvalidEmployeeCodeThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            seller.setEmployeeCode(0);
        }, "El codigo de empleado del vendedor debe ser mayor a cero");
    }

    @Test
    public void testCreateSellerWithEmptyUsernameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Seller(202, 460.0f, "1787654321", "Maria Lopez", "0987654321", "maria@jequality.com", "   ", "1234");
        }, "No se debe permitir crear un vendedor con nombre de usuario en blanco");
    }

    @Test
    public void testSetInvalidIdLengthThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            seller.setId("12345");
        }, "La cedula o RUC del vendedor debe tener exactamente 10 o 13 digitos");
    }

    @Test
    public void testCalculateSalaryWithZeroCommissionBoundary() {
        seller.setAccumulatedCommission(0.0f);
        assertEquals(460.0f, seller.calculateSalary(), 0.001, "Si no hay comisiones, el sueldo total debe ser igual al sueldo base");
    }
}