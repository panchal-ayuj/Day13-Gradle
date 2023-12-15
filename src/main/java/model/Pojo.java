
package model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

public class Pojo {
    private String transactionId;
    private String accountNumber;
    private int debit;
    private int credit;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Date date;

    private String comment;
    private AdditionalDetails additionalDetails;

    public String getTransactionId() {
        return transactionId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public int getDebit() {
        return debit;
    }

    public int getCredit() {
        return credit;
    }

    public Date getDate() {
        return date;
    }

    public String getComment() {
        return comment;
    }

    public AdditionalDetails getAdditionalDetails() {
        return additionalDetails;
    }

    public static class AdditionalDetails {
        private String merchant;
        private String location;

        public String getMerchant() {
            return merchant;
        }

        public String getLocation() {
            return location;
        }

        @Override
        public String toString() {
            return "AdditionalDetails{" +
                    "merchant='" + merchant + '\'' +
                    ", location='" + location + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Pojo{" +
                "transactionId='" + transactionId + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", debit=" + debit +
                ", credit=" + credit +
                ", date=" + date +
                ", comment='" + comment + '\'' +
                ", additionalDetails=" + additionalDetails +
                '}';
    }
}