package mz.org.fgh.idartlite.base.viewModel;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.AndroidViewModel;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mz.org.fgh.idartlite.BR;
import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.recyclerview.listable.Listble;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.fragment.GenericFragment;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.base.service.ServiceProvider;
import mz.org.fgh.idartlite.common.ApplicationStep;
import mz.org.fgh.idartlite.listener.dialog.IDialogListener;
import mz.org.fgh.idartlite.model.AppSettings;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.ClinicSector;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.util.DateUtilities;
import mz.org.fgh.idartlite.util.LoadingDialog;
import mz.org.fgh.idartlite.util.SecurePreferences;
import mz.org.fgh.idartlite.util.Utilities;

public abstract class BaseViewModel  extends AndroidViewModel implements Observable, IDialogListener {

    private PropertyChangeRegistry callbacks;
    private BaseActivity relatedActivity;

    protected BaseModel relatedRecord;

    protected IBaseService relatedService;

    protected GenericFragment relatedFragment;

    private boolean viewListEditButton;
    private boolean viewListRemoveButton;

    private Listble selectedListble;
    protected User currentUser;
    protected Clinic currentClinic;

    protected List<Listble> selectedListbles;


    protected List<AppSettings> systemSettings;

    protected ClinicSector currentClinicSector;

    protected ServiceProvider serviceProvider;

    private static final String SETTINGS_PREF = "settings_pref";

    private static final String APP_LAST_SYNC_DATE = "app_last_sync";

    protected SecurePreferences settingsPreferences;

    private NotificationManagerCompat notificationManager;

    LoadingDialog loadingDialog;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        callbacks = new PropertyChangeRegistry();

        serviceProvider = ServiceProvider.getInstance(application);

        this.relatedRecord = initRecord();

        relatedService = initRelatedService();

        selectedListbles = new ArrayList<>();

        initFormData();

        settingsPreferences = new SecurePreferences(application, SETTINGS_PREF,  true);

        notificationManager = NotificationManagerCompat.from(getApplication());

    }

    public boolean isCentralServerConfigured(){
        if (!Utilities.listHasElements(systemSettings)) return false;

        for (AppSettings appSettings : systemSettings){
            if (appSettings.isUrlSetting()) return true;
        }

        return false;
    }

    public NotificationManagerCompat getNotificationManager() {
        return notificationManager;
    }

    public void issueNotification(String contentMsg, String channel){
        Utilities.issueNotification(getNotificationManager(), getApplication(),contentMsg, channel);
    }

    protected abstract IBaseService initRelatedService();

    protected abstract BaseModel initRecord();

    protected abstract void initFormData();

    public void save(){
        try {
            if (getCurrentStep().isApplicationstepCreate()){
                relatedService.save(relatedRecord);
            }else if (getCurrentStep().isApplicationStepEdit()){
                relatedService.update(relatedRecord);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initEdition(Context context){
        String errors = getSelectedRecord().canBeEdited(context);

        if (Utilities.stringHasValue(errors)){
            Utilities.displayAlertDialog(context, errors);
            return;
        }else {
            getCurrentStep().changeToEdit();
        }
    }

    public void initRemotion(Context context){
        String errors = getSelectedRecord().canBeRemoved(context);

        if (Utilities.stringHasValue(errors)){
            Utilities.displayAlertDialog(context, errors);
            return;
        }else {
            getCurrentStep().changeToRemove();
        }
    }



    public BaseActivity getRelatedActivity() {
        return relatedActivity;
    }

    public void setRelatedActivity(BaseActivity relatedActivity) {
        this.relatedActivity = relatedActivity;
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        callbacks.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        callbacks.remove(callback);
    }

    /**
     * Notifies observers that all properties of this instance have changed.
     */
    protected void notifyChange() {
        callbacks.notifyCallbacks(this, 0, null);
    }

    /**
     * Notifies observers that a specific property has changed. The getter for the
     * property that changes should be marked with the @Bindable annotation to
     * generate a field in the BR class to be used as the fieldId parameter.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    protected void notifyPropertyChanged(int fieldId) {
        callbacks.notifyCallbacks(this, fieldId, null);
    }

    @Bindable
    public ApplicationStep getCurrentStep(){
        return getRelatedActivity().getApplicationStep();
    }

    public String getAppVersionNumber(){
        if (getRelatedActivity() == null) return null;
        return "iDART Mobile v"+getRelatedActivity().getAppVersionNumber();
    }

    public String getAppVersionName(){
        if (getRelatedActivity() == null) return null;
        return "iDART Mobile v"+getRelatedActivity().getAppVersionName();
    }

    public Listble getSelectedListble() {
        return selectedListble;
    }

    public void setSelectedListble(Listble selectedListble) {
        this.selectedListble = selectedListble;
    }

    public boolean isViewListEditButton() {
        return viewListEditButton;
    }

    public void setViewListEditButton(boolean viewListEditButton) {
        this.viewListEditButton = viewListEditButton;
    }

    public boolean isViewListRemoveButton() {
        return viewListRemoveButton;
    }

    public void setViewListRemoveButton(boolean viewListRemoveButton) {
        this.viewListRemoveButton = viewListRemoveButton;
    }

    @Bindable
    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        notifyPropertyChanged(BR.currentUser);
    }

    @Bindable
    public Clinic getCurrentClinic() {
        return currentClinic;
    }

    public void setCurrentClinic(Clinic currentClinic) {
        this.currentClinic = currentClinic;
        notifyPropertyChanged(BR.currentClinic);
    }

    @Bindable
    public ClinicSector getCurrentClinicSector() {
        return currentClinicSector;
    }

    public void setCurrentClinicSector(ClinicSector currentClinicSector) {
        this.currentClinicSector = currentClinicSector;
        notifyPropertyChanged(BR.currentClinicSector);
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    @Bindable
    public BaseModel getRelatedRecord() {
        return relatedRecord;
    }

    @Override
    public void doOnConfirmed() {

    }

    @Override
    public void doOnDeny() {

    }

    public GenericFragment getRelatedFragment() {
        return relatedFragment;
    }

    public void setRelatedFragment(GenericFragment relatedFragment) {
        this.relatedFragment = relatedFragment;
    }

    public IBaseService getRelatedService() {
        return relatedService;
    }

    public BaseModel getSelectedRecord() {
        return relatedRecord;
    }

    public void setSelectedRecord(Serializable selectedRecord) {
        this.relatedRecord = (BaseModel) selectedRecord;
    }

    public void backToPreviusActivity(){
        getRelatedActivity().finish();
    }

    public abstract void preInit();

    public List<AppSettings> getSystemSettings(){
        return this.systemSettings;
    }

    public void setSystemSettings(List<AppSettings> systemSettings) {
        this.systemSettings = systemSettings;
    }

    public List<Listble> getSelectedListbles() {
        return selectedListbles;
    }

    public void addSelectedListable(Listble listble){
        this.selectedListbles.add(listble);
    }

    public void removeSelectedListable(Listble listble){
        this.selectedListbles.remove(listble);
    }

    public void saveLastSyncDateTime(){
        settingsPreferences.put(APP_LAST_SYNC_DATE, DateUtilities.formatToDDMMYYYY_HHMISS(DateUtilities.getCurrentDate()));
    }

    public String getSettingsData(String key) {
        if (settingsPreferences.containsKey(key)) {
            return  settingsPreferences.getString(key);
        }

        return "Sem data";
    }

    public String getAppLastSyncDate(){
        return "Última Sincronização: "+getSettingsData(APP_LAST_SYNC_DATE);
    }

    public void generatePDFFile(String fileTitle, HashMap<String, HashMap<String, Float>> dataMap, String fileName) throws IOException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/sdcard");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
        }
        String pdfname = fileName;
        File pdfFile = new File(docsFolder.getAbsolutePath(), pdfname);
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document(PageSize.A4);

        PdfPTable tableImage = new PdfPTable(1);
        tableImage.setWidthPercentage(100);
        tableImage.setWidths(new float[]{3});
        tableImage.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        tableImage.getDefaultCell().setVerticalAlignment(Element.ALIGN_CENTER);
        tableImage.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

        PdfPCell cell;

        Drawable d = getApplication().getResources().getDrawable(R.mipmap.ic_misau);
        Bitmap bmp =((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        Image image = Image.getInstance(stream.toByteArray());
        image.setWidthPercentage(80);
        image.scaleToFit(105,55);
        cell = new PdfPCell(image);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setUseAscender(true);
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setPadding(2f);
        tableImage.addCell(cell);

        List<String> columnTitles = new ArrayList<>();
        List<String> tableContent = new ArrayList<>();

        float[] columnsSize = new float[0];
        int counter = 0;

        Iterator it = dataMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, HashMap<String, Float>> pair = (Map.Entry<String, HashMap<String, Float>>) it.next();

            columnTitles.add((String) pair.getKey());

            Map.Entry<String,Float> entry = pair.getValue().entrySet().iterator().next();
            tableContent.add(entry.getKey());

            columnsSize[counter] = entry.getValue();

            //System.out.println(pair.getKey() + " = " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }

        PdfPTable table = new PdfPTable(columnsSize);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setFixedHeight(50);
        table.setTotalWidth(PageSize.A4.getWidth());
        table.setWidthPercentage(100);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        for (String s : columnTitles){
            table.addCell(s);
        }

        table.setHeaderRows(1);
        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j = 0; j < cells.length; j++) {
            cells[j].setBackgroundColor(BaseColor.GRAY);
        }

        for (String s : tableContent){
            table.addCell(s);
        }

        PdfWriter.getInstance(document, output);
        document.open();
        document.add(tableImage);
        // document.add(image);
        Font f = new Font(Font.FontFamily.TIMES_ROMAN, 35.0f, Font.UNDERLINE, BaseColor.RED);
        Font g = new Font(Font.FontFamily.TIMES_ROMAN, 20.0f, Font.NORMAL, BaseColor.RED);
        document.add(new Paragraph(fileTitle +"\n\n", f));

        document.add(table);

        document.close();

        Utilities.previewPdfFiles(getApplication(),pdfFile);

    }

    public void initLoadingDialog() {
        this.loadingDialog = new LoadingDialog(getRelatedActivity());
    }

    public LoadingDialog getLoadingDialog() {
        return loadingDialog;
    }
}
