package pt.ipg.coronasports.Estatisticas;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pt.ipg.coronasports.Adaptadores.AdaptadorEstatisticasAtletas;
import pt.ipg.coronasports.Adaptadores.AdaptadorEstatisticasPais;
import pt.ipg.coronasports.Bdcorona.BdTableAtletas;
import pt.ipg.coronasports.Bdcorona.ContentProviderCorona;
import pt.ipg.coronasports.MainActivity;
import pt.ipg.coronasports.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class EstatisticasAtletaFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    //https://www.tutorialspoint.com/sqlite/sqlite_useful_functions.htm

    private AdaptadorEstatisticasAtletas adaptadorEstatisticasAtletas;
    private static final int ID_CURSO_LOADER_ATLETAS = 0;
    private TextView totalAtletas;
    private TextView mediaAtletas;
    private TextView ultimoAtleta;
    private Button buttonEstatAtletas;
    private Dialog myDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_estatisticas_atleta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();

        MainActivity activity = (MainActivity) getActivity();
        activity.setFragmentActual(this);
        activity.setMenuActual(R.menu.menu_estat_atleta);


        RecyclerView recyclerViewAtletas = view.findViewById(R.id.recycleViewEstatAtleta);
        adaptadorEstatisticasAtletas = new AdaptadorEstatisticasAtletas(context);
        recyclerViewAtletas.setAdapter(adaptadorEstatisticasAtletas);
        recyclerViewAtletas.setLayoutManager(new LinearLayoutManager(context));

        adaptadorEstatisticasAtletas.setCursor(null);

        LoaderManager.getInstance(this).initLoader(ID_CURSO_LOADER_ATLETAS, null, this);
    }

    public void AbreDetalhesAtleta(){
        myDialog = new Dialog(getActivity());
        myDialog.setContentView(R.layout.dialog_estatistica_atletas);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        totalAtletas = myDialog.findViewById(R.id.numtotalatletas);
        mediaAtletas = myDialog.findViewById(R.id.mediaAtleta);
        ultimoAtleta = myDialog.findViewById(R.id.textmaxatleta);

        TotalAtletas();
        MediaAtletas();
        UltimoAtletaInserido();

        buttonEstatAtletas = myDialog.findViewById(R.id.buttonEstatAtleta);
        buttonEstatAtletas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

    public void TotalAtletas(){
        try {
            SQLiteDatabase db = getActivity().openOrCreateDatabase("Corona.db", Context.MODE_PRIVATE,null);

            Cursor c = db.rawQuery("SELECT COUNT(*) FROM atletas", null);
            if (c.getCount() > 0){
                c.moveToFirst();
                int totalatletas = c.getInt(0);
                totalAtletas.setText(String.valueOf(totalatletas));
                c.moveToNext();
            }
            c.close();
        } catch (Exception e){
            totalAtletas.setError("Impossivel somar");
        }
    }

    public void MediaAtletas(){
        try {
            SQLiteDatabase db = getActivity().openOrCreateDatabase("Corona.db", Context.MODE_PRIVATE,null);

            Cursor c = db.rawQuery("SELECT AVG(idade_atleta) FROM atletas", null);
            if (c.getCount() > 0){
                c.moveToFirst();
                int mediaatleas = c.getInt(0);
                mediaAtletas.setText(String.valueOf(mediaatleas));
                c.moveToNext();
            }
            c.close();
        } catch (Exception e){
            mediaAtletas.setError("Impossivel somar");
        }
    }

    public void UltimoAtletaInserido(){
        try {
            SQLiteDatabase db = getActivity().openOrCreateDatabase("Corona.db", Context.MODE_PRIVATE,null);

            Cursor c = db.rawQuery("SELECT nome_atleta FROM atletas", null);
            if (c.getCount() > 0){
                c.moveToLast();
                String totalpaises = c.getString(0);
                ultimoAtleta.setText(totalpaises);
                c.close();
            }
        } catch (Exception e){
            ultimoAtleta.setError("Erro na BD");
        }
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(getContext(), ContentProviderCorona.ENDERECO_ATLETAS, BdTableAtletas.TODAS_COLUNAS, null, null, BdTableAtletas.CAMPO_NOME);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adaptadorEstatisticasAtletas.setCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adaptadorEstatisticasAtletas.setCursor(null);
    }
}