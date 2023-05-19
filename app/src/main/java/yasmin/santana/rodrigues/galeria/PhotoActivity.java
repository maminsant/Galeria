package yasmin.santana.rodrigues.galeria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.File;

public class PhotoActivity extends AppCompatActivity {
    String photoPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        Toolbar toolbar = findViewById(R.id.tbPhoto);
        setSupportActionBar(toolbar); //action bar padrão

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); //disponibiliza botão de voltar

        Intent i = getIntent();
        photoPath = i.getStringExtra("photo_path"); //pega endereço da foto

        Bitmap bitmap = Util.getBitmap(photoPath);
        ImageView imPhoto = findViewById(R.id.imPhoto);
        imPhoto.setImageBitmap(bitmap); //setando o bitmap
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.photo_activity_tb, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){ //chama quando tiver clique na toolbar
        switch(item.getItemId()){
            case R.id.opShare:
                sharePhoto(); //caso clique na câmera
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    void sharePhoto(){
        Uri photoUri = FileProvider.getUriForFile(PhotoActivity.this, "yasmin.santana.rodrigues.galeria.fileprovider", new File(photoPath)); //gERA URI
        Intent i = new Intent(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_STREAM, photoUri); //arq que queremos compartilhar
        i.setType("image/jpeg");
        startActivity(i);
        //recebe uma lista dos apps no qual ele pode abrir a camera
    }
}