package ec.edu.espe.JEQualityFootwear.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
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
}