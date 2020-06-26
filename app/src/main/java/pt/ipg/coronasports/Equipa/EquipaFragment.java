package pt.ipg.coronasports.Equipa;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pt.ipg.coronasports.Adaptadores.AdaptadorEquipa;
import pt.ipg.coronasports.Bdcorona.BdTableEquipa;
import pt.ipg.coronasports.Bdcorona.ContentProviderCorona;
import pt.ipg.coronasports.MainActivity;
import pt.ipg.coronasports.R;

public class EquipaFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    //https://stackoverflow.com/questions/7940765/how-to-hide-the-soft-keyboard-from-inside-a-fragment

    private static final int ID_CURSO_LOADER_EQUIPAS = 0;
    private AdaptadorEquipa adaptadorEquipa;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        hideSoftKeyboard();
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragmento_equipa, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();

        MainActivity activity = (MainActivity) getActivity();
        activity.setFragmentActual(this);
        activity.setMenuActual(R.menu.menu_lista_equipa);

        RecyclerView recyclerViewSeries = (RecyclerView) view.findViewById(R.id.recyclerViewEquipa);
        adaptadorEquipa = new AdaptadorEquipa(context);
        recyclerViewSeries.setAdapter(adaptadorEquipa);
        recyclerViewSeries.setLayoutManager( new LinearLayoutManager(context) );

        adaptadorEquipa.setCursor(null);

        LoaderManager.getInstance(this).initLoader(ID_CURSO_LOADER_EQUIPAS, null, this);
    }


    public void novoEquipa(){
        NavController navController = NavHostFragment.findNavController(EquipaFragment.this);
        navController.navigate(R.id.action_adicionaEquipa);
    }

    public void editaEquipa(){
        NavController navController = NavHostFragment.findNavController(EquipaFragment.this);
        navController.navigate(R.id.action_editaEquipaFragment);
    }

    public void apagaEquipa(){
        NavController navController = NavHostFragment.findNavController(EquipaFragment.this);
        navController.navigate(R.id.action_eliminarEquipaFragment);
    }

    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(getContext(), ContentProviderCorona.ENDERECO_EQUIPAS, BdTableEquipa.TODAS_COLUNAS, null, null, BdTableEquipa.CAMPO_NOME);
    }


    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adaptadorEquipa.setCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adaptadorEquipa.setCursor(null);
    }
}
