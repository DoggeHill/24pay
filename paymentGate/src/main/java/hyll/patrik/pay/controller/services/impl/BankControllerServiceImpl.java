package hyll.patrik.pay.controller.services.impl;

import hyll.patrik.pay.controller.services.BankControllerService;
import hyll.patrik.pay.helpers.SignGenerator;
import hyll.patrik.pay.helpers.TimeStamp;
import hyll.patrik.pay.model.PaymentResolution;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 * REST API data processing implementation
 * @author Patrik
 */
@Service
public class BankControllerServiceImpl implements BankControllerService {

    // Server side variables
    @Value("${mid}")
    private String mid;

    @Value("${key}")
    private String key;

    @Value("${eshopId}")
    private String eshopId;

    @Value("${rurl}")
    private String rurl;

    // IV vector
    private String iv;

    /**
     * Generate sign and add server stored fields to the model.
     * After final model is generated it immediately send post request to the 24-pay server
     * through HTML form post method
     *
     * @author Patrik
     * @param paymentResolution POJO
     * @param model Model object
     * @return model
     */
    @Override
    public String createRequest(PaymentResolution paymentResolution, Model model) {

        // Message object
        // MESSAGE = Mid ⊕ Amount ⊕ CurrencyAlphaCode ⊕ MsTxnId ⊕ FirstName ⊕ FamilyName ⊕ Timestamp
        String message = mid + paymentResolution.getAmount()
                + paymentResolution.getCurrAlphaCode() + paymentResolution.getMsTxnId()
                + paymentResolution.getClient().getFirstName() + paymentResolution.getClient().getFamilyName()
                + paymentResolution.getTimeStamp();

        iv = SignGenerator.reverseString(mid);

        model.addAttribute("payment", paymentResolution);
        model.addAttribute("mid", mid);
        model.addAttribute("eshopId", eshopId);
        model.addAttribute("rurl", rurl);
        model.addAttribute("sign", SignGenerator.generateSign(message, key, iv));

        return "sendPayment";
    }

    /**
     * Handle response from the server. Compares signs and display the result of the request
     *
     * @author Patrik
     * @param model Model object
     * @param MsTxnId payment data
     * @param Amount payment data
     * @param CurrCode payment data
     * @param Result payment data
     * @param Sign payment data
     * @return
     */
    @Override
    public String processRequest(Model model, String MsTxnId, String Amount, String CurrCode, String Result, String Sign) {

        //MESSAGE = MsTxnId ⊕ Amount ⊕ CurrCode ⊕ Result
        String message = MsTxnId + Amount + CurrCode + Result;
        System.out.println(message);

        // Set sign
        String sign = SignGenerator.generateSign(message, key, iv);

        // Signs not equal
        assert sign != null;
        if (!sign.toUpperCase().equals(Sign.toUpperCase())) {
            model.addAttribute("status", "FAILED");
            return "result";
        }

        switch (Result) {
            case "OK":
                model.addAttribute("status", "OK");
                model.addAttribute("amount", Amount);
                model.addAttribute("currCode", CurrCode);
                model.addAttribute("transaction", MsTxnId);
                break;
            case "FAIL":
                model.addAttribute("status", "FAIL");
                model.addAttribute("transaction", MsTxnId);
                break;
            case "PENDING":
                model.addAttribute("status", "PENDING");
                model.addAttribute("transaction", MsTxnId);
                break;
            case "AUTHORIZED":
                model.addAttribute("status", "AUTHORIZED");
                model.addAttribute("transaction", MsTxnId);
                break;
        }
        return "result";
    }
}
