package com.example.adminapp.ui.DocsActivity;

import java.util.Arrays;
import java.util.List;

public class DocumentApplication {
    private String documentType;
    private String reason, prn, date, time;

    public DocumentApplication() {
        // Required empty constructor for Firestore
    }

    public DocumentApplication(String prn, String documentType, String reason, String date, String time) {
        this.documentType = documentType;
        this.reason = reason;
        this.prn = prn;
        this.date = date;
        this.time = time;
    }

    // Generate getters and setters

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public static List<String> getDocumentTypeOptions() {
        return Arrays.asList("Bonafide Certificate", "Expenditure Certificate", "ID Card Replacement", "Library Card", "Other");
    }

    public String getPrn() {
        return prn;
    }

    public void setPrn(String prn) {
        this.prn = prn;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }
}
