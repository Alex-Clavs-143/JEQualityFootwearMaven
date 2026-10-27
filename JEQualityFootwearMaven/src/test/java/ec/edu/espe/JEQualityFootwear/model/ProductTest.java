package ec.edu.espe.JEQualityFootwear.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {
    
    private Product product;

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
        product = new Product("P001", "Formal Shoes", 75.50f, 20, null, "Brown", 41);
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testUpdateStockPositive() {
        product.updateStock(5);
        assertEquals(25, product.getCurrentStock());
    }

    @Test
    public void testUpdateStockNegativeSales() {
        product.updateStock(-5);
        assertEquals(15, product.getCurrentStock());
        assertEquals(5, product.getUnitsSold());
    }

    @Test
    public void testUpdateStockExceedsLimitFails() {
        product.updateStock(-30);
        assertEquals(20, product.getCurrentStock());
    }

    @Test
    public void testAssignCategory() {
        Category category = new Category("Formal", "Men");
        product.assignCategory(category);
        assertEquals(category, product.getCategory());
    }

    @Test
    public void testGetAndSetPrice() {
        product.setPrice(85.0f);
        assertEquals(85.0f, product.getPrice(), 0.01f);
    }

    @Test
    public void testSetNegativePriceIgnored() {
        product.setPrice(-10.0f);
        assertEquals(75.50f, product.getPrice(), 0.01f);
    }

    @Test
    public void testGetAndSetProductId() {
        product.setProductId("P999");
        assertEquals("P999", product.getProductId());
    }

    @Test
    public void testGetAndSetName() {
        product.setName("Sneakers");
        assertEquals("Sneakers", product.getName());
    }

    @Test
    public void testGetAndSetColorAndSize() {
        product.setColor("White");
        product.setSize(43);
        assertEquals("White", product.getColor());
        assertEquals(43, product.getSize());
    }

    @Test
    public void testToString() {
        String result = product.toString();
        assertNotNull(result);
        assertTrue(result.contains("P001"));
        assertTrue(result.contains("Formal Shoes"));
    }
}