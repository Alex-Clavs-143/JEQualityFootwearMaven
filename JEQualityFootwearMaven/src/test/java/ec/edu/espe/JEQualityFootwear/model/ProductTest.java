
package ec.edu.espe.JEQualityFootwear.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    private Product product;
    private Category category;

    public ProductTest() {
    }

    @BeforeEach
    public void setUp() {
        category = new Category("Deportivo", "Hombres");

        product = new Product("ZAP-001", "Zapato Deportivo",
                75.50f, 10, category, "Negro", 42);
    }

    @Test
    public void testUpdateStock() {
        product.updateStock(-3);

        assertEquals(7, product.getCurrentStock(),
                "El stock debe disminuir al vender productos");

        assertEquals(3, product.getUnitsSold(),
                "Las unidades vendidas deben actualizarse correctamente");

        product.updateStock(5);

        assertEquals(12, product.getCurrentStock(),
                "El stock debe aumentar al ingresar nuevas unidades");

        assertEquals(3, product.getUnitsSold(),
                "Agregar stock no debe incrementar las ventas");

        product.updateStock(-20);

        assertEquals(12, product.getCurrentStock(),
                "No se debe permitir que el stock quede negativo");

        assertEquals(3, product.getUnitsSold(),
                "Una venta rechazada no debe aumentar las unidades vendidas");
    }

    @Test
    public void testAssignCategory() {
        Category newCategory = new Category("Formal", "Mujeres");

        product.assignCategory(newCategory);

        assertSame(newCategory, product.getCategory(),
                "El producto debe actualizar su categoria");

        assertThrows(IllegalArgumentException.class, () -> {
            product.assignCategory(null);
        }, "El producto no debe permitir una categoria nula");
    }

    @Test
    public void testGetPrice() {
        float expResult = 75.50f;
        float result = product.getPrice();

        assertEquals(expResult, result, 0.001f,
                "El precio debe coincidir con el registrado");
    }

    @Test
    public void testSetPrice() {
        product.setPrice(90.00f);

        assertEquals(90.00f, product.getPrice(), 0.001f,
                "El sistema debe actualizar correctamente el precio");

        assertThrows(IllegalArgumentException.class, () -> {
            product.setPrice(-50.00f);
        }, "El sistema debe rechazar precios negativos");
    }

    @Test
    public void testGetProductId() {
        String expResult = "ZAP-001";
        String result = product.getProductId();

        assertEquals(expResult, result,
                "El codigo debe coincidir con el producto registrado");
    }

    @Test
    public void testSetProductId() {
        product.setProductId("ZAP-002");

        assertEquals("ZAP-002", product.getProductId(),
                "El sistema debe permitir actualizar el codigo");

        assertThrows(IllegalArgumentException.class, () -> {
            product.setProductId("   ");
        }, "El codigo del producto no puede estar vacio");
    }

    @Test
    public void testGetName() {
        String expResult = "Zapato Deportivo";
        String result = product.getName();

        assertEquals(expResult, result,
                "El nombre debe coincidir con el modelo registrado");
    }

    @Test
    public void testSetName() {
        product.setName("Zapato Casual");

        assertEquals("Zapato Casual", product.getName(),
                "El sistema debe actualizar el nombre del producto");

        assertThrows(IllegalArgumentException.class, () -> {
            product.setName("   ");
        }, "El nombre del producto no puede contener solo espacios");
    }

    @Test
    public void testGetCurrentStock() {
        int expResult = 10;
        int result = product.getCurrentStock();

        assertEquals(expResult, result,
                "El stock inicial debe coincidir con el registrado");
    }

    @Test
    public void testSetCurrentStock() {
        product.setCurrentStock(25);

        assertEquals(25, product.getCurrentStock(),
                "El sistema debe actualizar el stock correctamente");

        assertThrows(IllegalArgumentException.class, () -> {
            product.setCurrentStock(-5);
        }, "El stock no puede ser negativo");
    }

    @Test
    public void testGetCategory() {
        Category result = product.getCategory();

        assertSame(category, result,
                "El producto debe conservar la categoria asignada");

        assertEquals("Deportivo", result.getCategoryName(),
                "El nombre de la categoria debe ser correcto");

        assertEquals("Hombres", result.getGender(),
                "La seccion del producto debe coincidir");
    }

    @Test
    public void testGetColor() {
        String expResult = "Negro";
        String result = product.getColor();

        assertEquals(expResult, result,
                "El color debe coincidir con el registrado");
    }

    @Test
    public void testSetColor() {
        product.setColor("Blanco");

        assertEquals("Blanco", product.getColor(),
                "El sistema debe actualizar correctamente el color");

        assertThrows(IllegalArgumentException.class, () -> {
            product.setColor("   ");
        }, "El color no puede contener solamente espacios");
    }

    @Test
    public void testGetSize() {
        int expResult = 42;
        int result = product.getSize();

        assertEquals(expResult, result,
                "La talla debe coincidir con la registrada");
    }

    @Test
    public void testSetSize() {
        product.setSize(40);

        assertEquals(40, product.getSize(),
                "El sistema debe actualizar correctamente la talla");

        assertThrows(IllegalArgumentException.class, () -> {
            product.setSize(-2);
        }, "El sistema no debe permitir tallas negativas");
    }

    @Test
    public void testGetUnitsSold() {
        int expResult = 0;
        int result = product.getUnitsSold();

        assertEquals(expResult, result,
                "Un producto nuevo debe comenzar con cero unidades vendidas");

        product.updateStock(-4);

        assertEquals(4, product.getUnitsSold(),
                "Las unidades vendidas deben reflejar las ventas realizadas");
    }

    @Test
    public void testSetUnitsSold() {
        product.setUnitsSold(15);

        assertEquals(15, product.getUnitsSold(),
                "El sistema debe actualizar correctamente las unidades vendidas");

        assertThrows(IllegalArgumentException.class, () -> {
            product.setUnitsSold(-10);
        }, "Las unidades vendidas no pueden ser negativas");
    }

    @Test
    public void testToString() {
        String result = product.toString();

        assertNotNull(result,
                "La descripcion del producto no debe ser null");

        assertTrue(result.contains("ZAP-001"),
                "La descripcion debe incluir el codigo");

        assertTrue(result.contains("Zapato Deportivo"),
                "La descripcion debe incluir el nombre");

        assertTrue(result.contains("Hombres - Deportivo"),
                "La descripcion debe incluir la categoria");

        assertTrue(result.contains("Talla: 42"),
                "La descripcion debe mostrar la talla");

        assertTrue(result.contains("Color: Negro"),
                "La descripcion debe mostrar el color");

        assertTrue(result.contains("Precio: $75.5"),
                "La descripcion debe incluir el precio");

        assertTrue(result.contains("Stock: 10"),
                "La descripcion debe incluir el stock");

        assertTrue(result.contains("Vendidos: 0"),
                "La descripcion debe incluir las unidades vendidas");
    }
}
