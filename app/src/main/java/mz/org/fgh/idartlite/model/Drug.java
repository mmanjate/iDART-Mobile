package mz.org.fgh.idartlite.model;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import mz.org.fgh.idartlite.base.BaseModel;
import mz.org.fgh.idartlite.dao.DrugDaoImpl;

import java.util.Objects;

@DatabaseTable(tableName = "drug", daoClass = DrugDaoImpl.class)
public class Drug extends BaseModel {

    public static final String COLUMN_FNMCODE = "fnm_code";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_PACK_SIZE = "packSize";
    public static final String COLUMN_INSTRUCTION = "instruction";
    public static final String COLUMN_FORM = "form_id";
    public static final String COLUMN_DISEASE_TYPE = "diseaseType_id";


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

    @DatabaseField(columnName = COLUMN_FORM, canBeNull = false, foreign = true)
    private Form form;

    @DatabaseField(columnName = COLUMN_DISEASE_TYPE, canBeNull = false, foreign = true)
    private DiseaseType diseaseType;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFnmcode() {
        return fnmcode;
    }

    public void setFnmcode(String fnmcode) {
        this.fnmcode = fnmcode;
    }

    public String getDescription() {
        return description;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drug drug = (Drug) o;
        return id == drug.id &&
                packSize == drug.packSize &&
                fnmcode.equals(drug.fnmcode) &&
                description.equals(drug.description) &&
                instruction.equals(drug.instruction) &&
                form.equals(drug.form) &&
                diseaseType.equals(drug.diseaseType);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(id, fnmcode, description, packSize, instruction, form, diseaseType);
    }

    @Override
    public String toString() {
        return "Drug{" +
                "id=" + id +
                ", fnmcode='" + fnmcode + '\'' +
                ", description='" + description + '\'' +
                ", packSize=" + packSize +
                ", instruction='" + instruction + '\'' +
                ", form=" + form +
                ", diseaseType=" + diseaseType +
                '}';
    }
}
