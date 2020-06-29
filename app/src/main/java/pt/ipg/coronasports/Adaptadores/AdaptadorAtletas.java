package pt.ipg.coronasports.Adaptadores;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import pt.ipg.coronasports.MainActivity;
import pt.ipg.coronasports.R;
import pt.ipg.coronasports.Atleta.AtletaFragment;
import pt.ipg.coronasports.Modelos.Atleta;

public class AdaptadorAtletas extends RecyclerView.Adapter<AdaptadorAtletas.ViewHolderAtleta> {
    private Cursor cursor;
    private Context context;

    public AdaptadorAtletas(Context context){
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
    public ViewHolderAtleta onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemAtleta = LayoutInflater.from(context).inflate(R.layout.item_atleta, parent, false);

        return new ViewHolderAtleta(itemAtleta);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAtleta holderAtleta, int position) {
        cursor.moveToPosition(position);
        Atleta atleta = Atleta.fromCursor(cursor);
        holderAtleta.setAtleta(atleta);

    }

    @Override
    public int getItemCount() {
        if (cursor == null) {
            return 0;
        }
        return cursor.getCount();
    }



    public Atleta getAtletaSelecionada() {
        if(viewHolderAtletaSelecionada == null) return null;

        return viewHolderAtletaSelecionada.atleta;
    }

    private static ViewHolderAtleta viewHolderAtletaSelecionada = null;

    public class ViewHolderAtleta extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewNome;
        private TextView textViewfuncao;
        private TextView textViewequipa;
        private TextView textViewpais;
        private TextView textViewestado;
        private TextView textViewdata;
        private TextView textViewidade;

        private Atleta atleta;

        public ViewHolderAtleta(@NonNull View itemView) {
            super(itemView);

            textViewNome = (TextView) itemView.findViewById(R.id.item_atleta);
            textViewfuncao = (TextView) itemView.findViewById(R.id.textViewfuncao);
            textViewequipa =itemView.findViewById(R.id.textViewequipa);
            textViewpais = itemView.findViewById(R.id.textViewpais);
            textViewdata = itemView.findViewById(R.id.textViewdata);
            textViewidade = itemView.findViewById(R.id.textView12);
            textViewestado =itemView.findViewById(R.id.textViewestado);


            itemView.setOnClickListener(this);

        }

        public void setAtleta(Atleta atleta) {
            this.atleta = atleta;

            textViewNome.setText(atleta.getNome_atleta());
            textViewpais.setText(String.valueOf(atleta.getNomePaisAtleta()));
            textViewequipa.setText(String.valueOf(atleta.getNomeEquipaAtleta()));
            textViewfuncao.setText(atleta.getFuncao());
            textViewdata.setText(atleta.getData_corona());
            textViewidade.setText(String.valueOf(atleta.getIdade_atleta()));

            if(atleta.getEstado_atleta()==0){
                textViewestado.setText(R.string.infetado);
                textViewestado.setTextColor(Color.rgb(255,195,77));
            }else if (atleta.getEstado_atleta()==1){
                textViewestado.setText(R.string.recuperado);
                textViewestado.setTextColor(Color.rgb(0,128,0));
            }else {
                textViewestado.setText(R.string.falecido);
                textViewestado.setTextColor(Color.rgb(178,0,32));
            }

        }

        @Override
        public void onClick(View v) {
            if (viewHolderAtletaSelecionada != null) {
                viewHolderAtletaSelecionada.desSeleciona();
            }

            viewHolderAtletaSelecionada = this;

            seleciona();

            MainActivity activity = (MainActivity) AdaptadorAtletas.this.context;
            activity.atualizaOpcoesMenuAtleta(atleta);
        }


        private void desSeleciona() {
            itemView.setBackgroundResource(R.color.transparent);
        }

        private void seleciona() {
            itemView.setBackgroundResource(R.color.blue);
        }
    }
}
