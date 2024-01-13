package com.example.isgaexamen;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.HashMap;

public class medecin_controller extends AppCompatActivity {
    Button a,b,e,selectImageButton;
    EditText n,p,s,v,d;
    DAO md;
    Boolean update_Context=false;
    String Code_medecin;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    ImageView imgMedecin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        md=new DAO(this);
        setContentView(R.layout.activity_add_medecin);
        a=findViewById(R.id.btnSave);
        b=findViewById(R.id.btnAll);
        n=findViewById(R.id.nom);
        p=findViewById(R.id.prenom);
        s=findViewById(R.id.spec);
        v=findViewById(R.id.Ville);
        d=findViewById(R.id.Description);
        e=findViewById(R.id.btnDelete);
        e.setEnabled(false);
        selectImageButton = findViewById(R.id.btnPickImage); // Initialize the button
        imgMedecin = findViewById(R.id.imgMedecin);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                HashMap<String, Object> selectedMedecin = (HashMap<String, Object>) intent.getSerializableExtra("selectedMedecinDetails");
                if (selectedMedecin != null) {
                    Code_medecin = selectedMedecin.containsKey("valCode") ? selectedMedecin.get("valCode").toString() : "";
                    String nom_medecin = selectedMedecin.containsKey("valNomMed") ? selectedMedecin.get("valNomMed").toString() : "";
                    String prenom_medecin = selectedMedecin.containsKey("valpreMed") ? selectedMedecin.get("valpreMed").toString() : "";
                    String spec = selectedMedecin.containsKey("valspecMed") ? selectedMedecin.get("valspecMed").toString() : "";
                    String Ville = selectedMedecin.containsKey("valvilleMed") ? selectedMedecin.get("valvilleMed").toString() : "";
                    String Desc = selectedMedecin.containsKey("valdescMed") ? selectedMedecin.get("valdescMed").toString() : "";
                    n.setText(nom_medecin);
                    p.setText(prenom_medecin);
                    s.setText(spec);
                    v.setText(Ville);
                    d.setText(Desc);
                    e.setEnabled(true);
                    update_Context = true;

                }
            }
        }

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b= new Intent(getApplicationContext(),Medecin_welcome.class);
                startActivity(b);
            }
        });
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vv) {
                if (update_Context){
                    Medecin medecin= new Medecin();
                    medecin.setCode_Medecin(Integer.parseInt(Code_medecin));
                    medecin.setNom_Medecin(n.getText().toString());
                    medecin.setPrenom_Medecin(p.getText().toString());
                    medecin.setSpecialite_Medecin(s.getText().toString());
                    medecin.setVille_Medecin(v.getText().toString());
                    medecin.setDescription_Medecin(d.getText().toString());
                    medecin.setImage_Path(imageUri.toString());
                    md.updateMedecin(medecin);

                    n.setHint("Nom du Médecin :");
                    p.setHint("Prénom du Médecin :");
                    s.setHint("Spécialité :  ");
                    v.setHint("Ville : ");
                    d.setHint("Description : ");
                    n.setText("");
                    p.setText("");
                    s.setText("");
                    v.setText("");
                    d.setText("");
                    Toast.makeText(getApplicationContext(), "Mis à Jour!", Toast.LENGTH_SHORT).show();

                }
                else{


                    Medecin medecin= new Medecin();
                    medecin.setNom_Medecin(n.getText().toString());
                    medecin.setPrenom_Medecin(p.getText().toString());
                    medecin.setSpecialite_Medecin(s.getText().toString());
                    medecin.setVille_Medecin(v.getText().toString());
                    medecin.setDescription_Medecin(d.getText().toString());
                    medecin.setImage_Path(imageUri.toString());

                    md.addMedecin(medecin);
                    n.setHint("Nom du Médecin :");
                    p.setHint("Prénom du Médecin :");
                    s.setHint("Spécialité :  ");
                    v.setHint("Ville : ");
                    d.setHint("Description : ");
                    n.setText("");
                    p.setText("");
                    s.setText("");
                    v.setText("");
                    d.setText("");

                    Toast.makeText(getApplicationContext(), "Ajouté à la Bd!", Toast.LENGTH_SHORT).show();

                }
            }
        });

        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vv) {
                Medecin medecin= new Medecin();
                medecin.setCode_Medecin(Integer.parseInt(Code_medecin));;
                medecin.setNom_Medecin(n.getText().toString());
                medecin.setPrenom_Medecin(p.getText().toString());
                medecin.setSpecialite_Medecin(s.getText().toString());
                medecin.setVille_Medecin(v.getText().toString());
                medecin.setDescription_Medecin(d.getText().toString());
                medecin.setImage_Path(imageUri.toString());
                md.deleteMedecin(medecin);
                n.setHint("Nom du Médecin :");
                p.setHint("Prénom du Médecin :");
                s.setHint("Spécialité :  ");
                v.setHint("Ville : ");
                d.setHint("Description : ");
                n.setText("");
                p.setText("");
                s.setText("");
                v.setText("");
                d.setText("");
                Toast.makeText(getApplicationContext(), "Supprimé de la Bd!", Toast.LENGTH_SHORT).show();


            }

        });



    }

    private final ActivityResultLauncher<String> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            result -> {
                if (result != null) {
                    imageUri = result;
                    Glide.with(this)
                            .load(imageUri)
                            .placeholder(R.drawable.placeholder_image)
                            .into(imgMedecin);
                }
            }
    );


    private void openGallery() {
        galleryLauncher.launch("image/*");
    }



}
