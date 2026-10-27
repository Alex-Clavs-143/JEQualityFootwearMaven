package ec.edu.espe.JEQualityFootwear.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {
    
    private Person person;

    public PersonTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        person = new Person("1711223344", "Juan Perez", "0991112223", "juan@mail.com", "jperez", "pass123");
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetInfo() {
        String info = person.getInfo();
        assertNotNull(info);
        assertTrue(info.contains("1711223344"));
        assertTrue(info.contains("Juan Perez"));
    }

    @Test
    public void testGetAndSetId() {
        person.setId("1722334455");
        assertEquals("1722334455", person.getId());
    }

    @Test
    public void testGetAndSetName() {
        person.setName("Maria Gomez");
        assertEquals("Maria Gomez", person.getName());
    }

    @Test
    public void testGetAndSetPhoneNumber() {
        person.setPhoneNumber("0988888888");
        assertEquals("0988888888", person.getPhoneNumber());
    }

    @Test
    public void testGetAndSetEmail() {
        person.setEmail("maria@mail.com");
        assertEquals("maria@mail.com", person.getEmail());
    }

    @Test
    public void testGetAndSetUsername() {
        person.setUsername("mgomez");
        assertEquals("mgomez", person.getUsername());
    }

    @Test
    public void testGetAndSetPassword() {
        person.setPassword("newpass456");
        assertEquals("newpass456", person.getPassword());
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals("1711223344", person.getId());
        assertEquals("Juan Perez", person.getName());
        assertEquals("0991112223", person.getPhoneNumber());
        assertEquals("juan@mail.com", person.getEmail());
    }

    @Test
    public void testCredentialsGetters() {
        assertEquals("jperez", person.getUsername());
        assertEquals("pass123", person.getPassword());
    }

    @Test
    public void testToString() {
        String result = person.toString();
        assertNotNull(result);
        assertTrue(result.contains("Juan Perez"));
        assertTrue(result.contains("1711223344"));
    }
}
