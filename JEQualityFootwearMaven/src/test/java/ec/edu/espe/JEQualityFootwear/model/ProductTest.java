package ec.edu.espe.JEQualityFootwear.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    private Product testProduct;
    private Category testCategory;

    public ProductTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        testCategory = new Category("Formal", "Hombres");
        testProduct = new Product("ZAP-001", "Zapato Cuero Classic", 45.00f, 20, testCategory, "Negro", 40);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testUpdateStock() {
        System.out.println("updateStock");
        testProduct.updateStock(-5);
        assertEquals(15, testProduct.getCurrentStock());
        assertEquals(5, testProduct.getUnitsSold());

        testProduct.updateStock(10);
        assertEquals(25, testProduct.getCurrentStock());
    }

    @Test
    public void testAssignCategory() {
        System.out.println("assignCategory");
        Category newCategory = new Category("Deportivo", "Mujeres");
        testProduct.assignCategory(newCategory);
        assertEquals(newCategory, testProduct.getCategory());
        assertEquals("Mujeres - Deportivo", testProduct.getCategory().toString());
    }

    @Test
    public void testGetAndSetPrice() {
        System.out.println("getPrice & setPrice");
        assertEquals(45.00f, testProduct.getPrice(), 0.001f);
        
        float newPrice = 52.50f;
        testProduct.setPrice(newPrice);
        assertEquals(newPrice, testProduct.getPrice(), 0.001f);
    }

    @Test
    public void testGetAndSetProductId() {
        System.out.println("getProductId & setProductId");
        assertEquals("ZAP-001", testProduct.getProductId());

        String newCode = "ZAP-999";
        testProduct.setProductId(newCode);
        assertEquals(newCode, testProduct.getProductId());
    }

    @Test
    public void testGetAndSetName() {
        System.out.println("getName & setName");
        assertEquals("Zapato Cuero Classic", testProduct.getName());

        String newName = "Zapato Formal Ejecutivo";
        testProduct.setName(newName);
        assertEquals(newName, testProduct.getName());
    }

    @Test
    public void testGetAndSetCurrentStock() {
        System.out.println("getCurrentStock & setCurrentStock");
        assertEquals(20, testProduct.getCurrentStock());

        testProduct.setCurrentStock(50);
        assertEquals(50, testProduct.getCurrentStock());
    }

    @Test
    public void testGetAndSetColor() {
        System.out.println("getColor & setColor");
        assertEquals("Negro", testProduct.getColor());

        testProduct.setColor("Cafe");
        assertEquals("Cafe", testProduct.getColor());
    }

    @Test
    public void testGetAndSetSize() {
        System.out.println("getSize & setSize");
        assertEquals(40, testProduct.getSize());

        testProduct.setSize(42);
        assertEquals(42, testProduct.getSize());
    }

    @Test
    public void testGetAndSetUnitsSold() {
        System.out.println("getUnitsSold & setUnitsSold");
        assertEquals(0, testProduct.getUnitsSold());

        testProduct.setUnitsSold(12);
        assertEquals(12, testProduct.getUnitsSold());
    }

    @Test
    public void testToString() {
        System.out.println("toString");
        String result = testProduct.toString();
        assertNotNull(result);
        assertTrue(result.contains("ZAP-001"));
        assertTrue(result.contains("Zapato Cuero Classic"));
    }
}