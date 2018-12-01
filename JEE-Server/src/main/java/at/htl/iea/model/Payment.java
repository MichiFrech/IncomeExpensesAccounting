package at.htl.iea.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;

@XmlRootElement
public class Payment {

    // region Variables
    private LocalDateTime bookingDate = null; // buchungsdatum
    private String partnerName = "";
    private String partnerIban = "";
    private String partnerBic = "";
    private String partnerAccountNumber = "";
    private String partnerBankCode = "";
    private Double amount = 0.0;
    private String currency = "";
    private String bookingText = "";
    private String initialRecognitionReference = ""; // ersterfassungsreferenz (z.B.: 128379247QWEV2345FLHO9)
    private String note = "";
    private LocalDateTime valueDate = null; // valutadatum
    // endregion

    // region Constructor
    public Payment(){}
    // endregion

    // region Getter & Setter
    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerIban() {
        return partnerIban;
    }

    public void setPartnerIban(String partnerIban) {
        this.partnerIban = partnerIban;
    }

    public String getPartnerBic() {
        return partnerBic;
    }

    public void setPartnerBic(String partnerBic) {
        this.partnerBic = partnerBic;
    }

    public String getPartnerAccountNumber() {
        return partnerAccountNumber;
    }

    public void setPartnerAccountNumber(String partnerAccountNumber) {
        this.partnerAccountNumber = partnerAccountNumber;
    }

    public String getPartnerBankCode() {
        return partnerBankCode;
    }

    public void setPartnerBankCode(String partnerBankCode) {
        this.partnerBankCode = partnerBankCode;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBookingText() {
        return bookingText;
    }

    public void setBookingText(String bookingText) {
        this.bookingText = bookingText;
    }

    public String getInitialRecognitionReference() {
        return initialRecognitionReference;
    }

    public void setInitialRecognitionReference(String initialRecognitionReference) {
        this.initialRecognitionReference = initialRecognitionReference;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getValueDate() {
        return valueDate;
    }

    public void setValueDate(LocalDateTime valueDate) {
        this.valueDate = valueDate;
    }
    // endregion
}