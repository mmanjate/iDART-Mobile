package mz.org.fgh.idartlite.common;

public class ApplicationStep {

    public static final String STEP_INIT = "INIT";
    public static final String STEP_SAVE = "SAVE";
    public static final String STEP_EDIT = "EDIT";
    public static final String STEP_LIST = "LIST";
    public static final String STEP_DISPLAY = "DYSPLAY";
    public static final String STEP_CREATE = "CREATE";

    private int id;
    private String descrption;
    private String code;

    public ApplicationStep() {
    }

    public ApplicationStep(String code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static ApplicationStep fastCreate(String code){
        return new ApplicationStep(code);
    }

    public boolean isApplicationStepInit(){
        return this.code.equals(STEP_INIT);
    }

    public boolean isApplicationStepSave(){
        return this.code.equals(STEP_SAVE);
    }

    public boolean isApplicationStepEdit(){
        return this.code.equals(STEP_EDIT);
    }

    public boolean isApplicationStepList(){
        return this.code.equals(STEP_LIST);
    }

    public boolean isApplicationStepDisplay(){
        return this.code.equals(STEP_DISPLAY);
    }

    public boolean isapplicationstepcreate(){
        return this.code.equals(STEP_CREATE);
    }

    public void changeToInit(){
        this.code = STEP_INIT;
    }

    public void changeToSave(){
        this.code = STEP_SAVE;
    }

    public void changeToEdit(){
        this.code = STEP_EDIT;
    }

    public void changeToList(){
        this.code = STEP_LIST;
    }

    public void changeToDisplay(){
        this.code = STEP_DISPLAY;
    }

    public void changetocreate(){
        this.code = STEP_CREATE;
    }

    public void changeTo(String stepCode){
        this.code = stepCode;
    }
}
