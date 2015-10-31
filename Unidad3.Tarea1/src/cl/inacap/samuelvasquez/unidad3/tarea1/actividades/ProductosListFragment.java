package cl.inacap.samuelvasquez.unidad3.tarea1.actividades;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import cl.inacap.samuelvasquez.unidad3.tarea1.dataaccess.DAOProducto;
import cl.inacap.samuelvasquez.unidad3.tarea1.dataaccess.ProductoListAdapter;
import cl.inacap.samuelvasquez.unidad3.tarea1.datamodel.Producto;

public class ProductosListFragment extends  Fragment {
	public static final String ARG_ITEM_ID = "productos_list";

	Activity activity;
    ListView list_producto;
    ArrayList<Producto> productos;
 
    ProductoListAdapter productoListAdapter;
    DAOProducto productoDAO;
 	 
    private GetProductosTask task;
    
	public ProductosListFragment(){}
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        productoDAO = new DAOProducto(activity);
    }
	
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	  
	        View rootView = inflater.inflate(R.layout.fragment_list_producto, container, false);
	          
	        findViewsById(rootView);
	        
	        task = new GetProductosTask(activity);
	        task.execute((Void) null);
	        
	        return rootView;
	    }
		
	    private void findViewsById(View view) {
	    	list_producto = (ListView) view.findViewById(R.id.list_producto);
	    }
	 
	    @Override
	    public void onResume() {
	        getActivity().setTitle(R.string.title_fragment_productos);
	        getActivity().getActionBar().setTitle(R.string.title_fragment_productos);
	        super.onResume();
	    }
	
	    public class GetProductosTask extends AsyncTask<Void, Void, ArrayList<Producto>> {
	    	 
	        private final WeakReference<Activity> activityWeakRef;
	 
	        public GetProductosTask(Activity context) {
	            this.activityWeakRef = new WeakReference<Activity>(context);
	        }
	 
	        @Override
	        protected ArrayList<Producto> doInBackground(Void... arg0) {
	            ArrayList<Producto> productoList = productoDAO.getAllProductos();
	            return productoList;
	        }
	 
	        @Override
	        protected void onPostExecute(ArrayList<Producto> productoList) {
	            if (activityWeakRef.get() != null
	                    && !activityWeakRef.get().isFinishing()) {
	                Log.d("cliente", productoList.toString());
	                productos = productoList;
	                if (productoList != null) {
	                    if (productoList.size() != 0) {
	                        productoListAdapter = new ProductoListAdapter(activity,
	                        		productoList);
	                        list_producto.setAdapter(productoListAdapter);
	                    } else {
	                        Toast.makeText(activity, "Sin productos",
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
	        task = new GetProductosTask(activity);
	        task.execute((Void) null);
	    }

}
