package ec.edu.espe.JEQualityFootwear.model;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InvoiceTest {
    
    private Invoice invoice;
    private Customer customer;
    private Seller seller;
    private List<Product> products;
    private List<Integer> quantities;
    private Date date;

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
        customer = new Customer(1, "1711223344", "Juan Perez", "0991112223", "juan@mail.com", "jperez", "pass123");

        seller = new Seller(501, 1200.0f, "1722334455", "Maria Gomez", "0988877665", "maria@mail.com", "mgomez", "pass123");

        products = new ArrayList<>();
        Product product = new Product("P001", "Sneakers", 50.0f, 10, null, "Black", 42);
        products.add(product);

        quantities = new ArrayList<>();
        quantities.add(2);

        date = new Date();
        invoice = new Invoice("INV-001", date, customer, seller, products, quantities, "Local");
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testCalculateTotal() {
        float result = invoice.calculateTotal();
        assertEquals(115.0f, result, 0.01f);
    }

    @Test
    public void testGetDetails() {
        String details = invoice.getDetails();
        assertNotNull(details);
        assertTrue(details.contains("INV-001"));
        assertTrue(details.contains("Juan Perez"));
    }

    @Test
    public void testGetAndSetInvoiceNumber() {
        invoice.setInvoiceNumber("INV-999");
        assertEquals("INV-999", invoice.getInvoiceNumber());
    }

    @Test
    public void testGetAndSetDate() {
        Date newDate = new Date(0);
        invoice.setDate(newDate);
        assertEquals(newDate, invoice.getDate());
    }

    @Test
    public void testGetAndSetCustomer() {
        Customer newCustomer = new Customer(2, "1733445566", "Ana Lopez", "0977766554", "ana@mail.com", "alopez", "pass123");
        invoice.setCustomer(newCustomer);
        assertEquals(newCustomer, invoice.getCustomer());
    }

    @Test
    public void testGetAndSetSeller() {
        Seller newSeller = new Seller(502, 1300.0f, "1744556677", "Carlos Ruiz", "0966655443", "carlos@mail.com", "cruiz", "pass123");
        invoice.setSeller(newSeller);
        assertEquals(newSeller, invoice.getSeller());
    }

    @Test
    public void testGetAndSetPurchasedItems() {
        List<Product> newProducts = new ArrayList<>();
        invoice.setPurchasedItems(newProducts);
        assertEquals(newProducts, invoice.getPurchasedItems());
    }

    @Test
    public void testGetAndSetQuantities() {
        List<Integer> newQuantities = new ArrayList<>();
        newQuantities.add(5);
        invoice.setQuantities(newQuantities);
        assertEquals(newQuantities, invoice.getQuantities());
    }

    @Test
    public void testGetSubtotalAndVatAndTotalAmount() {
        assertEquals(100.0f, invoice.getSubtotal(), 0.01f);
        assertEquals(15.0f, invoice.getVatAmount(), 0.01f);
        assertEquals(115.0f, invoice.getTotalAmount(), 0.01f);
    }

    @Test
    public void testCashTenderedAndChangeDue() {
        invoice.setCashTendered(150.0f);
        invoice.setChangeDue(35.0f);
        assertEquals(150.0f, invoice.getCashTendered(), 0.01f);
        assertEquals(35.0f, invoice.getChangeDue(), 0.01f);
        
        invoice.setIssuanceChannel("Online");
        assertEquals("Online", invoice.getIssuanceChannel());

        String toStringResult = invoice.toString();
        assertNotNull(toStringResult);
        assertTrue(toStringResult.contains("INV-001"));
    }
}