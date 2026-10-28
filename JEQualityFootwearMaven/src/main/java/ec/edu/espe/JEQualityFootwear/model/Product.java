package ec.edu.espe.JEQualityFootwear.model;

public class Product {
    private String productId;
    private String name;
    private float price;
    private int currentStock;
    private Category category;
    private String color;
    private int size;
    private int unitsSold;

    public Product(String productId, String name, float price, int currentStock, Category category, String color, int size) {
        setProductId(productId);
        setName(name);
        setPrice(price);
        setCurrentStock(currentStock);
        assignCategory(category);
        setColor(color);
        setSize(size);
        this.unitsSold = 0;
    }

    public void updateStock(int amount) {
        if (this.currentStock + amount >= 0) {
            this.currentStock += amount;
            if (amount < 0) {
                this.unitsSold += Math.abs(amount);
            }
        } else {
            System.out.println("[ALERTA] Operacion rechazada: El stock no puede quedar en negativo.");
        }
    }

    public void assignCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("El producto no debe permitir una categoria nula");
        }
        this.category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        if (price < 0) {
            throw new IllegalArgumentException("El sistema debe rechazar precios negativos");
        }
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        if (productId == null || productId.trim().isEmpty()) {
            throw new IllegalArgumentException("El codigo del producto no puede estar vacio");
        }
        this.productId = productId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede contener solo espacios");
        }
        this.name = name.trim();
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(int currentStock) {
        if (currentStock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        this.currentStock = currentStock;
    }

    public Category getCategory() {
        return category;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        if (color == null || color.trim().isEmpty()) {
            throw new IllegalArgumentException("El color no puede contener solamente espacios");
        }
        this.color = color.trim();
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("El sistema no debe permitir tallas negativas");
        }
        this.size = size;
    }

    public int getUnitsSold() {
        return unitsSold;
    }

    public void setUnitsSold(int unitsSold) {
        if (unitsSold < 0) {
            throw new IllegalArgumentException("Las unidades vendidas no pueden ser negativas");
        }
        this.unitsSold = unitsSold;
    }

    @Override
    public String toString() {
        return "[" + productId + "] " + name + " | Cat: " + (category != null ? category.toString() : "Sin Categoria")
                + " | Talla: " + size + " | Color: " + color + " | Precio: $" + price
                + " | Stock: " + currentStock + " | Vendidos: " + unitsSold;
    }
}