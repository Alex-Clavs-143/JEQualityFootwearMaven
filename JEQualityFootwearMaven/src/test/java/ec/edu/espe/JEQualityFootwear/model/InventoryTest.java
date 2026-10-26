
package ec.edu.espe.JEQualityFootwear.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {

    private Inventory inventory;
    private Product product;
    private Category category;

    public InventoryTest() {
    }

    @BeforeEach
    public void setUp() {
        inventory = new Inventory();
        category = new Category("Deportivo", "Hombres");
        product = new Product("ZAP-001", "Zapato Deportivo",
                75.50f, 10, category, "Negro", 42);
    }

    @Test
    public void testAddProduct() {
        inventory.addProduct(product);

        assertEquals(1, inventory.getProducts().size(),
                "Debe registrar correctamente el producto en el inventario");

        assertSame(product, inventory.findProduct("ZAP-001"),
                "El producto agregado debe poder localizarse por su codigo");

        inventory.addProduct(product);

        assertEquals(1, inventory.getProducts().size(),
                "No se debe registrar dos veces el mismo producto");
    }

    @Test
    public void testRemoveProduct() {
        inventory.addProduct(product);

        boolean result = inventory.removeProduct("ZAP-001");

        assertTrue(result,
                "Debe permitir eliminar un producto registrado");

        assertNull(inventory.findProduct("ZAP-001"),
                "El producto eliminado no debe permanecer en el inventario");

        assertEquals(0, inventory.getProducts().size(),
                "El inventario debe actualizarse despues de eliminar el producto");
    }

    @Test
    public void testFindProduct() {
        inventory.addProduct(product);

        Product result = inventory.findProduct("zap-001");

        assertNotNull(result,
                "Debe encontrar un producto registrado");

        assertSame(product, result,
                "Debe encontrar el producto sin importar mayusculas o minusculas");

        assertNull(inventory.findProduct("ZAP-999"),
                "Debe devolver null cuando el producto no existe");
    }

    @Test
    public void testCheckStock() {
        inventory.addProduct(product);

        int expResult = 10;
        int result = inventory.checkStock(product);

        assertEquals(expResult, result,
                "Debe devolver correctamente la cantidad disponible");

        assertEquals(0, inventory.checkStock(null),
                "Debe manejar correctamente la consulta de un producto nulo");
    }

    @Test
    public void testFilterBySection() {
        inventory.addProduct(product);

        Category categoryWomen = new Category("Formal", "Mujeres");

        Product productWomen = new Product("ZAP-002",
                "Zapato Formal", 85.00f, 8,
                categoryWomen, "Cafe", 38);

        inventory.addProduct(productWomen);

        List<Product> result = inventory.filterBySection("Hombres");

        assertEquals(1, result.size(),
                "Debe mostrar solamente los productos de la seccion solicitada");

        assertSame(product, result.get(0),
                "El producto filtrado debe pertenecer a la seccion Hombres");

        assertTrue(inventory.filterBySection("Ninos").isEmpty(),
                "Debe devolver una lista vacia si no existen productos en esa seccion");
    }

    @Test
    public void testPerformAudit() {
        inventory.addProduct(product);

        int physicalCount = 6;

        boolean result = inventory.performAudit("ZAP-001", physicalCount);

        assertTrue(result,
                "Debe permitir realizar una auditoria de un producto existente");

        assertEquals(6, product.getCurrentStock(),
                "El stock debe actualizarse con la cantidad verificada fisicamente");

        assertNotNull(inventory.getLastAuditDate(),
                "Debe mantenerse un registro de la fecha de auditoria");

        assertFalse(inventory.performAudit("ZAP-001", -5),
                "No debe permitir auditorias con cantidades negativas");

        assertEquals(6, product.getCurrentStock(),
                "Una auditoria invalida no debe modificar el stock");
    }

    @Test
    public void testGetBestSellingProducts() {
        Product productTwo = new Product("ZAP-002",
                "Zapato Casual", 60.00f, 15,
                category, "Blanco", 40);

        Product productThree = new Product("ZAP-003",
                "Zapato Formal", 90.00f, 20,
                category, "Cafe", 41);

        inventory.addProduct(product);
        inventory.addProduct(productTwo);
        inventory.addProduct(productThree);

        product.updateStock(-3);
        productTwo.updateStock(-5);
        productThree.updateStock(-1);

        List<Product> result = inventory.getBestSellingProducts();

        assertEquals(3, result.size(),
                "El ranking debe contener todos los productos registrados");

        assertTrue(result.contains(product)
                && result.contains(productTwo)
                && result.contains(productThree),
                "El ranking debe conservar todos los productos");

        for (int i = 0; i < result.size() - 1; i++) {
            assertTrue(result.get(i).getUnitsSold()
                    >= result.get(i + 1).getUnitsSold(),
                    "Los productos deben ordenarse de mayor a menor cantidad vendida");
        }

        assertNotSame(inventory.getProducts(), result,
                "El ranking debe ser una lista independiente del inventario original");
    }

    @Test
    public void testGetProducts() {
        assertNotNull(inventory.getProducts(),
                "La lista de productos no debe ser nula");

        assertTrue(inventory.getProducts().isEmpty(),
                "Un inventario nuevo debe comenzar vacio");

        inventory.addProduct(product);

        assertEquals(1, inventory.getProducts().size(),
                "Debe devolver la lista actualizada de productos");
    }

    @Test
    public void testSetProducts() {
        assertThrows(IllegalArgumentException.class, () -> {
            inventory.setProducts(null);
        }, "No se debe permitir reemplazar el inventario con una lista nula");
    }

    @Test
    public void testGetLastAuditDate() {
        Date result = inventory.getLastAuditDate();

        assertNotNull(result,
                "El inventario debe mantener una fecha de auditoria");

        assertFalse(result.after(new Date()),
                "La fecha de auditoria no debe ser posterior a la fecha actual");
    }

    @Test
    public void testSetLastAuditDate() {
        assertThrows(IllegalArgumentException.class, () -> {
            inventory.setLastAuditDate(null);
        }, "No se debe permitir establecer una fecha de auditoria nula");
    }

    @Test
    public void testToString() {
        inventory.addProduct(product);

        String result = inventory.toString();

        assertNotNull(result,
                "La representacion del inventario no debe ser nula");

        assertTrue(result.contains("totalModelos=1"),
                "Debe mostrar correctamente la cantidad de productos registrados");

        assertTrue(result.contains("ultimaAuditoria="),
                "Debe incluir informacion de la ultima auditoria");
    }
}
