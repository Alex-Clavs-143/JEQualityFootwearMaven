package ec.edu.espe.JEQualityFootwear.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StaffMemberTest {

    private StaffMember staffMember;

    public StaffMemberTest() {
    }

    @BeforeEach
    public void setUp() {
        staffMember = new StaffMember(301, 600.0f, "1712345678", "Jorge Enriquez", "0991234567", "jorge@jequality.com", "staff_jorge", "staff123");
    }

    @Test
    public void testSetNegativeSalaryThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            staffMember.setSalary(-100.0f);
        }, "El sueldo de un miembro del personal no puede ser negativo");
    }

    @Test
    public void testSetZeroOrNegativeEmployeeCodeThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            staffMember.setEmployeeCode(-1);
        }, "El codigo de empleado no debe aceptar numeros negativos");
    }

    @Test
    public void testCreateStaffMemberWithNegativeSalaryThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new StaffMember(302, -450.0f, "1787654321", "Luis Unda", "0987654321", "luis@jequality.com", "luis_u", "1234");
        }, "No se debe permitir instanciar un empleado con sueldo inicial negativo");
    }

    @Test
    public void testCreateStaffMemberWithInvalidCodeThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new StaffMember(0, 500.0f, "1787654321", "Luis Unda", "0987654321", "luis@jequality.com", "luis_u", "1234");
        }, "El codigo de empleado al crear el registro debe ser mayor a cero");
    }

    @Test
    public void testCalculateSalaryReturnsExactBaseSalary() {
        staffMember.setSalary(750.50f);
        assertEquals(750.50f, staffMember.calculateSalary(), 0.001, "El calculo de sueldo de StaffMember debe retornar exactamente su sueldo base");
    }

    @Test
    public void testSetEmptyNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            staffMember.setName("   ");
        }, "El nombre del empleado heredado de Person no puede estar vacio");
    }

    @Test
    public void testSetInvalidIdWithLettersThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            staffMember.setId("17123ABCDE");
        }, "La cedula del empleado solo debe contener digitos numericos");
    }

    @Test
    public void testSetShortPhoneNumberThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            staffMember.setPhoneNumber("099123");
        }, "El numero telefonico del empleado debe tener exactamente 10 digitos");
    }

    @Test
    public void testSetNullUsernameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            staffMember.setUsername(null);
        }, "El nombre de usuario del empleado no puede ser nulo");
    }

    @Test
    public void testCalculateSalaryZeroBoundary() {
        staffMember.setSalary(0.0f);
        assertEquals(0.0f, staffMember.calculateSalary(), 0.001, "El sueldo limite de 0.0 debe ser soportado correctamente");
    }
}