package ec.edu.espe.JEQualityFootwear.model;

public class Person {
    private String id;
    private String name;
    private String phoneNumber;
    private String email;
    private String username;
    private String password;

    public Person(String id, String name, String phoneNumber, String email, String username, String password) {
        setId(id);
        setName(name);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setUsername(username);
        setPassword(password);
    }

    public String getInfo() {
        return "ID/RUC: " + id + " | Nombre: " + name + " | Tel: " + phoneNumber + " | Email: " + email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.trim().isEmpty() || !id.trim().matches("\\d{10}|\\d{13}")) {
            throw new IllegalArgumentException("La cedula debe tener 10 digitos o el RUC 13 digitos validos");
        }
        this.id = id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo ni contener solamente espacios en blanco");
        }
        this.name = name.trim();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || !phoneNumber.trim().matches("\\d+")) {
            throw new IllegalArgumentException("El telefono solo debe aceptar digitos numericos");
        }
        this.phoneNumber = phoneNumber.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty() || !email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("El correo electronico debe contener un formato valido");
        }
        this.email = email.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario no puede estar vacio");
        }
        this.username = username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("La contrasena no puede contener solamente espacios");
        }
        this.password = password.trim();
    }

    @Override
    public String toString() {
        return "Person{id='" + id + "', name='" + name + "', phoneNumber='" + phoneNumber + "', email='" + email + "', username='" + username + "'}";
    }
}