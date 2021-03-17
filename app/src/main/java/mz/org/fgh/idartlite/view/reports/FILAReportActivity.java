package mz.org.fgh.idartlite.view.reports;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.recyclerview.listable.ListbleReportFilaRecycleViewAdapter;
import mz.org.fgh.idartlite.adapter.recyclerview.patient.ContentListPatientAdapter;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.databinding.ActivityFilaDispenseReportDialogBinding;
import mz.org.fgh.idartlite.databinding.ActivityFilaReportBinding;
import mz.org.fgh.idartlite.listener.recyclerView.ClickListener;
import mz.org.fgh.idartlite.listener.recyclerView.IOnLoadMoreListener;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.util.DateUtilities;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.about.AboutActivity;
import mz.org.fgh.idartlite.viewmodel.dispense.FILAReportVM;

public class FILAReportActivity extends BaseActivity {

    private RecyclerView recyclerPatients;
    private ActivityFilaReportBinding filaReportBinding;
    private ContentListPatientAdapter adapter;
    private ListbleReportFilaRecycleViewAdapter listbleReportFilaRecycleViewAdapter;


    private static final String TAG = "FILAReportActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        filaReportBinding=DataBindingUtil.setContentView(this, R.layout.activity_fila_report);

        recyclerPatients = filaReportBinding.reyclerPatients;

        filaReportBinding.setViewModel(getRelatedViewModel());



        filaReportBinding.executePendingBindings();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerPatients.setLayoutManager(layoutManager);
        recyclerPatients.setHasFixedSize(true);
        recyclerPatients.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        recyclerPatients.addOnItemTouchListener(
                new ClickListener(getApplicationContext(), recyclerPatients, new ClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        showDialog(FILAReportActivity.this,getRelatedViewModel().getSearchResults().get(position));
                    }

                    @Override
                    public void onLongItemClick(View view, int position) { }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }
                )
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            //Back button
            case R.id.about:
                //If this activity started from other activity
                Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void displaySearchResult() {
        if (adapter == null) {
            adapter = new ContentListPatientAdapter(recyclerPatients, getRelatedViewModel().getAllDisplyedRecords(), this,false);
            recyclerPatients.setAdapter(adapter);
        }

        if (adapter.getOnLoadMoreListener() == null) {
            adapter.setOnLoadMoreListener(new IOnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    getRelatedViewModel().loadMoreRecords(recyclerPatients, adapter);
                }
            });
        }
    }

    @Override
    public FILAReportVM getRelatedViewModel() {
        return (FILAReportVM) super.getRelatedViewModel();
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(FILAReportVM.class);
    }

    public void showDialog(Activity activity, Patient patient) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
       // dialog.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        ActivityFilaDispenseReportDialogBinding dialogFila =DataBindingUtil.inflate(LayoutInflater.from(activity.getApplicationContext()), R.layout. activity_fila_dispense_report_dialog, null, false);
       // setContentView(dialogFila.getRoot());
      dialog.setContentView(dialogFila.getRoot());

       // setContentView(binding.getRoot());

        dialog.setCancelable(false);
      //  dialog.setContentView(R.layout.activity_fila_dispense_report_dialog);

        this.getRelatedViewModel().setPatient(patient);
        dialogFila.setViewModel(getRelatedViewModel());

      //  DispenseService dispenseService = new DispenseService(activity.getApplication(), getCurrentUser());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity.getApplication());


        RecyclerView recyclerDispenses = dialogFila.reyclerDispenses;

        recyclerDispenses.setLayoutManager(layoutManager);
        recyclerDispenses.setHasFixedSize(true);
        recyclerDispenses.addItemDecoration(new DividerItemDecoration(activity.getApplication(), LinearLayout.VERTICAL));
        List<Dispense> dispenses = new ArrayList<>();
        try {
            dispenses = getRelatedViewModel().getAllDispensesByPatient(patient);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        listbleReportFilaRecycleViewAdapter = new ListbleReportFilaRecycleViewAdapter(recyclerDispenses, dispenses, (BaseActivity) activity);
        recyclerDispenses.setAdapter(listbleReportFilaRecycleViewAdapter);



      //  Button ok = (Button) dialog.findViewById(R.id.buttonId);
        dialogFila.buttonId .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        List<Dispense> finalDispenses = dispenses;
        dialogFila.buttonPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPdfDocument(finalDispenses,patient);
            }
        });

        if (!dispenses.isEmpty()) {
            dialog.show();
        }
        else {
            Utilities.displayAlertDialog(this, getString(R.string.paciente_nao_tem_dispensas)).show();
        }

    }

    public void createPdfDocument(List<Dispense> dispenses,Patient patient)  {
        try {
            createPdf(dispenses,patient);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }


    @SuppressLint("LongLogTag")
    private void createPdf(List<Dispense> dispenses,Patient patient) throws IOException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/sdcard");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, getString(R.string.criar_no_directorio));
        }
        String pdfname = "FILA_NID_"+patient.getNid()+".pdf";
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


        PdfPTable table = new PdfPTable(new float[]{3, 3, 3,3});
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setFixedHeight(50);
        table.setTotalWidth(PageSize.A4.getWidth());
        table.setWidthPercentage(100);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(getString(R.string.dta_levantamento));
        table.addCell(getString(R.string.dt_proximo_levantamento));
        table.addCell(getString(R.string.therapeutic_regimen));
        table.addCell(getString(R.string.dispense_type_report));
        table.setHeaderRows(1);
        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j = 0; j < cells.length; j++) {
            cells[j].setBackgroundColor(BaseColor.GRAY);
        }

        for (Dispense dispense:dispenses){

            table.addCell(String.valueOf(DateUtilities.formatToDDMMYYYY(dispense.getPickupDate())));
            table.addCell(String.valueOf(DateUtilities.formatToDDMMYYYY(dispense.getNextPickupDate())));
            table.addCell(String.valueOf(dispense.getPrescription().getTherapeuticRegimen().getDescription()));
            table.addCell(String.valueOf(dispense.getPrescription().getDispenseType().getDescription()));
        }

        PdfWriter.getInstance(document, output);
        document.open();
        document.add(tableImage);
        Font f = new Font(Font.FontFamily.TIMES_ROMAN, 35.0f, Font.UNDERLINE, BaseColor.RED);
        Font g = new Font(Font.FontFamily.TIMES_ROMAN, 20.0f, Font.BOLD, BaseColor.BLACK);
        Font normal = new Font(Font.FontFamily.TIMES_ROMAN, 15.0f, Font.NORMAL, BaseColor.BLACK);
        document.add(new Paragraph(getString(R.string.dispensas_paciente_fila), f));
        document.add(new Paragraph("\n\n"));
        document.add(new Paragraph(getString(R.string.nid)+patient.getNid(),g));
        document.add(new Paragraph(getString(R.string.name)+patient.getFullName(),normal));
        document.add(new Paragraph(getString(R.string.genero) + patient.getGender(),normal));
        document.add(new Paragraph(getString(R.string.idade)+patient.getAge(),normal));
        document.add(new Paragraph("\n\n"));

        document.add(table);

        document.close();

        Utilities.previewPdfFiles(this,pdfFile );
    }
    }