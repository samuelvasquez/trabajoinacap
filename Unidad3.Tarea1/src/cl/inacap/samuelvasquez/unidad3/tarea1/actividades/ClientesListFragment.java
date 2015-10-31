package cl.inacap.samuelvasquez.unidad3.tarea1.actividades;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import cl.inacap.samuelvasquez.unidad3.tarea1.dataaccess.ClienteListAdapter;
import cl.inacap.samuelvasquez.unidad3.tarea1.dataaccess.DAOCliente;
import cl.inacap.samuelvasquez.unidad3.tarea1.datamodel.Cliente;

public class ClientesListFragment 
	extends Fragment {
	//implements OnItemClickListener, OnItemLongClickListener {

	public static final String ARG_ITEM_ID = "cliente_list";
	 
    Activity activity;
    ListView list_cliente;
    ArrayList<Cliente> clientes;
    Cliente cliente;
 
    ClienteListAdapter clienteListAdapter;
    DAOCliente clienteDAO;
 
    private GetClienteTask task;
    
    public int id_vendedor;
    private ActionMode mActionMode;
			
	public ClientesListFragment(){}
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        clienteDAO = new DAOCliente(activity);
        setHasOptionsMenu(true);
    }
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.fragment_list_cliente, container, false);
          
        Bundle bundle = this.getArguments();
        id_vendedor = bundle.getInt("id_vendedor", 0);
        
        findViewsById(rootView);
        
        task = new GetClienteTask(activity);
        task.execute((Void) null);
 
        //list_cliente.setOnItemClickListener(this);
        //list_cliente.setOnItemLongClickListener(this);
        
        return rootView;
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		
			switch (id) {
		        case R.id.action_add:
		        	((MainActivity)activity).goToAddCliente();
		        	return true;
			}
			
		return super.onOptionsItemSelected(item);
	}
	
    private void findViewsById(View view) {
    	list_cliente = (ListView) view.findViewById(R.id.list_cliente);
    }
 
    @Override
    public void onResume() {
        getActivity().setTitle(R.string.title_fragment_clientes);
        getActivity().getActionBar().setTitle(R.string.title_fragment_clientes);
        super.onResume();
    }
 
    /*
    @Override
    public void onItemClick(AdapterView<?> list, View arg1, int position,
            long arg3) {
        Cliente cliente = (Cliente)list.getItemAtPosition(position);
 
        if (cliente != null) {
            Bundle arguments = new Bundle();
            //arguments.putParcelable("selectedCliente", cliente);
            
            ClientesDialogFragment customEmpDialogFragment = new ClientesDialogFragment();
            customEmpDialogFragment.setArguments(arguments);
            customEmpDialogFragment.show(getFragmentManager(),
            		ClientesDialogFragment.ARG_ITEM_ID);
        }
    }
 
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view,
            int position, long arg3) {
        Cliente cliente = (Cliente) parent.getItemAtPosition(position);
 
        // Use AsyncTask to delete from database
        clienteDAO.delete(cliente);
        clienteListAdapter.remove(cliente);
        return true;
    }
 */
    public class GetClienteTask extends AsyncTask<Void, Void, ArrayList<Cliente>> {
 
        private final WeakReference<Activity> activityWeakRef;
 
        public GetClienteTask(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
        }
 
        @Override
        protected ArrayList<Cliente> doInBackground(Void... arg0) {
            ArrayList<Cliente> clienteList = clienteDAO.getClientesByIdVendedor(id_vendedor);
            return clienteList;
        }
 
        @Override
        protected void onPostExecute(ArrayList<Cliente> clienteList) {
            if (activityWeakRef.get() != null
                    && !activityWeakRef.get().isFinishing()) {
                Log.d("cliente", clienteList.toString());
                clientes = clienteList;
                if (clienteList != null) {
                    if (clienteList.size() != 0) {
                        clienteListAdapter = new ClienteListAdapter(activity,
                                clienteList);
                        list_cliente.setAdapter(clienteListAdapter);
                        clienteListAdapter.notifyDataSetChanged();
                  	  
                	    // ListView Item Click Listener
                        list_cliente.setOnItemClickListener(new OnItemClickListener() {

                              @Override
                              public void onItemClick(AdapterView<?> parent, View view,
                                 int position, long id) {
                                  cliente = (Cliente)list_cliente.getItemAtPosition(position);
                            	  if(mActionMode == null)
                            	  {
	                            	  mActionMode = activity.startActionMode(mActionModeCallback);
	                                  view.setSelected(true);
                            	  }
                              }
                         }); 
                    } else {
                        Toast.makeText(activity, "Sin clientes",
                                Toast.LENGTH_LONG).show();
                    }
                }
 
            }
        }
    }
 
    /*
     * This method is invoked from MainActivity onFinishDialog() method. It is
     * called from CustomEmpDialogFragment when an employee record is updated.
     * This is used for communicating between fragments.
     */
    public void updateView() {
        task = new GetClienteTask(activity);
        task.execute((Void) null);
    }
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       inflater.inflate(R.menu.main_cliente, menu);
    }
    
    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

	    // Called when the action mode is created; startActionMode() was called
	    @Override
	    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
	        // Inflate a menu resource providing context menu items
	        MenuInflater inflater = mode.getMenuInflater();
	        inflater.inflate(R.menu.clientes, menu);
	        return true;
	    }

	    // Called each time the action mode is shown. Always called after onCreateActionMode, but
	    // may be called multiple times if the mode is invalidated.
	    @Override
	    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
	        return false; // Return false if nothing is done
	    }

	    // Called when the user selects a contextual menu item
	    @Override
	    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
	        switch (item.getItemId()) {
	          case R.id.action_edit:
	        	editarCliente();
	            mode.finish(); // Action picked, so close the CAB
	            return true;
	        case R.id.action_delete:
	        	confirmarBorrado();
	        	mode.finish(); // Action picked, so close the CAB
	            return true;
	        default:
	                return false;
	        }
	    }

	    // Called when the user exits the action mode
	    @Override
	    public void onDestroyActionMode(ActionMode mode) {
	    	mActionMode  = null;
	    }
	};

	private void editarCliente()
	{
		if(cliente != null)
		{
			((MainActivity)activity).goToEditCliente(cliente.id);
		}
	}
	
	/**
	 * Se pregunta al usuario si realmente desea eliminar usuario
	 * */
	private void confirmarBorrado()
	{
		AlertDialog.Builder dialogo1 = new AlertDialog.Builder(activity);  
        dialogo1.setTitle("Confirmacion");  
        dialogo1.setMessage("Esta seguro de eliminar este cliente?");
        dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {  
            public void onClick(DialogInterface dialogo1, int id) {  
            	borrarCliente();
            }  
        });  
        dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {  
            public void onClick(DialogInterface dialogo1, int id) {  
                return;
            }  
        });            
        dialogo1.show();  
	}
	
	/**
	 * Se realiza la eliminacion de un cliente
	 * */
	private void borrarCliente()
	{
		if(cliente != null)
		{
			if(cliente.id == id_vendedor)
			{
				Toast.makeText(activity, "No se puede eliminar el usuario Logeado", Toast.LENGTH_SHORT).show();
				return;
			}
			clienteDAO.delete(cliente);
	        clienteListAdapter.remove(cliente);
		}
	}
}
