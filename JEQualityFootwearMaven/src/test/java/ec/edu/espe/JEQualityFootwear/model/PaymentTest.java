package ec.edu.espe.JEQualityFootwear.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {

    private Payment paymentInstance;

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
        paymentInstance = new Payment(50.00f, 42.50f);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testCalculateChange() {
        System.out.println("calculateChange");
        float amountReceived = 50.00f;
        float expResult = 7.50f;
        float result = paymentInstance.calculateChange(amountReceived);
        assertEquals(expResult, result, 0.001f);

        float insufficientAmount = 30.00f;
        float resultInsufficient = paymentInstance.calculateChange(insufficientAmount);
        assertEquals(-1.0f, resultInsufficient, 0.001f);
    }

    @Test
    public void testProcessPayment() {
        System.out.println("processPayment");
        boolean result = paymentInstance.processPayment();
        assertTrue(result);
        assertEquals(7.50f, paymentInstance.getChangeGiven(), 0.001f);

        Payment failedPayment = new Payment(20.00f, 42.50f);
        boolean failedResult = failedPayment.processPayment();
        assertFalse(failedResult);
    }

    @Test
    public void testGetAmountReceived() {
        System.out.println("getAmountReceived");
        float expResult = 50.00f;
        float result = paymentInstance.getAmountReceived();
        assertEquals(expResult, result, 0.001f);
    }

    @Test
    public void testSetAmountReceived() {
        System.out.println("setAmountReceived");
        float amountReceived = 100.00f;
        paymentInstance.setAmountReceived(amountReceived);
        assertEquals(amountReceived, paymentInstance.getAmountReceived(), 0.001f);
    }

    @Test
    public void testGetChangeGiven() {
        System.out.println("getChangeGiven");
        paymentInstance.processPayment();
        float expResult = 7.50f;
        float result = paymentInstance.getChangeGiven();
        assertEquals(expResult, result, 0.001f);
    }

    @Test
    public void testSetChangeGiven() {
        System.out.println("setChangeGiven");
        float changeGiven = 10.00f;
        paymentInstance.setChangeGiven(changeGiven);
        assertEquals(changeGiven, paymentInstance.getChangeGiven(), 0.001f);
    }

    @Test
    public void testGetTotalAmount() {
        System.out.println("getTotalAmount");
        float expResult = 42.50f;
        float result = paymentInstance.getTotalAmount();
        assertEquals(expResult, result, 0.001f);
    }

    @Test
    public void testSetTotalAmount() {
        System.out.println("setTotalAmount");
        float totalAmount = 60.00f;
        paymentInstance.setTotalAmount(totalAmount);
        assertEquals(totalAmount, paymentInstance.getTotalAmount(), 0.001f);
    }

    @Test
    public void testToString() {
        System.out.println("toString");
        paymentInstance.processPayment();
        String result = paymentInstance.toString();
        assertNotNull(result);
        assertTrue(result.contains("42.5"));
        assertTrue(result.contains("50.0"));
        assertTrue(result.contains("7.5"));
    }

    @Test
    public void testExactAmountPayment() {
        System.out.println("exactAmountPayment");
        Payment exactPayment = new Payment(42.50f, 42.50f);
        boolean processResult = exactPayment.processPayment();
        assertTrue(processResult);
        assertEquals(0.00f, exactPayment.getChangeGiven(), 0.001f);
    }
}