package ec.edu.espe.JEQualityFootwear.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    private Person personInstance;

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
        personInstance = new Person("1712345678", "Juan Perez", "0991234567", "juan@gmail.com", "jperez", "pass123");
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetInfo() {
        System.out.println("getInfo");
        String result = personInstance.getInfo();
        assertNotNull(result);
        assertTrue(result.contains("1712345678"));
        assertTrue(result.contains("Juan Perez"));
        assertTrue(result.contains("0991234567"));
        assertTrue(result.contains("juan@gmail.com"));
    }

    @Test
    public void testGetAndSetId() {
        System.out.println("getId & setId");
        assertEquals("1712345678", personInstance.getId());
        
        String newId = "1790012345001";
        personInstance.setId(newId);
        assertEquals(newId, personInstance.getId());
    }

    @Test
    public void testGetAndSetName() {
        System.out.println("getName & setName");
        assertEquals("Juan Perez", personInstance.getName());
        
        String newName = "Carlos Andrade";
        personInstance.setName(newName);
        assertEquals(newName, personInstance.getName());
    }

    @Test
    public void testGetAndSetPhoneNumber() {
        System.out.println("getPhoneNumber & setPhoneNumber");
        assertEquals("0991234567", personInstance.getPhoneNumber());
        
        String newPhone = "0987654321";
        personInstance.setPhoneNumber(newPhone);
        assertEquals(newPhone, personInstance.getPhoneNumber());
    }

    @Test
    public void testGetAndSetEmail() {
        System.out.println("getEmail & setEmail");
        assertEquals("juan@gmail.com", personInstance.getEmail());
        
        String newEmail = "carlos@gmail.com";
        personInstance.setEmail(newEmail);
        assertEquals(newEmail, personInstance.getEmail());
    }

    @Test
    public void testGetAndSetUsername() {
        System.out.println("getUsername & setUsername");
        assertEquals("jperez", personInstance.getUsername());
        
        String newUsername = "candrade";
        personInstance.setUsername(newUsername);
        assertEquals(newUsername, personInstance.getUsername());
    }

    @Test
    public void testGetAndSetPassword() {
        System.out.println("getPassword & setPassword");
        assertEquals("pass123", personInstance.getPassword());
        
        String newPassword = "securePass456";
        personInstance.setPassword(newPassword);
        assertEquals(newPassword, personInstance.getPassword());
    }

    @Test
    public void testToString() {
        System.out.println("toString");
        String result = personInstance.toString();
        assertNotNull(result);
        assertTrue(result.contains("1712345678"));
        assertTrue(result.contains("Juan Perez"));
        assertTrue(result.contains("jperez"));
    }

    @Test
    public void testUpdateAllPersonData() {
        System.out.println("updateAllPersonData");
        personInstance.setId("0987654321");
        personInstance.setName("Maria Gomez");
        personInstance.setPhoneNumber("0998887776");
        personInstance.setEmail("maria@gmail.com");

        assertEquals("0987654321", personInstance.getId());
        assertEquals("Maria Gomez", personInstance.getName());
        assertEquals("0998887776", personInstance.getPhoneNumber());
        assertEquals("maria@gmail.com", personInstance.getEmail());
    }

    @Test
    public void testAuthenticationCredentials() {
        System.out.println("authenticationCredentials");
        assertTrue(personInstance.getUsername().equals("jperez"));
        assertTrue(personInstance.getPassword().equals("pass123"));
    }
}