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
        List<Admin> admins = new ArrayList<>();
        List<Seller> sellers = new ArrayList<>();

        for (StaffMember staff : salesManagement.getStaffList()) {
            if (staff instanceof Admin) {
                admins.add((Admin) staff);
            } else if (staff instanceof Seller) {
                sellers.add((Seller) staff);
            }
        }

        saveToFile(ADMINS_FILE, admins);
        saveToFile(SELLERS_FILE, sellers);
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

    private static void saveToFile(String filePath, Object data) {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            System.out.println("[ERROR JSON] No se pudo guardar en " + filePath + ": " + e.getMessage());
        }
    }

    public static boolean loadData(Inventory inventory, SalesManagement salesManagement, PromotionCalendar promotionCalendar) {
        boolean loadedAny = false;

        salesManagement.getStaffList().clear();

        File adminsFile = new File(ADMINS_FILE);
        if (adminsFile.exists()) {
            try (FileReader reader = new FileReader(adminsFile)) {
                Type listType = new TypeToken<ArrayList<Admin>>(){}.getType();
                List<Admin> admins = gson.fromJson(reader, listType);
                if (admins != null) {
                    salesManagement.getStaffList().addAll(admins);
                    loadedAny = true;
                }
            } catch (IOException e) {
                System.out.println("[ERROR JSON] Fallo al leer " + ADMINS_FILE);
            }
        }

        File sellersFile = new File(SELLERS_FILE);
        if (sellersFile.exists()) {
            try (FileReader reader = new FileReader(sellersFile)) {
                Type listType = new TypeToken<ArrayList<Seller>>(){}.getType();
                List<Seller> sellers = gson.fromJson(reader, listType);
                if (sellers != null) {
                    salesManagement.getStaffList().addAll(sellers);
                    loadedAny = true;
                }
            } catch (IOException e) {
                System.out.println("[ERROR JSON] Fallo al leer " + SELLERS_FILE);
            }
        }

        File customersFile = new File(CUSTOMERS_FILE);
        if (customersFile.exists()) {
            try (FileReader reader = new FileReader(customersFile)) {
                Type listType = new TypeToken<ArrayList<Customer>>(){}.getType();
                List<Customer> customers = gson.fromJson(reader, listType);
                salesManagement.getCustomerDirectory().clear();
                if (customers != null) {
                    for (Customer c : customers) {
                        if (c.getPurchaseHistory() == null) {
                            c.setPurchaseHistory(new ArrayList<>());
                        }
                        salesManagement.getCustomerDirectory().add(c);
                    }
                    loadedAny = true;
                }
            } catch (IOException e) {
                System.out.println("[ERROR JSON] Fallo al leer " + CUSTOMERS_FILE);
            }
        }

        File invFile = new File(INVENTORY_FILE);
        if (invFile.exists()) {
            try (FileReader reader = new FileReader(invFile)) {
                Inventory loadedInv = gson.fromJson(reader, Inventory.class);
                if (loadedInv != null) {
                    inventory.setProducts(loadedInv.getProducts() != null ? loadedInv.getProducts() : new ArrayList<>());
                    if (loadedInv.getLastAuditDate() != null) {
                        inventory.setLastAuditDate(loadedInv.getLastAuditDate());
                    }
                    loadedAny = true;
                }
            } catch (IOException e) {
                System.out.println("[ERROR JSON] Fallo al leer " + INVENTORY_FILE);
            }
        }

        File invoicesFile = new File(INVOICES_FILE);
        if (invoicesFile.exists()) {
            try (FileReader reader = new FileReader(invoicesFile)) {
                InvoicesData db = gson.fromJson(reader, InvoicesData.class);
                if (db != null) {
                    salesManagement.setSystemId(db.systemId);
                    salesManagement.setCommission(db.commission);
                    salesManagement.setCashRegisterBalance(db.cashRegisterBalance);
                    salesManagement.getSalesRegistry().clear();
                    if (db.salesRegistry != null) {
                        for (Invoice inv : db.salesRegistry) {
                            salesManagement.getSalesRegistry().add(inv);
                            if (inv.getCustomer() != null) {
                                Customer registeredCustomer = salesManagement.findCustomerById(inv.getCustomer().getId());
                                if (registeredCustomer != null) {
                                    registeredCustomer.getInvoice(inv);
                                }
                            }
                        }
                    }
                    loadedAny = true;
                }
            } catch (IOException e) {
                System.out.println("[ERROR JSON] Fallo al leer " + INVOICES_FILE);
            }
        }

        File promoFile = new File(PROMOS_FILE);
        if (promoFile.exists()) {
            try (FileReader reader = new FileReader(promoFile)) {
                PromotionCalendar loadedPromo = gson.fromJson(reader, PromotionCalendar.class);
                if (loadedPromo != null) {
                    promotionCalendar.setBranchName(loadedPromo.getBranchName());
                    promotionCalendar.setBranchAddress(loadedPromo.getBranchAddress());
                    promotionCalendar.setContactPhone(loadedPromo.getContactPhone());
                    promotionCalendar.setOpeningHour(loadedPromo.getOpeningHour());
                    promotionCalendar.setClosingHour(loadedPromo.getClosingHour());
                    promotionCalendar.setScheduledPromotions(
                            loadedPromo.getScheduledPromotions() != null ? loadedPromo.getScheduledPromotions() : new ArrayList<>()
                    );
                    loadedAny = true;
                }
            } catch (IOException e) {
                System.out.println("[ERROR JSON] Fallo al leer " + PROMOS_FILE);
            }
        }

        return loadedAny;
    }


    private static PrintWriter createCsvWriter(String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(0xEF);
        fos.write(0xBB);
        fos.write(0xBF);
        return new PrintWriter(new OutputStreamWriter(fos, StandardCharsets.UTF_8));
    }

    private static String escapeCsv(String value) {
        if (value == null) {
            return "";
        }
        String cleaned = value.replace("\"", "\"\"");
        if (cleaned.contains(";") || cleaned.contains("\"") || cleaned.contains("\n")) {
            return "\"" + cleaned + "\"";
        }
        return cleaned;
    }

    public static boolean exportAdminsToCsv(SalesManagement salesManagement) {
        String fileName = "reporte_admins.csv";
        try (PrintWriter writer = createCsvWriter(fileName)) {
            writer.println("Codigo Empleado;Cedula/RUC;Nombre Completo;Telefono;Correo Electronico;Usuario;Sueldo Base ($ USD)");
            for (StaffMember staff : salesManagement.getStaffList()) {
                if (staff instanceof Admin) {
                    writer.println(staff.getEmployeeCode() + ";"
                            + escapeCsv(staff.getId()) + ";"
                            + escapeCsv(staff.getName()) + ";"
                            + escapeCsv(staff.getPhoneNumber()) + ";"
                            + escapeCsv(staff.getEmail()) + ";"
                            + escapeCsv(staff.getUsername()) + ";"
                            + String.format("%.2f", staff.getSalary()));
                }
            }
            System.out.println("[OK] Reporte generado: " + fileName);
            return true;
        } catch (IOException e) {
            System.out.println("[ERROR CSV] No se pudo generar " + fileName + ": " + e.getMessage());
            return false;
        }
    }

    public static boolean exportSellersToCsv(SalesManagement salesManagement) {
        String fileName = "reporte_sellers.csv";
        try (PrintWriter writer = createCsvWriter(fileName)) {
            writer.println("Codigo Empleado;Cedula/RUC;Nombre Completo;Telefono;Correo Electronico;Usuario;Ventas Cerradas;Sueldo Base ($ USD);Comisiones ($ USD);Sueldo Total ($ USD)");
            for (StaffMember staff : salesManagement.getStaffList()) {
                if (staff instanceof Seller) {
                    Seller seller = (Seller) staff;
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
        } catch (IOException e) {
            System.out.println("[ERROR CSV] No se pudo generar " + fileName + ": " + e.getMessage());
            return false;
        }
    }

    public static boolean exportCustomersToCsv(SalesManagement salesManagement) {
        String fileName = "reporte_customers.csv";
        try (PrintWriter writer = createCsvWriter(fileName)) {
            writer.println("Codigo Cliente;Cedula/RUC;Nombre Completo;Telefono;Correo Electronico;Usuario;Compras Realizadas");
            for (Customer customer : salesManagement.getCustomerDirectory()) {
                int purchases = customer.getPurchaseHistory() != null ? customer.getPurchaseHistory().size() : 0;
                writer.println(customer.getCustomerCode() + ";"
                        + escapeCsv(customer.getId()) + ";"
                        + escapeCsv(customer.getName()) + ";"
                        + escapeCsv(customer.getPhoneNumber()) + ";"
                        + escapeCsv(customer.getEmail()) + ";"
                        + escapeCsv(customer.getUsername()) + ";"
                        + purchases);
            }
            System.out.println("[OK] Reporte generado: " + fileName);
            return true;
        } catch (IOException e) {
            System.out.println("[ERROR CSV] No se pudo generar " + fileName + ": " + e.getMessage());
            return false;
        }
    }

    public static boolean exportInventoryToCsv(Inventory inventory) {
        String fileName = "reporte_inventory.csv";
        try (PrintWriter writer = createCsvWriter(fileName)) {
            writer.println("Codigo Producto;Modelo / Nombre;Seccion;Subcategoria;Talla;Color;Precio Unitario ($ USD);Stock Actual;Unidades Vendidas;Ingresos Generados ($ USD)");
            for (Product product : inventory.getProducts()) {
                String section = product.getCategory() != null ? product.getCategory().getGender() : "Sin Seccion";
                String subCat = product.getCategory() != null ? product.getCategory().getCategoryName() : "Sin Categoria";
                float revenue = product.getUnitsSold() * product.getPrice();
                writer.println(escapeCsv(product.getProductId()) + ";"
                        + escapeCsv(product.getName()) + ";"
                        + escapeCsv(section) + ";"
                        + escapeCsv(subCat) + ";"
                        + product.getSize() + ";"
                        + escapeCsv(product.getColor()) + ";"
                        + String.format("%.2f", product.getPrice()) + ";"
                        + product.getCurrentStock() + ";"
                        + product.getUnitsSold() + ";"
                        + String.format("%.2f", revenue));
            }
            System.out.println("[OK] Reporte generado: " + fileName);
            return true;
        } catch (IOException e) {
            System.out.println("[ERROR CSV] No se pudo generar " + fileName + ": " + e.getMessage());
            return false;
        }
    }

    public static boolean exportInvoicesToCsv(SalesManagement salesManagement) {
        String fileName = "reporte_invoices.csv";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try (PrintWriter writer = createCsvWriter(fileName)) {
            writer.println("Nro Factura;Fecha Emision;Cedula/RUC Cliente;Nombre Cliente;Cod Vendedor;Nombre Vendedor;Detalle Productos;Subtotal ($ USD);IVA 15% ($ USD);Total Factura ($ USD);Efectivo Recibido ($ USD);Vuelto ($ USD);Canal de Entrega");
            for (Invoice inv : salesManagement.getSalesRegistry()) {
                StringBuilder itemsSummary = new StringBuilder();
                if (inv.getPurchasedItems() != null && inv.getQuantities() != null) {
                    for (int i = 0; i < inv.getPurchasedItems().size(); i++) {
                        Product p = inv.getPurchasedItems().get(i);
                        int q = inv.getQuantities().get(i);
                        if (i > 0) {
                            itemsSummary.append(" | ");
                        }
                        itemsSummary.append(q).append("x ").append(p.getName()).append(" (").append(p.getProductId()).append(")");
                    }
                }
                String dateStr = inv.getDate() != null ? dateFormat.format(inv.getDate()) : "";
                String custId = inv.getCustomer() != null ? inv.getCustomer().getId() : "";
                String custName = inv.getCustomer() != null ? inv.getCustomer().getName() : "";
                int sellerCode = inv.getSeller() != null ? inv.getSeller().getEmployeeCode() : 0;
                String sellerName = inv.getSeller() != null ? inv.getSeller().getName() : "";

                writer.println(escapeCsv(inv.getInvoiceNumber()) + ";"
                        + escapeCsv(dateStr) + ";"
                        + escapeCsv(custId) + ";"
                        + escapeCsv(custName) + ";"
                        + sellerCode + ";"
                        + escapeCsv(sellerName) + ";"
                        + escapeCsv(itemsSummary.toString()) + ";"
                        + String.format("%.2f", inv.getSubtotal()) + ";"
                        + String.format("%.2f", inv.getVatAmount()) + ";"
                        + String.format("%.2f", inv.getTotalAmount()) + ";"
                        + String.format("%.2f", inv.getCashTendered()) + ";"
                        + String.format("%.2f", inv.getChangeDue()) + ";"
                        + escapeCsv(inv.getIssuanceChannel()));
            }
            System.out.println("[OK] Reporte generado: " + fileName);
            return true;
        } catch (IOException e) {
            System.out.println("[ERROR CSV] No se pudo generar " + fileName + ": " + e.getMessage());
            return false;
        }
    }

    public static boolean exportPromotionsToCsv(PromotionCalendar promotionCalendar) {
        String fileName = "reporte_promotions.csv";
        try (PrintWriter writer = createCsvWriter(fileName)) {
            writer.println("Nro;Sucursal;Direccion;Telefono;Horario;Detalle de Promocion Programada");
            String schedule = promotionCalendar.getOpeningHour() + ":00 a " + promotionCalendar.getClosingHour() + ":00";
            List<String> promos = promotionCalendar.getScheduledPromotions();
            if (promos == null || promos.isEmpty()) {
                writer.println("1;"
                        + escapeCsv(promotionCalendar.getBranchName()) + ";"
                        + escapeCsv(promotionCalendar.getBranchAddress()) + ";"
                        + escapeCsv(promotionCalendar.getContactPhone()) + ";"
                        + escapeCsv(schedule) + ";"
                        + "Sin promociones activas");
            } else {
                for (int i = 0; i < promos.size(); i++) {
                    writer.println((i + 1) + ";"
                            + escapeCsv(promotionCalendar.getBranchName()) + ";"
                            + escapeCsv(promotionCalendar.getBranchAddress()) + ";"
                            + escapeCsv(promotionCalendar.getContactPhone()) + ";"
                            + escapeCsv(schedule) + ";"
                            + escapeCsv(promos.get(i)));
                }
            }
            System.out.println("[OK] Reporte generado: " + fileName);
            return true;
        } catch (IOException e) {
            System.out.println("[ERROR CSV] No se pudo generar " + fileName + ": " + e.getMessage());
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