package ec.edu.espe.JEQualityFootwear.model;

public class Payment {
    private float cashAmountReceived;
    private float cashChangeGiven;
    private float totalAmountToPay;

    public Payment(float cashAmountReceived, float totalAmountToPay) {
        this.cashAmountReceived = cashAmountReceived;
        this.totalAmountToPay = totalAmountToPay;
        this.cashChangeGiven = 0.0f;
    }

    public float calculateCashChange(float cashAmountReceived) {
        this.cashAmountReceived = cashAmountReceived;
        if (cashAmountReceived >= totalAmountToPay) {
            this.cashChangeGiven = cashAmountReceived - totalAmountToPay;
        } else {
            this.cashChangeGiven = -1.0f;
        }
        return this.cashChangeGiven;
    }

    public float calculateChange(float cashAmountReceived) {
        return calculateCashChange(cashAmountReceived);
    }

    public boolean processPayment() {
        if (this.cashAmountReceived >= this.totalAmountToPay && this.totalAmountToPay > 0) {
            calculateCashChange(this.cashAmountReceived);
            return true;
        }
        this.cashChangeGiven = 0.0f;
        return false;
    }

    public float getAmountReceived() {
        return cashAmountReceived;
    }

    public void setAmountReceived(float cashAmountReceived) {
        if (cashAmountReceived < 0) {
            throw new IllegalArgumentException("El sistema debe rechazar montos de efectivo negativos");
        }
        this.cashAmountReceived = cashAmountReceived;
    }

    public float getCashChangeGiven() {
        return cashChangeGiven;
    }

    public void setCashChangeGiven(float cashChangeGiven) {
        if (cashChangeGiven < 0) {
            throw new IllegalArgumentException("El vuelto entregado no debe ser negativo");
        }
        this.cashChangeGiven = cashChangeGiven;
    }

    public float getChangeGiven() {
        return getCashChangeGiven();
    }

    public void setChangeGiven(float cashChangeGiven) {
        setCashChangeGiven(cashChangeGiven);
    }

    public float getTotalAmount() {
        return totalAmountToPay;
    }

    public void setTotalAmount(float totalAmountToPay) {
        if (totalAmountToPay < 0) {
            throw new IllegalArgumentException("El total a pagar no debe aceptar valores negativos");
        }
        this.totalAmountToPay = totalAmountToPay;
    }

    @Override
    public String toString() {
        return "Payment{total=$" + totalAmountToPay + ", efectivoRecibido=$" + cashAmountReceived + ", vueltoEntregado=$" + cashChangeGiven + "}";
    }
}