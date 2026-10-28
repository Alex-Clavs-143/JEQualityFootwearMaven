package ec.edu.espe.JEQualityFootwear.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Invoice {
    public static final float VAT_RATE = 0.15f;

    private String invoiceNumber;
    private Date date;
    private Customer customer;
    private Seller seller;
    private List<Product> purchasedItems;
    private List<Integer> quantities;
    private float subtotal;
    private float vatAmount;
    private float totalAmount;
    private float cashTendered;
    private float changeDue;
    private String issuanceChannel;

    public Invoice(String invoiceNumber, Date date, Customer customer, Seller seller,
                   List<Product> purchasedItems, List<Integer> quantities, String issuanceChannel) {
        setInvoiceNumber(invoiceNumber);
        setDate(date);
        setCustomer(customer);
        setSeller(seller);
        setPurchasedItems(purchasedItems);
        setQuantities(quantities);
        setIssuanceChannel(issuanceChannel);
        this.cashTendered = 0.0f;
        this.changeDue = 0.0f;
        calculateTotal();
    }

    public final float calculateTotal() {
        this.subtotal = 0.0f;
        if (this.purchasedItems != null && this.quantities != null) {
            int limit = Math.min(this.purchasedItems.size(), this.quantities.size());
            for (int i = 0; i < limit; i++) {
                Product p = this.purchasedItems.get(i);
                int q = this.quantities.get(i);
                if (p != null && q > 0) {
                    this.subtotal += p.getPrice() * q;
                }
            }
        }
        this.vatAmount = this.subtotal * VAT_RATE;
        this.totalAmount = this.subtotal + this.vatAmount;
        return this.totalAmount;
    }

    public String getDetails() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dateStr = (date != null) ? sdf.format(date) : "N/A";
        StringBuilder sb = new StringBuilder();
        sb.append("\n======================================================================\n");
        sb.append("                    FACTURA - CALZADO JORGE ENRIQUEZ                  \n");
        sb.append("======================================================================\n");
        sb.append("Factura Nro : ").append(invoiceNumber).append("\n");
        sb.append("Fecha       : ").append(dateStr).append("\n");
        sb.append("Canal       : ").append(issuanceChannel).append("\n");
        sb.append("Cliente     : ").append(customer != null ? customer.getInfo() : "Consumidor Final").append("\n");
        sb.append("Vendedor    : ").append(seller != null ? seller.getName() + " (Cod: " + seller.getEmployeeCode() + ")" : "N/A").append("\n");
        sb.append("----------------------------------------------------------------------\n");
        sb.append("DETALLE DE PRODUCTOS:\n");
        if (purchasedItems != null && quantities != null) {
            int limit = Math.min(purchasedItems.size(), quantities.size());
            for (int i = 0; i < limit; i++) {
                Product p = purchasedItems.get(i);
                int q = quantities.get(i);
                sb.append("  - ").append(q).append("x [").append(p.getProductId()).append("] ")
                  .append(p.getName()).append(" ($").append(p.getPrice()).append(" c/u) -> $")
                  .append(p.getPrice() * q).append("\n");
            }
        }
        sb.append("----------------------------------------------------------------------\n");
        sb.append("Subtotal sin impuestos : $").append(subtotal).append(" USD\n");
        sb.append("IVA (15%)              : $").append(vatAmount).append(" USD\n");
        sb.append("TOTAL A PAGAR          : $").append(totalAmount).append(" USD\n");
        sb.append("Efectivo recibido      : $").append(cashTendered).append(" USD\n");
        sb.append("Vuelto entregado       : $").append(changeDue).append(" USD\n");
        sb.append("======================================================================\n");
        return sb.toString();
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        if (invoiceNumber == null || invoiceNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("El numero de factura no puede estar vacio");
        }
        this.invoiceNumber = invoiceNumber.trim();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("La factura no debe permitir una fecha de emision null");
        }
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Una factura no debe quedar sin cliente");
        }
        this.customer = customer;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        if (seller == null) {
            throw new IllegalArgumentException("Una factura no debe quedar sin vendedor");
        }
        this.seller = seller;
    }

    public List<Product> getPurchasedItems() {
        return purchasedItems;
    }

    public void setPurchasedItems(List<Product> purchasedItems) {
        if (purchasedItems == null || purchasedItems.isEmpty()) {
            throw new IllegalArgumentException("La lista de productos no debe aceptar null ni estar vacia");
        }
        this.purchasedItems = purchasedItems;
        calculateTotal();
    }

    public List<Integer> getQuantities() {
        return quantities;
    }

    public void setQuantities(List<Integer> quantities) {
        if (quantities == null || quantities.isEmpty()) {
            throw new IllegalArgumentException("Las cantidades no pueden ser null");
        }
        for (Integer q : quantities) {
            if (q == null || q <= 0) {
                throw new IllegalArgumentException("No se deben permitir cantidades negativas o cero en una factura");
            }
        }
        this.quantities = quantities;
        calculateTotal();
    }

    public float getSubtotal() {
        return subtotal;
    }

    public float getVatAmount() {
        return vatAmount;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public float getCashTendered() {
        return cashTendered;
    }

    public void setCashTendered(float cashTendered) {
        if (cashTendered < 0) {
            throw new IllegalArgumentException("El sistema debe rechazar montos de efectivo negativos");
        }
        this.cashTendered = cashTendered;
    }

    public float getChangeDue() {
        return changeDue;
    }

    public void setChangeDue(float changeDue) {
        if (changeDue < 0) {
            throw new IllegalArgumentException("El vuelto entregado no debe ser negativo");
        }
        this.changeDue = changeDue;
    }

    public String getIssuanceChannel() {
        return issuanceChannel;
    }

    public void setIssuanceChannel(String issuanceChannel) {
        if (issuanceChannel == null || issuanceChannel.trim().isEmpty()) {
            throw new IllegalArgumentException("El canal de entrega no puede ser nulo o vacio");
        }
        String upper = issuanceChannel.toUpperCase();
        if (!upper.contains("FISICA") && !upper.contains("ELECTRONICA")) {
            throw new IllegalArgumentException("El sistema debe rechazar canales de entrega invalidos");
        }
        this.issuanceChannel = issuanceChannel;
    }

    @Override
    public String toString() {
        return "Factura Nro: " + invoiceNumber
                + " | Cliente: " + (customer != null ? customer.getName() : "N/A")
                + " | Vendedor: " + (seller != null ? seller.getName() : "N/A")
                + " | Total: $" + totalAmount
                + " | Canal: " + issuanceChannel;
    }
}