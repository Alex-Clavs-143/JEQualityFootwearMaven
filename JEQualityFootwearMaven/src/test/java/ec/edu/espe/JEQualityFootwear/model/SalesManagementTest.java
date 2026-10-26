package ec.edu.espe.JEQualityFootwear.model;

import java.util.ArrayList;
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
    public void testSetNegativeCommissionThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            salesManagement.setCommission(-0.10f);
        }, "El porcentaje de comision no puede ser negativo");
    }

    @Test
    public void testSetCommissionGreaterThanOneHundredPercentThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            salesManagement.setCommission(1.50f);
        }, "La comision no puede superar el 100% (1.0)");
    }

    @Test
    public void testSetNegativeCashRegisterBalanceThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            salesManagement.setCashRegisterBalance(-50.0f);
        }, "El saldo en caja registradora no puede ser negativo");
    }

    @Test
    public void testSetInvalidSystemIdThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            salesManagement.setSystemId(0);
        }, "El ID del sistema debe ser un numero entero positivo mayor a cero");
    }

    @Test
    public void testRegisterSaleWithEmptyShoppingCartThrowsException() {
        Customer customer = new Customer(1001, "1712345678", "Carlos Perez", "0991234567", "carlos@mail.com", "carlos1", "1234");
        Seller seller = new Seller(201, 460.0f, "1787654321", "Ana Gomez", "0987654321", "ana@jequality.com", "ana_v", "pass");
        Payment payment = new Payment(50.0f, 50.0f);

        assertThrows(IllegalArgumentException.class, () -> {
            salesManagement.registerSale(customer, seller, new ArrayList<>(), new ArrayList<>(), 101, payment, "FISICA");
        }, "No se debe permitir registrar una venta con el carrito de compras vacio");
    }

    @Test
    public void testRegisterSaleWithNullCustomerThrowsException() {
        Seller seller = new Seller(201, 460.0f, "1787654321", "Ana Gomez", "0987654321", "ana@jequality.com", "ana_v", "pass");
        Payment payment = new Payment(50.0f, 50.0f);

        assertThrows(NullPointerException.class, () -> {
            salesManagement.registerSale(null, seller, new ArrayList<>(), new ArrayList<>(), 101, payment, "FISICA");
        }, "Registrar una venta sin cliente (null) debe lanzar NullPointerException");
    }

    @Test
    public void testAuthenticateUserWithEmptyCredentialsThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            salesManagement.authenticateUser("", "   ");
        }, "No se debe procesar la autenticacion si las credenciales estan vacias");
    }

    @Test
    public void testProcessCashPaymentWithNegativeAmountThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            salesManagement.processCashPayment(-20.0f, 45.0f);
        }, "El monto recibido en efectivo no puede ser negativo");
    }

    @Test
    public void testRemoveNullCustomerAccountThrowsException() {
        assertThrows(NullPointerException.class, () -> {
            salesManagement.removeCustomerAccount(null);
        }, "Intentar eliminar una cuenta de cliente nula debe lanzar NullPointerException");
    }

    @Test
    public void testFindStaffByNegativeCodeThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            salesManagement.findStaffByCode(-5);
        }, "La busqueda de personal no debe aceptar codigos de empleado negativos");
    }
}