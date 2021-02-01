package mz.org.fgh.idartlite.view.stock.inventory;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.recyclerview.listable.ListbleRecycleViewAdapter;
import mz.org.fgh.idartlite.adapter.spinner.listable.ListableSpinnerAdapter;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.databinding.ActivityIventoryBinding;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.StockAjustment;
import mz.org.fgh.idartlite.util.DateUtilities;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.viewmodel.stock.InventoryVM;

public class IventoryActivity extends BaseActivity {

    private ListableSpinnerAdapter drugArrayAdapter;

    private RecyclerView rcvSelectedDrugs;

    private ListbleRecycleViewAdapter listbleRecycleViewAdapter;

    private ActivityIventoryBinding iventoryBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iventoryBinding = DataBindingUtil.setContentView(this, R.layout.activity_iventory);

        List<Drug> drugs = new ArrayList<>();
        drugs.addAll(getRelatedViewModel().getDrugs());

        populateDrugs(drugs);



        iventoryBinding.setViewModel(getRelatedViewModel());

        iventoryBinding.autCmpDrugs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                getRelatedViewModel().setSelectedDrug((Drug) adapterView.getItemAtPosition(pos));
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRelatedViewModel().back();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utilities.listHasElements(getRelatedViewModel().getAdjustmentList())) displaySelectedDrugStockAjustmentInfo();
    }

    @Override
    public void onBackPressed() {
        getRelatedViewModel().back();
    }

    public void populateDrugs(List<Drug> drugs){
        drugArrayAdapter = new ListableSpinnerAdapter(this, R.layout.simple_auto_complete_item, drugs);
        iventoryBinding.autCmpDrugs.setAdapter(drugArrayAdapter);
        iventoryBinding.autCmpDrugs.setThreshold(1);
    }

    public void changeFormSectionVisibility(View view) {
        if (view.equals(iventoryBinding.initialData)) {
            if (iventoryBinding.initialDataLyt.getVisibility() == View.VISIBLE){
                iventoryBinding.ibtnInitialData.animate().setDuration(200).rotation(180);
                Utilities.collapse(iventoryBinding.initialDataLyt);
            }else {
                iventoryBinding.ibtnInitialData.animate().setDuration(200).rotation(0);
                Utilities.expand(iventoryBinding.initialDataLyt);
            }
        }
    }

    public void displaySelectedDrugStockAjustmentInfo(){
        if (listbleRecycleViewAdapter != null) {
            listbleRecycleViewAdapter.notifyDataSetChanged();

        }else {
            rcvSelectedDrugs = iventoryBinding.rcvSelectedDrugs;

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(IventoryActivity.this);
            rcvSelectedDrugs.setLayoutManager(mLayoutManager);
            rcvSelectedDrugs.setItemAnimator(new DefaultItemAnimator());
            rcvSelectedDrugs.addItemDecoration(new DividerItemDecoration(IventoryActivity.this, 0));

            listbleRecycleViewAdapter = new ListbleRecycleViewAdapter(rcvSelectedDrugs, getRelatedViewModel().getAdjustmentList(), this);
            rcvSelectedDrugs.setAdapter(listbleRecycleViewAdapter);
        }

        Utilities.hideSoftKeyboard(this);
        iventoryBinding.autCmpDrugs.dismissDropDown();
    }

    public void displayResumeStockAjustmentInfo(){


            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(IventoryActivity.this);
            rcvSelectedDrugs.setLayoutManager(mLayoutManager);
            rcvSelectedDrugs.setItemAnimator(new DefaultItemAnimator());
            rcvSelectedDrugs.addItemDecoration(new DividerItemDecoration(IventoryActivity.this, 0));

            listbleRecycleViewAdapter = new ListbleRecycleViewAdapter(rcvSelectedDrugs, getRelatedViewModel().getAdjustmentList(), this);
            rcvSelectedDrugs.setAdapter(listbleRecycleViewAdapter);

    }

    @Override
    public InventoryVM getRelatedViewModel() {
        return (InventoryVM) super.getRelatedViewModel();
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(InventoryVM.class);
    }

    public void summarizeView(int visibility) {
        iventoryBinding.lblDrugs.setVisibility(visibility);
        iventoryBinding.autCmpDrugs.setVisibility(visibility);
        iventoryBinding.lblCount.setVisibility(visibility);
        iventoryBinding.navigation.setVisibility(visibility);
        iventoryBinding.btnCloseInventory.setVisibility(visibility);
        iventoryBinding.save.setVisibility(visibility == View.VISIBLE ? View.GONE : View.VISIBLE);
        iventoryBinding.confirmation.setVisibility(visibility == View.VISIBLE ? View.GONE : View.VISIBLE);
    }

    public void createPdf(List<Drug> drugs) throws IOException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/sdcard");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
        }

        String pdfname = "folha_de_contagem_de_stock.pdf";
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

        Drawable d = getResources().getDrawable(R.mipmap.ic_misau);
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


        PdfPTable table = new PdfPTable(new float[]{2, 3, 3,2, 2, 4});
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setFixedHeight(50);
        table.setTotalWidth(PageSize.A4.getWidth());
        table.setWidthPercentage(100);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(getString(R.string.lote));
        table.addCell(getString(R.string.drug));
        table.addCell("Validade");
        table.addCell("Saldo Actual");
        table.addCell("Frascos contados");
        table.addCell("Notas");
        table.setHeaderRows(1);

        PdfPCell[] cells = table.getRow(0).getCells();

        for (int j = 0; j < cells.length; j++) {
            cells[j].setBackgroundColor(BaseColor.GRAY);
        }

        for (Drug drug : drugs) {
            for (StockAjustment ajustment : drug.getAjustmentInfo()) {
                table.addCell(ajustment.getLote());
                table.addCell(ajustment.getDescription());
                table.addCell(String.valueOf(DateUtilities.formatToDDMMYYYY(ajustment.getValidate())));
                table.addCell(String.valueOf(ajustment.getSaldoActual()));
                table.addCell(ajustment.getQtyToModify() > 0 ? String.valueOf(ajustment.getQtyToModify()) : "");
                table.addCell(ajustment.getNotes());
            }
        }

        PdfWriter.getInstance(document, output);
        document.open();
        document.add(tableImage);
        // document.add(image);
        Font f = new Font(Font.FontFamily.TIMES_ROMAN, 18.0f, Font.UNDERLINE, BaseColor.RED);
        document.add(new Paragraph("Folha de Contagem de Stock \n\n", f));

        document.add(table);

        document.close();

        Utilities.previewPdfFiles(this,pdfFile );
    }

}