package pt.ipg.coronasports.Atleta;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import pt.ipg.coronasports.R;

public class AdicionaAtletaFragment extends Fragment {

    private String[] string = new String[]{"Infetado", "Recuperado", "Falecido"};
    private Spinner spinnerEstado;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_atleta, container, false);
        setHasOptionsMenu(true);
        spinnerEstado = view.findViewById(R.id.spinner_estado);

      /*  ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,string);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstado.setAdapter(adapter);*/
        return  view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_guardar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_guardar) {
            return true;
        }else if (id == R.id.action_sair){
            sair();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }


    public void sair(){
        NavController navController = NavHostFragment.findNavController(AdicionaAtletaFragment.this);
        navController.navigate(R.id.action_adicionaAtleta_to_AtletaFragment);
    }

}
