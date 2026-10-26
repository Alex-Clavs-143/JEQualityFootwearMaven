package ec.edu.espe.JEQualityFootwear.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JsonDataManager {

    private static final String INVENTORY_FILE = "inventory.json";
    private static final String SALES_FILE = "sales_management.json";
    private static final String PROMOS_FILE = "promotions.json";

    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .setDateFormat("dd/MM/yyyy HH:mm:ss")
            .create();

    private static class SalesDatabase {
        int systemId;
        float commission;
        float cashRegisterBalance;
        List<Admin> admins = new ArrayList<>();
        List<Seller> sellers = new ArrayList<>();
        List<Customer> customers = new ArrayList<>();
        List<Invoice> salesRegistry = new ArrayList<>();
    }

    public static void saveData(Inventory inventory, SalesManagement salesManagement, PromotionCalendar promotionCalendar) {
        try (FileWriter writer = new FileWriter(INVENTORY_FILE)) {
            gson.toJson(inventory, writer);
        } catch (IOException e) {
            System.out.println("[ERROR JSON] No se pudo guardar en " + INVENTORY_FILE + ": " + e.getMessage());
        }

        try (FileWriter writer = new FileWriter(PROMOS_FILE)) {
            gson.toJson(promotionCalendar, writer);
        } catch (IOException e) {
            System.out.println("[ERROR JSON] No se pudo guardar en " + PROMOS_FILE + ": " + e.getMessage());
        }

        SalesDatabase salesDb = new SalesDatabase();
        salesDb.systemId = salesManagement.getSystemId();
        salesDb.commission = salesManagement.getCommission();
        salesDb.cashRegisterBalance = salesManagement.getCashRegisterBalance();

        for (StaffMember staff : salesManagement.getStaffList()) {
            if (staff instanceof Admin) {
                salesDb.admins.add((Admin) staff);
            } else if (staff instanceof Seller) {
                salesDb.sellers.add((Seller) staff);
            }
        }

        salesDb.customers = salesManagement.getCustomerDirectory();
        salesDb.salesRegistry = salesManagement.getSalesRegistry();

        try (FileWriter writer = new FileWriter(SALES_FILE)) {
            gson.toJson(salesDb, writer);
        } catch (IOException e) {
            System.out.println("[ERROR JSON] No se pudo guardar en " + SALES_FILE + ": " + e.getMessage());
        }
    }

    public static boolean loadData(Inventory inventory, SalesManagement salesManagement, PromotionCalendar promotionCalendar) {
        boolean loadedAny = false;

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
                System.out.println("[ERROR JSON] Fallo al leer el archivo " + INVENTORY_FILE + ": " + e.getMessage());
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
                System.out.println("[ERROR JSON] Fallo al leer el archivo " + PROMOS_FILE + ": " + e.getMessage());
            }
        }

        File salesFile = new File(SALES_FILE);
        if (salesFile.exists()) {
            try (FileReader reader = new FileReader(salesFile)) {
                SalesDatabase db = gson.fromJson(reader, SalesDatabase.class);
                if (db != null) {
                    salesManagement.setSystemId(db.systemId);
                    salesManagement.setCommission(db.commission);
                    salesManagement.setCashRegisterBalance(db.cashRegisterBalance);

                    salesManagement.getStaffList().clear();
                    if (db.admins != null) {
                        salesManagement.getStaffList().addAll(db.admins);
                    }
                    if (db.sellers != null) {
                        salesManagement.getStaffList().addAll(db.sellers);
                    }

                    salesManagement.getCustomerDirectory().clear();
                    if (db.customers != null) {
                        for (Customer c : db.customers) {
                            if (c.getPurchaseHistory() == null) {
                                c.setPurchaseHistory(new ArrayList<>());
                            }
                            salesManagement.getCustomerDirectory().add(c);
                        }
                    }

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
                System.out.println("[ERROR JSON] Fallo al leer el archivo " + SALES_FILE + ": " + e.getMessage());
            }
        }

        return loadedAny;
    }
}