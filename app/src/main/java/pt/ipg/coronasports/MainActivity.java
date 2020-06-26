package pt.ipg.coronasports;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import pt.ipg.coronasports.Atleta.AdicionaAtletaFragment;
import pt.ipg.coronasports.Atleta.AtletaFragment;
import pt.ipg.coronasports.Atleta.EditaAtletaFragment;
import pt.ipg.coronasports.Equipa.AdicionaEquipaFragment;
import pt.ipg.coronasports.Equipa.EditaEquipaFragment;
import pt.ipg.coronasports.Equipa.EquipaFragment;
import pt.ipg.coronasports.Estatisticas.EstatisticasAtletaFragment;
import pt.ipg.coronasports.Estatisticas.EstatisticasEquipaFragment;
import pt.ipg.coronasports.Estatisticas.EstatisticasPaisFragment;
import pt.ipg.coronasports.Modelos.Atleta;
import pt.ipg.coronasports.Modelos.Equipa;
import pt.ipg.coronasports.Modelos.Pais;
import pt.ipg.coronasports.Pais.AdicionaPaisFragment;
import pt.ipg.coronasports.Pais.EditaPaisFragment;
import pt.ipg.coronasports.Pais.PaisFragment;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import static pt.ipg.coronasports.R.id.nav_host_fragment;

public class MainActivity extends AppCompatActivity {

    //https://developer.android.com/guide/topics/ui/layout/linear

    //https://www.youtube.com/watch?v=DMkzIOLppf4 - How to Add a Toolbar
    //https://www.youtube.com/watch?v=bjYstsO1PgI - Navigation Drawer with Fragments

    //https://stackoverflow.com/questions/37526195/how-to-hide-navigation-drawer-item-while-the-respective-fragment-is-opened
    //https://stackoverflow.com/questions/4165414/how-to-hide-soft-keyboard-on-android-after-clicking-outside-edittext

    //https://developer.android.com/guide/navigation/navigation-ui

    private AppBarConfiguration mAppBarConfiguration;
    private Pais pais = null;
    private Equipa equipa = null;
    private Atleta atleta = null;
    private Fragment fragmentActual = null;
    private int menuActual = R.menu.menu_main;
    private Menu menu;


    public Pais getPais(){
        return pais;
    }

    public Equipa getEquipa(){
        return equipa;
    }

    public Atleta getAtleta(){
        return atleta;
    }

    public void setFragmentActual(Fragment fragmentActual) {
        this.fragmentActual = fragmentActual;
    }

    public void setMenuActual(int menuActual) {
        if (menuActual != this.menuActual) {
            this.menuActual = menuActual;
            invalidateOptionsMenu();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout mDrawerLayout = findViewById(R.id.drawermain);
        final NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_pais, R.id.nav_equipas, R.id.nav_atleta, R.id.nav_estatisticas_pais, R.id.nav_home,
                R.id.nav_estatisticas_pais, R.id.nav_estatisticas_equipa, R.id.nav_estatisticas_atleta)
                .setDrawerLayout(mDrawerLayout)
                .build();
        NavController navController = Navigation.findNavController(this, nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        final BottomNavigationView bottomNavigationView = findViewById(R.id.nav_viewBottom);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if(destination.getId() == R.id.nav_estatisticas_pais){
                    bottomNavigationView.setVisibility(View.VISIBLE);
                } else if (destination.getId() == R.id.nav_estatisticas_equipa){
                    bottomNavigationView.setVisibility(View.VISIBLE);
                    navigationView.getMenu().getItem(4).setChecked(true);
                } else if (destination.getId() == R.id.nav_estatisticas_atleta){
                    bottomNavigationView.setVisibility(View.VISIBLE);
                    navigationView.getMenu().getItem(4).setChecked(true);
                } else {
                    bottomNavigationView.setVisibility(View.GONE);
                }
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(menuActual, menu);

        this.menu = menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (menuActual == R.menu.menu_lista_pais) {
            if (processaOpcoesMenuListaPais(id)) return true;
        } else if (menuActual == R.menu.menu_lista_equipa) {
            if (processaOpcoesMenuListaEquipa(id)) return true;
        } else if (menuActual == R.menu.menu_lista_atleta) {
            if (processaOpcoesMenuListaAtleta(id)) return true;
        } else if (menuActual == R.menu.menu_guardar_pais) {
            if (processaOpcoesMenuInserirPais(id)) return true;
        }else if (menuActual == R.menu.menu_guardar_equipas) {
            if (processaOpcoesMenuInserirEquipa(id)) return true;
        }else if (menuActual == R.menu.menu_guardar_atletas) {
            if (processaOpcoesMenuInserirAtleta(id)) return true;
        } else if (menuActual == R.menu.menu_editar_pais) {
            if (processaOpcoesMenuAlterarPais(id)) return true;
        } else if (menuActual == R.menu.menu_editar_equipas){
            if (processaOpcoesMenuAlterarEquipa(id)) return true;
        } else if (menuActual == R.menu.menu_editar_atletas){
            if (processaOpcoesMenuAlterarAtleta(id)) return true;
        } else if (menuActual == R.menu.menu_estat_pais){
            if (processaOpcoesMenuDetalhesPais(id) ) return true;
        } else if (menuActual == R.menu.menu_estat_equipa){
            if(processaOpcoesMenuDetalhesEquipa(id)) return true;
        } else if (menuActual == R.menu.menu_estat_atleta){
            if(processaOpcoesMenuDetalhesAtleta(id)) return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean processaOpcoesMenuListaPais(int id) {
        PaisFragment paisFragment = (PaisFragment) fragmentActual;

        if (id == R.id.action_inserir_pais) {
            paisFragment.novoPais();
            return true;
        } else if (id == R.id.action_editar_pais) {
            paisFragment.editaPais();
            return true;
        } else if (id == R.id.action_apagar_pais) {
            paisFragment.apagaPais();
            return true;
        }

        return false;
    }

    private boolean processaOpcoesMenuListaEquipa(int id) {
        EquipaFragment equipaFragment = (EquipaFragment) fragmentActual;

        if (id == R.id.action_inserir_equipa) {
            equipaFragment.novoEquipa();
            return true;
        } else if (id == R.id.action_editar_equipa) {
            equipaFragment.editaEquipa();
            return true;
        } else if (id == R.id.action_apagar_equipa) {
            equipaFragment.apagaEquipa();
            return true;
        }

        return false;
    }

    private boolean processaOpcoesMenuListaAtleta(int id) {
        AtletaFragment atletaFragment = (AtletaFragment) fragmentActual;

        if (id == R.id.action_inserir_atleta) {
            atletaFragment.novoAtleta();
            return true;
        } else if (id == R.id.action_editar_atleta) {
            atletaFragment.editaAtelta();
            return true;
        } else if (id == R.id.action_apagar_atleta) {
            atletaFragment.apagaAtleta();
            return true;
        }

        return false;
    }

    private boolean processaOpcoesMenuInserirPais(int id) {
        AdicionaPaisFragment adicionaPaisFragment = (AdicionaPaisFragment) fragmentActual;

        if (id == R.id.action_guardar_pais) {
            adicionaPaisFragment.guardar();
            return true;
        } else if (id == R.id.action_sair_pais) {
            adicionaPaisFragment.sair();
            return true;
        }

        return false;
    }

    private boolean processaOpcoesMenuInserirEquipa(int id) {
        AdicionaEquipaFragment adicionarEquipaFragment = (AdicionaEquipaFragment) fragmentActual;

        if (id == R.id.action_guardar_equipa) {
            adicionarEquipaFragment.guardar();
            return true;
        } else if (id == R.id.action_sair_equipa) {
            adicionarEquipaFragment.sair();
            return true;
        }

        return false;
    }

    private boolean processaOpcoesMenuInserirAtleta(int id) {
        AdicionaAtletaFragment adicionarAteltaFragment = (AdicionaAtletaFragment) fragmentActual;

        if (id == R.id.action_guardar_atleta) {
            adicionarAteltaFragment.guardar();
            return true;
        } else if (id == R.id.action_sair_atleta) {
            adicionarAteltaFragment.sair();
            return true;
        }

        return false;
    }

    private boolean processaOpcoesMenuAlterarPais(int id) {
        EditaPaisFragment editaPaisFragment = (EditaPaisFragment) fragmentActual;

        if (id == R.id.action_guardar_pais) {
            editaPaisFragment.guardar();
            return true;
        } else if (id == R.id.action_sair_pais) {
            editaPaisFragment.sair();
            return true;
        }

        return false;
    }

    private boolean processaOpcoesMenuAlterarEquipa(int id) {
        EditaEquipaFragment editaEquipaFragment = (EditaEquipaFragment) fragmentActual;

        if (id == R.id.action_guardar_equipa) {
            editaEquipaFragment.guardar();
            return true;
        } else if (id == R.id.action_sair_equipa) {
            editaEquipaFragment.sair();
            return true;
        }

        return false;
    }

    private boolean processaOpcoesMenuAlterarAtleta(int id) {
        EditaAtletaFragment editaAtletaFragment = (EditaAtletaFragment) fragmentActual;

        if (id == R.id.action_guardar_atleta) {
            editaAtletaFragment.guardar();
            return true;
        } else if (id == R.id.action_sair_atleta) {
            editaAtletaFragment.sair();
            return true;
        }

        return false;
    }



    public void atualizaOpcoesMenuPais(Pais pais) {
        this.pais = pais;

        boolean mostraAlterarEliminar = (pais != null);

        menu.findItem(R.id.action_editar_pais).setVisible(mostraAlterarEliminar);
        menu.findItem(R.id.action_apagar_pais).setVisible(mostraAlterarEliminar);
        menu.findItem(R.id.action_settings).setVisible(false);

    }

    public void atualizaOpcoesMenuEquipa(Equipa equipa) {
        this.equipa = equipa;

        boolean mostraAlterarEliminar = (equipa != null);

        menu.findItem(R.id.action_editar_equipa).setVisible(mostraAlterarEliminar);
        menu.findItem(R.id.action_apagar_equipa).setVisible(mostraAlterarEliminar);
        menu.findItem(R.id.action_settings).setVisible(false);


    }

    public void atualizaOpcoesMenuAtleta(Atleta atleta) {
        this.atleta = atleta;

        boolean mostraAlterarEliminar = (atleta != null);

        menu.findItem(R.id.action_editar_atleta).setVisible(mostraAlterarEliminar);
        menu.findItem(R.id.action_apagar_atleta).setVisible(mostraAlterarEliminar);
        menu.findItem(R.id.action_settings).setVisible(false);
    }

    private boolean processaOpcoesMenuDetalhesPais(int id) {
        EstatisticasPaisFragment estatisticasPaisFragment = (EstatisticasPaisFragment) fragmentActual;

        if (id == R.id.action_detalhes_pais) {
            estatisticasPaisFragment.AbreDetalhesPais();
            return true;
        }

        return false;
    }

    private boolean processaOpcoesMenuDetalhesEquipa(int id) {
        EstatisticasEquipaFragment estatisticasEquipaFragment = (EstatisticasEquipaFragment) fragmentActual;

        if (id == R.id.action_detalhes_equipa) {
            estatisticasEquipaFragment.AbreDetalhesEquipa();
            return true;
        }

        return false;
    }

    private boolean processaOpcoesMenuDetalhesAtleta(int id) {
        EstatisticasAtletaFragment estatisticasAtletaFragment = (EstatisticasAtletaFragment) fragmentActual;

        if (id == R.id.action_detalhes_atleta) {
            estatisticasAtletaFragment.AbreDetalhesAtleta();
            return true;
        }

        return false;
    }


}
