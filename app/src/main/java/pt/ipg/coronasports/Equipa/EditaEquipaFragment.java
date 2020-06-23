package pt.ipg.coronasports.Equipa;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import pt.ipg.coronasports.Bdcorona.BdTablePaises;
import pt.ipg.coronasports.Bdcorona.ContentProviderCorona;
import pt.ipg.coronasports.MainActivity;
import pt.ipg.coronasports.Modelos.Equipa;
import pt.ipg.coronasports.R;

public class EditaEquipaFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int ID_CURSO_LOADER_EQUIPA = 0;

    private EditText editTextDataFundacao;
    private EditText editTextNomeEquipa;
    private EditText editTextModalidades;
    private Spinner spinnerPais;
    private Equipa equipa;

    private boolean paisesCarregados = false;
    private boolean paisAtualizado = false;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editar_equipa, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity activity = (MainActivity) getActivity();
        activity.setFragmentActual(this);
        activity.setMenuActual(R.menu.menu_editar_equipas);

        editTextDataFundacao = view.findViewById(R.id.data_fundacao);
        editTextNomeEquipa = view.findViewById(R.id.nome_equipa);
        editTextModalidades = view.findViewById(R.id.modalidade);
        spinnerPais = view.findViewById(R.id.spinner_pais);

        equipa = activity.getEquipa();

        editTextNomeEquipa.setText(equipa.getNome_equipa());
        editTextModalidades.setText(equipa.getModalidade());
        editTextDataFundacao.setText(equipa.getData_fundacao());

        LoaderManager.getInstance(this).initLoader(ID_CURSO_LOADER_EQUIPA, null, this);

        atualizaPaisSelecionado();
    }

    public void sair(){
        NavController navController = NavHostFragment.findNavController(EditaEquipaFragment.this);
        navController.navigate(R.id.action_editaEquipaFragment_to_EquipaFragment);
    }

    public void guardar() {
        String nome = editTextNomeEquipa.getText().toString();

        if(nome.trim().isEmpty()){
            editTextNomeEquipa.setError("O campo não pode estar vazio");
            return;
        }


        String data = editTextDataFundacao.getText().toString();

        if(data.trim().isEmpty()){
            editTextDataFundacao.setError("O campo não pode estar vazio");
            return;
        }

        String modalidades = editTextModalidades.getText().toString();

        if(modalidades.trim().isEmpty()){
            editTextModalidades.setError("O campo não pode estar vazio");
            return;
        }

        long idPais = spinnerPais.getSelectedItemId();

        equipa.setNome_equipa(nome);
        equipa.setData_fundacao(data);
        equipa.setModalidade(modalidades);
        equipa.setNome_pais(idPais);

        try {
            Uri enderecoEquipaEditar = Uri.withAppendedPath(ContentProviderCorona.ENDERECO_EQUIPAS, String.valueOf(equipa.getId()));

            getActivity().getContentResolver().update(enderecoEquipaEditar, equipa.getContentValues(),null,null);

            Toast.makeText(getContext(), "Equipa guardada com sucesso", Toast.LENGTH_SHORT).show();
            NavController navController = NavHostFragment.findNavController(EditaEquipaFragment.this);
            navController.navigate(R.id.action_editaEquipaFragment_to_EquipaFragment);
        } catch (Exception e) {
            Snackbar.make(
                    editTextNomeEquipa,
                    "Erro ao guardar Equipa",
                    Snackbar.LENGTH_LONG)
                    .show();
            e.printStackTrace();
        }

    }

    private void atualizaPaisSelecionado() {
        if(!paisesCarregados) return;
        if(paisAtualizado) return;

        long idPais = equipa.getNome_pais();

        for(int i = 0; i < spinnerPais.getCount(); i++){
            if(spinnerPais.getItemIdAtPosition(i) == idPais){
                spinnerPais.setSelection(i);
                break;
            }
        }

        paisAtualizado = true;
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
        return new CursorLoader(getContext(), ContentProviderCorona.ENDERECO_PAISES, BdTablePaises.TODAS_COLUNAS, null, null, BdTablePaises.CAMPO_NOME);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mostraPaisesSpinner(data);
        paisesCarregados = true;
        atualizaPaisSelecionado();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        paisesCarregados = false;
        paisAtualizado = false;
        mostraPaisesSpinner(null);
    }
}
