package mz.org.fgh.idartlite.view.reports;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;

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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.recyclerview.report.AdverseReactionReportAdapter;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.databinding.ActivityAdverseReactionReportBinding;
import mz.org.fgh.idartlite.listener.recyclerView.IOnLoadMoreListener;
import mz.org.fgh.idartlite.model.ClinicInformation;
import mz.org.fgh.idartlite.util.DateUtilities;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.viewmodel.report.AdverseReactionReportVM;

public class AdverseReactionReportActivity extends BaseActivity {

    private ActivityAdverseReactionReportBinding adverseReactionReportBinding;
    private RecyclerView reportRecycler;
    private AdverseReactionReportAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = this.getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null) {
                getRelatedViewModel().setReportType((String) bundle.getSerializable(ClinicInformation.PARAM_RAM_STATUS));
            }
        }

        adverseReactionReportBinding = DataBindingUtil.setContentView(this, R.layout.activity_adverse_reaction_report);

        reportRecycler = adverseReactionReportBinding.reyclerPatient;



        adverseReactionReportBinding.setViewModel(getRelatedViewModel());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(v -> finish());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        reportRecycler.setLayoutManager(layoutManager);
        reportRecycler.setHasFixedSize(true);
        reportRecycler.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        adverseReactionReportBinding.edtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear, mMonth, mDay;

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(AdverseReactionReportActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        adverseReactionReportBinding.edtStartDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        adverseReactionReportBinding.edtStartDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    int mYear, mMonth, mDay;

                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(AdverseReactionReportActivity.this, new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            adverseReactionReportBinding.edtStartDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });

        adverseReactionReportBinding.edtEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear, mMonth, mDay;

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(AdverseReactionReportActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        adverseReactionReportBinding.edtEndDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        adverseReactionReportBinding.edtEndDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    int mYear, mMonth, mDay;

                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(AdverseReactionReportActivity.this, new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            adverseReactionReportBinding.edtEndDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });
    }

    @Override
    public AdverseReactionReportVM getRelatedViewModel() {
        return (AdverseReactionReportVM) super.getRelatedViewModel();
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(AdverseReactionReportVM.class);
    }

    public void displaySearchResult() {
        if (adapter == null) {

            adapter = new AdverseReactionReportAdapter(reportRecycler, getRelatedViewModel().getAllDisplyedRecords(), this);

            reportRecycler.setAdapter(adapter);
        }else adapter.notifyDataSetChanged();

        if (adapter.getOnLoadMoreListener() == null) {
            adapter.setOnLoadMoreListener(new IOnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    getRelatedViewModel().loadMoreRecords(reportRecycler, adapter);
                }
            });
        }

    }

    public void createPdfDocument() throws IOException, DocumentException {
        createPdf(getRelatedViewModel().getAllDisplyedRecords());
    }


    @SuppressLint("LongLogTag")
    private void createPdf(List<ClinicInformation> clinicInformations) throws IOException, DocumentException {
        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/sdcard");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
        }
        String pdfname = "Lista_RAMs"+ DateUtilities.formatToDDMMYYYY(new Date())+".pdf";
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


        PdfPTable table = new PdfPTable(new float[]{4, 4, 3, 3, 3, 3,3});
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setFixedHeight(50);
        table.setTotalWidth(PageSize.A4.getWidth());
        table.setWidthPercentage(100);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(getString(R.string.nid_report));
        table.addCell(getString(R.string.name_report));
        table.addCell("Gėnero");
        table.addCell("Idade");
        table.addCell("Possui RAM");
        table.addCell("Unidade Sanitária");
        table.addCell("Data do Rastreio");

        table.setHeaderRows(1);
        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j = 0; j < cells.length; j++) {
            cells[j].setBackgroundColor(BaseColor.GRAY);
        }

        for (ClinicInformation clinicInformation : clinicInformations){

            table.addCell(String.valueOf(clinicInformation.getPatient().getNid()));
            table.addCell(String.valueOf(clinicInformation.getPatient().getFullName()));
            table.addCell(String.valueOf(clinicInformation.getPatient().getGender()));
            table.addCell(String.valueOf(clinicInformation.getPatient().getAge()));
            table.addCell(clinicInformation.isAdverseReactionOfMedicine() ? "SIM" : "Não");
            table.addCell(String.valueOf(clinicInformation.getPatient().getEpisodes1().iterator().next().getSanitaryUnit()));
            table.addCell(String.valueOf(DateUtilities.formatToDDMMYYYY(clinicInformation.getRegisterDate())));
        }

        PdfWriter.getInstance(document, output);
        document.open();
        document.add(tableImage);

        Font f = new Font(Font.FontFamily.TIMES_ROMAN, 16.0f, Font.UNDERLINE, BaseColor.RED);
        Font g = new Font(Font.FontFamily.TIMES_ROMAN, 20.0f, Font.NORMAL, BaseColor.RED);

        Paragraph titulo = new Paragraph(getRelatedViewModel().getReportTitle()+" \n", g);
        titulo.setAlignment(Element.ALIGN_CENTER);

        Paragraph subTitulo = new Paragraph("Período de "+DateUtilities.formatToDDMMYYYY(getRelatedViewModel().getSearchParams().getStartdate())+" à "+DateUtilities.formatToDDMMYYYY(getRelatedViewModel().getSearchParams().getEndDate())+ "\n\n", f);
        subTitulo.setAlignment(Element.ALIGN_CENTER);

        document.add(titulo);
        document.add(subTitulo);

        document.add(table);

        document.close();

        Utilities.previewPdfFiles(this,pdfFile );
    }
}