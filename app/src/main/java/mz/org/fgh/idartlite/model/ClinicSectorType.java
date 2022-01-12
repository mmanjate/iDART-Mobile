package mz.org.fgh.idartlite.model;

import android.content.Context;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mz.org.fgh.idartlite.adapter.recyclerview.listable.Listble;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.dao.clinic.ClinicSectorTypeDaoImpl;
import mz.org.fgh.idartlite.dao.patient.PatientDaoImpl;

@DatabaseTable(tableName = "clinic_sector_type", daoClass = ClinicSectorTypeDaoImpl.class)
public class ClinicSectorType extends BaseModel implements Listble {

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CODE = "code";
    public static final String COLUMN_DESCRIPTION = "description";

    @DatabaseField(columnName = COLUMN_ID, generatedId = true)
    private int id;

    @DatabaseField(columnName = COLUMN_CODE, unique = true)
    private String code;

    @DatabaseField(columnName = COLUMN_DESCRIPTION)
    private String description;

    private List<ClinicSector> clinicSectorList;

    public ClinicSectorType() {
    }

    public ClinicSectorType(String description) {
        String padrao = "\\s";
        Pattern regPat = Pattern.compile(padrao);
        Matcher matcher = regPat.matcher(description);
        String res = matcher.replaceAll("_");

        this.code = res.toUpperCase();
        this.description = description;
    }

    public int getId() {
        return id;
    }

    @Override
    public int getPosition() {
        return 0;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    @Override
    public int compareTo(BaseModel baseModel) {
        return 0;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int getQuantity() {
        return 0;
    }

    @Override
    public String getLote() {
        return null;
    }

    @Override
    public String getOrigin() {
        return null;
    }

    @Override
    public String getFnmcode() {
        return null;
    }

    @Override
    public Date getValidate() {
        return null;
    }

    @Override
    public int getSaldoActual() {
        return 0;
    }

    @Override
    public void setSaldoActual(int saldo) {

    }

    @Override
    public int getQtyToModify() {
        return 0;
    }

    @Override
    public void setQtyToModify(int qtyToDestroy) {

    }

    @Override
    public int getAdjustedValue() {
        return 0;
    }

    @Override
    public void setAdjustedValue(int adjustedValue) {

    }

    @Override
    public String getNotes() {
        return null;
    }

    @Override
    public void setNotes(String notes) {

    }

    @Override
    public boolean isStockListing() {
        return false;
    }

    @Override
    public boolean isPrescriptionDrugListing() {
        return false;
    }

    @Override
    public boolean isDispenseDrugListing() {
        return false;
    }

    @Override
    public boolean isStockDestroyListing() {
        return false;
    }

    @Override
    public boolean isIventoryListing() {
        return false;
    }

    @Override
    public boolean isIventorySelectionListing() {
        return false;
    }

    @Override
    public boolean isReturnDispenseListing() {
        return false;
    }

    @Override
    public boolean isReferedStockListing() {
        return false;
    }

    @Override
    public int getDrawable() {
        return 0;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ClinicSector> getClinicSectorList() {
        return clinicSectorList;
    }

    public void setClinicSectorList(List<ClinicSector> clinicSectorList) {
        this.clinicSectorList = clinicSectorList;
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

    @Override
    public String toString() {
        if(description == null){
            return " ";
        }else{
            return  description;
        }
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
