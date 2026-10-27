package ec.edu.espe.JEQualityFootwear.model;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SalesManagementTest {

    private SalesManagement salesManagement;

    public SalesManagementTest() {
    }

    @BeforeEach
    public void setUp() {
        salesManagement = new SalesManagement(101, 0.05f, 460.0f);
    }

    @Test
    public void testProcessCashPayment() {
        float amountReceived = 50.0f;
        float totalAmount = 40.0f;
        Payment payment = salesManagement.processCashPayment(amountReceived, totalAmount);

        assertNotNull(payment, "El pago procesado no debe ser null");
        assertEquals(10.0f, payment.getChangeGiven(), 0.001f,
                "El cambio calculado debe ser correcto");
    }

    @Test
    public void testAuthenticateUser() {
        Person result = salesManagement.authenticateUser("no_existente", "1234");

        assertNull(result,
                "La autenticacion con credenciales invalidas debe retornar null");
    }

    @Test
    public void testFindCustomerById() {
        Customer result = salesManagement.findCustomerById("1712345678");

        assertNull(result,
                "Debe retornar null si el cliente no se encuentra registrado");
    }

    @Test
    public void testGetCashRegisterBalance() {
        float expResult = 200.00f;
        float result = salesManagement.getCashRegisterBalance();

        assertEquals(expResult, result, 0.001f,
                "El saldo inicial en caja registradora debe coincidir");
    }

    @Test
    public void testSetCommission() {
        float commission = 0.10f;
        salesManagement.setCommission(commission);

        assertEquals(commission, salesManagement.getCommission(), 0.001f,
                "La comision vigente debe actualizarse correctamente");
    }

    @Test
    public void testToString() {
        String result = salesManagement.toString();

        assertNotNull(result, "La representacion en texto no debe ser null");
        assertTrue(result.contains("systemId=101"),
                "La descripcion debe contener el id del sistema");
    }

    @Test
    public void testSetNegativeCommissionThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            salesManagement.setCommission(-0.05f);
        }, "No se debe permitir un porcentaje de comision negativo");
    }

    @Test
    public void testSetNegativeCashRegisterBalanceThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            salesManagement.setCashRegisterBalance(-150.0f);
        }, "No se debe permitir un saldo de caja registradora negativo");
    }

    @Test
    public void testAuthenticateUserWithNullCredentialsThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            salesManagement.authenticateUser(null, null);
        }, "La autenticacion no debe aceptar credenciales nulas");
    }

    @Test
    public void testFindCustomerByNullIdThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            salesManagement.findCustomerById(null);
        }, "La busqueda de cliente no debe aceptar un identificador nulo");
    }
}