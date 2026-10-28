
package ec.edu.espe.JEQualityFootwear.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    private Person person;

    public PersonTest() {
    }

    @BeforeEach
    public void setUp() {
        person = new Person("1712345678", "Juan Perez",
                "0991234567", "juan@gmail.com",
                "juan123", "ClaveSegura!9");
    }

    @Test
    public void testGetInfo() {
        String result = person.getInfo();

        assertNotNull(result,
                "La informacion de la persona no debe ser null");

        assertTrue(result.contains("Juan Perez"),
                "La informacion debe contener el nombre");

        assertTrue(result.contains("1712345678"),
                "La informacion debe contener la cedula");

        assertTrue(result.contains("0991234567"),
                "La informacion debe contener el telefono");

        assertTrue(result.contains("juan@gmail.com"),
                "La informacion debe contener el correo");

        assertFalse(result.contains("ClaveSegura!9"),
                "La informacion personal no debe mostrar la contrasena");
    }

    @Test
    public void testGetId() {
        String expResult = "1712345678";
        String result = person.getId();

        assertEquals(expResult, result,
                "La cedula debe coincidir con la registrada");
    }

    @Test
    public void testSetId() {
        person.setId("1790012345001");

        assertEquals("1790012345001", person.getId(),
                "El sistema debe permitir actualizar la cedula o RUC");

        assertThrows(IllegalArgumentException.class, () -> {
            person.setId("12345");
        }, "La cedula debe tener 10 digitos o el RUC 13 digitos validos");
    }

    @Test
    public void testGetName() {
        String expResult = "Juan Perez";
        String result = person.getName();

        assertEquals(expResult, result,
                "El nombre debe coincidir con el registrado");
    }

    @Test
    public void testSetName() {
        person.setName("Maria Lopez");

        assertEquals("Maria Lopez", person.getName(),
                "El sistema debe actualizar correctamente el nombre");

        assertThrows(IllegalArgumentException.class, () -> {
            person.setName("   ");
        }, "El nombre no puede contener solamente espacios en blanco");
    }

    @Test
    public void testGetPhoneNumber() {
        String expResult = "0991234567";
        String result = person.getPhoneNumber();

        assertEquals(expResult, result,
                "El telefono debe coincidir con el registrado");
    }

    @Test
    public void testSetPhoneNumber() {
        person.setPhoneNumber("0987654321");

        assertEquals("0987654321", person.getPhoneNumber(),
                "El sistema debe actualizar correctamente el telefono");

        assertThrows(IllegalArgumentException.class, () -> {
            person.setPhoneNumber("ABCDEFGHIJ");
        }, "El telefono no debe aceptar letras");
    }

    @Test
    public void testGetEmail() {
        String expResult = "juan@gmail.com";
        String result = person.getEmail();

        assertEquals(expResult, result,
                "El correo electronico debe coincidir con el registrado");
    }

    @Test
    public void testSetEmail() {
        person.setEmail("nuevo@gmail.com");

        assertEquals("nuevo@gmail.com", person.getEmail(),
                "El sistema debe actualizar correctamente el correo");

        assertThrows(IllegalArgumentException.class, () -> {
            person.setEmail("correoInvalido.com");
        }, "El correo electronico debe contener un formato valido");
    }

    @Test
    public void testGetUsername() {
        String expResult = "juan123";
        String result = person.getUsername();

        assertEquals(expResult, result,
                "El nombre de usuario debe coincidir con el registrado");
    }

    @Test
    public void testSetUsername() {
        person.setUsername("juan_nuevo");

        assertEquals("juan_nuevo", person.getUsername(),
                "El sistema debe actualizar correctamente el usuario");

        assertThrows(IllegalArgumentException.class, () -> {
            person.setUsername("   ");
        }, "El nombre de usuario no puede estar vacio");
    }

    @Test
    public void testGetPassword() {
        String expResult = "ClaveSegura!9";
        String result = person.getPassword();

        assertEquals(expResult, result,
                "La contrasena debe coincidir con la registrada");
    }

    @Test
    public void testSetPassword() {
        person.setPassword("NuevaClave2026!");

        assertEquals("NuevaClave2026!", person.getPassword(),
                "El sistema debe actualizar correctamente la contrasena");

        assertThrows(IllegalArgumentException.class, () -> {
            person.setPassword("   ");
        }, "La contrasena no puede contener solamente espacios");
    }

    @Test
    public void testToString() {
        String result = person.toString();

        assertNotNull(result,
                "La descripcion de la persona no debe ser null");

        assertTrue(result.contains("1712345678"),
                "La descripcion debe incluir la cedula");

        assertTrue(result.contains("Juan Perez"),
                "La descripcion debe incluir el nombre");

        assertTrue(result.contains("0991234567"),
                "La descripcion debe incluir el telefono");

        assertTrue(result.contains("juan@gmail.com"),
                "La descripcion debe incluir el correo");

        assertTrue(result.contains("juan123"),
                "La descripcion debe incluir el usuario");

        assertFalse(result.contains("ClaveSegura!9"),
                "La descripcion no debe exponer la contrasena");
    }
}
