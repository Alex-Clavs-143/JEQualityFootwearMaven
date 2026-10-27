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
        staffMember = new StaffMember(301, 800.0f, "1710001112", "Maria Gomez",
                "0998877665", "maria@jequality.com", "maria_s", "staff123");
    }

    @Test
    public void testCalculateSalary() {
        float expResult = 800.0f;
        float result = staffMember.calculateSalary();

        assertEquals(expResult, result, 0.001f,
                "El sueldo calculado del miembro del personal debe ser igual a su sueldo base");
    }

    @Test
    public void testGetEmployeeCode() {
        int expResult = 301;
        int result = staffMember.getEmployeeCode();

        assertEquals(expResult, result,
                "El codigo de empleado debe coincidir con el configurado");
    }

    @Test
    public void testSetEmployeeCode() {
        int employeeCode = 302;
        staffMember.setEmployeeCode(employeeCode);

        assertEquals(employeeCode, staffMember.getEmployeeCode(),
                "El codigo de empleado debe actualizarse correctamente");
    }

    @Test
    public void testGetSalary() {
        float expResult = 800.0f;
        float result = staffMember.getSalary();

        assertEquals(expResult, result, 0.001f,
                "El sueldo base debe coincidir con el configurado");
    }

    @Test
    public void testToString() {
        String result = staffMember.toString();

        assertNotNull(result, "La descripcion del miembro del personal no debe ser null");
        assertTrue(result.contains("Maria Gomez"),
                "La descripcion debe contener el nombre del empleado");
        assertTrue(result.contains("301"),
                "La descripcion debe contener el codigo del empleado");
    }

    @Test
    public void testSetNegativeSalaryThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            staffMember.setSalary(-500.0f);
        }, "No se debe permitir asignar un sueldo negativo al personal");
    }

    @Test
    public void testSetNegativeEmployeeCodeThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            staffMember.setEmployeeCode(-10);
        }, "El codigo de empleado no puede ser negativo");
    }

    @Test
    public void testCreateStaffMemberWithNullNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new StaffMember(303, 700.0f, "1700000000", null,
                    "0999999999", "test@test.com", "user", "pass");
        }, "No se debe permitir crear un miembro del personal sin nombre");
    }

    @Test
    public void testSetEmptyIdThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            staffMember.setId("");
        }, "La cedula o RUC del personal no puede estar vacia");
    }

    @Test
    public void testSetInvalidEmailThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            staffMember.setEmail("correoInvalido.com");
        }, "El correo electronico del personal debe tener un formato valido");
    }
}