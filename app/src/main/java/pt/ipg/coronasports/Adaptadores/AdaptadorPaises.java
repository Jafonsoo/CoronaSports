package pt.ipg.coronasports.Adaptadores;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;
import pt.ipg.coronasports.MainActivity;
import pt.ipg.coronasports.Modelos.Equipa;
import pt.ipg.coronasports.Pais.PaisFragment;
import pt.ipg.coronasports.R;
import pt.ipg.coronasports.Modelos.Pais;

public class AdaptadorPaises extends RecyclerView.Adapter<AdaptadorPaises.ViewHolderPaises>{
    private Cursor cursor = null;
    private final Context context;


    public AdaptadorPaises(Context context){
        this.context = context;
    }




    public void setCursor(Cursor cursor){
        if (this.cursor != cursor){
            this.cursor = cursor;
            notifyDataSetChanged();
        }
    }



    @NonNull
    @Override
    public ViewHolderPaises onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemPaises = LayoutInflater.from(context).inflate(R.layout.item_pais, parent, false);

        return new ViewHolderPaises(itemPaises);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPaises holder, int position) {
        cursor.moveToPosition(position);
        Pais pais = Pais.fromCursor(cursor);
        holder.setPais(pais);



    }

    @Override
    public int getItemCount() {
        if (cursor == null) {
            return 0;
        }
        return cursor.getCount();
    }

    private static ViewHolderPaises viewHolderPaisesSelecionado = null;


    public class ViewHolderPaises extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView textViewNome;
        private TextView textViewMortos;
        private TextView textViewInfetados;
        private TextView textViewEstado;

        private Pais pais = null;

        public ViewHolderPaises(@NonNull View itemView) {
            super(itemView);
            textViewNome = (TextView) itemView.findViewById(R.id.item_nome_atleta345);
            textViewMortos = (TextView) itemView.findViewById(R.id.item_mortos_pais);
            textViewInfetados = (TextView) itemView.findViewById(R.id.item_infetados_pais);
            textViewEstado = itemView.findViewById(R.id.item_estado);


            itemView.setOnClickListener(this);
        }



        public void setPais(Pais pais){

            this.pais=pais;

            textViewNome.setText(pais.getNome_pais());
            textViewMortos.setText(String.valueOf(pais.getNum_obitos()));
            textViewInfetados.setText(String.valueOf(pais.getNum_infetados()));



            if(pais.getNum_infetados() >= 0 && pais.getNum_infetados() < 5000){
                textViewEstado.setText("Risco Baixo");
                textViewEstado.setTextColor(Color.GREEN);
            } else if(pais.getNum_infetados() >= 5000 && pais.getNum_infetados() < 10000){
                textViewEstado.setText("Risco Moderado");
                textViewEstado.setTextColor(Color.rgb(252,212,19));
            }else if(pais.getNum_infetados()>=10000 && pais.getNum_infetados() < 30000){
                textViewEstado.setText("Risco Elevado");
                textViewEstado.setTextColor(Color.rgb(255,171,36));
            }else{
                textViewEstado.setText("Risco Extremo");
                textViewEstado.setTextColor(Color.rgb(178,0,32));
            }


        }

        @Override
        public void onClick(View v) {
            if(viewHolderPaisesSelecionado != null){
                viewHolderPaisesSelecionado.desSeleciona();
            }
            viewHolderPaisesSelecionado = this;
            seleciona();

            MainActivity activity = (MainActivity) AdaptadorPaises.this.context;
            activity.atualizaOpcoesMenuPais(pais);
        }


        void desSeleciona() {
        itemView.setBackgroundResource(R.color.transparent);
        }

        private void seleciona() {
            itemView.setBackgroundResource(R.color.blue);
        }


    }


}
