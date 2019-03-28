package com.julia.bachelor;


import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.julia.bachelor.helperClass.Annet;
import com.julia.bachelor.helperClass.BondensMarked;
import com.julia.bachelor.helperClass.Hjemme;
import com.julia.bachelor.helperClass.Videresalg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

public class PdfCreatorActivity extends AppCompatActivity {
    private static final String TAG = "PdfCreatorActivity";
    private EditText Startdato, Sluttdato, Lagresom;
    private Button mCreateButton;
    private File pdfFile;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;
    private static final String KEY_BUNDLE = "Bundle";
    private static final String KEY_SALG = "AllSalg";
    private ArrayList<Object> solgt;

    @Override @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfcreator);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(KEY_BUNDLE);
        solgt = (ArrayList<Object>) bundle.getSerializable(KEY_SALG);
;

        Startdato = findViewById(R.id.startdato);
        Sluttdato = findViewById(R.id.sluttdato);
        Lagresom = findViewById(R.id.lagresom);
        mCreateButton = findViewById(R.id.button_create);
        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkDate(Startdato.getText().toString())|| !checkDate(Sluttdato.getText().toString())){
                    Toast.makeText(PdfCreatorActivity.this,"Ugyldig dato", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!datehigherthan()){
                    Toast.makeText(PdfCreatorActivity.this,"Sluttdato er tidligere enn Startdato", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    createPdfWrapper();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    private void createPdfWrapper() throws FileNotFoundException,DocumentException{


        int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                    showMessageOKCancel("You need to allow access to Storage",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                REQUEST_CODE_ASK_PERMISSIONS);
                                    }
                                }
                            });
                    return;
                }

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        }else {
            createPdf();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    try {
                        createPdfWrapper();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Permission Denied
                    Toast.makeText(this, "WRITE_EXTERNAL Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void createPdf() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }

        pdfFile = new File(docsFolder.getAbsolutePath(),Lagresom.getText().toString() + ".pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        document.open();

        PdfPTable hjemmetable = new PdfPTable(3);
        hjemmetable.addCell("Dato");
        hjemmetable.addCell("Beløp");
        hjemmetable.addCell("MVA");

        PdfPTable videretable = new PdfPTable(3);
        videretable.addCell("Dato");
        videretable.addCell("Beløp");
        videretable.addCell("MVA");

        PdfPTable annettable = new PdfPTable(3);
        annettable.addCell("Dato");
        annettable.addCell("Beløp");
        annettable.addCell("MVA");

        for(Object salg : solgt){
            if(salg instanceof BondensMarked) {
            }else if(salg instanceof Hjemme) {
                Hjemme hjemmeSalg = (Hjemme) salg;
                if(greaterThan(hjemmeSalg.getDato(), Startdato.getText().toString())&& !greaterThan(hjemmeSalg.getDato(),Sluttdato.getText().toString())){
                    hjemmetable.addCell(hjemmeSalg.getDato());
                    hjemmetable.addCell(Integer.toString(hjemmeSalg.getBelop()));
                    hjemmetable.addCell(Double.toString(sharedPreferences.getInt("ferdigprodukt",15)));
                }
            }else if(salg instanceof Videresalg) {
                Videresalg videresalg = (Videresalg) salg;
                if(greaterThan(videresalg.getDato(), Startdato.getText().toString())&& !greaterThan(videresalg.getDato(),Sluttdato.getText().toString())){
                    videretable.addCell(videresalg.getDato());
                    videretable.addCell(Integer.toString(videresalg.getBelop()));
                    videretable.addCell(Double.toString(videresalg.getMoms()));
                }
            }else if(salg instanceof Annet) {
                Annet annet = (Annet) salg;
                if(greaterThan(annet.getDato(), Startdato.getText().toString())&& !greaterThan(annet.getDato(),Sluttdato.getText().toString())){
                    annettable.addCell(annet.getDato());
                    annettable.addCell(Integer.toString(annet.getBelop()));
                    annettable.addCell(Double.toString(sharedPreferences.getInt("ikkeferdig",25)));
                }
            }else{
                Toast.makeText(this, "Noe gikk galt", Toast.LENGTH_SHORT).show();
            }
        }

        document.add(new Paragraph("Faktura oversikt", new Font(Font.FontFamily.HELVETICA,18,Font.BOLD)));
        document.add( Chunk.NEWLINE );

        document.add(new Paragraph("Startdato: " + Startdato.getText().toString()));
        document.add(new Paragraph("Sluttdato: " + Sluttdato.getText().toString()));

        document.add( Chunk.NEWLINE );
        document.add(new Paragraph("Hjemmesalg", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
        document.add( Chunk.NEWLINE );
        document.add(hjemmetable);

        document.add( Chunk.NEWLINE );
        document.add(new Paragraph("Videresalg", new Font(Font.FontFamily.HELVETICA,12,Font.BOLD)));
        document.add( Chunk.NEWLINE );
        document.add(videretable);

        document.add( Chunk.NEWLINE );
        document.add(new Paragraph("Annet salg", new Font(Font.FontFamily.HELVETICA,12,Font.BOLD)));
        document.add( Chunk.NEWLINE );
        document.add(annettable);

        document.close();
        Toast.makeText(this, "PDF laget", Toast.LENGTH_SHORT ).show();
        previewPdf();

    }
    private void previewPdf() {
        File file;
        file = new File(Environment.getExternalStorageDirectory()+"/Documents/"+Lagresom.getText().toString() + ".pdf");
        Toast.makeText(getApplicationContext(), file.toString() , Toast.LENGTH_LONG).show();
        if(file.exists()) {
            Intent target = new Intent(Intent.ACTION_VIEW);
            target.setDataAndType(FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".com.julia.bachelor.GenericFileProvider", file), "application/pdf");
            target.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            Intent intent = Intent.createChooser(target, "Open File");
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getApplicationContext(), "Installer en PDF-leser." , Toast.LENGTH_LONG).show();
            }
        }
        else
            Toast.makeText(getApplicationContext(), "File path is incorrect." , Toast.LENGTH_LONG).show();
    }
    public boolean checkDate(String date) {
        String regex = "^\\d{4}\\.(0?[1-9]|1[012])\\.(0?[1-9]|[12][0-9]|3[01])$";
        return date.matches(regex);
    }
    public boolean datehigherthan(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        try {
            Date date1 = sdf.parse(Startdato.getText().toString());
            Date date2 = sdf.parse(Sluttdato.getText().toString());
            if(date2.after(date1)){
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
    boolean greaterThan(String current, String next){
        SimpleDateFormat punktum = new SimpleDateFormat("yyyy.MM.dd");
        SimpleDateFormat bindestrek = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = bindestrek.parse(current);
            Date date2 = punktum.parse(next);
            if(date1.after(date2)){
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

}