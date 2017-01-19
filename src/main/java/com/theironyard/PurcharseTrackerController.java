package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by temp on 1/19/17.
 */
@Controller
public class PurcharseTrackerController {
    List<Customer> customerList = new ArrayList<>();
    List<Purchase> purchaseList = new ArrayList<>();

    @Autowired
    CustomerRepository customers;

    @Autowired
    PurchaseRepository purchases;

    @PostConstruct
    public void init() throws IOException {
        if (customers.count() == 0 && purchases.count() == 0) {
            String csvCustomersFile = "customers.csv";
            String csvPurchasesFile = "purchases.csv";
            BufferedReader brCustomers;
            BufferedReader brPurchases;
            String lineCustomers;
            String linePurchases;
            String cvsSplitBy = ",";

            brCustomers = new BufferedReader(new FileReader(csvCustomersFile));
            while ((lineCustomers = brCustomers.readLine()) != null) {
                String[] customers = lineCustomers.split(cvsSplitBy);
                Customer customerObject = new Customer();
                customerObject.setName(customers[0]);
                customerObject.setEmail(customers[1]);

                customerList.add(customerObject);
            }

            brPurchases = new BufferedReader(new FileReader(csvPurchasesFile));
            while ((linePurchases = brPurchases.readLine()) != null) {
                String[] purchases = linePurchases.split(cvsSplitBy);
                Purchase purchaseObject = new Purchase();
                purchaseObject.setDate(purchases[1]);
                purchaseObject.setCreditCard(purchases[2]);
                purchaseObject.setCvv(purchases[3]);
                purchaseObject.setCategory(purchases[4]);

                purchaseList.add(purchaseObject);
            }
        }
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("customers", customerList);
        model.addAttribute("purchases", purchaseList);
        return "home";
    }
}
