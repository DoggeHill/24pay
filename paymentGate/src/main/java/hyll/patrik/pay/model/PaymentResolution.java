package hyll.patrik.pay.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Payment object POJO
 *
 * @author Patrik
 */
public class PaymentResolution {

    private String debug = "false";

    public PaymentResolution() {
    }

    // Fields

    @NotNull
    @Valid
    private Client client;

    @NotBlank(message = "Timestamp may not be blank")
    private String timeStamp;

    @NotBlank(message = "MsTxnId may not be blank")
    @Size(min = 1, max = 32)
    private String msTxnId;

    @NotBlank(message = "Amount may not be blank")
    @Pattern(regexp = "^[0-9]*\\.[0-9]{2}$", message = "Wrong amount format")
    private String amount;

    @NotBlank(message = "CurrAlphaCode may not be blank")
    private String currAlphaCode;


    // Standard getters and setters

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrAlphaCode() {
        return currAlphaCode;
    }

    public void setCurrAlphaCode(String currAlphaCode) {
        this.currAlphaCode = currAlphaCode;
    }


    public String getMsTxnId() {
        return msTxnId;
    }

    @Autowired
    public void setMsTxnId(@Value("") String msTxnId) {
        this.msTxnId = msTxnId;
    }


    public String getTimeStamp() {
        return timeStamp;
    }

    @Autowired
    public void setTimeStamp(@Value("") String timeStamp) {
        this.timeStamp = timeStamp;
    }


    public String getRedirectSign() {
        return "true";
    }

    public String getDebug() {
        return this.debug;
    }

    @Autowired
    public void setDebug(@Value("") String debug) {
        this.debug = debug;
    }
}

