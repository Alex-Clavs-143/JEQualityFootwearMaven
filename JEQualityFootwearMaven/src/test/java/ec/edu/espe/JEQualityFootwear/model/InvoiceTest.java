package ec.edu.espe.JEQualityFootwear.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InvoiceTest {

    private Invoice invoiceInstance;
    private Customer testCustomer;
    private Seller testSeller;
    private List<Product> testProducts;
    private List<Integer> testQuantities;

    public InvoiceTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        testCustomer = new Customer(1001, "1712345678", "Juan Perez", "0991234567", "juan@gmail.com", "jperez", "pass123");
        testSeller = new Seller(201, 450.00f, "1798765432", "Carlos Vendedor", "0987654321", "carlos@tienda.com", "cvendedor", "seller123");

        Category category = new Category("Formal", "Hombres");
        Product product1 = new Product("ZAP-001", "Zapato Cuero", 50.00f, 10, category, "Negro", 40);
        Product product2 = new Product("ZAP-002", "Mocasines", 30.00f, 15, category, "Cafe", 41);

        testProducts = new ArrayList<>();
        testProducts.add(product1);
        testProducts.add(product2);

        testQuantities = new ArrayList<>();
        testQuantities.add(2);
        testQuantities.add(1);

        invoiceInstance = new Invoice("001-001-000001", new Date(), testCustomer, testSeller, testProducts, testQuantities, "FISICA (IMPRESA EN CAJA)");
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testCalculateTotal() {
        System.out.println("calculateTotal");
        float expectedTotal = 149.50f;
        float result = invoiceInstance.calculateTotal();
        assertEquals(expectedTotal, result, 0.001f);
        assertEquals(130.00f, invoiceInstance.getSubtotal(), 0.001f);
        assertEquals(19.50f, invoiceInstance.getVatAmount(), 0.001f);
    }

    @Test
    public void testGetDetails() {
        System.out.println("getDetails");
        invoiceInstance.setCashTendered(150.00f);
        invoiceInstance.setChangeDue(0.50f);
        String details = invoiceInstance.getDetails();
        assertNotNull(details);
        assertTrue(details.contains("001-001-000001"));
        assertTrue(details.contains("Juan Perez"));
        assertTrue(details.contains("Carlos Vendedor"));
        assertTrue(details.contains("149.5"));
    }

    @Test
    public void testGetAndSetInvoiceNumber() {
        System.out.println("getInvoiceNumber & setInvoiceNumber");
        assertEquals("001-001-000001", invoiceInstance.getInvoiceNumber());

        String newNumber = "001-001-000002";
        invoiceInstance.setInvoiceNumber(newNumber);
        assertEquals(newNumber, invoiceInstance.getInvoiceNumber());
    }

    @Test
    public void testGetAndSetDate() {
        System.out.println("getDate & setDate");
        assertNotNull(invoiceInstance.getDate());

        Date newDate = new Date();
        invoiceInstance.setDate(newDate);
        assertEquals(newDate, invoiceInstance.getDate());
    }

    @Test
    public void testGetAndSetCustomer() {
        System.out.println("getCustomer & setCustomer");
        assertEquals(testCustomer, invoiceInstance.getCustomer());

        Customer newCustomer = new Customer(1002, "0987654321", "Maria Lopez", "0998887776", "maria@gmail.com", "mlopez", "mypass");
        invoiceInstance.setCustomer(newCustomer);
        assertEquals(newCustomer, invoiceInstance.getCustomer());
    }

    @Test
    public void testGetAndSetSeller() {
        System.out.println("getSeller & setSeller");
        assertEquals(testSeller, invoiceInstance.getSeller());

        Seller newSeller = new Seller(202, 500.00f, "1711112223", "Ana Vendedora", "0977776665", "ana@tienda.com", "avendedor", "ana123");
        invoiceInstance.setSeller(newSeller);
        assertEquals(newSeller, invoiceInstance.getSeller());
    }

    @Test
    public void testGetAndSetPurchasedItemsAndQuantities() {
        System.out.println("getPurchasedItems, getQuantities & Setters");
        assertEquals(2, invoiceInstance.getPurchasedItems().size());
        assertEquals(2, invoiceInstance.getQuantities().size());

        List<Product> newProducts = new ArrayList<>();
        List<Integer> newQuantities = new ArrayList<>();
        invoiceInstance.setPurchasedItems(newProducts);
        invoiceInstance.setQuantities(newQuantities);

        assertEquals(0, invoiceInstance.getPurchasedItems().size());
        assertEquals(0, invoiceInstance.getQuantities().size());
    }

    @Test
    public void testGetAndSetCashTenderedAndChangeDue() {
        System.out.println("getCashTendered, getChangeDue & Setters");
        invoiceInstance.setCashTendered(150.00f);
        invoiceInstance.setChangeDue(0.50f);

        assertEquals(150.00f, invoiceInstance.getCashTendered(), 0.001f);
        assertEquals(0.50f, invoiceInstance.getChangeDue(), 0.001f);
    }

    @Test
    public void testGetAndSetIssuanceChannel() {
        System.out.println("getIssuanceChannel & setIssuanceChannel");
        assertEquals("FISICA (IMPRESA EN CAJA)", invoiceInstance.getIssuanceChannel());

        String newChannel = "ELECTRONICA (PDF VIA EMAIL)";
        invoiceInstance.setIssuanceChannel(newChannel);
        assertEquals(newChannel, invoiceInstance.getIssuanceChannel());
    }

    @Test
    public void testToString() {
        System.out.println("toString");
        String result = invoiceInstance.toString();
        assertNotNull(result);
        assertTrue(result.contains("001-001-000001"));
        assertTrue(result.contains("Juan Perez"));
        assertTrue(result.contains("Carlos Vendedor"));
    }
}