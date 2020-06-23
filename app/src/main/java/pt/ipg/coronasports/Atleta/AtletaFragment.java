package pt.ipg.coronasports.Atleta;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pt.ipg.coronasports.Adaptadores.AdaptadorAtletas;
import pt.ipg.coronasports.Bdcorona.BdTableAtletas;
import pt.ipg.coronasports.Bdcorona.ContentProviderCorona;
import pt.ipg.coronasports.Equipa.EquipaFragment;
import pt.ipg.coronasports.MainActivity;
import pt.ipg.coronasports.Pais.PaisFragment;
import pt.ipg.coronasports.R;

//https://stackoverflow.com/questions/7940765/how-to-hide-the-soft-keyboard-from-inside-a-fragment

public class AtletaFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int ID_CURSO_LOADER_ATLETAS = 0;
    private AdaptadorAtletas adaptadorAtleta;

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
        return inflater.inflate(R.layout.fragmento_atletas, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();

        MainActivity activity = (MainActivity) getActivity();
        activity.setFragmentActual(this);
        activity.setMenuActual(R.menu.menu_lista_atleta);

        RecyclerView recyclerViewAtletas = (RecyclerView) view.findViewById(R.id.recyclerViewAtleta);
        adaptadorAtleta = new AdaptadorAtletas(context);
        recyclerViewAtletas.setAdapter(adaptadorAtleta);
        recyclerViewAtletas.setLayoutManager( new LinearLayoutManager(context) );

        LoaderManager.getInstance(this).initLoader(ID_CURSO_LOADER_ATLETAS, null, this);
    }


    public void novoAtleta(){
        NavController navController = NavHostFragment.findNavController(AtletaFragment.this);
        navController.navigate(R.id.action_adicionaAtleta);
    }

    public void editaAtelta(){
        NavController navController = NavHostFragment.findNavController(AtletaFragment.this);
        navController.navigate(R.id.action_editaAtletaFragment);
    }

    public void apagaAtleta(){
        NavController navController = NavHostFragment.findNavController(AtletaFragment.this);
        navController.navigate(R.id.action_eliminarAtletaFragment);
    }

    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(getContext(), ContentProviderCorona.ENDERECO_ATLETAS, BdTableAtletas.TODAS_COLUNAS, null, null, BdTableAtletas.CAMPO_NOME);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adaptadorAtleta.setCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adaptadorAtleta.setCursor(null);
    }
}
