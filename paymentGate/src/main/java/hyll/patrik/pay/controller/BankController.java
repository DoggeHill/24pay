package hyll.patrik.pay.controller;

import hyll.patrik.pay.controller.services.BankControllerService;
import hyll.patrik.pay.model.PaymentResolution;
import hyll.patrik.pay.helpers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller
 *
 * @author Patrik
 */
@Controller
public class BankController {

    // Autowired validator in order to validate Model after using custom setters
    @Autowired
    private Validator validator;

    // Functionality
    BankControllerService bankControllerService;

    public BankController(BankControllerService bankControllerService) {
        this.bankControllerService = bankControllerService;
    }

    /**
     * Display payment form to the user
     *
     * @param model Model object
     * @return Model
     * @author Patrik
     */
    @GetMapping("/request")
    @Profile("production")
    public String initializePaymentProd(Model model) {
        model.addAttribute("payment", new PaymentResolution());
        return "payment";
    }

    /**
     * Create model filled in with data from USER input into PaymentResolution POJO
     * First server side fields are added to the POJO then the validation is done.
     *
     * @param paymentResolution POJO
     * @param bindingResult     Validation
     * @param model             Model object
     * @return model
     * @throws Exception
     * @author Patrik
     */
    @PostMapping("/request")
    public String paymentSubmit(@ModelAttribute PaymentResolution paymentResolution, final BindingResult bindingResult, Model model) throws Exception {


        // Set unique transaction code[strategy random]
        paymentResolution.setMsTxnId(SignGenerator.randomAlphaNumericString());

        // Set unique client id[strategy random]
        paymentResolution.getClient().setClientId(SignGenerator.randomAlphaNumericString());

        // Set timestamp
        paymentResolution.setTimeStamp(TimeStamp.getTimeStamp());

        validator.validate(paymentResolution, bindingResult);

        if (bindingResult.hasErrors()) {
            System.out.println("wrong attrs");
            List<ObjectError> objectErrorList = bindingResult.getAllErrors();
            for (ObjectError objectError : objectErrorList) {
                System.err.println(objectError.toString());
            }
            return "error";
        }

        return bankControllerService.createRequest(paymentResolution, model);
    }

    /**
     * Display the results of the POST request, request to this endpoint is send from
     * 24-pay sever
     *
     * @param model    Model object
     * @param MsTxnId  paymentData
     * @param Amount   paymentData
     * @param CurrCode paymentData
     * @param Result   paymentData
     * @param Sign     paymentData
     * @return model
     * @author Patrik
     */
    @GetMapping("rurl")
    public String onPaymentProcessed(Model model, @RequestParam String MsTxnId, @RequestParam String Amount, @RequestParam String CurrCode,
                                     @RequestParam String Result, @RequestParam String Sign) {

        return bankControllerService.processRequest(model, MsTxnId, Amount, CurrCode, Result, Sign);
    }
}
