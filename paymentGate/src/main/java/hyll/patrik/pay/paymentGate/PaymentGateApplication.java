package hyll.patrik.pay.paymentGate;

import hyll.patrik.pay.controller.BankController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Java developer assignment
 * 24-pay payments implementation
 * @author Patrik
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = BankController.class)
public class PaymentGateApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentGateApplication.class, args);
    }

}
