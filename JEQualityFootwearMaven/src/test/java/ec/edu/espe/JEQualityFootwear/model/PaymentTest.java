package ec.edu.espe.JEQualityFootwear.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {
    
    private Payment payment;

    public PaymentTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        payment = new Payment(120.0f, 100.0f);
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testCalculateChange() {
        float change = payment.calculateChange(150.0f);
        assertEquals(50.0f, change, 0.01f);
    }

    @Test
    public void testProcessPaymentSuccess() {
        assertTrue(payment.processPayment());
    }

    @Test
    public void testProcessPaymentInsufficientFunds() {
        Payment lowPayment = new Payment(80.0f, 100.0f);
        assertFalse(lowPayment.processPayment());
    }

    @Test
    public void testGetAndSetAmountReceived() {
        payment.setAmountReceived(200.0f);
        assertEquals(200.0f, payment.getAmountReceived(), 0.01f);
    }

    @Test
    public void testGetAndSetChangeGiven() {
        payment.setChangeGiven(25.0f);
        assertEquals(25.0f, payment.getChangeGiven(), 0.01f);
    }

    @Test
    public void testGetAndSetTotalAmount() {
        payment.setTotalAmount(250.0f);
        assertEquals(250.0f, payment.getTotalAmount(), 0.01f);
    }

    @Test
    public void testCalculateChangeExactAmount() {
        float change = payment.calculateChange(100.0f);
        assertEquals(0.0f, change, 0.01f);
    }

    @Test
    public void testCalculateChangeInsufficient() {
        float change = payment.calculateChange(50.0f);
        assertEquals(-1.0f, change, 0.01f);
    }

    @Test
    public void testSetTotalAmountBoundary() {
        payment.setTotalAmount(0.0f);
        assertEquals(0.0f, payment.getTotalAmount(), 0.01f);
    }

    @Test
    public void testToString() {
        String result = payment.toString();
        assertNotNull(result);
        assertTrue(result.contains("100.0"));
    }
}
