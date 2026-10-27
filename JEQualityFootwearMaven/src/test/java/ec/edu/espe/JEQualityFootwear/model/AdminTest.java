
package ec.edu.espe.JEQualityFootwear.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AdminTest {

    private Admin admin;

    public AdminTest() {
    }

    @BeforeEach
    public void setUp() {
        admin = new Admin(101, 1500.0f, "1712345678", "David Clavon",
                "0991234567", "david@jequality.com", "admin_david", "admin123");
    }

    @Test
    public void testManageUser() {
        Person person = new Person("1723456789", "Juan Perez",
                "0987654321", "juan@gmail.com", "juan123", "1234");

        assertDoesNotThrow(() -> {
            admin.manageUser(person);
        }, "El administrador debe poder gestionar un usuario valido");
    }

    @Test
    public void testCalculateSalary() {
        float expResult = 1500.0f;
        float result = admin.calculateSalary();

        assertEquals(expResult, result, 0.001f,
                "El sueldo del administrador debe ser igual a su sueldo base");
    }

    @Test
    public void testToString() {
        String result = admin.toString();

        assertNotNull(result, "La descripcion del administrador no debe ser null");
        assertTrue(result.contains("David Clavon"),
                "La descripcion debe contener el nombre del administrador");
        assertTrue(result.contains("101"),
                "La descripcion debe contener el codigo del empleado");
        assertTrue(result.contains("1500.0"),
                "La descripcion debe contener el sueldo del administrador");
    }

    @Test
    public void testSetNegativeSalaryThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            admin.setSalary(-500.0f);
        }, "El sistema debe rechazar sueldos negativos");
    }

    @Test
    public void testSetInvalidEmployeeCodeThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            admin.setEmployeeCode(-10);
        }, "El codigo de empleado no puede ser negativo");
    }

    @Test
    public void testCreateAdminWithNullNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Admin(102, 1000.0f, "1700000000", null,
                    "0999999999", "test@test.com", "user", "pass");
        }, "No se debe permitir crear administradores sin nombre");
    }

    @Test
    public void testSetEmptyIdThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            admin.setId("");
        }, "La cedula o RUC no puede estar vacia");
    }

    @Test
    public void testSetEmptyPasswordThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            admin.setPassword("   ");
        }, "La contrasena no puede contener solamente espacios");
    }

    @Test
    public void testSetInvalidPhoneNumberThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            admin.setPhoneNumber("ABCDEFGHIJ");
        }, "El telefono no debe aceptar caracteres alfabeticos");
    }

    @Test
    public void testSetInvalidEmailThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            admin.setEmail("correoInvalido.com");
        }, "El correo electronico debe tener un formato valido");
    }
}
