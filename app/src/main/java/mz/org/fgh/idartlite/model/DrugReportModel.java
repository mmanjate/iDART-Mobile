package mz.org.fgh.idartlite.model;

import android.content.Context;

import java.util.List;

import mz.org.fgh.idartlite.base.model.BaseModel;

public class DrugReportModel extends BaseModel {

        private String fnm;
        private String drugName;
        private String balance;
        private List<StockReportModel> stockReportList;

    public String getFnm() {
        return fnm;
    }

    public void setFnm(String fnm) {
        this.fnm = fnm;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public List<StockReportModel> getStockReportList() {
        return stockReportList;
    }

    public void setStockReportList(List<StockReportModel> stockReportList) {
        this.stockReportList = stockReportList;
    }

    @Override
    public String isValid(Context context) {
        return null;
    }

    @Override
    public String canBeEdited(Context context) {
        return null;
    }

    @Override
    public String canBeRemoved(Context context) {
        return null;
    }
}
