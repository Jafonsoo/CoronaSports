package pt.ipg.coronasports.Adaptadores;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class AdaptadorEstatisticasEquipa extends RecyclerView.Adapter<AdaptadorEstatisticasEquipa.ViewHolderEquipa>{
    private Cursor cursor;
    private Context context;
    private Dialog myDialog;
    private int count=0;

    public AdaptadorEstatisticasEquipa(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderEquipa onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemEquipas = LayoutInflater.from(context).inflate(R.layout.item_estatisticas_equipa, parent, false);

        return new ViewHolderEquipa(itemEquipas);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderEquipa holder, final int position) {
        cursor.moveToPosition(position);
        final Equipa equipa = Equipa.fromCursor(cursor);
        holder.setPais(equipa);

        myDialog = new Dialog(context);
        myDialog.setContentView(R.layout.dialog_detalhes_equipas);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dialog_nome = myDialog.findViewById(R.id.dialog_nome_equipa);
                TextView dialog_pais = myDialog.findViewById(R.id.dialog_estado_pais);
                TextView dialog_modalidade = myDialog.findViewById(R.id.detalhes_modalidade);
                TextView dialog_fundacao = myDialog.findViewById(R.id.detalhes_fundaçao);

                Button buttonDetalhesPais = myDialog.findViewById(R.id.buttonDetalhesPais);

                dialog_nome.setText(equipa.getNome_equipa());
                dialog_pais.setText(equipa.getNomePais());
                dialog_modalidade.setText(String.valueOf(equipa.getModalidade()));
                dialog_fundacao.setText(String.valueOf(equipa.getData_fundacao()));



                Toast.makeText(context,"Visualizar " + equipa.getNome_equipa(),Toast.LENGTH_SHORT).show();
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
    public static class ViewHolderEquipa extends RecyclerView.ViewHolder{
        private TextView textViewNome;
        private TextView textViewEstado;
        private RelativeLayout relativeLayout;
        private Equipa equipa;

        public ViewHolderEquipa(@NonNull View itemView) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.item_nome_equipa);
            textViewEstado = itemView.findViewById(R.id.item_estado_equipa);
            relativeLayout = itemView.findViewById(R.id.setModels);
        }

        public void setPais(Equipa equipa){

            this.equipa=equipa;

            textViewNome.setText(equipa.getNome_equipa());

            textViewEstado.setText(equipa.getNomePais());

        }
    }


}
