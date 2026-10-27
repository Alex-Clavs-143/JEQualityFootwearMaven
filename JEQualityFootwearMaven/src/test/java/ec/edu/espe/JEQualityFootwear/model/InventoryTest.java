
package ec.edu.espe.JEQualityFootwear.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {

    private Inventory inventory;
    private Product product1;
    private Product product2;
    private Product product3;

    public InventoryTest() {
    }

    @BeforeEach
    public void setUp() {
        inventory = new Inventory();

        Category category1 = new Category("Deportivo", "Hombres");
        Category category2 = new Category("Formal", "Mujeres");

        product1 = new Product("ZAP-001", "Zapato Deportivo",
                75.50f, 10, category1, "Negro", 42);

        product2 = new Product("ZAP-002", "Zapato Formal",
                65.00f, 5, category2, "Cafe", 38);

        product3 = new Product("ZAP-003", "Zapato Casual",
                90.00f, 7, category1, "Blanco", 40);
    }

    @Test
    public void testAddProduct() {
        inventory.addProduct(product1);
        inventory.addProduct(product1);
        inventory.addProduct(null);

        Product duplicate = new Product("zap-001", "Otro Zapato",
                50.00f, 5, product1.getCategory(), "Azul", 40);

        inventory.addProduct(duplicate);

        assertEquals(1, inventory.getProducts().size(),
                "No se deben registrar productos duplicados o nulos");

        assertSame(product1, inventory.findProduct("ZAP-001"),
                "El inventario debe conservar el producto original");
    }

    @Test
    public void testRemoveProduct() {
        inventory.addProduct(product1);

        assertFalse(inventory.removeProduct("ZAP-999"),
                "No se debe eliminar un producto que no existe");

        assertTrue(inventory.removeProduct("ZAP-001"),
                "El sistema debe permitir eliminar un producto existente");

        assertNull(inventory.findProduct("ZAP-001"),
                "El producto eliminado no debe encontrarse en el inventario");
    }

    @Test
    public void testFindProduct() {
        inventory.addProduct(product1);

        Product result = inventory.findProduct("zap-001");

        assertSame(product1, result,
                "La busqueda debe encontrar productos sin distinguir mayusculas");

        assertNull(inventory.findProduct("ZAP-999"),
                "La busqueda debe devolver null si el producto no existe");
    }

    @Test
    public void testCheckStock() {
        inventory.addProduct(product1);

        assertEquals(10, inventory.checkStock(product1),
                "El sistema debe mostrar el stock inicial correcto");

        product1.updateStock(-3);

        assertEquals(7, inventory.checkStock(product1),
                "El stock debe reflejar las unidades descontadas");

        assertEquals(0, inventory.checkStock(null),
                "El stock de un producto nulo debe ser cero");
    }

    @Test
    public void testFilterBySection() {
        inventory.addProduct(product1);
        inventory.addProduct(product2);
        inventory.addProduct(product3);

        List<Product> result = inventory.filterBySection("hombres");

        assertEquals(2, result.size(),
                "El filtro debe devolver los dos productos de Hombres");

        assertTrue(result.contains(product1),
                "El filtro debe incluir el primer producto de Hombres");

        assertTrue(result.contains(product3),
                "El filtro debe incluir el segundo producto de Hombres");

        assertFalse(result.contains(product2),
                "El filtro no debe incluir productos de Mujeres");
    }

    @Test
    public void testPerformAudit() {
        inventory.addProduct(product1);

        assertFalse(inventory.performAudit("ZAP-001", -5),
                "No se debe permitir una auditoria con stock negativo");

        assertEquals(10, product1.getCurrentStock(),
                "Una auditoria invalida no debe modificar el stock");

        assertTrue(inventory.performAudit("ZAP-001", 15),
                "El sistema debe permitir registrar una auditoria valida");

        assertEquals(15, product1.getCurrentStock(),
                "El stock debe actualizarse al valor verificado");

        assertNotNull(inventory.getLastAuditDate(),
                "La auditoria debe mantener una fecha de registro");

        assertFalse(inventory.performAudit("ZAP-999", 20),
                "No se debe auditar un producto inexistente");
    }

    @Test
    public void testGetBestSellingProducts() {
        product1.setUnitsSold(4);
        product2.setUnitsSold(12);
        product3.setUnitsSold(7);

        inventory.addProduct(product1);
        inventory.addProduct(product2);
        inventory.addProduct(product3);

        List<Product> result = inventory.getBestSellingProducts();

        assertEquals(3, result.size(),
                "El reporte debe incluir todos los productos");

        assertSame(product2, result.get(0),
                "El producto con mas ventas debe ocupar el primer lugar");

        assertSame(product3, result.get(1),
                "El segundo producto debe tener menos ventas que el primero");

        assertSame(product1, result.get(2),
                "El producto con menos ventas debe ocupar el ultimo lugar");

        assertSame(product1, inventory.getProducts().get(0),
                "El reporte no debe modificar el orden del inventario original");
    }

    @Test
    public void testGetProducts() {
        List<Product> result = inventory.getProducts();

        assertNotNull(result,
                "La lista de productos no debe ser null");

        assertTrue(result.isEmpty(),
                "Un inventario nuevo debe comenzar sin productos");

        inventory.addProduct(product1);

        assertEquals(1, inventory.getProducts().size(),
                "El inventario debe contener el producto registrado");

        assertSame(product1, inventory.getProducts().get(0),
                "La lista debe contener el producto correcto");
    }

    @Test
    public void testSetProducts() {
        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        inventory.setProducts(products);

        assertEquals(2, inventory.getProducts().size(),
                "El inventario debe almacenar la lista asignada");

        assertThrows(IllegalArgumentException.class, () -> {
            inventory.setProducts(null);
        }, "El inventario no debe aceptar una lista de productos null");
    }

    @Test
    public void testGetLastAuditDate() {
        Date result = inventory.getLastAuditDate();

        assertNotNull(result,
                "El inventario debe tener una fecha inicial de auditoria");

        inventory.addProduct(product1);
        inventory.performAudit("ZAP-001", 20);

        Date updatedDate = inventory.getLastAuditDate();

        assertNotNull(updatedDate,
                "La fecha no debe ser null despues de una auditoria");

        assertTrue(updatedDate.compareTo(result) >= 0,
                "La fecha actualizada no debe ser anterior a la inicial");
    }

    @Test
    public void testSetLastAuditDate() {
        Date lastAuditDate = new Date(0);

        inventory.setLastAuditDate(lastAuditDate);

        assertEquals(lastAuditDate, inventory.getLastAuditDate(),
                "El sistema debe guardar correctamente la fecha asignada");

        assertThrows(IllegalArgumentException.class, () -> {
            inventory.setLastAuditDate(null);
        }, "La fecha de auditoria no debe aceptar valores null");
    }

    @Test
    public void testToString() {
        inventory.addProduct(product1);
        inventory.addProduct(product2);

        String result = inventory.toString();

        assertNotNull(result,
                "La descripcion del inventario no debe ser null");

        assertTrue(result.contains("totalModelos=2"),
                "La descripcion debe mostrar la cantidad de productos");

        assertTrue(result.contains("ultimaAuditoria="),
                "La descripcion debe incluir la fecha de ultima auditoria");
    }
}
