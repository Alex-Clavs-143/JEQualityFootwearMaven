
package ec.edu.espe.JEQualityFootwear.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    private Customer customer;

    public CustomerTest() {
    }

    @BeforeEach
    public void setUp() {
        customer = new Customer(1001, "1712345678", "Cliente Prueba",
                "0991234567", "cliente@jequality.com",
                "cliente_test", "123456");
    }

    private Invoice createTestInvoice() {
        Seller seller = new Seller(201, 460.0f, "1723456789",
                "Vendedor Prueba", "0987654321",
                "vendedor@jequality.com", "seller_test", "123456");

        return new Invoice("001-001-000001", new Date(),
                customer, seller, new ArrayList<>(),
                new ArrayList<>(), "FISICA");
    }

    @Test
    public void testGetInvoice() {
        Invoice invoice = createTestInvoice();

        customer.getInvoice(invoice);
        customer.getInvoice(null);

        assertEquals(1, customer.getPurchaseHistory().size(),
                "Debe registrar la factura valida e ignorar facturas nulas");

        assertSame(invoice, customer.getPurchaseHistory().get(0),
                "La factura registrada debe coincidir con la recibida");
    }

    @Test
    public void testGetCustomerCode() {
        int expResult = 1001;
        int result = customer.getCustomerCode();

        assertEquals(expResult, result,
                "Debe devolver correctamente el codigo del cliente");
    }

    @Test
    public void testSetCustomerCode() {
        customer.setCustomerCode(2002);

        assertEquals(2002, customer.getCustomerCode(),
                "Debe actualizar correctamente el codigo del cliente");
    }

    @Test
    public void testGetPurchaseHistory() {
        List<Invoice> result = customer.getPurchaseHistory();

        assertNotNull(result,
                "El historial de compras no debe ser nulo");

        assertTrue(result.isEmpty(),
                "Un cliente nuevo debe tener su historial de compras vacio");
    }

    @Test
    public void testSetPurchaseHistory() {
        List<Invoice> purchaseHistory = new ArrayList<>();
        Invoice invoice = createTestInvoice();
        purchaseHistory.add(invoice);

        customer.setPurchaseHistory(purchaseHistory);

        assertEquals(1, customer.getPurchaseHistory().size(),
                "Debe actualizar correctamente el historial de compras");

        assertSame(invoice, customer.getPurchaseHistory().get(0),
                "Debe conservar la factura asignada al historial");
    }

    @Test
    public void testToString() {
        String expResult = "Customer{customerCode=1001, "
                + "ID/RUC: 1712345678 | Nombre: Cliente Prueba "
                + "| Tel: 0991234567 | Email: cliente@jequality.com, "
                + "comprasRealizadas=0}";

        String result = customer.toString();

        assertEquals(expResult, result,
                "Debe mostrar correctamente los datos del cliente");
    }

    @Test
    public void testSetNegativeCustomerCodeThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            customer.setCustomerCode(-100);
        }, "No se deben permitir codigos de cliente negativos");
    }

    @Test
    public void testCreateCustomerWithNegativeCodeThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer(-1, "1712345678", "Cliente Invalido",
                    "0991234567", "test@jequality.com",
                    "cliente_invalido", "123456");
        }, "No se debe crear un cliente con un codigo negativo");
    }

    @Test
    public void testSetNullPurchaseHistoryThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            customer.setPurchaseHistory(null);
        }, "El historial de compras no debe aceptar valores nulos");
    }

    @Test
    public void testAddDuplicateInvoiceDoesNotDuplicateHistory() {
        Invoice invoice = createTestInvoice();

        customer.getInvoice(invoice);
        customer.getInvoice(invoice);

        assertEquals(1, customer.getPurchaseHistory().size(),
                "Una misma factura no debe registrarse dos veces en el historial");
    }
}
