package ec.edu.espe.JEQualityFootwear.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JsonDataManager {

    private static final String ADMINS_FILE = "admins.json";
    private static final String SELLERS_FILE = "sellers.json";
    private static final String CUSTOMERS_FILE = "customers.json";
    private static final String INVENTORY_FILE = "inventory.json";
    private static final String INVOICES_FILE = "invoices.json";
    private static final String PROMOS_FILE = "promotions.json";

    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .setDateFormat("dd/MM/yyyy HH:mm:ss")
            .create();

    private static class InvoicesData {
        int systemId;
        float commission;
        float cashRegisterBalance;
        List<Invoice> salesRegistry = new ArrayList<>();
    }

    public static void saveData(Inventory inventory, SalesManagement salesManagement, PromotionCalendar promotionCalendar) {
        List<Admin> adminList = new ArrayList<>();
        List<Seller> sellerList = new ArrayList<>();
        
        for (StaffMember staffMember : salesManagement.getStaffList()) {
            if (staffMember instanceof Admin) {
                adminList.add((Admin) staffMember);
            } else if (staffMember instanceof Seller) {
                sellerList.add((Seller) staffMember);
            }
        }

        saveToFile(ADMINS_FILE, adminList);
        saveToFile(SELLERS_FILE, sellerList);
        saveToFile(CUSTOMERS_FILE, salesManagement.getCustomerDirectory());
        saveToFile(INVENTORY_FILE, inventory);
        saveToFile(PROMOS_FILE, promotionCalendar);

        InvoicesData invoicesData = new InvoicesData();
        invoicesData.systemId = salesManagement.getSystemId();
        invoicesData.commission = salesManagement.getCommission();
        invoicesData.cashRegisterBalance = salesManagement.getCashRegisterBalance();
        invoicesData.salesRegistry = salesManagement.getSalesRegistry();

        saveToFile(INVOICES_FILE, invoicesData);
    }

    private static void saveToFile(String filePath, Object dataToSerialize) {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(dataToSerialize, writer);
        } catch (IOException ioException) {
            System.out.println("[ERROR JSON] No se pudo guardar en " + filePath + ": " + ioException.getMessage());
        }
    }

    public static boolean loadData(Inventory inventory, SalesManagement salesManagement, PromotionCalendar promotionCalendar) {
        boolean loadedAnyFile = false;

        salesManagement.getStaffList().clear();

        File adminsFile = new File(ADMINS_FILE);
        if (adminsFile.exists()) {
            try (FileReader reader = new FileReader(adminsFile)) {
                Type adminListType = new TypeToken<ArrayList<Admin>>(){}.getType();
                List<Admin> loadedAdmins = gson.fromJson(reader, adminListType);
                if (loadedAdmins != null) {
                    salesManagement.getStaffList().addAll(loadedAdmins);
                    loadedAnyFile = true;
                }
            } catch (IOException ioException) {
                System.out.println("[ERROR JSON] Fallo al leer " + ADMINS_FILE);
            }
        }

        File sellersFile = new File(SELLERS_FILE);
        if (sellersFile.exists()) {
            try (FileReader reader = new FileReader(sellersFile)) {
                Type sellerListType = new TypeToken<ArrayList<Seller>>(){}.getType();
                List<Seller> loadedSellers = gson.fromJson(reader, sellerListType);
                if (loadedSellers != null) {
                    salesManagement.getStaffList().addAll(loadedSellers);
                    loadedAnyFile = true;
                }
            } catch (IOException ioException) {
                System.out.println("[ERROR JSON] Fallo al leer " + SELLERS_FILE);
            }
        }

        File customersFile = new File(CUSTOMERS_FILE);
        if (customersFile.exists()) {
            try (FileReader reader = new FileReader(customersFile)) {
                Type customerListType = new TypeToken<ArrayList<Customer>>(){}.getType();
                List<Customer> loadedCustomers = gson.fromJson(reader, customerListType);
                salesManagement.getCustomerDirectory().clear();
                if (loadedCustomers != null) {
                    for (Customer loadedCustomer : loadedCustomers) {
                        if (loadedCustomer.getPurchaseHistory() == null) {
                            loadedCustomer.setPurchaseHistory(new ArrayList<>());
                        }
                        salesManagement.getCustomerDirectory().add(loadedCustomer);
                    }
                    loadedAnyFile = true;
                }
            } catch (IOException ioException) {
                System.out.println("[ERROR JSON] Fallo al leer " + CUSTOMERS_FILE);
            }
        }

        File inventoryFile = new File(INVENTORY_FILE);
        if (inventoryFile.exists()) {
            try (FileReader reader = new FileReader(inventoryFile)) {
                Inventory loadedInventory = gson.fromJson(reader, Inventory.class);
                if (loadedInventory != null) {
                    inventory.setProducts(loadedInventory.getProducts() != null ? loadedInventory.getProducts() : new ArrayList<>());
                    if (loadedInventory.getLastAuditDate() != null) {
                        inventory.setLastAuditDate(loadedInventory.getLastAuditDate());
                    } else {
                        inventory.setLastAuditDate(new Date());
                    }
                    loadedAnyFile = true;
                }
            } catch (IOException ioException) {
                System.out.println("[ERROR JSON] Fallo al leer " + INVENTORY_FILE);
            }
        }

        File invoicesFile = new File(INVOICES_FILE);
        if (invoicesFile.exists()) {
            try (FileReader reader = new FileReader(invoicesFile)) {
                InvoicesData loadedInvoicesData = gson.fromJson(reader, InvoicesData.class);
                if (loadedInvoicesData != null) {
                    salesManagement.setSystemId(loadedInvoicesData.systemId);
                    if (loadedInvoicesData.commission >= 0) {
                        salesManagement.setCommission(loadedInvoicesData.commission);
                    }
                    if (loadedInvoicesData.cashRegisterBalance >= 0) {
                        salesManagement.setCashRegisterBalance(loadedInvoicesData.cashRegisterBalance);
                    }
                    salesManagement.getSalesRegistry().clear();
                    if (loadedInvoicesData.salesRegistry != null) {
                        for (Invoice registeredInvoice : loadedInvoicesData.salesRegistry) {
                            salesManagement.getSalesRegistry().add(registeredInvoice);
                            if (registeredInvoice.getCustomer() != null && registeredInvoice.getCustomer().getId() != null) {
                                Customer registeredCustomer = salesManagement.findCustomerById(registeredInvoice.getCustomer().getId());
                                if (registeredCustomer != null) {
                                    registeredCustomer.addBillToHistory(registeredInvoice);
                                }
                            }
                        }
                    }
                    loadedAnyFile = true;
                }
            } catch (IOException ioException) {
                System.out.println("[ERROR JSON] Fallo al leer " + INVOICES_FILE);
            }
        }

        File promotionsFile = new File(PROMOS_FILE);
        if (promotionsFile.exists()) {
            try (FileReader reader = new FileReader(promotionsFile)) {
                PromotionCalendar loadedPromotionCalendar = gson.fromJson(reader, PromotionCalendar.class);
                if (loadedPromotionCalendar != null) {
                    if (loadedPromotionCalendar.getBranchName() != null && !loadedPromotionCalendar.getBranchName().trim().isEmpty()) {
                        promotionCalendar.setBranchName(loadedPromotionCalendar.getBranchName());
                    }
                    promotionCalendar.setBranchAddress(loadedPromotionCalendar.getBranchAddress());
                    if (loadedPromotionCalendar.getContactPhone() != null && loadedPromotionCalendar.getContactPhone().trim().matches("\\d+")) {
                        promotionCalendar.setContactPhone(loadedPromotionCalendar.getContactPhone());
                    }
                    if (loadedPromotionCalendar.getOpeningHour() >= 0 && loadedPromotionCalendar.getOpeningHour() <= 24) {
                        promotionCalendar.setOpeningHour(loadedPromotionCalendar.getOpeningHour());
                    }
                    if (loadedPromotionCalendar.getClosingHour() >= 0 && loadedPromotionCalendar.getClosingHour() <= 24) {
                        promotionCalendar.setClosingHour(loadedPromotionCalendar.getClosingHour());
                    }
                    promotionCalendar.setScheduledPromotions(
                            loadedPromotionCalendar.getScheduledPromotions() != null ? loadedPromotionCalendar.getScheduledPromotions() : new ArrayList<>()
                    );
                    loadedAnyFile = true;
                }
            } catch (IOException ioException) {
                System.out.println("[ERROR JSON] Fallo al leer " + PROMOS_FILE);
            }
        }

        return loadedAnyFile;
    }

    private static PrintWriter createCsvWriter(String fileName) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        fileOutputStream.write(0xEF);
        fileOutputStream.write(0xBB);
        fileOutputStream.write(0xBF);
        return new PrintWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
    }

    private static String escapeCsv(String rawValue) {
        if (rawValue == null) {
            return "";
        }
        String cleanedValue = rawValue.replace("\"", "\"\"");
        if (cleanedValue.contains(";") || cleanedValue.contains("\"") || cleanedValue.contains("\n")) {
            return "\"" + cleanedValue + "\"";
        }
        return cleanedValue;
    }

    public static boolean exportAdminsToCsv(SalesManagement salesManagement) {
        String fileName = "reporte_admins.csv";
        try (PrintWriter writer = createCsvWriter(fileName)) {
            writer.println("Codigo Empleado;Cedula/RUC;Nombre Completo;Telefono;Correo Electronico;Usuario;Sueldo Base ($ USD)");
            for (StaffMember staffMember : salesManagement.getStaffList()) {
                if (staffMember instanceof Admin) {
                    writer.println(staffMember.getEmployeeCode() + ";"
                            + escapeCsv(staffMember.getId()) + ";"
                            + escapeCsv(staffMember.getName()) + ";"
                            + escapeCsv(staffMember.getPhoneNumber()) + ";"
                            + escapeCsv(staffMember.getEmail()) + ";"
                            + escapeCsv(staffMember.getUsername()) + ";"
                            + String.format("%.2f", staffMember.getSalary()));
                }
            }
            System.out.println("[OK] Reporte generado: " + fileName);
            return true;
        } catch (IOException ioException) {
            System.out.println("[ERROR CSV] No se pudo generar " + fileName + ": " + ioException.getMessage());
            return false;
        }
    }

    public static boolean exportSellersToCsv(SalesManagement salesManagement) {
        String fileName = "reporte_sellers.csv";
        try (PrintWriter writer = createCsvWriter(fileName)) {
            writer.println("Codigo Empleado;Cedula/RUC;Nombre Completo;Telefono;Correo Electronico;Usuario;Ventas Cerradas;Sueldo Base ($ USD);Comisiones ($ USD);Sueldo Total ($ USD)");
            for (StaffMember staffMember : salesManagement.getStaffList()) {
                if (staffMember instanceof Seller) {
                    Seller seller = (Seller) staffMember;
                    writer.println(seller.getEmployeeCode() + ";"
                            + escapeCsv(seller.getId()) + ";"
                            + escapeCsv(seller.getName()) + ";"
                            + escapeCsv(seller.getPhoneNumber()) + ";"
                            + escapeCsv(seller.getEmail()) + ";"
                            + escapeCsv(seller.getUsername()) + ";"
                            + seller.getCompletedSalesCount() + ";"
                            + String.format("%.2f", seller.getSalary()) + ";"
                            + String.format("%.2f", seller.getAccumulatedCommission()) + ";"
                            + String.format("%.2f", seller.calculateSalary()));
                }
            }
            System.out.println("[OK] Reporte generado: " + fileName);
            return true;
        } catch (IOException ioException) {
            System.out.println("[ERROR CSV] No se pudo generar " + fileName + ": " + ioException.getMessage());
            return false;
        }
    }

    public static boolean exportCustomersToCsv(SalesManagement salesManagement) {
        String fileName = "reporte_customers.csv";
        try (PrintWriter writer = createCsvWriter(fileName)) {
            writer.println("Codigo Cliente;Cedula/RUC;Nombre Completo;Telefono;Correo Electronico;Usuario;Compras Realizadas");
            for (Customer customer : salesManagement.getCustomerDirectory()) {
                int completedPurchases = customer.getPurchaseHistory() != null ? customer.getPurchaseHistory().size() : 0;
                writer.println(customer.getCustomerCode() + ";"
                        + escapeCsv(customer.getId()) + ";"
                        + escapeCsv(customer.getName()) + ";"
                        + escapeCsv(customer.getPhoneNumber()) + ";"
                        + escapeCsv(customer.getEmail()) + ";"
                        + escapeCsv(customer.getUsername()) + ";"
                        + completedPurchases);
            }
            System.out.println("[OK] Reporte generado: " + fileName);
            return true;
        } catch (IOException ioException) {
            System.out.println("[ERROR CSV] No se pudo generar " + fileName + ": " + ioException.getMessage());
            return false;
        }
    }

    public static boolean exportInventoryToCsv(Inventory inventory) {
        String fileName = "reporte_inventory.csv";
        try (PrintWriter writer = createCsvWriter(fileName)) {
            writer.println("Codigo Producto;Modelo / Nombre;Seccion;Subcategoria;Talla;Color;Precio Unitario ($ USD);Stock Actual;Unidades Vendidas;Ingresos Generados ($ USD)");
            for (Product product : inventory.getProducts()) {
                String genderSection = product.getCategory() != null ? product.getCategory().getGender() : "Sin Seccion";
                String subCategoryName = product.getCategory() != null ? product.getCategory().getCategoryName() : "Sin Categoria";
                float generatedRevenue = product.getUnitsSold() * product.getPrice();
                writer.println(escapeCsv(product.getProductId()) + ";"
                        + escapeCsv(product.getName()) + ";"
                        + escapeCsv(genderSection) + ";"
                        + escapeCsv(subCategoryName) + ";"
                        + product.getSize() + ";"
                        + escapeCsv(product.getColor()) + ";"
                        + String.format("%.2f", product.getPrice()) + ";"
                        + product.getCurrentStock() + ";"
                        + product.getUnitsSold() + ";"
                        + String.format("%.2f", generatedRevenue));
            }
            System.out.println("[OK] Reporte generado: " + fileName);
            return true;
        } catch (IOException ioException) {
            System.out.println("[ERROR CSV] No se pudo generar " + fileName + ": " + ioException.getMessage());
            return false;
        }
    }

    public static boolean exportInvoicesToCsv(SalesManagement salesManagement) {
        String fileName = "reporte_invoices.csv";
        SimpleDateFormat invoiceDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try (PrintWriter writer = createCsvWriter(fileName)) {
            writer.println("Nro Factura;Fecha Emision;Cedula/RUC Cliente;Nombre Cliente;Cod Vendedor;Nombre Vendedor;Detalle Productos;Subtotal ($ USD);IVA 15% ($ USD);Total Factura ($ USD);Efectivo Recibido ($ USD);Vuelto ($ USD);Canal de Entrega");
            for (Invoice registeredInvoice : salesManagement.getSalesRegistry()) {
                StringBuilder purchasedItemsSummary = new StringBuilder();
                if (registeredInvoice.getPurchasedItems() != null && registeredInvoice.getPurchasedItemQuantities() != null) {
                    for (int index = 0; index < registeredInvoice.getPurchasedItems().size(); index++) {
                        Product purchasedProduct = registeredInvoice.getPurchasedItems().get(index);
                        int itemQuantity = registeredInvoice.getPurchasedItemQuantities().get(index);
                        if (index > 0) {
                            purchasedItemsSummary.append(" | ");
                        }
                        purchasedItemsSummary.append(itemQuantity).append("x ").append(purchasedProduct.getName()).append(" (").append(purchasedProduct.getProductId()).append(")");
                    }
                }
                String formattedDate = registeredInvoice.getDate() != null ? invoiceDateFormat.format(registeredInvoice.getDate()) : "";
                String customerId = registeredInvoice.getCustomer() != null ? registeredInvoice.getCustomer().getId() : "";
                String customerName = registeredInvoice.getCustomer() != null ? registeredInvoice.getCustomer().getName() : "";
                int sellerEmployeeCode = registeredInvoice.getSeller() != null ? registeredInvoice.getSeller().getEmployeeCode() : 0;
                String sellerName = registeredInvoice.getSeller() != null ? registeredInvoice.getSeller().getName() : "";

                writer.println(escapeCsv(registeredInvoice.getInvoiceNumber()) + ";"
                        + escapeCsv(formattedDate) + ";"
                        + escapeCsv(customerId) + ";"
                        + escapeCsv(customerName) + ";"
                        + sellerEmployeeCode + ";"
                        + escapeCsv(sellerName) + ";"
                        + escapeCsv(purchasedItemsSummary.toString()) + ";"
                        + String.format("%.2f", registeredInvoice.getSubtotal()) + ";"
                        + String.format("%.2f", registeredInvoice.getVatAmount()) + ";"
                        + String.format("%.2f", registeredInvoice.getTotalAmount()) + ";"
                        + String.format("%.2f", registeredInvoice.getCashTendered()) + ";"
                        + String.format("%.2f", registeredInvoice.getChangeDue()) + ";"
                        + escapeCsv(registeredInvoice.getIssuanceChannel()));
            }
            System.out.println("[OK] Reporte generado: " + fileName);
            return true;
        } catch (IOException ioException) {
            System.out.println("[ERROR CSV] No se pudo generar " + fileName + ": " + ioException.getMessage());
            return false;
        }
    }

    public static boolean exportPromotionsToCsv(PromotionCalendar promotionCalendar) {
        String fileName = "reporte_promotions.csv";
        try (PrintWriter writer = createCsvWriter(fileName)) {
            writer.println("Nro;Sucursal;Direccion;Telefono;Horario;Detalle de Promocion Programada");
            String storeSchedule = promotionCalendar.getOpeningHour() + ":00 a " + promotionCalendar.getClosingHour() + ":00";
            List<String> scheduledPromotionsList = promotionCalendar.getScheduledPromotions();
            if (scheduledPromotionsList == null || scheduledPromotionsList.isEmpty()) {
                writer.println("1;"
                        + escapeCsv(promotionCalendar.getBranchName()) + ";"
                        + escapeCsv(promotionCalendar.getBranchAddress()) + ";"
                        + escapeCsv(promotionCalendar.getContactPhone()) + ";"
                        + escapeCsv(storeSchedule) + ";"
                        + "Sin promociones activas");
            } else {
                for (int index = 0; index < scheduledPromotionsList.size(); index++) {
                    writer.println((index + 1) + ";"
                            + escapeCsv(promotionCalendar.getBranchName()) + ";"
                            + escapeCsv(promotionCalendar.getBranchAddress()) + ";"
                            + escapeCsv(promotionCalendar.getContactPhone()) + ";"
                            + escapeCsv(storeSchedule) + ";"
                            + escapeCsv(scheduledPromotionsList.get(index)));
                }
            }
            System.out.println("[OK] Reporte generado: " + fileName);
            return true;
        } catch (IOException ioException) {
            System.out.println("[ERROR CSV] No se pudo generar " + fileName + ": " + ioException.getMessage());
            return false;
        }
    }

    public static void exportAllToCsv(Inventory inventory, SalesManagement salesManagement, PromotionCalendar promotionCalendar) {
        exportAdminsToCsv(salesManagement);
        exportSellersToCsv(salesManagement);
        exportCustomersToCsv(salesManagement);
        exportInventoryToCsv(inventory);
        exportInvoicesToCsv(salesManagement);
        exportPromotionsToCsv(promotionCalendar);
    }
}