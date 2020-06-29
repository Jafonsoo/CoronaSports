package pt.ipg.coronasports.Atleta;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import pt.ipg.coronasports.Bdcorona.ContentProviderCorona;
import pt.ipg.coronasports.Equipa.EliminarEquipaFragment;
import pt.ipg.coronasports.MainActivity;
import pt.ipg.coronasports.Modelos.Atleta;
import pt.ipg.coronasports.Modelos.Equipa;
import pt.ipg.coronasports.R;

public class EliminarAtletaFragment extends Fragment {

    private Atleta atleta;
    private TextView textViewNome;


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        MainActivity activity = (MainActivity) getActivity();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_eliminar_atleta, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity activity = (MainActivity) getActivity();
        activity.setFragmentActual(this);
        activity.setMenuActual(R.menu.menu_main);

        textViewNome = (TextView) view.findViewById(R.id.textViewNomeAtleta);
        TextView textViewIdade = (TextView) view.findViewById(R.id.textViewIdade);
        TextView textViewData = (TextView) view.findViewById(R.id.textViewDataConfirmacao);
        TextView textViewDados = (TextView) view.findViewById(R.id.textViewDados);
        TextView textViewEstado = (TextView) view.findViewById(R.id.textViewEstado);
        TextView textViewFuncao = (TextView) view.findViewById(R.id.textViewFuncao);
        TextView textViewEquipa = (TextView) view.findViewById(R.id.textViewEquipa);
        TextView textViewPais = (TextView) view.findViewById(R.id.textViewPais2);

        Button buttonEliminar = (Button) view.findViewById(R.id.buttonEliminar);
        buttonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
            }
        });

        Button buttonCancelar = (Button) view.findViewById(R.id.buttonCancelar);
        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar();
            }
        });

        atleta = activity.getAtleta();

        textViewNome.setText(atleta.getNome_atleta());
        textViewIdade.setText(String.valueOf(atleta.getIdade_atleta()));
        textViewFuncao.setText(atleta.getFuncao());
        textViewDados.setText(atleta.getDados_atleta());
        textViewData.setText(atleta.getData_corona());
        textViewEquipa.setText(atleta.getNomeEquipaAtleta());
        textViewPais.setText(atleta.getNomePaisAtleta());

        if(atleta.getEstado_atleta()==0){
            textViewEstado.setText("Infetado");
        }else if (atleta.getEstado_atleta()==1){
            textViewEstado.setText("Recuperdo");
        }else {
            textViewEstado.setText("Falecido");
        }
    }

    public void eliminar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle(getString(R.string.eliminar) + atleta.getFuncao());
        builder.setMessage(getString(R.string.eliatleta) + atleta.getNome_atleta() + "'");
        builder.setIcon(R.drawable.ic_baseline_delete_24);
        builder.setPositiveButton(R.string.simeliminar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                confirmaEliminar();
            }
        });

        builder.setNegativeButton(R.string.naocancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // cancelar
            }
        });

        builder.show();
    }

    public void cancelar(){
        NavController navController = NavHostFragment.findNavController(EliminarAtletaFragment.this);
        navController.navigate(R.id.action_eliminarAtletaFragment_to_AtletaFragment);
    }

    private void confirmaEliminar() {
        try {
            Uri enderecoAtleta = Uri.withAppendedPath(ContentProviderCorona.ENDERECO_ATLETAS, String.valueOf(atleta.getId_atleta()));

            int apagados = getActivity().getContentResolver().delete(enderecoAtleta, null, null);

            if (apagados == 1) {
                Toast.makeText(getContext(), R.string.sucessatletea, Toast.LENGTH_SHORT).show();
                NavController navController = NavHostFragment.findNavController(EliminarAtletaFragment.this);
                navController.navigate(R.id.action_eliminarAtletaFragment_to_AtletaFragment);
                return;
            }
        } catch (Exception e) {

        }

        Snackbar.make(textViewNome, R.string.impeliminaratl, Snackbar.LENGTH_INDEFINITE).show();
    }
}
