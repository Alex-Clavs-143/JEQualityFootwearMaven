
package ec.edu.espe.JEQualityFootwear.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    private Customer customer;
    private Invoice invoice;

    public CustomerTest() {
    }

    @BeforeEach
    public void setUp() {
        customer = new Customer(1001, "1712345678", "Juan Perez",
                "0991234567", "juan@gmail.com", "juan123", "1234");

        Seller seller = new Seller(201, 460.0f, "1723456789",
                "Carlos Lopez", "0987654321", "carlos@gmail.com",
                "seller01", "1234");

        Category category = new Category("Deportivo", "Hombres");

        Product product = new Product("ZAP-001", "Zapato Deportivo",
                75.50f, 10, category, "Negro", 42);

        invoice = new Invoice("001-001-000001", new Date(),
                customer, seller, Arrays.asList(product),
                Arrays.asList(1), "FISICA");
    }

    @Test
    public void testGetInvoice() {
        customer.getInvoice(invoice);

        assertEquals(1, customer.getPurchaseHistory().size(),
                "La factura debe agregarse al historial del cliente");

        assertSame(invoice, customer.getPurchaseHistory().get(0),
                "La factura registrada debe coincidir con la recibida");
    }

    @Test
    public void testGetCustomerCode() {
        int expResult = 1001;
        int result = customer.getCustomerCode();

        assertEquals(expResult, result,
                "El codigo del cliente debe coincidir con el registrado");
    }

    @Test
    public void testSetCustomerCode() {
        customer.setCustomerCode(2005);

        assertEquals(2005, customer.getCustomerCode(),
                "El sistema debe permitir actualizar el codigo del cliente");
    }

    @Test
    public void testGetPurchaseHistory() {
        List<Invoice> result = customer.getPurchaseHistory();

        assertNotNull(result,
                "El historial de compras no debe ser null");

        assertTrue(result.isEmpty(),
                "Un cliente nuevo debe tener su historial de compras vacio");
    }

    @Test
    public void testSetPurchaseHistory() {
        List<Invoice> purchaseHistory = new ArrayList<>();
        purchaseHistory.add(invoice);

        customer.setPurchaseHistory(purchaseHistory);

        assertEquals(1, customer.getPurchaseHistory().size(),
                "El historial debe contener la factura asignada");

        assertSame(invoice, customer.getPurchaseHistory().get(0),
                "La factura almacenada debe coincidir con la original");
    }

    @Test
    public void testToString() {
        String result = customer.toString();

        assertNotNull(result,
                "La descripcion del cliente no debe ser null");

        assertTrue(result.contains("Juan Perez"),
                "La descripcion debe contener el nombre del cliente");

        assertTrue(result.contains("1001"),
                "La descripcion debe incluir el codigo del cliente");

        assertTrue(result.contains("comprasRealizadas=0"),
                "Un cliente nuevo debe mostrar cero compras realizadas");
    }

    @Test
    public void testGetInvoiceWithNull() {
        customer.getInvoice(null);

        assertEquals(0, customer.getPurchaseHistory().size(),
                "El sistema no debe registrar facturas nulas");
    }

    @Test
    public void testSetNegativeCustomerCodeThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            customer.setCustomerCode(-100);
        }, "El sistema debe rechazar codigos de cliente negativos");
    }

    @Test
    public void testSetPurchaseHistoryNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            customer.setPurchaseHistory(null);
        }, "El historial de compras no debe aceptar valores null");
    }

    @Test
    public void testGetInvoiceDuplicateNotAdded() {
        customer.getInvoice(invoice);
        customer.getInvoice(invoice);

        assertEquals(1, customer.getPurchaseHistory().size(),
                "Una misma factura no debe registrarse dos veces en el historial");
    }
}
