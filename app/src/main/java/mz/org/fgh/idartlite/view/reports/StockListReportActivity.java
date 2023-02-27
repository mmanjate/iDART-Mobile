package mz.org.fgh.idartlite.view.reports;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.recyclerview.stock.StockListAdapter;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.databinding.ActivityStockListReportBinding;
import mz.org.fgh.idartlite.listener.recyclerView.IOnLoadMoreListener;
import mz.org.fgh.idartlite.service.drug.DrugService;
import mz.org.fgh.idartlite.service.drug.IDrugService;
import mz.org.fgh.idartlite.util.DateUtilities;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.about.AboutActivity;
import mz.org.fgh.idartlite.model.DrugReportModel;
import mz.org.fgh.idartlite.viewmodel.stock.DrugReportModelVM;
import mz.org.fgh.idartlite.model.StockReportModel;

public class StockListReportActivity extends BaseActivity {

    private RecyclerView recyclerStock;
    private ActivityStockListReportBinding activityStockListReportBinding;
    private StockListAdapter adapter;

    private IDrugService drugService;
    private static final String TAG = "StockListReportActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityStockListReportBinding = DataBindingUtil.setContentView(this, R.layout.activity_stock_list_report);

        recyclerStock = activityStockListReportBinding.recyclerStock; //Area onde sera visivel o relatorio

        activityStockListReportBinding.setViewModel(getRelatedViewModel());

        activityStockListReportBinding.executePendingBindings();
        drugService = new DrugService(getApplication(), getCurrentUser());

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
        recyclerStock.setLayoutManager(layoutManager);
        recyclerStock.setHasFixedSize(true);
        recyclerStock.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        if (adapter == null) {
            recyclerStock.setAdapter(adapter);
        }

        // Setar startDate no campo
        activityStockListReportBinding.edtSearchParam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear, mMonth, mDay;

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(StockListReportActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        activityStockListReportBinding.edtSearchParam.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        activityStockListReportBinding.edtSearchParam.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    int mYear, mMonth, mDay;

                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(StockListReportActivity.this, new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            activityStockListReportBinding.edtSearchParam.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });

        activityStockListReportBinding.edtSearchParam2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear, mMonth, mDay;

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(StockListReportActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        activityStockListReportBinding.edtSearchParam2.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        activityStockListReportBinding.edtSearchParam2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    int mYear, mMonth, mDay;

                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(StockListReportActivity.this, new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            activityStockListReportBinding.edtSearchParam2.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });

    } //onCreate

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(DrugReportModelVM.class);
    }

    @Override
    public DrugReportModelVM getRelatedViewModel() {
        return (DrugReportModelVM) super.getRelatedViewModel();
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
            adapter = new StockListAdapter(recyclerStock, getRelatedViewModel().getAllDisplyedRecords(), this);
            recyclerStock.setAdapter(adapter);
        }

        if (adapter.getOnLoadMoreListener() == null) {
            adapter.setOnLoadMoreListener(new IOnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    getRelatedViewModel().loadMoreRecords(recyclerStock, adapter);
                }
            });
        }

    }

    public void createPdfDocument() throws IOException, DocumentException, SQLException {
        createPdf();
    }

    @SuppressLint("RestrictedApi")
    public void generatePdfButton(boolean show){
        FloatingActionButton generatePdf = activityStockListReportBinding.generatePdf;
        if(show) generatePdf.setVisibility(View.VISIBLE);
        else {generatePdf.setVisibility(View.GONE);}
    }

    @SuppressLint("LongLogTag")
    private void createPdf() throws IOException, DocumentException, SQLException {
        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/sdcard");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
        }
        String pdfname = "ListaDeStock"+ DateUtilities.formatToDDMMYYYY(new Date())+".pdf";
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


        PdfPTable table = new PdfPTable(new float[]{3,4, 3, 3,3,3});
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setFixedHeight(50);
        table.setTotalWidth(PageSize.A4.getWidth());
        table.setWidthPercentage(100);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(getString(R.string.batch_number));
        table.addCell(getString(R.string.drug));
        table.addCell(getString(R.string.entrances));
        table.addCell(getString(R.string.packs));
        table.addCell(getString(R.string.existing_stock));
        table.addCell(getString(R.string.expiry_date));
        table.setHeaderRows(1);

        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j = 0; j < cells.length; j++) {
            cells[j].setBackgroundColor(BaseColor.GRAY);
        }

         List<DrugReportModel> listDrugs =drugService.getAllWithLoteAndNotExpired(getRelatedViewModel().getSearchParams().getStartdate(), getRelatedViewModel().getSearchParams().getEndDate());

        PdfPTable tableParent = new PdfPTable(new float[]{3,4, 3});
    for (int i= 0, k=0; i<listDrugs.size();i++,k+=3) {
        tableParent.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableParent.getDefaultCell().setFixedHeight(50);
        tableParent.setTotalWidth(PageSize.A4.getWidth());
        tableParent.setWidthPercentage(100);
        tableParent.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

        tableParent.addCell(getString(R.string.fnm));
        tableParent.addCell(getString(R.string.drug));
        tableParent.addCell(getString(R.string.balance));

        tableParent.setHeaderRows(1);


        PdfPCell[] cellsParent = tableParent.getRow(k).getCells();
        for (int j = 0; j < cellsParent.length; j++) {
            try {
                cellsParent[j].setBackgroundColor(BaseColor.GRAY);
            } catch(Exception e) {


            }
        }

        PdfPTable tableChild = new PdfPTable(new float[]{3,4, 3,3,3});
        tableChild.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableChild.getDefaultCell().setFixedHeight(50);
        tableChild.setTotalWidth(PageSize.A4.getWidth());
        tableChild.setWidthPercentage(100);
        tableChild.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableChild.addCell(getString(R.string.batch_number));
        tableChild.addCell(getString(R.string.entrances));
        tableChild.addCell(getString(R.string.packs));
        tableChild.addCell(getString(R.string.existing_stock));
        tableChild.addCell(getString(R.string.expiry_date));
        for (StockReportModel stock : listDrugs.get(i).getStockReportList()){
            tableChild.addCell(stock.getBatchNumber());
            tableChild.addCell(stock.getEntranceQtd());
            tableChild.addCell(stock.getDispenseQtd());
            tableChild.addCell(stock.getExistingStock());
            tableChild.addCell(stock.getExpiryDate());
        }
        tableParent.addCell(listDrugs.get(i).getFnm());
        tableParent.addCell(listDrugs.get(i).getDrugName());
        tableParent.addCell(String.valueOf(listDrugs.get(i).getBalance()));
        PdfPCell newCell = new PdfPCell(tableChild);
        newCell.setColspan(3);
        tableParent.addCell(newCell);
    }

        PdfWriter.getInstance(document, output);
        document.open();
        document.add(tableImage);
        // document.add(image);
        Font f = new Font(Font.FontFamily.TIMES_ROMAN, 16.0f, Font.UNDERLINE, BaseColor.RED);
        Font g = new Font(Font.FontFamily.TIMES_ROMAN, 20.0f, Font.NORMAL, BaseColor.RED);

        Paragraph titulo = new Paragraph("Lista de Stock \n", g);
        titulo.setAlignment(Element.ALIGN_CENTER);

        Paragraph subTitulo = new Paragraph("Período de "+DateUtilities.formatToDDMMYYYY(getRelatedViewModel().getSearchParams().getStartdate())+" à "+DateUtilities.formatToDDMMYYYY(getRelatedViewModel().getSearchParams().getEndDate())+ "\n\n", f);
        subTitulo.setAlignment(Element.ALIGN_CENTER);

        document.add(titulo);
        document.add(subTitulo);



        document.add(tableParent);

        document.close();

        Utilities.previewPdfFiles(this,pdfFile );
    }


}
