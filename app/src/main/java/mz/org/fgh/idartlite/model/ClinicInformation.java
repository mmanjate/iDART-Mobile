package mz.org.fgh.idartlite.model;


import android.content.Context;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Calendar;
import java.util.Date;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.dao.clinicInfo.ClinicInfoDaoImpl;
import mz.org.fgh.idartlite.util.DateUtilities;


@DatabaseTable(tableName = "clinic_information", daoClass = ClinicInfoDaoImpl.class)
public class ClinicInformation extends BaseModel {

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_REGISTER_DATE = "register_date";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_HEIGHT = "height";
    public static final String COLUMN_IMC = "imc";
    public static final String COLUMN_DISTORT = "distort";
    public static final String COLUMN_SYSTOLE = "systole";
    public static final String COLUMN_PATIENT_ID = "patient_id";

    public static final String COLUMN_IS_TREATMENT_TPI = "is_treatment_tpi";
    public static final String COLUMN_IS_TREATMENT_TB = "is_treatment_tb";
    public static final String COLUMN_IS_COUGH = "is_cough";
    public static final String COLUMN_IS_FEVER = "is_fever";
    public static final String COLUMN_IS_LOST_WEIGHT = "is_lost_weight";
    public static final String COLUMN_IS_SWEATING = "is_sweating";
    public static final String COLUMN_PARENT_TB_TREATMENT = "parent_tb_treatment";
    public static final String COLUMN_IS_REFERED_TO_US_TB = "is_refered_to_us_tb";
    public static final String COLUMN_HAS_PATIENT_CAME_CORRECT_DATE = "has_patient_came_correct_date";
    public static final String COLUMN_LATE_DAYS = "late_days";
    public static final String COLUMN_PATIENT_FORGOT_MEDICINE = "patient_forgot_medicine";
    public static final String COLUMN_DAYS_WITHOUT_MEDICINE = "days_without_medicine";
    public static final String COLUMN_LATE_MOTIVES = "late_motives";
    public static final String COLUMN_ADVERSE_REACTION_MEDICINE = "adverse_reaction_medicine";
    public static final String COLUMN_ADVERSE_REACTION = "adverse_reaction";
    public static final String COLUMN_IS_REFERED_TO_US_RAM = "is_refered_to_us_ram";
    public static final String COLUMN_SYNC_STATUS = "sync_status";
    public static final String COLUMN_UUID = "uuid";
    public static final String COLUMN_IS_PREGNANT = "is_pregnant";
    public static final String COLUMN_HAS_HAD_MENSTRUATION_LAST_TWO_MONTHS = "has_had_menstruation_last_two_months";
    public static final String COLUMN_HAS_FATIGUE_OR_TIREDNESS_LAST_TWO_WEEKS = "has_fatigue_or_tiredness_last_two_weeks";
    public static final String COLUMN_START_TREATMENT_DATE = "start_treatment_date";

    public static final String PARAM_RAM_STATUS = "RAM_STATUS";
    public static final String PARAM_RAM_STATUS_POSETIVE = "RAM_STATUS_POSETIVE ";
    public static final String PARAM_RAM_STATUS_NEGATIVE = "RAM_STATUS_NEGATIVE";
    public static final String PARAM_RAM_STATUS_ALL = "RAM_STATUS_ALL";

    public static final String PARAM_FOLLOW_STATUS = "FOLLOW_STATUS";
    public static final String PARAM_FOLLOW_STATUS_WITH_LATE_DAYS = "FOLLOW_STATUS_WITH_LATE_DAYS";
    public static final String PARAM_FOLLOW_STATUS_ALL = "FOLLOW_STATUS_ALL";

    @DatabaseField(columnName = COLUMN_ID, generatedId = true)
    private int id;

    @DatabaseField(columnName = COLUMN_REGISTER_DATE)
    private Date registerDate;

    @DatabaseField(columnName = COLUMN_WEIGHT)
    private double weight;

    @DatabaseField(columnName = COLUMN_HEIGHT)
    private int height;

    @DatabaseField(columnName = COLUMN_IMC)
    private String imc;

    @DatabaseField(columnName = COLUMN_DISTORT)
    private int distort;

    @DatabaseField(columnName = COLUMN_SYSTOLE)
    private int systole;

    @DatabaseField(columnName = COLUMN_PATIENT_ID, canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Patient patient;

    @DatabaseField(columnName = COLUMN_IS_TREATMENT_TPI)
    private boolean isTreatmentTPI;

    @DatabaseField(columnName = COLUMN_IS_TREATMENT_TB)
    private boolean isTreatmentTB;

    @DatabaseField(columnName = COLUMN_IS_COUGH)
    private boolean isCough;

    @DatabaseField(columnName = COLUMN_IS_FEVER)
    private boolean isFever;

    @DatabaseField(columnName = COLUMN_IS_LOST_WEIGHT)
    private boolean isLostWeight;

    @DatabaseField(columnName = COLUMN_IS_SWEATING)
    private boolean isSweating;

    @DatabaseField(columnName = COLUMN_PARENT_TB_TREATMENT)
    private boolean hasParentTBTreatment;

    @DatabaseField(columnName = COLUMN_IS_REFERED_TO_US_TB)
    private boolean isReferedToUsTB;

    @DatabaseField(columnName = COLUMN_HAS_PATIENT_CAME_CORRECT_DATE)
    private boolean hasPatientCameCorrectDate;

    @DatabaseField(columnName = COLUMN_LATE_DAYS)
    private int lateDays;

    @DatabaseField(columnName = COLUMN_PATIENT_FORGOT_MEDICINE)
    private boolean patientForgotMedicine;

    @DatabaseField(columnName = COLUMN_DAYS_WITHOUT_MEDICINE)
    private int daysWithoutMedicine;

    @DatabaseField(columnName = COLUMN_LATE_MOTIVES)
    private String lateMotives;

    @DatabaseField(columnName = COLUMN_ADVERSE_REACTION_MEDICINE)
    private boolean adverseReactionOfMedicine;

    @DatabaseField(columnName = COLUMN_ADVERSE_REACTION)
    private String adverseReaction;

    @DatabaseField(columnName = COLUMN_IS_REFERED_TO_US_RAM)
    private boolean isReferedToUsRAM;

    @DatabaseField(columnName = COLUMN_SYNC_STATUS)
    private String syncStatus;

    @DatabaseField(columnName = COLUMN_UUID)
    private String uuid;


    @DatabaseField(columnName = COLUMN_HAS_FATIGUE_OR_TIREDNESS_LAST_TWO_WEEKS)
    private boolean hasFatigueOrTirednesLastTwoWeeks;

    @DatabaseField(columnName = COLUMN_IS_PREGNANT)
    private boolean isPregnant;

    @DatabaseField(columnName = COLUMN_HAS_HAD_MENSTRUATION_LAST_TWO_MONTHS)
    private boolean hasHadMenstruationLastTwoMonths;

    @DatabaseField(columnName = COLUMN_START_TREATMENT_DATE)
    private Date startTreatmentDate;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getImc() {
        return imc;
    }

    public void setImc(String imc) {
        this.imc = imc;
    }

    public int getDistort() {
        return distort;
    }

    public void setDistort(int distort) {
        this.distort = distort;
    }

    public int getSystole() {
        return systole;
    }

    public void setSystole(int systole) {
        this.systole = systole;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }


    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public boolean isTreatmentTPI() {
        return isTreatmentTPI;
    }

    public void setTreatmentTPI(boolean treatmentTPI) {
        isTreatmentTPI = treatmentTPI;
    }

    public boolean isTreatmentTB() {
        return isTreatmentTB;
    }

    public void setTreatmentTB(boolean treatmentTB) {
        isTreatmentTB = treatmentTB;
    }

    public boolean isCough() {
        return isCough;
    }

    public void setCough(boolean cough) {
        isCough = cough;
    }

    public boolean isFever() {
        return isFever;
    }

    public void setFever(boolean fever) {
        isFever = fever;
    }

    public boolean isLostWeight() {
        return isLostWeight;
    }

    public void setLostWeight(boolean lostWeight) {
        isLostWeight = lostWeight;
    }

    public boolean isSweating() {
        return isSweating;
    }

    public void setSweating(boolean sweating) {
        isSweating = sweating;
    }

    public boolean isHasParentTBTreatment() {
        return hasParentTBTreatment;
    }

    public void setHasParentTBTreatment(boolean hasParentTBTreatment) {
        this.hasParentTBTreatment = hasParentTBTreatment;
    }

    public boolean isReferedToUsTB() {
        return isReferedToUsTB;
    }

    public void setReferedToUsTB(boolean referedToUsTB) {
        isReferedToUsTB = referedToUsTB;
    }

    public boolean isHasPatientCameCorrectDate() {
        return hasPatientCameCorrectDate;
    }

    public void setHasPatientCameCorrectDate(boolean hasPatientCameCorrectDate) {
        this.hasPatientCameCorrectDate = hasPatientCameCorrectDate;
    }

    public int getLateDays() {
        return lateDays;
    }

    public void setLateDays(int lateDays) {
        this.lateDays = lateDays;
    }

    public boolean isPatientForgotMedicine() {
        return patientForgotMedicine;
    }

    public void setPatientForgotMedicine(boolean patientForgotMedicine) {
        this.patientForgotMedicine = patientForgotMedicine;
    }

    public int getDaysWithoutMedicine() {
        return daysWithoutMedicine;
    }

    public void setDaysWithoutMedicine(int daysWithoutMedicine) {
        this.daysWithoutMedicine = daysWithoutMedicine;
    }

    public String getLateMotives() {
        return lateMotives;
    }

    public void setLateMotives(String lateMotives) {
        this.lateMotives = lateMotives;
    }

    public boolean isAdverseReactionOfMedicine() {
        return adverseReactionOfMedicine;
    }

    public void setAdverseReactionOfMedicine(boolean adverseReactionOfMedicine) {
        this.adverseReactionOfMedicine = adverseReactionOfMedicine;
    }

    public String getAdverseReaction() {
        return adverseReaction;
    }

    public void setAdverseReaction(String adverseReaction) {
        this.adverseReaction = adverseReaction;
    }

    public boolean isReferedToUsRAM() {
        return isReferedToUsRAM;
    }

    public void setReferedToUsRAM(boolean referedToUsRAM) {
        isReferedToUsRAM = referedToUsRAM;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isHasFatigueOrTirednesLastTwoWeeks() {
        return hasFatigueOrTirednesLastTwoWeeks;
    }

    public void setHasFatigueOrTirednesLastTwoWeeks(boolean hasFatigueOrTirednesLastTwoWeeks) {
        this.hasFatigueOrTirednesLastTwoWeeks = hasFatigueOrTirednesLastTwoWeeks;
    }

    public boolean isPregnant() {
        return isPregnant;
    }

    public void setPregnant(boolean pregnant) {
        isPregnant = pregnant;
    }

    public boolean isHasHadMenstruationLastTwoMonths() {
        return hasHadMenstruationLastTwoMonths;
    }

    public void setHasHadMenstruationLastTwoMonths(boolean hasHadMenstruationLastTwoMonths) {
        this.hasHadMenstruationLastTwoMonths = hasHadMenstruationLastTwoMonths;
    }

    public Date getStartTreatmentDate() {
        return startTreatmentDate;
    }

    public void setStartTreatmentDate(Date startTreatmentDate) {
        this.startTreatmentDate = startTreatmentDate;
    }

    public String getStringRegisterDate(){
        return DateUtilities.parseDateToDDMMYYYYString(this.registerDate);
    }


    public String validateClinicInfoData(Context context){
        if(registerDate==null){
            return context.getString(R.string.register_date_required);
        }
        if(DateUtilities.dateDiff(registerDate, Calendar.getInstance().getTime(), DateUtilities.DAY_FORMAT) >=0){
            return context.getString(R.string.visit_date_cannot_be_future);
        }

        if(isTreatmentTB && startTreatmentDate== null)
        {
            return context.getString(R.string.treatment_start_date_required);
        }

        if(!hasPatientCameCorrectDate && lateDays==0) {
            return context.getString(R.string.late_days_cannot_be_zero);
        }

        if(isAdverseReactionOfMedicine() && adverseReaction == null){
            return context.getString(R.string.adverse_reaction_cannot_be_empty);
        }
        return "";
    }

    public boolean getCheckForTb(){
        return (this.isTreatmentTB ||
                this.isTreatmentTPI ||
                this.isFever ||
                this.isCough ||
                this.isLostWeight ||
                this.isSweating ||
                this.hasParentTBTreatment ||
                this.isReferedToUsTB ||
                this.hasFatigueOrTirednesLastTwoWeeks);
    }

    public boolean getCheckMonitoring(){
        return (this.hasPatientCameCorrectDate ||
                this.patientForgotMedicine ||
                 lateMotives!=null ||
                  lateDays !=0 );
    }

    public boolean getCheckAdversity(){
        return (this.adverseReactionOfMedicine ||
                this.isReferedToUsRAM ||
                this.adverseReaction!= null);
    }

    public boolean getCheckVitalSigns(){
        return (this.weight!=0.0 ||
                this.height!=0.0 ||
                this.imc!=null ||
                this.systole!=0 ||
                this.distort!=0);
    }

    public boolean getCheckForTbSyntoms(){
        return (this.isFever ||
                this.isCough ||
                this.isLostWeight ||
                this.isSweating ||
                this.hasParentTBTreatment ||
                this.isReferedToUsTB ||
                this.hasFatigueOrTirednesLastTwoWeeks);
    }

    public boolean getCheckPregnancy(){
        return (this.isPregnant ||
                this.hasHadMenstruationLastTwoMonths);
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
