package pt.ipg.coronasports.Pais;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class EliminarPaisFragment extends Fragment {

    private Pais pais;
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

        return inflater.inflate(R.layout.fragment_eliminar_pais, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity activity = (MainActivity) getActivity();
        activity.setFragmentActual(this);
        activity.setMenuActual(R.menu.menu_main);

        textViewNome = (TextView) view.findViewById(R.id.textViewNomePais);
        TextView textViewMortos = (TextView) view.findViewById(R.id.textViewNumMortos);
        TextView textViewSuspeitos = (TextView) view.findViewById(R.id.textViewSuspeitos);
        TextView textViewinfetados = (TextView) view.findViewById(R.id.textViewInfetados);
        TextView textViewrecuperados = (TextView) view.findViewById(R.id.textViewRecuperados);

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

        pais = activity.getPais();

        textViewNome.setText(pais.getNome_pais());
        textViewMortos.setText(String.valueOf(pais.getNum_obitos()));
        textViewinfetados.setText(String.valueOf(pais.getNum_infetados()));
        textViewSuspeitos.setText(String.valueOf(pais.getNum_suspeito()));
        textViewrecuperados.setText(String.valueOf(pais.getNum_recuperados()));
    }

    public void eliminar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Eliminar Pais");
        builder.setMessage("Tem a certeza que pretende eliminar o pais '" + pais.getNome_pais() + "'");
        builder.setIcon(R.drawable.ic_baseline_delete_24);
        builder.setPositiveButton("Sim, eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                confirmaEliminar();
            }
        });

        builder.setNegativeButton("Não, cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // cancelar
            }
        });

        builder.show();
    }

    public void cancelar(){
        NavController navController = NavHostFragment.findNavController(EliminarPaisFragment.this);
        navController.navigate(R.id.action_eliminarPaisFragment_to_PaisFragment);
    }

    private void confirmaEliminar() {
        try {
            Uri enderecoPais = Uri.withAppendedPath(ContentProviderCorona.ENDERECO_PAISES, String.valueOf(pais.getId()));

            int apagados = getActivity().getContentResolver().delete(enderecoPais, null, null);

            if (apagados == 1) {
                Toast.makeText(getContext(), "Pais eliminado com sucesso", Toast.LENGTH_SHORT).show();
                NavController navController = NavHostFragment.findNavController(EliminarPaisFragment.this);
                navController.navigate(R.id.action_eliminarPaisFragment_to_PaisFragment);
                return;
            }
        } catch (Exception e) {
            Snackbar.make(textViewNome, "Erro: Não foi possível eliminar o pais", Snackbar.LENGTH_INDEFINITE).show();
            e.printStackTrace();
        }


    }
}
