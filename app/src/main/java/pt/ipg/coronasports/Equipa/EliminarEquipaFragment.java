package pt.ipg.coronasports.Equipa;

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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import pt.ipg.coronasports.Bdcorona.ContentProviderCorona;
import pt.ipg.coronasports.MainActivity;
import pt.ipg.coronasports.Modelos.Equipa;
import pt.ipg.coronasports.Modelos.Pais;
import pt.ipg.coronasports.Pais.EliminarPaisFragment;
import pt.ipg.coronasports.R;

public class EliminarEquipaFragment extends Fragment {

    private Equipa equipa;
    private TextView textViewNome;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_eliminar_equipa, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity activity = (MainActivity) getActivity();
        activity.setFragmentActual(this);
        activity.setMenuActual(R.menu.menu_main);

        textViewNome = (TextView) view.findViewById(R.id.textViewNomeEquipa);
        TextView textViewModalidade = (TextView) view.findViewById(R.id.textViewModalidade);
        TextView textViewData = (TextView) view.findViewById(R.id.textViewData);
        TextView textViewPais = (TextView) view.findViewById(R.id.textViewPais);

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

        equipa = activity.getEquipa();

        textViewNome.setText(equipa.getNome_equipa());
        textViewModalidade.setText(equipa.getModalidade());
        textViewData.setText(equipa.getData_fundacao());
        textViewPais.setText(equipa.getNomePais());
    }

    public void eliminar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle(R.string.eliminequipa);
        builder.setMessage(getString(R.string.eliequipa) + equipa.getNome_equipa() + "'");
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
        NavController navController = NavHostFragment.findNavController(EliminarEquipaFragment.this);
        navController.navigate(R.id.action_eliminarEquipaFragment_to_EquipasFragment);
    }

    private void confirmaEliminar() {
        try {
            Uri enderecoEquipa = Uri.withAppendedPath(ContentProviderCorona.ENDERECO_EQUIPAS, String.valueOf(equipa.getId()));

            int apagados = getActivity().getContentResolver().delete(enderecoEquipa, null, null);

            if (apagados == 1) {
                Toast.makeText(getContext(), R.string.eqeliminada, Toast.LENGTH_SHORT).show();
                NavController navController = NavHostFragment.findNavController(EliminarEquipaFragment.this);
                navController.navigate(R.id.action_eliminarEquipaFragment_to_EquipasFragment);
                return;
            }
        } catch (Exception e) {
            Snackbar.make(textViewNome, R.string.impeliminar, Snackbar.LENGTH_INDEFINITE).show();
            e.printStackTrace();
        }


    }
}
