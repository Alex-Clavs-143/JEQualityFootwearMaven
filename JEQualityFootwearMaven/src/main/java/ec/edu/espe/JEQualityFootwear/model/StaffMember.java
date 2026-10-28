package ec.edu.espe.JEQualityFootwear.model;

public class StaffMember extends Person {
    private int employeeCode;
    private float salary;

    public StaffMember(int employeeCode, float salary, String id, String name, String phoneNumber, String email, String username, String password) {
        super(id, name, phoneNumber, email, username, password);
        setEmployeeCode(employeeCode);
        setSalary(salary);
    }

    public float calculateSalary() {
        return salary;
    }

    public int getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(int employeeCode) {
        if (employeeCode <= 0) {
            throw new IllegalArgumentException("El codigo de empleado no puede ser negativo ni cero");
        }
        this.employeeCode = employeeCode;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        if (salary < 0) {
            throw new IllegalArgumentException("No se debe permitir asignar un sueldo negativo al personal");
        }
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "StaffMember{employeeCode=" + employeeCode + ", baseSalary=$" + salary + ", " + super.getInfo() + "}";
    }
}