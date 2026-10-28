package ec.edu.espe.JEQualityFootwear.model;

import java.util.ArrayList;
import java.util.List;

public class Customer extends Person {
    private int customerCode;
    private transient List<Invoice> purchaseHistory;

    public Customer(int customerCode, String id, String name, String phoneNumber, String email, String username, String password) {
        super(id, name, phoneNumber, email, username, password);
        setCustomerCode(customerCode);
        this.purchaseHistory = new ArrayList<>();
    }

    public void getInvoice(Invoice invoice) {
        if (invoice != null) {
            if (this.purchaseHistory == null) {
                this.purchaseHistory = new ArrayList<>();
            }
            if (!this.purchaseHistory.contains(invoice)) {
                this.purchaseHistory.add(invoice);
            }
        }
    }

    public int getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(int customerCode) {
        if (customerCode <= 0) {
            throw new IllegalArgumentException("El sistema debe rechazar codigos de cliente negativos o cero");
        }
        this.customerCode = customerCode;
    }

    public List<Invoice> getPurchaseHistory() {
        return purchaseHistory;
    }

    public void setPurchaseHistory(List<Invoice> purchaseHistory) {
        if (purchaseHistory == null) {
            throw new IllegalArgumentException("El historial de compras no debe aceptar valores null");
        }
        this.purchaseHistory = purchaseHistory;
    }

    @Override
    public String toString() {
        int count = (purchaseHistory != null) ? purchaseHistory.size() : 0;
        return "Customer{customerCode=" + customerCode + ", " + super.getInfo() + ", comprasRealizadas=" + count + "}";
    }
}