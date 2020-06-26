package pt.ipg.coronasports.Pais;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import pt.ipg.coronasports.Modelos.Pais;
import pt.ipg.coronasports.R;

public class EditaPaisFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    public static final int ID_CURSOR_LOADER_Pais = 0;

    private EditText editTextNomePais;
    private EditText editTextObitos;
    private EditText editTextSuspeitos;
    private EditText editTextInfetados;
    private EditText editTextRecuperados;
    private Pais pais;
    private Button buttonPais;


    public EditaPaisFragment(){

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_editar_pais, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity activity = (MainActivity) getActivity();
        activity.setFragmentActual(this);
        activity.setMenuActual(R.menu.menu_editar_pais);

        editTextNomePais = (EditText) view.findViewById(R.id.nome_pais);
        editTextObitos = (EditText) view.findViewById(R.id.num_obitos);
        editTextInfetados = (EditText) view.findViewById(R.id.pessoas_infetadas);
        editTextSuspeitos = (EditText) view.findViewById(R.id.casos_suspeitos);
        editTextRecuperados = (EditText) view.findViewById(R.id.num_recuperados);

        pais = activity.getPais();

        editTextNomePais.setText(pais.getNome_pais());
        editTextObitos.setText(String.valueOf(pais.getNum_obitos()));
        editTextSuspeitos.setText(String.valueOf(pais.getNum_suspeito()));
        editTextInfetados.setText(String.valueOf(pais.getNum_infetados()));
        editTextRecuperados.setText(String.valueOf(pais.getNum_recuperados()));

        buttonPais = (Button) view.findViewById(R.id.button_inserir_pais);

        buttonPais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
            }
        });

        LoaderManager.getInstance(this).initLoader(ID_CURSOR_LOADER_Pais, null, this);

    }


    public void sair(){
        NavController navController = NavHostFragment.findNavController(EditaPaisFragment.this);
        navController.navigate(R.id.action_editaPaisFragment_to_PaisFragment);
    }

    public void guardar(){
        String nome = editTextNomePais.getText().toString();

        if(nome.trim().isEmpty()){
            editTextNomePais.setError("O campo não pode estar vazio");
            return;
        }

        int suspeitos;

        String strSuspeitos = editTextSuspeitos.getText().toString();

        if (strSuspeitos.trim().isEmpty()){
            editTextSuspeitos.setError("O campo não pode estar vazio");
            return;
        }

        try {
            suspeitos = Integer.parseInt(strSuspeitos);
        } catch (NumberFormatException e) {
            editTextSuspeitos.setError("Suspeitos Inválidos");
            return;
        }

        int infetados;

        String strInfetados = editTextInfetados.getText().toString();

        if (strInfetados.trim().isEmpty()){
            editTextInfetados.setError("O campo não pode estar vazio");
            return;
        }

        try {
            infetados = Integer.parseInt(strInfetados);
        } catch (NumberFormatException e) {
            editTextInfetados.setError("infetados Inválidos");
            return;
        }

        int mortos;

        String strMortos = editTextObitos.getText().toString();

        if (strMortos.trim().isEmpty()){
            editTextObitos.setError("O campo não pode estar vazio");
            return;
        }

        try {
            mortos = Integer.parseInt(strMortos);
        } catch (NumberFormatException e) {
            editTextObitos.setError("Obitos Inválidos");
            return;
        }

        int recuperados;

        String strRecuperados = editTextRecuperados.getText().toString();

        if (strRecuperados.trim().isEmpty()){
            editTextRecuperados.setError("O campo não pode estar vazio");
            return;
        }

        try {
            recuperados = Integer.parseInt(strRecuperados);
        } catch (NumberFormatException e) {
            editTextRecuperados.setError("Recuperados Inválidos");
            return;
        }

        pais.setNome_pais(nome);
        pais.setNum_obitos(mortos);
        pais.setNum_infetados(infetados);
        pais.setNum_suspeito(suspeitos);
        pais.setNum_recuperados(recuperados);

        try {
            Uri enderecoPaisEditar = Uri.withAppendedPath(ContentProviderCorona.ENDERECO_PAISES, String.valueOf(pais.getId()));

            getActivity().getContentResolver().update(enderecoPaisEditar, pais.getContentValues(),null, null);

                Toast.makeText(getContext(), "Pais guardado com sucesso", Toast.LENGTH_SHORT).show();
                NavController navController = NavHostFragment.findNavController(EditaPaisFragment.this);
                navController.navigate(R.id.action_editaPaisFragment_to_PaisFragment);

        } catch (Exception e) {
            Snackbar.make(editTextNomePais,"Erro: Não foi possível criar o pais", Snackbar.LENGTH_INDEFINITE).show();
            e.printStackTrace();
        }

    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(getContext(), ContentProviderCorona.ENDERECO_PAISES, BdTablePaises.TODAS_COLUNAS, null, null, BdTablePaises.CAMPO_NOME);

    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
