package ec.edu.espe.JEQualityFootwear.model;

public class Category {
    private String name;
    private String gender;

    public Category(String name, String gender) {
        setName(name);
        setGender(gender);
    }

    public String getCategoryName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El sistema debe rechazar nombres de categorias nulos o vacios");
        }
        this.name = name.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if (gender == null || gender.trim().isEmpty()) {
            throw new IllegalArgumentException("La seccion no puede ser nula o vacia");
        }
        String g = gender.trim();
        if (!g.equalsIgnoreCase("Hombres") && !g.equalsIgnoreCase("Mujeres")
                && !g.equalsIgnoreCase("Ninos") && !g.equalsIgnoreCase("Niños")
                && !g.equalsIgnoreCase("Accesorios")) {
            throw new IllegalArgumentException("El sistema debe aceptar solamente secciones validas del catalogo");
        }
        this.gender = g;
    }

    public float sale(Product product) {
        if (product != null) {
            return product.getPrice();
        }
        return 0.0f;
    }

    @Override
    public String toString() {
        return gender + " - " + name;
    }
}