package br.unicamp.apptriunfalevent.APIconfig;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import br.unicamp.apptriunfalevent.Models.Usuario;
import br.unicamp.apptriunfalevent.R;

import br.unicamp.apptriunfalevent.ui.profile.ProfileFragment;

public class GridViewAdapter extends BaseAdapter {

    private List<Usuario> lista;
    private Context context;

    public GridViewAdapter(Context context, List<Usuario> recebeParametroListaDog){
        this.lista = recebeParametroListaDog;
        this.context = context;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.layoute_gridview, parent,false);
        }

        TextView tvNome = view.findViewById(R.id.tvNome_grid);
        TextView tvRaca = view.findViewById(R.id.tvRaca_grid);

        Usuario user = lista.get(position);

        tvNome.setText(user.getNome());
        tvRaca.setText(user.getEmail());

       /* if((dog.getImagem() != null) && (dog.getImagem().length()>0)){
            Picasso.get().load(dog.getImagem()).into(dogImageView);
        }else{
            Toast.makeText(context, "Não carregou a imagem", Toast.LENGTH_LONG).show();
        }*/

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, user.getNome(), Toast.LENGTH_LONG).show();

                Usuario usuario = new Usuario(user.getUsername(), user.getNome(), user.getNascimento(),
                        user.getEmail(), user.getSenha());

                Intent intent = new Intent(context, ProfileFragment.class);
                intent.putExtra("userSerializable",(Serializable) usuario);
                context.startActivity(intent);

            }
        });
        return view;
    }
}
