
package ec.edu.espe.JEQualityFootwear.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {

    private Payment payment;

    public PaymentTest() {
    }

    @BeforeEach
    public void setUp() {
        payment = new Payment(120.0f, 100.0f);
    }

    @Test
    public void testCalculateChange() {
        float expResult = 20.0f;
        float result = payment.calculateChange(120.0f);

        assertEquals(expResult, result, 0.001f,
                "El vuelto debe ser la diferencia entre el efectivo recibido y el total");

        assertEquals(0.0f, payment.calculateChange(100.0f), 0.001f,
                "Si el cliente paga el valor exacto, el vuelto debe ser cero");

        assertEquals(-1.0f, payment.calculateChange(80.0f), 0.001f,
                "El sistema debe indicar cuando el efectivo es insuficiente");
    }

    @Test
    public void testProcessPayment() {
        boolean result = payment.processPayment();

        assertTrue(result,
                "El pago debe procesarse cuando el efectivo cubre el total");

        assertEquals(20.0f, payment.getChangeGiven(), 0.001f,
                "El vuelto debe calcularse correctamente");

        Payment insufficientPayment = new Payment(50.0f, 100.0f);

        assertFalse(insufficientPayment.processPayment(),
                "El sistema debe rechazar pagos insuficientes");

        Payment zeroTotalPayment = new Payment(50.0f, 0.0f);

        assertFalse(zeroTotalPayment.processPayment(),
                "No se deben procesar pagos con un total igual a cero");
    }

    @Test
    public void testGetAmountReceived() {
        float expResult = 120.0f;
        float result = payment.getAmountReceived();

        assertEquals(expResult, result, 0.001f,
                "El efectivo recibido debe coincidir con el registrado");
    }

    @Test
    public void testSetAmountReceived() {
        payment.setAmountReceived(150.0f);

        assertEquals(150.0f, payment.getAmountReceived(), 0.001f,
                "El sistema debe actualizar el efectivo recibido");

        assertThrows(IllegalArgumentException.class, () -> {
            payment.setAmountReceived(-50.0f);
        }, "El sistema debe rechazar montos de efectivo negativos");
    }

    @Test
    public void testGetChangeGiven() {
        payment.processPayment();

        float expResult = 20.0f;
        float result = payment.getChangeGiven();

        assertEquals(expResult, result, 0.001f,
                "El vuelto entregado debe coincidir con el calculado");
    }

    @Test
    public void testSetChangeGiven() {
        payment.setChangeGiven(30.0f);

        assertEquals(30.0f, payment.getChangeGiven(), 0.001f,
                "El sistema debe actualizar correctamente el vuelto");

        assertThrows(IllegalArgumentException.class, () -> {
            payment.setChangeGiven(-20.0f);
        }, "El vuelto entregado no debe ser negativo");
    }

    @Test
    public void testGetTotalAmount() {
        float expResult = 100.0f;
        float result = payment.getTotalAmount();

        assertEquals(expResult, result, 0.001f,
                "El total a pagar debe coincidir con el valor registrado");
    }

    @Test
    public void testSetTotalAmount() {
        payment.setTotalAmount(200.0f);

        assertEquals(200.0f, payment.getTotalAmount(), 0.001f,
                "El sistema debe actualizar correctamente el total");

        assertThrows(IllegalArgumentException.class, () -> {
            payment.setTotalAmount(-100.0f);
        }, "El total a pagar no debe aceptar valores negativos");
    }

    @Test
    public void testToString() {
        payment.processPayment();

        String result = payment.toString();

        assertNotNull(result,
                "La descripcion del pago no debe ser null");

        assertTrue(result.contains("total=$100.0"),
                "La descripcion debe mostrar el total del pago");

        assertTrue(result.contains("efectivoRecibido=$120.0"),
                "La descripcion debe mostrar el efectivo recibido");

        assertTrue(result.contains("vueltoEntregado=$20.0"),
                "La descripcion debe incluir el vuelto entregado");
    }

    @Test
    public void testProcessPaymentAfterInsufficientFunds() {
        assertTrue(payment.processPayment(),
                "El primer pago debe procesarse correctamente");

        assertEquals(20.0f, payment.getChangeGiven(), 0.001f,
                "El vuelto inicial debe ser de 20 dolares");

        payment.setAmountReceived(50.0f);

        assertFalse(payment.processPayment(),
                "El sistema debe rechazar el pago cuando el efectivo es insuficiente");

        assertEquals(0.0f, payment.getChangeGiven(), 0.001f,
                "Un pago rechazado no debe conservar el vuelto anterior");
    }
}
