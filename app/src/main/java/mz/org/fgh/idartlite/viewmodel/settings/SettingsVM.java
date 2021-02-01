package mz.org.fgh.idartlite.viewmodel.settings;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.idartlite.BR;
import mz.org.fgh.idartlite.adapter.recyclerview.listable.Listble;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.model.AppSettings;
import mz.org.fgh.idartlite.service.settings.AppSettingsService;
import mz.org.fgh.idartlite.service.settings.IAppSettingsService;
import mz.org.fgh.idartlite.util.SimpleValue;
import mz.org.fgh.idartlite.util.Utilities;

public class SettingsVM extends BaseViewModel {

    private List<SimpleValue> syncPeriodList;
    private List<SimpleValue> metadataSyncPeriodList;
    private List<SimpleValue> dataDeletionPeriodList;

    private AppSettings serverUrl;

    private AppSettings dataSyncPeriod;

    private AppSettings metadataSyncPeriod;

    private AppSettings dataRemotionPeriod;

    private List<AppSettings> appSettings;


    public SettingsVM(@NonNull Application application) {
        super(application);
    }

    @Override
    protected IBaseService initRelatedService() {
        return new AppSettingsService(getApplication());
    }

    @Override
    public IAppSettingsService getRelatedService() {
        return (IAppSettingsService) super.getRelatedService();
    }

    @Override
    protected BaseModel initRecord() {
        return null;
    }

    @Override
    protected void initFormData() {

        serverUrl = new AppSettings();
        dataSyncPeriod = new AppSettings();
        metadataSyncPeriod = new AppSettings();
        dataRemotionPeriod = new AppSettings();

        syncPeriodList = new ArrayList<>();
        metadataSyncPeriodList = new ArrayList<>();
        dataDeletionPeriodList = new ArrayList<>();

        loadSyncPeriods();
        loadMetadataSyncPeriods();
        loadDataDeletionPeriods();

        loadSettings();
    }

    private void loadSettings() {
        try {
            appSettings = getRelatedService().getAll();

            if (Utilities.listHasElements(appSettings)){
                for (AppSettings appSettings : appSettings){
                    if (appSettings.isUrlSetting()) serverUrl = appSettings;
                    else if (appSettings.isDataSyncPeriodSetting()) dataSyncPeriod = appSettings;
                    else if (appSettings.isMetadataSyncPediorSetting()) metadataSyncPeriod = appSettings;
                    else if (appSettings.isDataRemotionPeriodSetting()) dataRemotionPeriod = appSettings;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void preInit() {


    }

    private void loadSyncPeriods(){
        syncPeriodList.add(new SimpleValue());
        syncPeriodList.add(SimpleValue.fastCreate(1, "1 Hora"));
        syncPeriodList.add(SimpleValue.fastCreate(2, "2 Horas"));
        syncPeriodList.add(SimpleValue.fastCreate(3, "3 Horas"));
        syncPeriodList.add(SimpleValue.fastCreate(4, "4 Horas"));
        syncPeriodList.add(SimpleValue.fastCreate(5, "5 Horas"));
        syncPeriodList.add(SimpleValue.fastCreate(6, "6 Horas"));
        syncPeriodList.add(SimpleValue.fastCreate(7, "7 Horas"));
        syncPeriodList.add(SimpleValue.fastCreate(8, "8 Horas (Padrão)"));
        syncPeriodList.add(SimpleValue.fastCreate(9, "9 Horas"));
        syncPeriodList.add(SimpleValue.fastCreate(10, "10 Horas"));
    }

    private void loadMetadataSyncPeriods(){
        metadataSyncPeriodList.add(new SimpleValue());
        metadataSyncPeriodList.add(SimpleValue.fastCreate(1, "1 Semana (Padrão)"));
        metadataSyncPeriodList.add(SimpleValue.fastCreate(2, "2 Semanas"));
        metadataSyncPeriodList.add(SimpleValue.fastCreate(3, "3 Semanas"));
        metadataSyncPeriodList.add(SimpleValue.fastCreate(4, "4 Semanas"));
    }

    private void loadDataDeletionPeriods(){
        dataDeletionPeriodList.add(new SimpleValue());
        dataDeletionPeriodList.add(SimpleValue.fastCreate(1, "Último mês"));
        dataDeletionPeriodList.add(SimpleValue.fastCreate(2, "Últimos 2 meses (Padrão)"));
        dataDeletionPeriodList.add(SimpleValue.fastCreate(3, "Últimos 3 meses"));
        dataDeletionPeriodList.add(SimpleValue.fastCreate(4, "Últimos 4 meses"));
    }


    @Bindable
    public Listble getDataSyncPeriod() {
        if (dataSyncPeriod == null || dataSyncPeriod.getDescription() == null) return null;
        return Utilities.findOnArray(this.syncPeriodList, SimpleValue.fastCreate(Integer.parseInt(dataSyncPeriod.getValue())));
    }

    public void setDataSyncPeriod(Listble dataSyncPeriod) {
        this.dataSyncPeriod.setValue(String.valueOf(dataSyncPeriod.getId()));
        if (dataSyncPeriod.getId() > 0) {
            this.dataSyncPeriod.setCode(AppSettings.DATA_SYNC_PERIOD_SETTING);

            this.dataSyncPeriod.setDescription("Periodo de sincronização de dados");

            saveSettings(this.dataSyncPeriod);
        }
        notifyPropertyChanged(BR.dataSyncPeriod);
    }

    @Bindable
    public Listble getMetadataSyncPeriod() {
        if (metadataSyncPeriod == null || metadataSyncPeriod.getDescription() == null) return null;

        return Utilities.findOnArray(this.metadataSyncPeriodList, SimpleValue.fastCreate(Integer.parseInt(metadataSyncPeriod.getValue())));
    }

    public void setMetadataSyncPeriod(Listble metadataSyncPeriod) {
        this.metadataSyncPeriod.setValue(String.valueOf(metadataSyncPeriod.getId()));


        if (metadataSyncPeriod.getId() > 0) {
            this.metadataSyncPeriod.setCode(AppSettings.METADATA_SYNC_PERIOD_SETTING);

            this.metadataSyncPeriod.setDescription("Periodo de sincronização de metadados");

            saveSettings(this.metadataSyncPeriod);
        }
        notifyPropertyChanged(BR.metadataSyncPeriod);
    }

    @Bindable
    public Listble getDataRemotionPeriod() {
        if (dataRemotionPeriod == null || dataRemotionPeriod.getDescription() == null) return null;

        return Utilities.findOnArray(this.dataDeletionPeriodList, SimpleValue.fastCreate(Integer.parseInt(dataRemotionPeriod.getValue())));
    }

    public void setDataRemotionPeriod(Listble dataRemotionPeriod) {
        this.dataRemotionPeriod.setValue(String.valueOf(dataRemotionPeriod.getId()));

        if (dataRemotionPeriod.getId() > 0){
            this.dataRemotionPeriod.setCode(AppSettings.DATA_REMOTION_PERIOD);

            this.dataRemotionPeriod.setDescription("Periodo de remoção de dados da base de dados");

            saveSettings(this.dataRemotionPeriod);
        }

        notifyPropertyChanged(BR.dataRemotionPeriod);
    }

    @Bindable
    public String getServerUrl(){
        return this.serverUrl.getValue();


    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl.setValue(serverUrl);

        if (!Utilities.stringHasValue(this.serverUrl.getCode())){
            this.serverUrl.setCode(AppSettings.SERVER_URL_SETTING);
            this.serverUrl.setDescription("Endereço do servidor central");
        }

        notifyPropertyChanged(BR.serverUrl);
    }

    public void changeDataViewStatus(View view){

    }

    public List<SimpleValue> getSyncPeriodList() {
        return syncPeriodList;
    }

    public List<SimpleValue> getMetadataSyncPeriodList() {
        return metadataSyncPeriodList;
    }

    public List<SimpleValue> getDataDeletionPeriodList() {
        return dataDeletionPeriodList;
    }

    public void saveUrlSettings(){
        saveSettings(this.serverUrl);

        Utilities.displayAlertDialog(getRelatedFragment().getContext(), "A configuração do servidor central foi gravada com sucesso.").show();
    }

    public void saveSettings(AppSettings settings){
        try {

            String errors = settings.isValid(getRelatedFragment().getContext());

            if (Utilities.stringHasValue(errors)){
                Utilities.displayAlertDialog(getRelatedFragment().getContext(), errors).show();
            }else {
                getRelatedService().saveSetting(settings);

                loadSettings();


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void syncDataNow(){
        getRelatedService().runDataSync();
    }

    public void syncMetadataNow(){
        getRelatedService().runMetadataSync();
    }

    public void initDataRemotionNow(){
        getRelatedService().runDataRemotion();
    }

}
