package mz.org.fgh.idartlite.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "drug")
public class Drug {

    public static final String COLUMN_FNMCODE = "fnm_code";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_PACK_SIZE = "packSize";
    public static final String COLUMN_INSTRUCTION = "instruction";
    public static final String COLUMN_FORM = "form_id";
    public static final String COLUMN_DISEASE_TYPE = "diseaseType_id";


    @DatabaseField(columnName = "id", id = true)
    private int id;

    @DatabaseField(columnName = "fnm_code")
    private String fnmcode;

    @DatabaseField(columnName = "description")
    private String description;

    @DatabaseField(columnName = "pack_size")
    private int packSize;

    @DatabaseField(columnName = "instruction")
    private String instruction;

    @DatabaseField(canBeNull = false, foreign = true)
    private Form form;

    @DatabaseField(canBeNull = false, foreign = true)
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
}
