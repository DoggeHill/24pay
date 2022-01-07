package hyll.patrik.pay.controller.services;

import hyll.patrik.pay.model.PaymentResolution;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

/**
 * Service for data management, connected with rest endpoints
 * @author Patrik
 */
public interface BankControllerService {
    String createRequest(PaymentResolution paymentResolution, Model model);
    String processRequest(Model model, String MsTxnId, String Amount, String CurrCode,
                          String Result, String Sign);
}
