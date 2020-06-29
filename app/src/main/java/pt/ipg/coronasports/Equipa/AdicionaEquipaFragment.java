package pt.ipg.coronasports.Equipa;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import pt.ipg.coronasports.Bdcorona.BdTablePaises;
import pt.ipg.coronasports.Bdcorona.ContentProviderCorona;
import pt.ipg.coronasports.MainActivity;
import pt.ipg.coronasports.Modelos.Equipa;
import pt.ipg.coronasports.Pais.AdicionaPaisFragment;
import pt.ipg.coronasports.R;

public class AdicionaEquipaFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final int ID_CURSO_LOADER_PAISES = 0;

    private EditText editTextDataFundacao;
    private EditText editTextNomeEquipa;
    private EditText editTextModalidades;
    private Spinner spinnerPais;
    private Button buttonEquipa;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_equipa, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity activity = (MainActivity) getActivity();
        activity.setFragmentActual(this);
        activity.setMenuActual(R.menu.menu_guardar_equipas);

        editTextDataFundacao = view.findViewById(R.id.data_fundacao);
        editTextNomeEquipa = view.findViewById(R.id.nome_equipa);
        editTextModalidades = view.findViewById(R.id.modalidade);
        spinnerPais = view.findViewById(R.id.spinner_pais);

        buttonEquipa = (Button) view.findViewById(R.id.button_inserir_equipa);

        buttonEquipa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
            }
        });

        LoaderManager.getInstance(this).initLoader(ID_CURSO_LOADER_PAISES, null, this);
    }


    public void sair(){
        NavController navController = NavHostFragment.findNavController(AdicionaEquipaFragment.this);
        navController.navigate(R.id.action_adicionaEquipaFragment_to_EquipasFragment);
    }

    public void guardar() {
        String nome = editTextNomeEquipa.getText().toString();

        if(nome.trim().isEmpty()){
            editTextNomeEquipa.setError(getString(R.string.validaçao));
            return;
        }

        String modalidades = editTextModalidades.getText().toString();

        if(modalidades.trim().isEmpty()){
            editTextModalidades.setError(getString(R.string.validaçao));
            return;
        }

        String data = editTextDataFundacao.getText().toString();

        if(data.trim().length() != 4){
            editTextDataFundacao.setError(getString(R.string.invalido));
            editTextDataFundacao.requestFocus();
            return;
        }

        long idPais = spinnerPais.getSelectedItemId();

        Equipa equipa = new Equipa();

        equipa.setNome_equipa(nome);
        equipa.setData_fundacao(data);
        equipa.setModalidade(modalidades);
        equipa.setNome_pais(idPais);

        try {
            getActivity().getContentResolver().insert(ContentProviderCorona.ENDERECO_EQUIPAS, equipa.getContentValues());

            Toast.makeText(getContext(), R.string.guardarequipa, Toast.LENGTH_SHORT).show();
            NavController navController = NavHostFragment.findNavController(AdicionaEquipaFragment.this);
            navController.navigate(R.id.action_adicionaEquipaFragment_to_EquipasFragment);
        } catch (Exception e) {
            Snackbar.make(
                    editTextNomeEquipa,
                    R.string.erroguaradrequipa,
                    Snackbar.LENGTH_LONG)
                    .show();
            e.printStackTrace();
        }

    }

    private void mostraPaisesSpinner(Cursor cursorPaises) {
        SimpleCursorAdapter adaptadorPais = new SimpleCursorAdapter(
                getContext(),
                android.R.layout.simple_list_item_1,
                cursorPaises,
                new String[]{BdTablePaises.CAMPO_NOME},
                new int[]{android.R.id.text1}
        );
        spinnerPais.setAdapter(adaptadorPais);
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        androidx.loader.content.CursorLoader cursorLoader = new androidx.loader.content.CursorLoader(getContext(), ContentProviderCorona.ENDERECO_PAISES, BdTablePaises.TODAS_COLUNAS, null, null, BdTablePaises.CAMPO_NOME
        );

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mostraPaisesSpinner(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mostraPaisesSpinner(null);
    }
}
