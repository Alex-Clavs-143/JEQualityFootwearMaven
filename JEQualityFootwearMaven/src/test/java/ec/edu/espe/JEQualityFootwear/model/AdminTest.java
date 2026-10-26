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
        admin = new Admin(101, 1500.0f, "1712345678", "David Clavon", "0991234567", "david@jequality.com", "admin_david", "admin123");
    }

    @Test
    public void testSetNegativeSalaryThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            admin.setSalary(-500.0f);
        }, "El sistema deberia rechazar sueldos negativos lanzando una excepcion");
    }

    @Test
    public void testSetInvalidEmployeeCodeThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            admin.setEmployeeCode(-10);
        }, "El sistema no debe permitir codigos de empleado negativos");
    }

    @Test
    public void testCreateAdminWithNullNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Admin(102, 1000.0f, "1700000000", null, "0999999999", "test@test.com", "user", "pass");
        }, "No se debe poder crear un administrador sin nombre (null)");
    }

    @Test
    public void testSetEmptyIdThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            admin.setId("");
        }, "El ID/Cedula no puede ser un texto vacio");
    }

    @Test
    public void testSetEmptyPasswordThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            admin.setPassword("   ");
        }, "La contrasena no puede estar compuesta solo de espacios en blanco");
    }

    @Test
    public void testManageNullUserThrowsException() {
        assertThrows(NullPointerException.class, () -> {
            admin.manageUser(null);
        }, "Administrar un usuario nulo debe arrojar NullPointerException explícitamente");
    }

    @Test
    public void testSetExtremelyHighSalary() {
        admin.setSalary(999999.99f);
        assertEquals(999999.99f, admin.calculateSalary(), 0.01, "Debe soportar sueldos altos válidos");
    }

    @Test
    public void testSetInvalidPhoneNumberThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            admin.setPhoneNumber("ABCDEFGHIJ");
        }, "El teléfono heredado de Person no debe aceptar letras");
    }

    @Test
    public void testSetInvalidEmailThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            admin.setEmail("correoInvalido.com");
        }, "El email debe contener un @ para ser valido");
    }

    @Test
    public void testCalculateSalaryZeroBoundary() {
        admin.setSalary(0.0f);
        assertEquals(0.0f, admin.calculateSalary(), 0.001, "El sueldo limite de 0 debe ser manejado correctamente");
    }
}