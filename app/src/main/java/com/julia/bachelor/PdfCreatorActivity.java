package com.julia.bachelor;


import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.julia.bachelor.helperClass.Annet;
import com.julia.bachelor.helperClass.Hjemme;
import com.julia.bachelor.helperClass.SalgTemplate;
import com.julia.bachelor.helperClass.Videresalg;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class PdfCreatorActivity extends AppCompatActivity {
    private static final String TAG = "PdfCreatorActivity";
    private EditText Startdato, Sluttdato, Lagresom;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;
    private static final String KEY_BUNDLE = "Bundle";
    private static final String KEY_SALG = "AllSalg";
    private ArrayList<SalgTemplate> solgt;
    Bitmap image;

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfcreator);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(KEY_BUNDLE);
        solgt = (ArrayList<SalgTemplate>) bundle.getSerializable(KEY_SALG);
        Startdato = findViewById(R.id.startdato);
        Sluttdato = findViewById(R.id.sluttdato);
        Lagresom = findViewById(R.id.lagresom);
        image = BitmapFactory.decodeResource(getResources(), R.drawable.bie);
        Button mCreateButton = findViewById(R.id.button_create);
        Sluttdato.setText(Tools.getDate());
        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Tools.checkDate(Startdato.getText().toString()) || !Tools.checkDate(Sluttdato.getText().toString())) {
                    Toast.makeText(PdfCreatorActivity.this, "Ugyldig dato", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!datehigherthan()) {
                    Toast.makeText(PdfCreatorActivity.this, "Sluttdato er tidligere enn Startdato", Toast.LENGTH_SHORT).show();
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

    private void createPdfWrapper() throws FileNotFoundException, DocumentException {


        int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                    showMessageOKCancel(
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                            REQUEST_CODE_ASK_PERMISSIONS);
                                }
                            });
                    return;
                }

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
        } else {
            createPdf();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
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
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void showMessageOKCancel(DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage("You need to allow access to Storage")
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void createPdf() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            boolean created = docsFolder.mkdir();
            if (created) {
                Log.i(TAG, "Created a new directory for PDF");
            }
        }

        File pdfFile = new File(docsFolder.getAbsolutePath(), Lagresom.getText().toString() + ".pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
        Image img = null;
        byte[] byteArray = stream.toByteArray();
        try {
            img = Image.getInstance(byteArray);
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        document.open();

        PdfPTable hjemmetable = new PdfPTable(4);
        hjemmetable.addCell("Dato");
        hjemmetable.addCell("Beløp/Salg");
        hjemmetable.addCell("Moms %");
        hjemmetable.addCell("Moms kr");

        PdfPTable videretable = new PdfPTable(4);
        videretable.addCell("Dato");
        videretable.addCell("Beløp/Salg");
        videretable.addCell("Moms %");
        videretable.addCell("Moms kr");

        PdfPTable annettable = new PdfPTable(4);
        annettable.addCell("Dato");
        annettable.addCell("Beløp/Salg");
        annettable.addCell("Moms%");
        annettable.addCell("Moms kr");

        for (SalgTemplate salg : solgt) {
            if (salg instanceof Hjemme) {
                Hjemme hjemmeSalg = (Hjemme) salg;
                if (greaterThan(hjemmeSalg.getDato(), Startdato.getText().toString()) && !greaterThan(hjemmeSalg.getDato(), Sluttdato.getText().toString())) {
                    hjemmetable.addCell(hjemmeSalg.getDato());
                    hjemmetable.addCell(Integer.toString(hjemmeSalg.getBelop()));
                    hjemmetable.addCell(Double.toString(hjemmeSalg.getMoms() * 100));
                    hjemmetable.addCell(Double.toString(hjemmeSalg.getBelop() * hjemmeSalg.getMoms()));
                }
            } else if (salg instanceof Videresalg) {
                Videresalg videresalg = (Videresalg) salg;
                if (greaterThan(videresalg.getDato(), Startdato.getText().toString()) && !greaterThan(videresalg.getDato(), Sluttdato.getText().toString())) {
                    videretable.addCell(videresalg.getDato());
                    videretable.addCell(Integer.toString(videresalg.getBelop()));
                    videretable.addCell(Double.toString(videresalg.getMoms()));
                    videretable.addCell(Double.toString((videresalg.getBelop() * videresalg.getMoms()) / 100));
                }
            } else if (salg instanceof Annet) {
                Annet annet = (Annet) salg;
                if (greaterThan(annet.getDato(), Startdato.getText().toString()) && !greaterThan(annet.getDato(), Sluttdato.getText().toString())) {
                    annettable.addCell(annet.getDato());
                    annettable.addCell(Integer.toString(annet.getBelop()));
                    annettable.addCell(Double.toString(annet.getMoms() * 100));
                    annettable.addCell(Double.toString(annet.getBelop() * annet.getMoms()));
                }
            }
        }
        ArrayList<SalgTemplate> tmp = Tools.separateHjemme(solgt);
        addtotal(hjemmetable, Tools.sumList(tmp));
        tmp = Tools.separateVideresalg(solgt);
        addtotal(videretable, Tools.sumList(tmp));
        tmp = Tools.separateAnnet(solgt);
        addtotal(annettable, Tools.sumList(tmp));
        if (img != null) {
            img.scaleToFit(90f, 90f);
            img.setAlignment(Element.ALIGN_LEFT | Image.TEXTWRAP);
        }
        document.add(Image.getInstance(img));

        document.add(new Paragraph("    Salgsoversikt", new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD)));
        document.add(Chunk.NEWLINE);

        document.add(new Paragraph("      Startdato: " + Startdato.getText().toString()));
        document.add(new Paragraph("      Sluttdato: " + Sluttdato.getText().toString()));

        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("Hjemmesalg", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
        document.add(Chunk.NEWLINE);
        document.add(hjemmetable);

        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("Videresalg", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
        document.add(Chunk.NEWLINE);
        document.add(videretable);

        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("Annet salg", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
        document.add(Chunk.NEWLINE);
        document.add(annettable);

        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("Total: " + (Tools.sumList(solgt)), new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD)));

        document.close();
        Toast.makeText(this, "PDF laget", Toast.LENGTH_SHORT).show();
        previewPdf();
    }

    private void previewPdf() {
        File file;
        file = new File(Environment.getExternalStorageDirectory() + "/Documents/" + Lagresom.getText().toString() + ".pdf");
        if (file.exists()) {
            Intent target = new Intent(Intent.ACTION_VIEW);
            target.setDataAndType(FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".com.julia.bachelor.GenericFileProvider", file), "application/pdf");
            target.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            Intent intent = Intent.createChooser(target, "Open File");
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getApplicationContext(), "Installer en PDF-leser.", Toast.LENGTH_LONG).show();
            }
        } else
            Toast.makeText(getApplicationContext(), "File path is incorrect.", Toast.LENGTH_LONG).show();
    }

    public boolean datehigherthan() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd", Locale.US);
        try {
            Date date1 = sdf.parse(Startdato.getText().toString());
            Date date2 = sdf.parse(Sluttdato.getText().toString());
            if (date2.after(date1)) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    boolean greaterThan(String current, String next) {
        SimpleDateFormat punktum = new SimpleDateFormat("yyyy.MM.dd", Locale.US);
        SimpleDateFormat bindestrek = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        try {
            Date date1 = bindestrek.parse(current);
            Date date2 = punktum.parse(next);
            if (date1.after(date2)) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addtotal(PdfPTable table, double teller) {
        table.addCell("Total:");
        table.addCell(Double.toString(teller));
        table.addCell("");
        table.addCell("");
    }

}