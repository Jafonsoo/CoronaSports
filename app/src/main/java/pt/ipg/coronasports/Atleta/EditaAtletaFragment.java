package pt.ipg.coronasports.Atleta;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
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
import pt.ipg.coronasports.R;

public class EditaAtletaFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final int ID_CURSO_LOADER_PAISES = 0;
    private static final int ID_CURSO_LOADER_EQUIPAS = 1;

    private EditText editTextNomeJogador;
    private EditText editTextDataJogador;
    private EditText editTextFuncao;
    private EditText editTextIdade;
    private EditText editTextDados;
    private Atleta atleta;

    private String[] stringEstado = new String[]{"Infetado", "Recuperado", "Falecido"};
    private Spinner spinnerEstado;
    private Spinner spinnerPaises;
    private Spinner spinnerEquipas;

    private boolean paisesCarregados = false;
    private boolean paisAtualizado = false;

    private boolean equipasCarregados = false;
    private boolean equipasatualizado = false;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editar_atleta, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity activity = (MainActivity) getActivity();
        activity.setFragmentActual(this);
        activity.setMenuActual(R.menu.menu_editar_atletas);

        editTextNomeJogador = view.findViewById(R.id.nome_jogador);
        editTextDataJogador = view.findViewById(R.id.data_jogador);
        editTextFuncao = view.findViewById(R.id.funcao_jogador);
        editTextIdade = view.findViewById(R.id.idade);
        editTextDados = view.findViewById(R.id.dados_clinicos);
        spinnerEstado = view.findViewById(R.id.spinner_estado);
        spinnerEquipas = view.findViewById(R.id.spinner_equipa);
        spinnerPaises = view.findViewById(R.id.spinner_pais);

        atleta = activity.getAtleta();

        editTextNomeJogador.setText(atleta.getNome_atleta());
        editTextIdade.setText(String.valueOf(atleta.getIdade_atleta()));
        editTextFuncao.setText(atleta.getFuncao());
        editTextDados.setText(atleta.getDados_atleta());
        editTextDataJogador.setText(atleta.getData_corona());

        LoaderManager.getInstance(this).initLoader(ID_CURSO_LOADER_PAISES, null, this);
        LoaderManager.getInstance(this).initLoader(ID_CURSO_LOADER_EQUIPAS, null, this);

        setSpinnerEstado();

        atualizaEquipaSelecionado();
        atualizaPaisSelecionado();
        estado();
    }

    public void sair(){
        NavController navController = NavHostFragment.findNavController(EditaAtletaFragment.this);
        navController.navigate(R.id.action_editaAtletaFragment_to_AtletaFragment);
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

        atleta.setNome_atleta(nome);
        atleta.setData_corona(data);
        atleta.setDados_atleta(dados);
        atleta.setIdade_atleta(idade);
        atleta.setEstado_atleta(idEstado);
        atleta.setFuncao(funcao);
        atleta.setPais_atleta(idPais);
        atleta.setEquipa_atleta(idEquipa);


        try {
            Uri enderecoEquipaEditar = Uri.withAppendedPath(ContentProviderCorona.ENDERECO_ATLETAS, String.valueOf(atleta.getId_atleta()));

            getActivity().getContentResolver().update(enderecoEquipaEditar, atleta.getContentValues(),null,null);

            Toast.makeText(getContext(), "Formulário guardado com sucesso", Toast.LENGTH_SHORT).show();
            NavController navController = NavHostFragment.findNavController(EditaAtletaFragment.this);
            navController.navigate(R.id.action_editaAtletaFragment_to_AtletaFragment);
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
                new int[]{android.R.id.text1}
        );
        spinnerEquipas.setAdapter(adaptadorEquipas);
    }

    private void atualizaEquipaSelecionado() {
        if(!equipasCarregados)
            return;
        if(equipasatualizado)
            return;

        for(int i = 0; i < spinnerEquipas.getCount(); i++) {
            if (spinnerEquipas.getItemIdAtPosition(i) == atleta.getEquipa_atleta()) {
                spinnerEquipas.setSelection(i);
                break;
            }
        }

        equipasatualizado = true;
    }

    private void estado() {
        for (int i = 0; i < spinnerEstado.getCount(); i++) {
            if (spinnerEstado.getItemIdAtPosition(i) == atleta.getEstado_atleta()) {
                spinnerEstado.setSelection(i);
                break;
            }
        }
    }

    private void atualizaPaisSelecionado() {
        if(!paisesCarregados) return;
        if(paisAtualizado) return;

        for(int i = 0; i < spinnerPaises.getCount(); i++) {
            if (spinnerPaises.getItemIdAtPosition(i) == atleta.getPais_atleta()) {
                spinnerPaises.setSelection(i);
                break;
            }
        }
        paisAtualizado = true;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        CursorLoader cursorLoader;

        if (id == ID_CURSO_LOADER_PAISES){
            cursorLoader = new androidx.loader.content.CursorLoader(getContext(), ContentProviderCorona.ENDERECO_PAISES, BdTablePaises.TODAS_COLUNAS, null, null, BdTablePaises.CAMPO_NOME);

            return cursorLoader;
        }else {
            cursorLoader = new androidx.loader.content.CursorLoader(getContext(), ContentProviderCorona.ENDERECO_EQUIPAS, BdTableEquipa.TODAS_COLUNAS, null, null, BdTableEquipa.CAMPO_NOME);
            return cursorLoader;
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if(loader.getId() == ID_CURSO_LOADER_PAISES){
            mostraPaisesSpinner(data);
            paisesCarregados=true;
            atualizaPaisSelecionado();
        } else{
            mostraEquipasSpinner(data);
            equipasCarregados=true;
            atualizaEquipaSelecionado();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        if(loader.getId() == ID_CURSO_LOADER_PAISES){
            paisesCarregados=false;
            paisAtualizado=false;
            mostraPaisesSpinner(null);
        } else {
            equipasCarregados=false;
            equipasatualizado=false;
            mostraEquipasSpinner(null);
        }
    }
}
