package pt.ipg.coronasports.Pais;

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
import pt.ipg.coronasports.Adaptadores.AdaptadorPaises;
import pt.ipg.coronasports.Bdcorona.BdTablePaises;
import pt.ipg.coronasports.Bdcorona.ContentProviderCorona;
import pt.ipg.coronasports.MainActivity;
import pt.ipg.coronasports.Modelos.Pais;
import pt.ipg.coronasports.R;


//https://www.youtube.com/watch?v=DMkzIOLppf4 ( incluir menu no Toolbar )
//https://stackoverflow.com/questions/7940765/how-to-hide-the-soft-keyboard-from-inside-a-fragment

public class PaisFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int ID_CURSO_LOADER_PAISES = 0;
    private AdaptadorPaises adaptadorPaises;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hideSoftKeyboard();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragmento_pais, container, false);

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();

        MainActivity activity = (MainActivity) getActivity();
        activity.setFragmentActual(this);
        activity.setMenuActual(R.menu.menu_lista_pais);

        RecyclerView recyclerViewPaises = (RecyclerView) view.findViewById(R.id.recyclerViewPais);
        adaptadorPaises = new AdaptadorPaises(context);
        recyclerViewPaises.setAdapter(adaptadorPaises);
        recyclerViewPaises.setLayoutManager(new LinearLayoutManager(context));

        adaptadorPaises.setCursor(null);

        LoaderManager.getInstance(this).initLoader(ID_CURSO_LOADER_PAISES, null, this);

    }


    public void novoPais(){
        NavController navController = NavHostFragment.findNavController(PaisFragment.this);
        navController.navigate(R.id.action_adicionaPais);

    }

    public void editaPais(){
        NavController navController = NavHostFragment.findNavController(PaisFragment.this);
        navController.navigate(R.id.action_editaPaisFragment);
    }

    public void apagaPais(){
        NavController navController = NavHostFragment.findNavController(PaisFragment.this);
        navController.navigate(R.id.action_eliminarPaisFragment);
    }

    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(getContext(), ContentProviderCorona.ENDERECO_PAISES, BdTablePaises.TODAS_COLUNAS, null, null, BdTablePaises.CAMPO_NOME);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adaptadorPaises.setCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adaptadorPaises.setCursor(null);
    }



}
