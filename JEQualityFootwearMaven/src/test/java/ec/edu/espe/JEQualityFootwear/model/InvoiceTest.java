
package ec.edu.espe.JEQualityFootwear.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InvoiceTest {

    private Invoice invoice;
    private Customer customer;
    private Seller seller;
    private Product product1;
    private Product product2;
    private Date date;

    public InvoiceTest() {
    }

    @BeforeEach
    public void setUp() {
        customer = new Customer(1001, "1712345678", "Juan Perez",
                "0991234567", "juan@gmail.com", "juan123", "1234");

        seller = new Seller(201, 460.0f, "1723456789",
                "Carlos Lopez", "0987654321", "carlos@gmail.com",
                "seller01", "1234");

        Category category = new Category("Deportivo", "Hombres");

        product1 = new Product("ZAP-001", "Zapato Deportivo",
                50.0f, 10, category, "Negro", 42);

        product2 = new Product("ZAP-002", "Zapato Casual",
                80.0f, 5, category, "Blanco", 40);

        date = new Date(1700000000000L);

        invoice = new Invoice("001-001-000001", date, customer,
                seller, Arrays.asList(product1, product2),
                Arrays.asList(2, 1), "FISICA");

        invoice.setCashTendered(250.0f);
        invoice.setChangeDue(43.0f);
    }

    @Test
    public void testCalculateTotal() {
        float result = invoice.calculateTotal();

        assertEquals(207.0f, result, 0.01f,
                "El total debe incluir el subtotal mas el IVA del 15%");

        assertEquals(180.0f, invoice.getSubtotal(), 0.01f,
                "El subtotal debe calcularse correctamente");

        assertEquals(27.0f, invoice.getVatAmount(), 0.01f,
                "El IVA debe representar el 15% del subtotal");
    }

    @Test
    public void testGetDetails() {
        String result = invoice.getDetails();

        assertNotNull(result,
                "Los detalles de la factura no deben ser null");

        assertTrue(result.contains("001-001-000001"),
                "La factura debe mostrar su numero");

        assertTrue(result.contains("Juan Perez"),
                "La factura debe incluir el nombre del cliente");

        assertTrue(result.contains("Carlos Lopez"),
                "La factura debe incluir el vendedor");

        assertTrue(result.contains("ZAP-001"),
                "La factura debe mostrar los productos comprados");

        assertTrue(result.contains("TOTAL A PAGAR"),
                "La factura debe incluir el total a pagar");
    }

    @Test
    public void testGetInvoiceNumber() {
        String expResult = "001-001-000001";
        String result = invoice.getInvoiceNumber();

        assertEquals(expResult, result,
                "El numero de factura debe coincidir con el registrado");
    }

    @Test
    public void testSetInvoiceNumber() {
        invoice.setInvoiceNumber("001-001-000002");

        assertEquals("001-001-000002", invoice.getInvoiceNumber(),
                "El sistema debe permitir actualizar el numero de factura");

        assertThrows(IllegalArgumentException.class, () -> {
            invoice.setInvoiceNumber("   ");
        }, "El numero de factura no puede estar vacio");
    }

    @Test
    public void testGetDate() {
        Date result = invoice.getDate();

        assertEquals(date, result,
                "La fecha debe coincidir con la fecha de emision registrada");
    }

    @Test
    public void testSetDate() {
        Date newDate = new Date(1700100000000L);

        invoice.setDate(newDate);

        assertEquals(newDate, invoice.getDate(),
                "El sistema debe actualizar correctamente la fecha");

        assertThrows(IllegalArgumentException.class, () -> {
            invoice.setDate(null);
        }, "La factura no debe permitir una fecha de emision null");
    }

    @Test
    public void testGetCustomer() {
        Customer result = invoice.getCustomer();

        assertSame(customer, result,
                "La factura debe estar asociada al cliente correcto");
    }

    @Test
    public void testSetCustomer() {
        Customer newCustomer = new Customer(1002, "1700000000",
                "Maria Lopez", "0998887776", "maria@gmail.com",
                "maria123", "1234");

        invoice.setCustomer(newCustomer);

        assertSame(newCustomer, invoice.getCustomer(),
                "El sistema debe actualizar el cliente de la factura");

        assertThrows(IllegalArgumentException.class, () -> {
            invoice.setCustomer(null);
        }, "Una factura no debe quedar sin cliente");
    }

    @Test
    public void testGetSeller() {
        Seller result = invoice.getSeller();

        assertSame(seller, result,
                "La factura debe estar asociada al vendedor correcto");

        assertEquals(201, result.getEmployeeCode(),
                "El codigo del vendedor debe ser correcto");
    }

    @Test
    public void testSetSeller() {
        Seller newSeller = new Seller(202, 500.0f,
                "1700000001", "Pedro Gomez", "0997776665",
                "pedro@gmail.com", "seller02", "1234");

        invoice.setSeller(newSeller);

        assertSame(newSeller, invoice.getSeller(),
                "El sistema debe permitir actualizar el vendedor");

        assertThrows(IllegalArgumentException.class, () -> {
            invoice.setSeller(null);
        }, "Una factura no debe quedar sin vendedor");
    }

    @Test
    public void testGetPurchasedItems() {
        List<Product> result = invoice.getPurchasedItems();

        assertNotNull(result,
                "La lista de productos comprados no debe ser null");

        assertEquals(2, result.size(),
                "La factura debe contener dos productos");

        assertTrue(result.contains(product1),
                "Debe incluir el primer producto");

        assertTrue(result.contains(product2),
                "Debe incluir el segundo producto");
    }

    @Test
    public void testSetPurchasedItems() {
        List<Product> newItems = Arrays.asList(product1);

        invoice.setPurchasedItems(newItems);

        assertEquals(1, invoice.getPurchasedItems().size(),
                "La factura debe almacenar la nueva lista de productos");

        assertSame(product1, invoice.getPurchasedItems().get(0),
                "El producto asignado debe coincidir con el original");

        assertThrows(IllegalArgumentException.class, () -> {
            invoice.setPurchasedItems(null);
        }, "La lista de productos no debe aceptar null");
    }

    @Test
    public void testGetQuantities() {
        List<Integer> result = invoice.getQuantities();

        assertNotNull(result,
                "Las cantidades de productos no deben ser null");

        assertEquals(2, result.size(),
                "Debe existir una cantidad por cada producto");

        assertEquals(Arrays.asList(2, 1), result,
                "Las cantidades deben coincidir con las registradas");
    }

    @Test
    public void testSetQuantities() {
        List<Integer> newQuantities = Arrays.asList(3, 2);

        invoice.setQuantities(newQuantities);

        assertEquals(newQuantities, invoice.getQuantities(),
                "El sistema debe actualizar las cantidades compradas");

        assertThrows(IllegalArgumentException.class, () -> {
            invoice.setQuantities(Arrays.asList(2, -1));
        }, "No se deben permitir cantidades negativas en una factura");
    }

    @Test
    public void testGetSubtotal() {
        float expResult = 180.0f;
        float result = invoice.getSubtotal();

        assertEquals(expResult, result, 0.01f,
                "El subtotal debe ser la suma de precios por cantidades");
    }

    @Test
    public void testGetVatAmount() {
        float expResult = 27.0f;
        float result = invoice.getVatAmount();

        assertEquals(expResult, result, 0.01f,
                "El IVA debe calcularse aplicando el porcentaje del 15%");
    }

    @Test
    public void testGetTotalAmount() {
        float expResult = 207.0f;
        float result = invoice.getTotalAmount();

        assertEquals(expResult, result, 0.01f,
                "El total de la factura debe incluir el IVA");

        assertTrue(result > invoice.getSubtotal(),
                "El total con impuestos debe superar al subtotal");
    }

    @Test
    public void testGetCashTendered() {
        float expResult = 250.0f;
        float result = invoice.getCashTendered();

        assertEquals(expResult, result, 0.01f,
                "El efectivo recibido debe coincidir con el registrado");
    }

    @Test
    public void testSetCashTendered() {
        invoice.setCashTendered(300.0f);

        assertEquals(300.0f, invoice.getCashTendered(), 0.01f,
                "El efectivo recibido debe actualizarse correctamente");

        assertThrows(IllegalArgumentException.class, () -> {
            invoice.setCashTendered(-100.0f);
        }, "El sistema debe rechazar montos de efectivo negativos");
    }

    @Test
    public void testGetChangeDue() {
        float expResult = 43.0f;
        float result = invoice.getChangeDue();

        assertEquals(expResult, result, 0.01f,
                "El vuelto debe coincidir con la diferencia entre pago y total");
    }

    @Test
    public void testSetChangeDue() {
        invoice.setChangeDue(50.0f);

        assertEquals(50.0f, invoice.getChangeDue(), 0.01f,
                "El sistema debe actualizar el vuelto correctamente");

        assertThrows(IllegalArgumentException.class, () -> {
            invoice.setChangeDue(-20.0f);
        }, "El vuelto entregado no debe ser negativo");
    }

    @Test
    public void testGetIssuanceChannel() {
        String expResult = "FISICA";
        String result = invoice.getIssuanceChannel();

        assertEquals(expResult, result,
                "El canal de entrega debe coincidir con el registrado");
    }

    @Test
    public void testSetIssuanceChannel() {
        invoice.setIssuanceChannel("ELECTRONICA (PDF VIA EMAIL)");

        assertEquals("ELECTRONICA (PDF VIA EMAIL)",
                invoice.getIssuanceChannel(),
                "El sistema debe actualizar el canal de entrega");

        assertThrows(IllegalArgumentException.class, () -> {
            invoice.setIssuanceChannel("DESCONOCIDO");
        }, "El sistema debe rechazar canales de entrega invalidos");
    }

    @Test
    public void testToString() {
        String result = invoice.toString();

        assertNotNull(result,
                "La descripcion de la factura no debe ser null");

        assertTrue(result.contains("001-001-000001"),
                "La descripcion debe incluir el numero de factura");

        assertTrue(result.contains("Juan Perez"),
                "La descripcion debe mostrar el cliente");

        assertTrue(result.contains("Carlos Lopez"),
                "La descripcion debe mostrar el vendedor");

        assertTrue(result.contains("207.0"),
                "La descripcion debe incluir el total de la factura");

        assertTrue(result.contains("FISICA"),
                "La descripcion debe mostrar el canal de entrega");
    }
}
