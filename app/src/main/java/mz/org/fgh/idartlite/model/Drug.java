package mz.org.fgh.idartlite.model;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.recyclerview.listable.Listble;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.dao.drug.DrugDaoImpl;
import mz.org.fgh.idartlite.model.inventory.InventoryRelatedObject;

@DatabaseTable(tableName = Drug.TABLE_NAME, daoClass = DrugDaoImpl.class)
public class Drug extends BaseModel implements Listble, InventoryRelatedObject {

    public static final String TABLE_NAME = "drug";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FNMCODE = "fnm_code";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_PACK_SIZE = "packSize";
    public static final String COLUMN_INSTRUCTION = "instruction";
    public static final String COLUMN_FORM = "form_id";
    public static final String COLUMN_DISEASE_TYPE = "diseaseType_id";
    public static final String COLUMN_REST_ID = "restid";

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = COLUMN_FNMCODE)
    private String fnmcode;

    @DatabaseField(columnName = COLUMN_DESCRIPTION)
    private String description;

    @DatabaseField(columnName = COLUMN_PACK_SIZE)
    private int packSize;

    @DatabaseField(columnName = COLUMN_INSTRUCTION)
    private String instruction;

    @DatabaseField(columnName = COLUMN_FORM, canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Form form;

    @DatabaseField(columnName = COLUMN_DISEASE_TYPE, canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private DiseaseType diseaseType;

    @DatabaseField(columnName = COLUMN_REST_ID)
    private int restid;

    private int quantity;

    private List<StockAjustment> ajustmentInfo;

    private List<DestroyedDrug> destroyedDrugsInfo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFnmcode() {
        return fnmcode;
    }

    @Override
    public int getDrawable() {
        return R.mipmap.ic_drug;
    }

    @Override
    public String getCode() {
        return fnmcode;
    }

    public void setFnmcode(String fnmcode) {
        this.fnmcode = fnmcode;
    }

    @Override
    public int getPosition() {
        return this.listPosition;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPackSize() {
        return packSize;
    }

    public void setPackSize(int packSize) {
        this.packSize = packSize;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public DiseaseType getDiseaseType() {
        return diseaseType;
    }

    public void setDiseaseType(DiseaseType diseaseType) {
        this.diseaseType = diseaseType;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public int getRestId() { return restid; }

    public void setRestId(int restid) { this.restid = restid; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drug drug = (Drug) o;
        if (id == drug.id && fnmcode.equals(drug.fnmcode)) return true;

        return id == drug.id &&
                packSize == drug.packSize &&
                fnmcode.equals(drug.fnmcode) &&
                instruction.equals(drug.instruction) &&
                form.getId() == drug.form.getId() &&
                diseaseType.getId() == drug.diseaseType.getId();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(id, fnmcode, description, packSize, instruction, form, diseaseType);
    }

    @Override
    public String toString() {
        return description;
    }


    @Override
    public int compareTo(BaseModel baseModel) {
        if (this.getPosition() == 0 || ((Drug) baseModel).getPosition() == 0) return 0;

        return this.getPosition() - ((Drug) baseModel).getPosition();
    }

    @Override
    public int compareTo(Object o) {
        return compareTo((BaseModel) o);
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
    public boolean isDispenseDrugListing() {
        return listType.equals(Listble.DISPENSE_DRUG_LISTING);
    }

    @Override
    public boolean isPrescriptionDrugListing() {
        return listType.equals(Listble.PRESCRIPTION_DRUG_LISTING);
    }

    @Override
    public boolean isIventorySelectionListing() {
        return listType.equals(Listble.INVENTORY_SELECTION_LISTING);
    }

    @Override
    public List<StockAjustment> getAjustmentInfo() {
        return ajustmentInfo;
    }

    @Override
    public void setAjustmentInfo(List<StockAjustment> ajustmentInfo) {
        this.ajustmentInfo = ajustmentInfo;
    }

    public void addAjustmentInfo(StockAjustment ajustment) {
        if (this.ajustmentInfo == null) this.ajustmentInfo = new ArrayList<>();

        this.ajustmentInfo.add(ajustment);
    }

    public void addStockDestructionInfo(DestroyedDrug destroyedDrug) {
        if (this.destroyedDrugsInfo == null) this.destroyedDrugsInfo = new ArrayList<>();

        this.destroyedDrugsInfo.add(destroyedDrug);
    }

    public List<DestroyedDrug> getDestroyedDrugsInfo() {
        return destroyedDrugsInfo;
    }

    public void setDestroyedDrugsInfo(List<DestroyedDrug> destroyedDrugsInfo) {
        this.destroyedDrugsInfo = destroyedDrugsInfo;
    }
}
