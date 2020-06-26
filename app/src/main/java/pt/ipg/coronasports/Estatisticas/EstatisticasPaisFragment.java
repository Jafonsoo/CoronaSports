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
import pt.ipg.coronasports.Adaptadores.AdaptadorEstatisticasPais;
import pt.ipg.coronasports.Bdcorona.BdTablePaises;
import pt.ipg.coronasports.Bdcorona.ContentProviderCorona;
import pt.ipg.coronasports.MainActivity;
import pt.ipg.coronasports.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class EstatisticasPaisFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    //https://www.youtube.com/watch?v=Zd0TUuoPP-s

    //https://www.tutorialspoint.com/sqlite/sqlite_useful_functions.htm

    private TextView totalPaises;
    private TextView totalinfetados;
    private TextView totalmortos;
    private TextView totalsuspeitos;
    private TextView totalrecuperados;
    private Button buttonEstatPais;
    private Dialog myDialog;
    private AdaptadorEstatisticasPais adaptadorEstatisticasPais;
    private static final int ID_CURSO_LOADER_PAISES = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragmento_estatisticas_pais, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();

        MainActivity activity = (MainActivity) getActivity();
        activity.setFragmentActual(this);
        activity.setMenuActual(R.menu.menu_estat_pais);


        RecyclerView recyclerViewPaises = view.findViewById(R.id.recycleViewEstatPais);
        adaptadorEstatisticasPais = new AdaptadorEstatisticasPais(context);
        recyclerViewPaises.setAdapter(adaptadorEstatisticasPais);
        recyclerViewPaises.setLayoutManager(new LinearLayoutManager(context));

        adaptadorEstatisticasPais.setCursor(null);

        LoaderManager.getInstance(this).initLoader(ID_CURSO_LOADER_PAISES, null, this);
    }

    public void AbreDetalhesPais(){
        myDialog = new Dialog(getActivity());
        myDialog.setContentView(R.layout.dialog_estatistica_pais);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        totalPaises = myDialog.findViewById(R.id.numtotalpaises);
        totalinfetados = myDialog.findViewById(R.id.numTotalInfetados);
        totalmortos = myDialog.findViewById(R.id.numTotalObitos);
        totalsuspeitos = myDialog.findViewById(R.id.numTotalSuspeitos);
        totalrecuperados = myDialog.findViewById(R.id.numTotalRecuperados);

        TotalPaises();
        TotalObitos();
        TotalInfetados();
        TotalSuspeitos();
        TotalRecuperados();

        buttonEstatPais = myDialog.findViewById(R.id.buttonEstatPais);
        buttonEstatPais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

    public void TotalPaises(){
        try {
            SQLiteDatabase db = getActivity().openOrCreateDatabase("Corona.db", Context.MODE_PRIVATE,null);

            Cursor c = db.rawQuery("SELECT COUNT(*) FROM paises", null);
            if (c.getCount() > 0){
                c.moveToFirst();
                int totalpaises = c.getInt(0);
                totalPaises.setText(String.valueOf(totalpaises));
                c.moveToNext();
            }
            c.close();
        } catch (Exception e){
            totalPaises.setError("Impossivel somar");
        }
    }

    public void TotalObitos(){
        try {
            SQLiteDatabase db = getActivity().openOrCreateDatabase("Corona.db", Context.MODE_PRIVATE,null);

            Cursor c = db.rawQuery("SELECT SUM(mortos_pais) FROM paises", null);
            if (c.moveToFirst()){
                int totalmortos2 = c.getInt(0);
                totalmortos.setText(String.valueOf(totalmortos2));
            }
            c.close();
        } catch (Exception e){
            totalmortos.setError("Impossivel somar");
        }
    }

    public void TotalInfetados(){
        try {
            SQLiteDatabase db = getActivity().openOrCreateDatabase("Corona.db", Context.MODE_PRIVATE,null);

            Cursor c = db.rawQuery("SELECT SUM(infetados_pais) FROM paises", null);
            if (c.moveToFirst()){
                int totalmorto = c.getInt(0);
                totalinfetados.setText(String.valueOf(totalmorto));
            }
            c.close();
        } catch (Exception e){
            totalinfetados.setError("Impossivel somar");
        }
    }

    public void TotalSuspeitos(){
        try {
            SQLiteDatabase db = getActivity().openOrCreateDatabase("Corona.db", Context.MODE_PRIVATE,null);

            Cursor c = db.rawQuery("SELECT SUM(suspeitos_pais) FROM paises", null);
            if (c.moveToFirst()){
                int totalsuspeito = c.getInt(0);
                totalsuspeitos.setText(String.valueOf(totalsuspeito));
            }
            c.close();
        } catch (Exception e){
            totalsuspeitos.setError("Impossivel somar");
        }
    }

    public void TotalRecuperados(){
        try {
            SQLiteDatabase db = getActivity().openOrCreateDatabase("Corona.db", Context.MODE_PRIVATE,null);

            Cursor c = db.rawQuery("SELECT SUM(recuperados_pais) FROM paises", null);
            if (c.moveToFirst()){
                int totalrecuparado = c.getInt(0);
                totalrecuperados.setText(String.valueOf(totalrecuparado));
            }
        } catch (Exception e){
            totalrecuperados.setError("Impossivel somar");
        }
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        CursorLoader cursorLoader = new CursorLoader(getContext(), ContentProviderCorona.ENDERECO_PAISES, BdTablePaises.TODAS_COLUNAS, null, null, BdTablePaises.CAMPO_NOME);

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adaptadorEstatisticasPais.setCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adaptadorEstatisticasPais.setCursor(null);
    }
}