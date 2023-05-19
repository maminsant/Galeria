package yasmin.santana.rodrigues.galeria;

import android.graphics.Bitmap;
import android.graphics.NinePatch;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter{
    MainActivity mainActivity;
    List<String> photos;

    public MainAdapter(MainActivity mainActivity, List<String> photos){
        this.mainActivity = mainActivity;
        this.photos = photos;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //cria os elementos da inerface para um obj que são guardados em VH
        LayoutInflater inflater = LayoutInflater.from(mainActivity);
        View v = inflater.inflate(R.layout.list_item,parent,false);
        return new MyViewHolder(v);
    }
    @Override
    public int getItemCount() { //quantos elementos a lista possui;
        return photos.size();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position){
        ImageView imPhoto = holder.itemView.findViewById(R.id.imItem);
        int w = (int)
        mainActivity.getResources().getDimension(R.dimen.itemWidth);//tamanho da imagem
        int h = (int)
        mainActivity.getResources().getDimension(R.dimen.itemHeight);
        Bitmap bitmap = Util.getBitmap(photos.get(position), w, h); //carrega foto bitmap
        imPhoto.setImageBitmap(bitmap);
        imPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.startPhotoActivity(photos.get(position));
            }
        });
    }
}
