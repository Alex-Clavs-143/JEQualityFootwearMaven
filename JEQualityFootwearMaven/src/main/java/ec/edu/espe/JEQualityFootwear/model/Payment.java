package ec.edu.espe.JEQualityFootwear.model;

public class Payment {
    private float amountReceived;
    private float changeGiven;
    private float totalAmount;

    public Payment(float amountReceived, float totalAmount) {
        this.amountReceived = amountReceived;
        this.totalAmount = totalAmount;
        this.changeGiven = 0.0f;
    }

    public float calculateChange(float amountReceived) {
        this.amountReceived = amountReceived;
        if (amountReceived >= totalAmount) {
            this.changeGiven = amountReceived - totalAmount;
        } else {
            this.changeGiven = -1.0f;
        }
        return this.changeGiven;
    }

    public boolean processPayment() {
        if (this.amountReceived >= this.totalAmount && this.totalAmount > 0) {
            calculateChange(this.amountReceived);
            return true;
        }
        this.changeGiven = 0.0f;
        return false;
    }

    public float getAmountReceived() {
        return amountReceived;
    }

    public void setAmountReceived(float amountReceived) {
        if (amountReceived < 0) {
            throw new IllegalArgumentException("El sistema debe rechazar montos de efectivo negativos");
        }
        this.amountReceived = amountReceived;
    }

    public float getChangeGiven() {
        return changeGiven;
    }

    public void setChangeGiven(float changeGiven) {
        if (changeGiven < 0) {
            throw new IllegalArgumentException("El vuelto entregado no debe ser negativo");
        }
        this.changeGiven = changeGiven;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        if (totalAmount < 0) {
            throw new IllegalArgumentException("El total a pagar no debe aceptar valores negativos");
        }
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "Payment{total=$" + totalAmount + ", efectivoRecibido=$" + amountReceived + ", vueltoEntregado=$" + changeGiven + "}";
    }
}