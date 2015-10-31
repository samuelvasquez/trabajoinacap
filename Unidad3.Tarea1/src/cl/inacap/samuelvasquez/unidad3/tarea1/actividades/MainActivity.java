package cl.inacap.samuelvasquez.unidad3.tarea1.actividades;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class MainActivity  
	extends FragmentActivity  {
	
	private Fragment contentFragment;
    //private ClientesListFragment clientesListFragment;
    //private ClientesAgregarFragment clienteAddFragment;
    //private ProductosListFragment productosListFragment;
        
	private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
  
    private String[] navMenuTitles;
    
    private int id_vendedor;
     
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Intent intent = getIntent();
	      Bundle extra = intent.getExtras();
	      if (extra != null)
	      {
	      if (extra.containsKey("id_vendedor"))
	      {
	    	  id_vendedor = extra.getInt("id_vendedor");
	      }
	      }
	      
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
	    mDrawerList = (ListView) findViewById(R.id.left_drawer);
	    
	    navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
	    
	    //mTitle = mDrawerTitle = getTitle();
	    
	    mDrawerList.setAdapter(new ArrayAdapter<String>(this,
        		android.R.layout.simple_list_item_activated_1, navMenuTitles));
	    
	    mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
	    
	    mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
   
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("content")) {
                String content = savedInstanceState.getString("content");
                
                if (content.equals(ClientesListFragment.ARG_ITEM_ID)) {
                 	if (fragmentManager.findFragmentByTag(ClientesListFragment.ARG_ITEM_ID) != null) {
                        setTitle(R.string.title_fragment_clientes);
                        contentFragment = fragmentManager.findFragmentByTag(ClientesListFragment.ARG_ITEM_ID);
                    }
                }
                if (content.equals(ProductosListFragment.ARG_ITEM_ID)) {
                 	if (fragmentManager.findFragmentByTag(ProductosListFragment.ARG_ITEM_ID) != null) {
                        setTitle(R.string.title_fragment_productos);
                        contentFragment = fragmentManager.findFragmentByTag(ProductosListFragment.ARG_ITEM_ID);
                    }
                }
                if (content.equals(ResumenFragment.ARG_ITEM_ID)) {
                 	if (fragmentManager.findFragmentByTag(ResumenFragment.ARG_ITEM_ID) != null) {
                        setTitle(R.string.title_fragment_resumen);
                        contentFragment = fragmentManager.findFragmentByTag(ResumenFragment.ARG_ITEM_ID);
                    }
                }
                if (content.equals(PedidosListFragment.ARG_ITEM_ID)) {
                 	if (fragmentManager.findFragmentByTag(PedidosListFragment.ARG_ITEM_ID) != null) {
                        setTitle(R.string.title_fragment_pedidos);
                        contentFragment = fragmentManager.findFragmentByTag(PedidosListFragment.ARG_ITEM_ID);
                    }
                }
                if (content.equals(ClientesAgregarFragment.ARG_ITEM_ID)) {
                 	if (fragmentManager.findFragmentByTag(ClientesAgregarFragment.ARG_ITEM_ID) != null) {
                        setTitle(R.string.add_cliente);
                        contentFragment = fragmentManager.findFragmentByTag(ClientesAgregarFragment.ARG_ITEM_ID);
                    }
                }
              
            }
            /*else
            {
            	  	if (fragmentManager.findFragmentByTag(ClientesListFragment.ARG_ITEM_ID) != null) {
                        setTitle(R.string.title_fragment_clientes);
                        contentFragment = fragmentManager.findFragmentByTag(ClientesListFragment.ARG_ITEM_ID);
            	  	}
            }
        } else {
        	ClientesListFragment clienteListFragment = new ClientesListFragment();
            setTitle(R.string.title_fragment_clientes);
            switchContent(clienteListFragment, ClientesListFragment.ARG_ITEM_ID);*/
        }
   }    
    
	@Override
    protected void onSaveInstanceState(Bundle outState) {
	 	if (contentFragment instanceof ClientesListFragment) {
            outState.putString("content", ClientesListFragment.ARG_ITEM_ID);
        }
        if (contentFragment instanceof ProductosListFragment) {
   	        outState.putString("content", ProductosListFragment.ARG_ITEM_ID);
        }
        if (contentFragment instanceof ResumenFragment) {
   	        outState.putString("content", ResumenFragment.ARG_ITEM_ID);
        }
        if (contentFragment instanceof PedidosListFragment) {
   	        outState.putString("content", PedidosListFragment.ARG_ITEM_ID);
        }
        if (contentFragment instanceof ClientesAgregarFragment) {
   	        outState.putString("content", ClientesAgregarFragment.ARG_ITEM_ID);
        }
        super.onSaveInstanceState(outState);
    }
	 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		/*
		if (contentFragment instanceof ClientesListFragment)
		{
			getMenuInflater().inflate(R.menu.main_cliente, menu);
		}
		*/
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
          return true;
        }
    	return super.onOptionsItemSelected(item);
	}
	
	/*
     * We consider EmpListFragment as the home fragment and it is not added to
     * the back stack.
     */
    public void switchContent(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        while (fragmentManager.popBackStackImmediate())
            ;
 
        if (fragment != null) {
            FragmentTransaction transaction = fragmentManager
                    .beginTransaction();
            transaction.replace(R.id.content_frame, fragment, tag);

            transaction.commit();
            contentFragment = fragment;
        }
    }
 /*
    protected void setFragmentTitle(int resourseId) {
        setTitle(resourseId);
        getActionBar().setTitle(resourseId);
 
    }
    */
    /*
	@Override
	public void setTitle(CharSequence title) {
	    getActionBar().setTitle(title);
	    super.setTitle(title);
	}
	*/
	/* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        //boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
    
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
	
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
	    @Override
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	        selectItem(position);
	    }
	}
	
	public void goToClientes()
	{
		ClientesListFragment fragment1 = new ClientesListFragment();
    	Bundle bundle1 = new Bundle();
    	bundle1.putInt("id_vendedor", id_vendedor);
    	fragment1.setArguments(bundle1);
    	switchContent(fragment1, ClientesListFragment.ARG_ITEM_ID);
    	invalidateOptionsMenu();
	}
	
	public void goToAddCliente()
	{
		ClientesAgregarFragment fragment = new ClientesAgregarFragment();
    	Bundle bundle1 = new Bundle();
    	bundle1.putInt("id_vendedor", id_vendedor);
    	fragment.setArguments(bundle1);
    	switchContent(fragment, ClientesAgregarFragment.ARG_ITEM_ID);
    	invalidateOptionsMenu();
    }

	public void goToEditCliente(int id_cliente)
	{
		ClientesAgregarFragment fragment = new ClientesAgregarFragment();
    	Bundle bundle1 = new Bundle();
    	bundle1.putBoolean("editar", true);
    	bundle1.putInt("id_vendedor", id_vendedor);
    	bundle1.putInt("id_cliente", id_cliente);
    	fragment.setArguments(bundle1);
    	switchContent(fragment, ClientesAgregarFragment.ARG_ITEM_ID);
    	invalidateOptionsMenu();
    }
	
	public void goToPedidos()
	{
		PedidosListFragment fragment1 = new PedidosListFragment();
    	Bundle bundle1 = new Bundle();
    	bundle1.putInt("id_vendedor", id_vendedor);
    	fragment1.setArguments(bundle1);
    	switchContent(fragment1, PedidosListFragment.ARG_ITEM_ID);
    	invalidateOptionsMenu();
	}
	
	public void goToAddPedido()
	{
		PedidosAgregarFragment fragment = new PedidosAgregarFragment();
    	Bundle bundle1 = new Bundle();
    	bundle1.putInt("id_vendedor", id_vendedor);
    	fragment.setArguments(bundle1);
    	switchContent(fragment, PedidosAgregarFragment.ARG_ITEM_ID);
    	invalidateOptionsMenu();
    }
	
	public void goToEditPedido(int id_pedido)
	{
		PedidosAgregarFragment fragment = new PedidosAgregarFragment();
    	Bundle bundle1 = new Bundle();
    	bundle1.putBoolean("editar", true);
    	bundle1.putInt("id_vendedor", id_vendedor);
    	bundle1.putInt("id_pedido", id_pedido);
    	fragment.setArguments(bundle1);
    	switchContent(fragment, PedidosAgregarFragment.ARG_ITEM_ID);
    	invalidateOptionsMenu();
    }
	
	private void selectItem(int position) 
	{
		switch (position) {
            case 0:
            	goToClientes();
                break;
            case 1:
            	goToPedidos();
                break;
            case 2:
                switchContent(new ProductosListFragment(), ProductosListFragment.ARG_ITEM_ID);
                invalidateOptionsMenu();
                break;
            case 3:
            	ResumenFragment fragment3 = new ResumenFragment();
            	Bundle bundle3 = new Bundle();
            	bundle3.putInt("id_vendedor", id_vendedor);
            	fragment3.setArguments(bundle3);
            	switchContent(fragment3, ResumenFragment.ARG_ITEM_ID);
            	invalidateOptionsMenu();
                break;
        }
 
		// Highlight the selected item, update the title, and close the drawer
		mDrawerList.setItemChecked(position, true);
		//setTitle(navMenuTitles[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
    public void onBackPressed() {
            onShowQuitDialog();
	}
    
    public void onShowQuitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
 
        builder.setMessage("Quiere cerrar la sesion?");
        builder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        builder.setNegativeButton(android.R.string.no,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }

}
