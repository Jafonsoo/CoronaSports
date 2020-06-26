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
import pt.ipg.coronasports.R;
import pt.ipg.coronasports.Modelos.Pais;

public class AdaptadorEstatisticasPais extends RecyclerView.Adapter<AdaptadorEstatisticasPais.ViewHolderPaises>{
    private Cursor cursor;
    private Context context;
    private Dialog myDialog;

    public AdaptadorEstatisticasPais(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderPaises onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemPaises = LayoutInflater.from(context).inflate(R.layout.item_estatisticas_pais, parent, false);

        return new ViewHolderPaises(itemPaises);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderPaises holder, final int position) {
        cursor.moveToPosition(position);
        final Pais pais = Pais.fromCursor(cursor);
        holder.setPais(pais);

        myDialog = new Dialog(context);
        myDialog.setContentView(R.layout.dialog_detalhes_pais);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dialog_nome = myDialog.findViewById(R.id.dialog_nome);
                TextView dialog_obitos = myDialog.findViewById(R.id.detalhes_obitos);
                TextView dialog_infetados = myDialog.findViewById(R.id.detalhes_infetados);
                TextView dialog_suspeitos = myDialog.findViewById(R.id.detalhes_suspeitos);
                TextView dialog_recuperados = myDialog.findViewById(R.id.detalhes_recuperados);
                TextView dialog_estado = myDialog.findViewById(R.id.dialog_estado);
                Button buttonDetalhesPais = myDialog.findViewById(R.id.buttonDetalhesPais);

                dialog_nome.setText(pais.getNome_pais());
                dialog_obitos.setText(String.valueOf(pais.getNum_obitos()));
                dialog_infetados.setText(String.valueOf(pais.getNum_infetados()));
                dialog_suspeitos.setText(String.valueOf(pais.getNum_suspeito()));
                dialog_recuperados.setText(String.valueOf(pais.getNum_recuperados()));

                if(pais.getNum_infetados() >= 0 && pais.getNum_infetados() < 5000){
                    dialog_estado.setText("Risco Baixo");
                    dialog_estado.setTextColor(Color.GREEN);
                } else if(pais.getNum_infetados() >= 5000 && pais.getNum_infetados() < 10000){
                    dialog_estado.setText("Risco Moderado");
                    dialog_estado.setTextColor(Color.rgb(252,212,19));
                }else if(pais.getNum_infetados()>=10000 && pais.getNum_infetados() < 30000){
                    dialog_estado.setText("Risco Elevado");
                    dialog_estado.setTextColor(Color.rgb(255,171,36));
                }else{
                    dialog_estado.setText("Risco Extremo");
                    dialog_estado.setTextColor(Color.rgb(178,0,32));
                }

                Toast.makeText(context,"Visualizar " + pais.getNome_pais(),Toast.LENGTH_SHORT).show();
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

    public static class ViewHolderPaises extends RecyclerView.ViewHolder{
        private TextView textViewNome;
        private TextView textViewEstado;
        private RelativeLayout relativeLayout;
        private Pais pais;

        public ViewHolderPaises(@NonNull View itemView) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.item_nome);
            textViewEstado = itemView.findViewById(R.id.item_estado2);
            relativeLayout = itemView.findViewById(R.id.setModels);
        }

        public void setPais(Pais pais){

            this.pais=pais;

            textViewNome.setText(pais.getNome_pais());

            if(pais.getNum_infetados() >= 0 && pais.getNum_infetados() < 5000){
                textViewEstado.setText("Risco Baixo");
            } else if(pais.getNum_infetados() >= 5000 && pais.getNum_infetados() < 10000){
                textViewEstado.setText("Risco Moderado");
            }else if(pais.getNum_infetados()>=10000 && pais.getNum_infetados() < 30000){
                textViewEstado.setText("Risco Elevado");
            }else{
                textViewEstado.setText("Risco Extremo");
            }
        }
    }
}
