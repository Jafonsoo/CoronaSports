package pt.ipg.coronasports.Pais;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import pt.ipg.coronasports.Bdcorona.ContentProviderCorona;
import pt.ipg.coronasports.MainActivity;
import pt.ipg.coronasports.Modelos.Pais;
import pt.ipg.coronasports.R;

public class AdicionaPaisFragment extends Fragment {

    private EditText editTextNomePais;
    private EditText editTextObitos;
    private EditText editTextSuspeitos;
    private EditText editTextInfetados;
    private EditText editTextRecuperados;
    private Pais pais;
    private Button button;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_add_pais, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity activity = (MainActivity) getActivity();
        activity.setFragmentActual(this);
        activity.setMenuActual(R.menu.menu_guardar_pais);

        editTextNomePais = (EditText) view.findViewById(R.id.nome_pais);
        editTextObitos = (EditText) view.findViewById(R.id.num_obitos);
        editTextInfetados = (EditText) view.findViewById(R.id.pessoas_infetadas);
        editTextSuspeitos = (EditText) view.findViewById(R.id.casos_suspeitos);
        editTextRecuperados = (EditText) view.findViewById(R.id.num_recuperados);
        button = (Button) view.findViewById(R.id.button_inserir_pais);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
            }
        });

    }



    public void sair(){
        NavController navController = NavHostFragment.findNavController(AdicionaPaisFragment.this);
        navController.navigate(R.id.action_AdicionaPais_to_PaisFragment);
    }




    public void guardar(){
        String nome = editTextNomePais.getText().toString();

        if(nome.trim().isEmpty()){
            editTextNomePais.setError(getString(R.string.validaçao));
            return;
        }

        int suspeitos;

        String strSuspeitos = editTextSuspeitos.getText().toString();

        if (strSuspeitos.trim().isEmpty()){
            editTextSuspeitos.setError(getString(R.string.validaçao));
            return;
        }

        try {
            suspeitos = Integer.parseInt(strSuspeitos);
        } catch (NumberFormatException e) {
            editTextSuspeitos.setError(getString(R.string.invalido));
            return;
        }

        int infetados;

        String strInfetados = editTextInfetados.getText().toString();

        if (strInfetados.trim().isEmpty()){
            editTextInfetados.setError(getString(R.string.validaçao));
            return;
        }

        try {
            infetados = Integer.parseInt(strInfetados);
        } catch (NumberFormatException e) {
            editTextInfetados.setError(getString(R.string.invalido));
            return;
        }

        int mortos;

        String strMortos = editTextObitos.getText().toString();

        if (strMortos.trim().isEmpty()){
            editTextObitos.setError(getString(R.string.validaçao));
            return;
        }

        try {
            mortos = Integer.parseInt(strMortos);
        } catch (NumberFormatException e) {
            editTextObitos.setError(getString(R.string.invalido));
            return;
        }

        int recuperados;

        String strRecuperados = editTextRecuperados.getText().toString();

        if (strRecuperados.trim().isEmpty()){
            editTextRecuperados.setError(getString(R.string.validaçao));
            return;
        }

        try {
            recuperados = Integer.parseInt(strRecuperados);
        } catch (NumberFormatException e) {
            editTextRecuperados.setError(getString(R.string.invalido));
            return;
        }

        Pais pais = new Pais();

        pais.setNome_pais(nome);
        pais.setNum_obitos(mortos);
        pais.setNum_infetados(infetados);
        pais.setNum_suspeito(suspeitos);
        pais.setNum_recuperados(recuperados);

        try {
            getActivity().getContentResolver().insert(ContentProviderCorona.ENDERECO_PAISES, pais.getContentValues());

            Toast.makeText(getContext(), R.string.guardarpais, Toast.LENGTH_SHORT).show();
            NavController navController = NavHostFragment.findNavController(AdicionaPaisFragment.this);
            navController.navigate(R.id.action_AdicionaPais_to_PaisFragment);
        } catch (Exception e) {
            Snackbar.make(editTextNomePais, R.string.erroguardarpais, Snackbar.LENGTH_LONG).show();

            e.printStackTrace();
        }
    }


}
