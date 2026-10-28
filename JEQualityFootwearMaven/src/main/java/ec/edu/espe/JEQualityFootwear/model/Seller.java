package ec.edu.espe.JEQualityFootwear.model;

public class Seller extends StaffMember {
    private float accumulatedCommission;
    private int completedSalesCount;

    public Seller(int employeeCode, float salary, String id, String name, String phoneNumber, String email, String username, String password) {
        super(employeeCode, salary, id, name, phoneNumber, email, username, password);
        this.accumulatedCommission = 0.0f;
        this.completedSalesCount = 0;
    }

    public void sell() {
        this.completedSalesCount++;
    }

    public void addCommission(float commissionAmount) {
        if (commissionAmount < 0) {
            throw new IllegalArgumentException("No se debe permitir agregar comisiones negativas");
        }
        this.accumulatedCommission += commissionAmount;
    }

    @Override
    public float calculateSalary() {
        return super.getSalary() + this.accumulatedCommission;
    }

    public float getAccumulatedCommission() {
        return accumulatedCommission;
    }

    public void setAccumulatedCommission(float accumulatedCommission) {
        if (accumulatedCommission < 0) {
            throw new IllegalArgumentException("La comision acumulada no puede ser negativa");
        }
        this.accumulatedCommission = accumulatedCommission;
    }

    public int getCompletedSalesCount() {
        return completedSalesCount;
    }

    public void setCompletedSalesCount(int completedSalesCount) {
        if (completedSalesCount < 0) {
            throw new IllegalArgumentException("El contador de ventas realizadas no puede ser negativo");
        }
        this.completedSalesCount = completedSalesCount;
    }

    @Override
    public String toString() {
        return "Seller{employeeCode=" + getEmployeeCode() + ", nombre='" + getName()
                + "', sueldoBase=$" + getSalary()
                + ", comisiones=$" + accumulatedCommission
                + ", sueldoTotal=$" + calculateSalary()
                + ", ventasRealizadas=" + completedSalesCount + "}";
    }
}