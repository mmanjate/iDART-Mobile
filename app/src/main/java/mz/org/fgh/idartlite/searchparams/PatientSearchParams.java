package mz.org.fgh.idartlite.searchparams;

import mz.org.fgh.idartlite.model.Patient;

public class PatientSearchParams extends AbstractSearchParams<Patient> {

    private String nid;

    private String name;

    private String surName;

    private String searchParam;

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSearchParam() {
        return searchParam;
    }

    public void setSearchParam(String searchParam) {
        this.searchParam = searchParam;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }
}
