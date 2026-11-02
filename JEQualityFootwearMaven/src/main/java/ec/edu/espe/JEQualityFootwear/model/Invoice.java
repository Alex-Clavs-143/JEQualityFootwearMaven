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
    private List<Integer> purchasedItemQuantities;
    private float subtotal;
    private float vatAmount;
    private float totalAmount;
    private float cashTendered;
    private float changeDue;
    private String issuanceChannel;

    public Invoice(String invoiceNumber, Date date, Customer customer, Seller seller,
                   List<Product> purchasedItems, List<Integer> purchasedItemQuantities, String issuanceChannel) {
        setInvoiceNumber(invoiceNumber);
        setDate(date);
        setCustomer(customer);
        setSeller(seller);
        setPurchasedItems(purchasedItems);
        setPurchasedItemQuantities(purchasedItemQuantities);
        setIssuanceChannel(issuanceChannel);
        this.cashTendered = 0.0f;
        this.changeDue = 0.0f;
        calculateTotal();
    }

    public final float calculateTotal() {
        this.subtotal = 0.0f;
        if (this.purchasedItems != null && this.purchasedItemQuantities != null) {
            int itemCountLimit = Math.min(this.purchasedItems.size(), this.purchasedItemQuantities.size());
            for (int index = 0; index < itemCountLimit; index++) {
                Product purchasedProduct = this.purchasedItems.get(index);
                int itemQuantity = this.purchasedItemQuantities.get(index);
                if (purchasedProduct != null && itemQuantity > 0) {
                    this.subtotal += purchasedProduct.getPrice() * itemQuantity;
                }
            }
        }
        this.vatAmount = this.subtotal * VAT_RATE;
        this.totalAmount = this.subtotal + this.vatAmount;
        return this.totalAmount;
    }

    public String getDetails() {
        SimpleDateFormat invoiceDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String formattedEmissionDate = (date != null) ? invoiceDateFormat.format(date) : "N/A";
        StringBuilder invoiceDetailsBuilder = new StringBuilder();
        invoiceDetailsBuilder.append("\n======================================================================\n");
        invoiceDetailsBuilder.append("                    FACTURA - CALZADO JORGE ENRIQUEZ                  \n");
        invoiceDetailsBuilder.append("======================================================================\n");
        invoiceDetailsBuilder.append("Factura Nro : ").append(invoiceNumber).append("\n");
        invoiceDetailsBuilder.append("Fecha       : ").append(formattedEmissionDate).append("\n");
        invoiceDetailsBuilder.append("Canal       : ").append(issuanceChannel).append("\n");
        invoiceDetailsBuilder.append("Cliente     : ").append(customer != null ? customer.getInfo() : "Consumidor Final").append("\n");
        invoiceDetailsBuilder.append("Vendedor    : ").append(seller != null ? seller.getName() + " (Cod: " + seller.getEmployeeCode() + ")" : "N/A").append("\n");
        invoiceDetailsBuilder.append("----------------------------------------------------------------------\n");
        invoiceDetailsBuilder.append("DETALLE DE PRODUCTOS:\n");
        if (purchasedItems != null && purchasedItemQuantities != null) {
            int itemCountLimit = Math.min(purchasedItems.size(), purchasedItemQuantities.size());
            for (int index = 0; index < itemCountLimit; index++) {
                Product purchasedProduct = purchasedItems.get(index);
                int itemQuantity = purchasedItemQuantities.get(index);
                invoiceDetailsBuilder.append("  - ").append(itemQuantity).append("x [").append(purchasedProduct.getProductId()).append("] ")
                  .append(purchasedProduct.getName()).append(" ($").append(purchasedProduct.getPrice()).append(" c/u) -> $")
                  .append(purchasedProduct.getPrice() * itemQuantity).append("\n");
            }
        }
        invoiceDetailsBuilder.append("----------------------------------------------------------------------\n");
        invoiceDetailsBuilder.append("Subtotal sin impuestos : $").append(subtotal).append(" USD\n");
        invoiceDetailsBuilder.append("IVA (15%)              : $").append(vatAmount).append(" USD\n");
        invoiceDetailsBuilder.append("TOTAL A PAGAR          : $").append(totalAmount).append(" USD\n");
        invoiceDetailsBuilder.append("Efectivo recibido      : $").append(cashTendered).append(" USD\n");
        invoiceDetailsBuilder.append("Vuelto entregado       : $").append(changeDue).append(" USD\n");
        invoiceDetailsBuilder.append("======================================================================\n");
        return invoiceDetailsBuilder.toString();
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

    public List<Integer> getPurchasedItemQuantities() {
        return purchasedItemQuantities;
    }

    public void setPurchasedItemQuantities(List<Integer> purchasedItemQuantities) {
        if (purchasedItemQuantities == null || purchasedItemQuantities.isEmpty()) {
            throw new IllegalArgumentException("Las cantidades no pueden ser null");
        }
        for (Integer itemQuantity : purchasedItemQuantities) {
            if (itemQuantity == null || itemQuantity <= 0) {
                throw new IllegalArgumentException("No se deben permitir cantidades negativas o cero en una factura");
            }
        }
        this.purchasedItemQuantities = purchasedItemQuantities;
        calculateTotal();
    }

    public List<Integer> getQuantities() {
        return getPurchasedItemQuantities();
    }

    public void setQuantities(List<Integer> purchasedItemQuantities) {
        setPurchasedItemQuantities(purchasedItemQuantities);
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
        String uppercaseChannel = issuanceChannel.toUpperCase();
        if (!uppercaseChannel.contains("FISICA") && !uppercaseChannel.contains("ELECTRONICA")) {
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