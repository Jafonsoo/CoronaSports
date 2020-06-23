package pt.ipg.coronasports.Adaptadores;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import pt.ipg.coronasports.MainActivity;
import pt.ipg.coronasports.R;
import pt.ipg.coronasports.Modelos.Equipa;

public class AdaptadorEquipa extends RecyclerView.Adapter<AdaptadorEquipa.ViewHolderEquipa> {
    private Cursor cursor;
    private Context context;

    public AdaptadorEquipa(Context context){
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
    public ViewHolderEquipa onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemSerie = LayoutInflater.from(context).inflate(R.layout.item_equipa, parent, false);

        return new ViewHolderEquipa(itemSerie);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolderEquipa holderEquipa, int position) {
        cursor.moveToPosition(position);
        Equipa equipa = Equipa.fromCursor(cursor);
        holderEquipa.setEquipa(equipa);

    }

    @Override
    public int getItemCount() {
        if (cursor == null) {
            return 0;
        }
        return cursor.getCount();
    }



    public Equipa getEquipaSelecionada() {
        if(viewHolderEquipaSelecionada == null) return null;

        return viewHolderEquipaSelecionada.equipa;
    }

    private static ViewHolderEquipa viewHolderEquipaSelecionada = null;

    public class ViewHolderEquipa extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewNome;
        private TextView textViewModalidade;
        private TextView textViewData;
        private TextView textViewPais;


        private Equipa equipa;

        public ViewHolderEquipa(@NonNull View itemView) {
            super(itemView);

            textViewNome = (TextView) itemView.findViewById(R.id.item_nome_atleta345);
            textViewModalidade = (TextView) itemView.findViewById(R.id.item_modalidade);
            textViewData = (TextView) itemView.findViewById(R.id.item_data);
            textViewPais = (TextView) itemView.findViewById(R.id.item_pais);

            itemView.setOnClickListener(this);

        }

        public void setEquipa(Equipa equipa) {
            this.equipa = equipa;


            textViewNome.setText(equipa.getNome_equipa());
            textViewModalidade.setText(equipa.getModalidade());
            textViewData.setText(String.valueOf(equipa.getData_fundacao()));
            textViewPais.setText(String.valueOf(equipa.getNomePais()));


        }

        @Override
        public void onClick(View v) {
            if (viewHolderEquipaSelecionada != null) {
                viewHolderEquipaSelecionada.desSeleciona();
            }

            viewHolderEquipaSelecionada = this;
            seleciona();

            MainActivity activity = (MainActivity) AdaptadorEquipa.this.context;
            activity.atualizaOpcoesMenuEquipa(equipa);
        }


        private void desSeleciona() {
            itemView.setBackgroundResource(R.color.transparent);
        }

        private void seleciona() {
            itemView.setBackgroundResource(R.color.blue);
        }
    }
}
