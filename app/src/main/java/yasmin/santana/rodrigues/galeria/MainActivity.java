package yasmin.santana.rodrigues.galeria;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static int RESULT_TAKE_PICTURE = 1;
    static int RESULT_REQUEST_PERMISSION = 2;
    String currentPhotoPath;
    List<String> photos = new ArrayList<>();
    MainAdapter mainAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File[] files = dir.listFiles(); //le a lista de fotos
        for(int i = 0; i < files.length; i++){ //add na lista de fotos
            photos.add(files[i].getAbsolutePath());
        }
        mainAdapter = new MainAdapter(MainActivity.this, photos);
        RecyclerView rvGallery = findViewById(R.id.rvGallery);
        rvGallery.setAdapter(mainAdapter);
        float w = getResources().getDimension(R.dimen.itemWidth); //calcula quantas colunas  cabe na tela do celular
        int numberOfColumns = Util.calculateNoOfColumns(MainActivity.this, w);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, numberOfColumns);
        rvGallery.setLayoutManager(gridLayoutManager); //exibe as fotos em grid

        Toolbar toolbar = findViewById(R.id.tbMain);
        setSupportActionBar(toolbar); //action bar padrão

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); //disponibiliza botão de voltar
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_tb, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){ //chama sempre que houver clique na toolbar
        switch(item.getItemId()){
            case R.id.opCamera:
                dispatchTakePictureIntent(); //se clicar na câmera
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void startPhotoActivity(String photoPath){ //passa a foto entre as telas
        Intent i = new Intent(MainActivity.this, PhotoActivity.class);
        i.putExtra("photo_path", photoPath);
        startActivity(i);
    }

    private void dispatchTakePictureIntent(){
        File f = null; //cria arquivo vazio dentro da pasta picture
        try{
            f = createImageFile(); //se der certo o método, ok
        } catch (IOException e){ //se n der certo, joga esse erro
            Toast.makeText(MainActivity.this, "Não foi possível criar um arquivo", Toast.LENGTH_SHORT).show();
            return;
        }
        currentPhotoPath = f.getAbsolutePath();
        if(f != null){
            Uri fUri = FileProvider.getUriForFile(MainActivity.this, "rodrigues.santana.yasmin.fileprovider",f); //obtem endereço da foto
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            i.putExtra(MediaStore.EXTRA_OUTPUT, fUri);
            startActivityForResult(i, RESULT_TAKE_PICTURE);
        }
    }

    private File createImageFile() throws IOException{ //iniciando app camera
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File f = File.createTempFile(imageFileName, ".jpg", storageDir);
        return f;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){ // se a foto for tirada, chama esse método
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_TAKE_PICTURE){
            if(resultCode == Activity.RESULT_OK){
                photos.add(currentPhotoPath); //add a foto na lista de fotos
                mainAdapter.notifyItemInserted(photos.size()-1);
            }
            else{
                File f = new File(currentPhotoPath);
                f.delete();
            }
        }
    }

    private void checkForPermissions(List<String> permissions){
        List<String> permissionsNotGranted = new ArrayList<>();

        for(String permission : permissions){
            if( !hasPermission(permission)){
                permissionsNotGranted.add(permission);
            }
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(permissionsNotGranted.size() > 0){
                requestPermissions(permissionsNotGranted.toArray(new String[permissionsNotGranted.size()]),RESULT_REQUEST_PERMISSION);
            }
        }
    }
    private boolea
}
