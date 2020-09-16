package mz.org.fgh.idartlite.model;

import android.os.Build;
import androidx.annotation.RequiresApi;
import mz.org.fgh.idartlite.base.BaseModel;

import java.util.Objects;

public class RegimenDrugs extends BaseModel {

	public static final String COLUMN_ID = "id";
	public static final String COLUMN_REGIMEN_ID = "regimen_id";

	private int id;

	private TherapeuticRegimen regimen_id;

	//private Drug drug_id;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TherapeuticRegimen getRegimen_id() {
		return regimen_id;
	}

	public void setRegimen_id(TherapeuticRegimen regimen_id) {
		this.regimen_id = regimen_id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RegimenDrugs that = (RegimenDrugs) o;
		return id == that.id &&
				regimen_id.equals(that.regimen_id);
	}

	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	@Override
	public int hashCode() {
		return Objects.hash(id, regimen_id);
	}

	@Override
	public String toString() {
		return "RegimenDrugs{" +
				"id=" + id +
				", regimen_id=" + regimen_id +
				'}';
	}
}
