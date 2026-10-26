
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
                "Debe devolver el nombre correcto de la categoria");
    }

    @Test
    public void testSetName() {
        category.setName("Formal");

        assertEquals("Formal", category.getCategoryName(),
                "Debe actualizar correctamente el nombre de la categoria");
    }

    @Test
    public void testGetGender() {
        String expResult = "Hombres";
        String result = category.getGender();

        assertEquals(expResult, result,
                "Debe devolver la seccion correcta de la categoria");
    }

    @Test
    public void testSetGender() {
        category.setGender("Mujeres");

        assertEquals("Mujeres", category.getGender(),
                "Debe permitir cambiar la seccion de la categoria");
    }

    @Test
    public void testSale() {
        Product product = new Product(
                "ZAP-001", "Zapato Deportivo", 75.50f,
                10, category, "Negro", 42);

        float expResult = 75.50f;
        float result = category.sale(product);

        assertEquals(expResult, result, 0.001f,
                "Debe devolver correctamente el precio del producto");
    }

    @Test
    public void testToString() {
        String expResult = "Hombres - Deportivo";
        String result = category.toString();

        assertEquals(expResult, result,
                "Debe mostrar correctamente la seccion y el nombre");
    }

    @Test
    public void testSetEmptyNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            category.setName("   ");
        }, "No se debe permitir un nombre compuesto solo por espacios");
    }

    @Test
    public void testCreateCategoryWithEmptyNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Category("", "Mujeres");
        }, "No se debe crear una categoria con el nombre vacio");
    }

    @Test
    public void testSetInvalidGenderThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            category.setGender("Vehiculos");
        }, "La seccion debe pertenecer a una categoria valida del sistema");
    }

    @Test
    public void testCreateCategoryWithNullGenderThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Category("Casual", null);
        }, "No se debe crear una categoria con una seccion nula");
    }
}
