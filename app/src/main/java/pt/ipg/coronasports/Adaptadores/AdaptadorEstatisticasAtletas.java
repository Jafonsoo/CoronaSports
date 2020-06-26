package pt.ipg.coronasports.Adaptadores;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import pt.ipg.coronasports.Modelos.Atleta;
import pt.ipg.coronasports.Modelos.Equipa;
import pt.ipg.coronasports.R;

public class AdaptadorEstatisticasAtletas extends RecyclerView.Adapter<AdaptadorEstatisticasAtletas.ViewHolderAtletas>{
    private Cursor cursor;
    private Context context;
    private Dialog myDialog;

    public AdaptadorEstatisticasAtletas(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderAtletas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemAtletas = LayoutInflater.from(context).inflate(R.layout.item_estatisticas_atleta, parent, false);

        return new ViewHolderAtletas(itemAtletas);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderAtletas holder, final int position) {
        cursor.moveToPosition(position);
        final Atleta atleta = Atleta.fromCursor(cursor);
        holder.setAtleta(atleta);

        myDialog = new Dialog(context);
        myDialog.setContentView(R.layout.dialog_detalhes_atleta);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dialog_nome = myDialog.findViewById(R.id.dialog_nomeAtleta);
                TextView dialog_estado = myDialog.findViewById(R.id.dialog_estadoAtleta);
                TextView dialog_idade = myDialog.findViewById(R.id.detalhes_idade);
                TextView dialog_funcao = myDialog.findViewById(R.id.detalhes_funcao);
                TextView dialog_data = myDialog.findViewById(R.id.detalhes_data);
                TextView dialog_dados = myDialog.findViewById(R.id.detalhes_dados);
                TextView dialog_equipa = myDialog.findViewById(R.id.detalhes_equipa);
                TextView dialog_paisAtleta = myDialog.findViewById(R.id.detalhes_pais);

                Button buttonDetalhesPais = myDialog.findViewById(R.id.buttonDetalhesPais);

                dialog_nome.setText(atleta.getNome_atleta());
                dialog_idade.setText(String.valueOf(atleta.getIdade_atleta()));
                dialog_funcao.setText(atleta.getFuncao());
                dialog_data.setText(atleta.getData_corona());
                dialog_dados.setText(atleta.getDados_atleta());
                dialog_equipa.setText(atleta.getNomeEquipaAtleta());
                dialog_paisAtleta.setText(atleta.getNomePaisAtleta());

                if(atleta.getEstado_atleta()==0){
                    dialog_estado.setText("Infetado");
                    dialog_estado.setTextColor(Color.rgb(255,195,77));
                }else if (atleta.getEstado_atleta()==1){
                    dialog_estado.setText("Recuperado");
                    dialog_estado.setTextColor(Color.rgb(0,128,0));
                }else {
                    dialog_estado.setText("Falecido");
                    dialog_estado.setTextColor(Color.rgb(178,0,32));
                }

                Toast.makeText(context,"Visualizar " + atleta.getNome_atleta(),Toast.LENGTH_SHORT).show();
                buttonDetalhesPais.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });


                myDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (cursor == null) {
            return 0;
        }
        return cursor.getCount();
    }
    public void setCursor(Cursor cursor){
        if (this.cursor != cursor){
            this.cursor = cursor;
            notifyDataSetChanged();
        }
    }
    public static class ViewHolderAtletas extends RecyclerView.ViewHolder{
        private TextView textViewNome;
        private TextView textViewEstado;
        private RelativeLayout relativeLayout;
        private Atleta atleta;
        private Equipa equipa;

        public ViewHolderAtletas(@NonNull View itemView) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.item_nome_atleta);
            textViewEstado = itemView.findViewById(R.id.item_estado_atleta);
            relativeLayout = itemView.findViewById(R.id.setModels);
        }

        public void setAtleta(Atleta atleta){

            this.atleta=atleta;

            textViewNome.setText(atleta.getNome_atleta());


            if(atleta.getEstado_atleta()==0){
                textViewEstado.setText("Infetado");
            }else if (atleta.getEstado_atleta()==1){
                textViewEstado.setText("Recuperado");
            }else {
                textViewEstado.setText("Falecido");
            }

        }

    }
}
