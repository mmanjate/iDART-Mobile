package mz.org.fgh.idartlite.model;

public class StockReportModel {
        private String batchNumber;
        private String entranceQtd;
        private String dispenseQtd;
        private String expiryDate;
        private String existingStock;

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getEntranceQtd() {
        return entranceQtd;
    }

    public void setEntranceQtd(String entranceQtd) {
        this.entranceQtd = entranceQtd;
    }

    public String getDispenseQtd() {
        return dispenseQtd;
    }

    public void setDispenseQtd(String dispenseQtd) {
        this.dispenseQtd = dispenseQtd;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getExistingStock() {
        return existingStock;
    }

    public void setExistingStock(String existingStock) {
        this.existingStock = existingStock;
    }
}
