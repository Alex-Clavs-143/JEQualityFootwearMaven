package ec.edu.espe.JEQualityFootwear.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SalesManagement {
    private int systemId;
    private float commission;
    private float sellerSalary;
    private float cashRegisterBalance;
    private int invoiceSequence;
    private List<Invoice> salesRegistry;
    private List<StaffMember> staffList;
    private List<Customer> customerDirectory;

    public SalesManagement(int systemId, float commission, float sellerSalary) {
        this.systemId = systemId;
        setCommission(commission);
        this.sellerSalary = sellerSalary;
        this.cashRegisterBalance = 200.00f;
        this.invoiceSequence = 1;
        this.salesRegistry = new ArrayList<>();
        this.staffList = new ArrayList<>();
        this.customerDirectory = new ArrayList<>();
    }

    public Invoice registerSale(Customer customer, Seller seller, List<Product> shoppingCart,
                                List<Integer> purchasedItemQuantities, int systemId, Payment payment, String issuanceChannel) {
        this.invoiceSequence = this.salesRegistry.size() + 1;
        String sequenceNumber = "001-001-00000" + this.invoiceSequence;
        Invoice invoice = new Invoice(sequenceNumber, new Date(), customer, seller, shoppingCart, purchasedItemQuantities, issuanceChannel);
        invoice.setCashTendered(payment.getAmountReceived());
        invoice.setChangeDue(payment.getCashChangeGiven());

        for (int index = 0; index < shoppingCart.size(); index++) {
            Product purchasedProduct = shoppingCart.get(index);
            int purchasedQuantity = purchasedItemQuantities.get(index);
            purchasedProduct.updateStock(-purchasedQuantity);
        }

        this.cashRegisterBalance += invoice.getTotalAmount();

        float earnedCommission = invoice.getSubtotal() * this.commission;
        seller.addCommission(earnedCommission);
        seller.sell();

        customer.addBillToHistory(invoice);
        this.salesRegistry.add(invoice);

        return invoice;
    }

    public Payment processCashPayment(float cashAmountReceived, float totalAmountToPay) {
        Payment payment = new Payment(cashAmountReceived, totalAmountToPay);
        payment.processPayment();
        return payment;
    }

    public String generateReceipt(Invoice invoice) {
        if (invoice == null) {
            return "[ERROR] No se pudo generar el comprobante.";
        }
        String receiptDetails = invoice.getDetails();
        if (invoice.getIssuanceChannel().contains("FISICA")) {
            receiptDetails = receiptDetails + "[SIMULACION IMPRESORA POS] -> Imprimiendo comprobante fisico en papel termico...\n"
                    + "[SIMULACION IMPRESORA POS] -> Factura fisica entregada al cliente en mostrador.\n";
        }
        if (invoice.getIssuanceChannel().contains("ELECTRONICA")) {
            receiptDetails = receiptDetails + "[SIMULACION SERVIDOR SMTP / PDF] -> Generando archivo digital " + invoice.getInvoiceNumber() + ".pdf...\n"
                    + "[SIMULACION SERVIDOR SMTP / PDF] -> Factura electronica enviada exitosamente a: " + invoice.getCustomer().getEmail() + "\n";
        }
        return receiptDetails;
    }

    public void addCommissionToSeller() {
        for (StaffMember staffMember : staffList) {
            if (staffMember instanceof Seller) {
                Seller seller = (Seller) staffMember;
                this.sellerSalary = seller.calculateSalary();
            }
        }
    }

    public float getSellerSalary(Seller seller) {
        if (seller != null) {
            return seller.calculateSalary();
        }
        return 0.0f;
    }

    public Person authenticateUser(String username, String password) {
        if (username == null || password == null) {
            throw new IllegalArgumentException("La autenticacion no debe aceptar credenciales nulas");
        }
        for (StaffMember staffMember : staffList) {
            if (staffMember.getUsername().equalsIgnoreCase(username) && staffMember.getPassword().equals(password)) {
                return staffMember;
            }
        }
        for (Customer customer : customerDirectory) {
            if (customer.getUsername().equalsIgnoreCase(username) && customer.getPassword().equals(password)) {
                return customer;
            }
        }
        return null;
    }

    public Person findPersonByIdOrUsername(String idOrUsernameToSearch) {
        if (idOrUsernameToSearch == null) {
            return null;
        }
        for (Customer customer : customerDirectory) {
            if (customer.getId().equalsIgnoreCase(idOrUsernameToSearch) || customer.getUsername().equalsIgnoreCase(idOrUsernameToSearch)) {
                return customer;
            }
        }
        for (StaffMember staffMember : staffList) {
            if (staffMember.getId().equalsIgnoreCase(idOrUsernameToSearch) || staffMember.getUsername().equalsIgnoreCase(idOrUsernameToSearch)) {
                return staffMember;
            }
        }
        return null;
    }

    public StaffMember findStaffByCode(int employeeCode) {
        for (StaffMember staffMember : staffList) {
            if (staffMember.getEmployeeCode() == employeeCode) {
                return staffMember;
            }
        }
        return null;
    }

    public boolean removeStaffByCode(int employeeCode) {
        StaffMember targetStaffMember = findStaffByCode(employeeCode);
        if (targetStaffMember != null) {
            return staffList.remove(targetStaffMember);
        }
        return false;
    }

    public Customer findCustomerById(String nationalIdOrTaxId) {
        if (nationalIdOrTaxId == null) {
            throw new IllegalArgumentException("La busqueda de cliente no debe aceptar un identificador nulo");
        }
        for (Customer customer : customerDirectory) {
            if (customer.getId().equalsIgnoreCase(nationalIdOrTaxId)) {
                return customer;
            }
        }
        return null;
    }

    public boolean removeCustomerAccount(Customer customer) {
        return customerDirectory.remove(customer);
    }

    public int getSystemId() {
        return systemId;
    }

    public void setSystemId(int systemId) {
        this.systemId = systemId;
    }

    public float getCommission() {
        return commission;
    }

    public void setCommission(float commission) {
        if (commission < 0) {
            throw new IllegalArgumentException("No se debe permitir un porcentaje de comision negativo");
        }
        this.commission = commission;
    }

    public float getCashRegisterBalance() {
        return cashRegisterBalance;
    }

    public void setCashRegisterBalance(float cashRegisterBalance) {
        if (cashRegisterBalance < 0) {
            throw new IllegalArgumentException("No se debe permitir un saldo de caja registradora negativo");
        }
        this.cashRegisterBalance = cashRegisterBalance;
    }

    public List<Invoice> getSalesRegistry() {
        return salesRegistry;
    }

    public List<StaffMember> getStaffList() {
        return staffList;
    }

    public List<Customer> getCustomerDirectory() {
        return customerDirectory;
    }

    @Override
    public String toString() {
        return "SalesManagement{systemId=" + systemId + ", comisionPorcentaje=" + (commission * 100) + "%, ventasRegistradas=" + salesRegistry.size() + ", saldoEnCaja=$" + cashRegisterBalance + "}";
    }
}