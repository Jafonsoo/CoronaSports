package pt.ipg.coronasports.Atleta;

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
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import pt.ipg.coronasports.Bdcorona.BdTableEquipa;
import pt.ipg.coronasports.Bdcorona.BdTablePaises;
import pt.ipg.coronasports.Bdcorona.ContentProviderCorona;
import pt.ipg.coronasports.MainActivity;
import pt.ipg.coronasports.Modelos.Atleta;
import pt.ipg.coronasports.Pais.AdicionaPaisFragment;
import pt.ipg.coronasports.R;

public class AdicionaAtletaFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final int ID_CURSO_LOADER_PAISES = 0;
    private static final int ID_CURSO_LOADER_EQUIPAS = 1;

    private EditText editTextNomeJogador;
    private EditText editTextDataJogador;
    private EditText editTextFuncao;
    private EditText editTextIdade;
    private EditText editTextDados;

    private String[] stringEstado = new String[]{"Infetado", "Recuperado", "Falecido"};
    private Spinner spinnerEstado;
    private Spinner spinnerPaises;
    private Spinner spinnerEquipas;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_atleta, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity activity = (MainActivity) getActivity();
        activity.setFragmentActual(this);
        activity.setMenuActual(R.menu.menu_guardar_atletas);

        editTextNomeJogador = view.findViewById(R.id.nome_jogador);
        editTextDataJogador = view.findViewById(R.id.data_jogador);
        editTextFuncao = view.findViewById(R.id.funcao_jogador);
        editTextIdade = view.findViewById(R.id.idade);
        editTextDados = view.findViewById(R.id.dados_clinicos);
        spinnerEstado = view.findViewById(R.id.spinner_estado);
        spinnerEquipas = view.findViewById(R.id.spinner_equipa);
        spinnerPaises = view.findViewById(R.id.spinner_pais);

        setSpinnerEstado();
        getActivity().getSupportLoaderManager().restartLoader(ID_CURSO_LOADER_PAISES, null, this);
        getActivity().getSupportLoaderManager().restartLoader(ID_CURSO_LOADER_EQUIPAS, null, this);
    }





    public void sair(){
        NavController navController = NavHostFragment.findNavController(AdicionaAtletaFragment.this);
        navController.navigate(R.id.action_adicionaAtleta_to_AtletaFragment);
    }

    public void guardar() {
        String nome = editTextNomeJogador.getText().toString();

        if (nome.trim().isEmpty()) {
            editTextNomeJogador.setError("O campo não pode estar vazio");
            return;
        }


        String data = editTextDataJogador.getText().toString();

        if (data.trim().isEmpty()) {
            editTextDataJogador.setError("O campo não pode estar vazio");
            return;
        }

        String funcao = editTextFuncao.getText().toString();

        if (funcao.trim().isEmpty()) {
            editTextFuncao.setError("O campo não pode estar vazio");
            return;
        }

        int idade;

        String strIdade = editTextIdade.getText().toString();

        if (strIdade.trim().isEmpty()) {
            editTextIdade.setError("O campo não pode estar vazio!");
            return;
        }
        try {
            idade = Integer.parseInt(strIdade);
        } catch (NumberFormatException e) {
            editTextIdade.setError("Campo Inválido");
            return;
        }

        String dados = editTextDados.getText().toString();

        if (dados.trim().isEmpty()) {
            editTextDados.setError("O campo não pode estar vazio");
            return;
        }



        long idEstado = spinnerEstado.getSelectedItemPosition();

        long idPais = spinnerPaises.getSelectedItemId();

        long idEquipa = spinnerEquipas.getSelectedItemId();

        Atleta atleta = new Atleta();

        atleta.setNome_atleta(nome);
        atleta.setData_corona(data);
        atleta.setDados_atleta(dados);
        atleta.setIdade_atleta(idade);
        atleta.setEstado_atleta(idEstado);
        atleta.setFuncao(funcao);
        atleta.setPais_atleta(idPais);
        atleta.setEquipa_atleta(idEquipa);


        try {
            getActivity().getContentResolver().insert(ContentProviderCorona.ENDERECO_ATLETAS, atleta.getContentValues());

            Toast.makeText(getContext(), "Formulário guardado com sucesso", Toast.LENGTH_SHORT).show();
            NavController navController = NavHostFragment.findNavController(AdicionaAtletaFragment.this);
            navController.navigate(R.id.action_adicionaAtleta_to_AtletaFragment);
        } catch (Exception e) {
            Snackbar.make(
                    editTextNomeJogador,
                    "Erro ao guardar Atleta",
                    Snackbar.LENGTH_LONG)
                    .show();
            e.printStackTrace();
        }

    }

    private void setSpinnerEstado() {

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, stringEstado);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstado.setAdapter(adapter);

    }

    private void mostraPaisesSpinner(Cursor cursorPaises) {
        SimpleCursorAdapter adaptadorPaises = new SimpleCursorAdapter(
                getContext(),
                android.R.layout.simple_list_item_1,
                cursorPaises,
                new String[]{BdTablePaises.CAMPO_NOME},
                new int[]{android.R.id.text1}
        );
        spinnerPaises.setAdapter(adaptadorPaises);
    }

    private void mostraEquipasSpinner(Cursor cursorEquipas) {
        SimpleCursorAdapter adaptadorEquipas = new SimpleCursorAdapter(
                getContext(),
                android.R.layout.simple_list_item_1,
                cursorEquipas,
                new String[]{BdTableEquipa.CAMPO_NOME},
                new int[]{android.R.id.text1 }
        );
        spinnerEquipas.setAdapter(adaptadorEquipas);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        CursorLoader cursorLoader;
        CursorLoader cursorLoader2;

        if (id == ID_CURSO_LOADER_PAISES){
            cursorLoader = new androidx.loader.content.CursorLoader(getContext(), ContentProviderCorona.ENDERECO_PAISES, BdTablePaises.TODAS_COLUNAS, null, null, BdTablePaises.CAMPO_NOME);

            return cursorLoader;
        }else {
            cursorLoader2 = new androidx.loader.content.CursorLoader(getContext(), ContentProviderCorona.ENDERECO_EQUIPAS, BdTableEquipa.TODAS_COLUNAS, null, null, BdTableEquipa.CAMPO_NOME);
            return cursorLoader2;
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if(loader.getId() == ID_CURSO_LOADER_PAISES){
            mostraPaisesSpinner(data);
        } else{
            mostraEquipasSpinner(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        if(loader.getId() == ID_CURSO_LOADER_PAISES){
            mostraPaisesSpinner(null);
        } else {
            mostraEquipasSpinner(null);
        }
    }
}
