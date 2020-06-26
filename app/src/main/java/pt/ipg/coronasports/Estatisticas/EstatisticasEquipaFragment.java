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
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pt.ipg.coronasports.Adaptadores.AdaptadorEstatisticasEquipa;
import pt.ipg.coronasports.Adaptadores.AdaptadorEstatisticasPais;
import pt.ipg.coronasports.Bdcorona.BdTableEquipa;
import pt.ipg.coronasports.Bdcorona.ContentProviderCorona;
import pt.ipg.coronasports.MainActivity;
import pt.ipg.coronasports.Pais.PaisFragment;
import pt.ipg.coronasports.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class EstatisticasEquipaFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    //https://www.tutorialspoint.com/sqlite/sqlite_useful_functions.htm


    private AdaptadorEstatisticasEquipa adaptadorEstatisticasEquipa;
    private static final int ID_CURSO_LOADER_EQUIPAS= 0;
    private TextView totalEquipas;
    private TextView maxEquipa;
    private Dialog myDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_estatisticas_equipa, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();

        MainActivity activity = (MainActivity) getActivity();
        activity.setFragmentActual(this);
        activity.setMenuActual(R.menu.menu_estat_equipa);


        RecyclerView recyclerViewEquipas = view.findViewById(R.id.recycleViewEstatEquipa);
        adaptadorEstatisticasEquipa = new AdaptadorEstatisticasEquipa(context);
        recyclerViewEquipas.setAdapter(adaptadorEstatisticasEquipa);
        recyclerViewEquipas.setLayoutManager(new LinearLayoutManager(context));

        adaptadorEstatisticasEquipa.setCursor(null);

        LoaderManager.getInstance(this).initLoader(ID_CURSO_LOADER_EQUIPAS, null, this);
    }

    public void AbreDetalhesEquipa(){
        myDialog = new Dialog(getActivity());
        myDialog.setContentView(R.layout.dialog_estatistica_equipas);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        totalEquipas = myDialog.findViewById(R.id.numtotalequipas);
        maxEquipa = myDialog.findViewById(R.id.textmaxequipa);

        TotalEquipa();
        UltimaEquipaInserida();

        Button buttonEstatEquipa = myDialog.findViewById(R.id.buttonEstatEquipa);
        buttonEstatEquipa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }


    public void TotalEquipa(){
        try {
            SQLiteDatabase db = getActivity().openOrCreateDatabase("Corona.db", Context.MODE_PRIVATE,null);

            Cursor c = db.rawQuery("SELECT COUNT(*) FROM equipa", null);
            if (c.getCount() > 0){
                c.moveToFirst();
                int totalpaises = c.getInt(0);
                totalEquipas.setText(String.valueOf(totalpaises));
                c.moveToNext();
            }
            c.close();
        } catch (Exception e){
            totalEquipas.setError("Impossivel contabilizar");
        }
    }

    public void UltimaEquipaInserida(){
        try {
            SQLiteDatabase db = getActivity().openOrCreateDatabase("Corona.db", Context.MODE_PRIVATE,null);

            Cursor c = db.rawQuery("SELECT nome_equipa FROM equipa", null);
            if (c.getCount() > 0){
                    c.moveToLast();
                    String totalpaises = c.getString(0);
                    maxEquipa.setText(totalpaises);
                    c.close();
            }
        } catch (Exception e){
            maxEquipa.setError("Erro na BD");
        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(getContext(), ContentProviderCorona.ENDERECO_EQUIPAS, BdTableEquipa.TODAS_COLUNAS, null, null, BdTableEquipa.CAMPO_NOME);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adaptadorEstatisticasEquipa.setCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adaptadorEstatisticasEquipa.setCursor(null);
    }
}