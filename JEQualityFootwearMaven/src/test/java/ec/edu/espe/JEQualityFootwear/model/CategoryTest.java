
package ec.edu.espe.JEQualityFootwear.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {

    private Category category;

    public CategoryTest() {
    }

    @BeforeEach
    public void setUp() {
        category = new Category("Deportivo", "Hombres");
    }

    @Test
    public void testGetCategoryName() {
        String expResult = "Deportivo";
        String result = category.getCategoryName();

        assertEquals(expResult, result,
                "El nombre de la categoria debe coincidir con el registrado");
    }

    @Test
    public void testSetName() {
        category.setName("Formal");

        assertEquals("Formal", category.getCategoryName(),
                "El sistema debe permitir actualizar el nombre de la categoria");
    }

    @Test
    public void testGetGender() {
        String expResult = "Hombres";
        String result = category.getGender();

        assertEquals(expResult, result,
                "La seccion del producto debe coincidir con la registrada");
    }

    @Test
    public void testSetGender() {
        category.setGender("Mujeres");

        assertEquals("Mujeres", category.getGender(),
                "El sistema debe permitir cambiar la seccion de una categoria");
    }

    @Test
    public void testSale() {
        Product product = new Product("ZAP-001", "Zapato Deportivo",
                75.50f, 10, category, "Negro", 42);

        float expResult = 75.50f;
        float result = category.sale(product);

        assertEquals(expResult, result, 0.001f,
                "El metodo sale debe devolver el precio correcto del producto");
    }

    @Test
    public void testToString() {
        String expResult = "Hombres - Deportivo";
        String result = category.toString();

        assertEquals(expResult, result,
                "La descripcion debe mostrar la seccion y el nombre de la categoria");
    }

    @Test
    public void testSaleWithNullProduct() {
        float expResult = 0.0f;
        float result = category.sale(null);

        assertEquals(expResult, result, 0.001f,
                "El sistema debe devolver cero cuando el producto sea null");
    }

    @Test
    public void testSetEmptyNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            category.setName("   ");
        }, "El sistema debe rechazar nombres de categorias vacios o con espacios");
    }

    @Test
    public void testSetInvalidGenderThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            category.setGender("Mascotas");
        }, "El sistema debe aceptar solamente secciones validas del catalogo");
    }

    @Test
    public void testCreateCategoryWithNullNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Category(null, "Hombres");
        }, "No se debe permitir crear una categoria con nombre null");
    }
}
