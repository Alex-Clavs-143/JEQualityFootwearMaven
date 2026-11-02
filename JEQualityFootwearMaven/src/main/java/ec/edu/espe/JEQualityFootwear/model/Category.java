package ec.edu.espe.JEQualityFootwear.model;

public class Category {
    private String productCategoryName;
    private String gender;

    public Category(String productCategoryName, String gender) {
        setName(productCategoryName);
        setGender(gender);
    }

    public String getCategoryName() {
        return productCategoryName;
    }

    public void setName(String productCategoryName) {
        if (productCategoryName == null || productCategoryName.trim().isEmpty()) {
            throw new IllegalArgumentException("El sistema debe rechazar nombres de categorias nulos o vacios");
        }
        this.productCategoryName = productCategoryName.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if (gender == null || gender.trim().isEmpty()) {
            throw new IllegalArgumentException("La seccion no puede ser nula o vacia");
        }
        String trimmedGenderSection = gender.trim();
        if (!trimmedGenderSection.equalsIgnoreCase("Hombres") && !trimmedGenderSection.equalsIgnoreCase("Mujeres")
                && !trimmedGenderSection.equalsIgnoreCase("Ninos") && !trimmedGenderSection.equalsIgnoreCase("Niños")
                && !trimmedGenderSection.equalsIgnoreCase("Accesorios")) {
            throw new IllegalArgumentException("El sistema debe aceptar solamente secciones validas del catalogo");
        }
        this.gender = trimmedGenderSection;
    }

    public float sale(Product product) {
        if (product != null) {
            return product.getPrice();
        }
        return 0.0f;
    }

    @Override
    public String toString() {
        return gender + " - " + productCategoryName;
    }
}