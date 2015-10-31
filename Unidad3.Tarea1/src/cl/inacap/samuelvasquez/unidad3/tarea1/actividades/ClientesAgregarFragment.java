package cl.inacap.samuelvasquez.unidad3.tarea1.actividades;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import cl.inacap.samuelvasquez.unidad3.tarea1.dataaccess.DAOCliente;
import cl.inacap.samuelvasquez.unidad3.tarea1.datamodel.Cliente;

public class ClientesAgregarFragment 
	extends Fragment 
	//implements OnClickListener 
	{
		Activity activity;
    
		// UI references
		private EditText etxt_nombre;
		private EditText etxt_direccion;
		private EditText etxt_telefono;

		private DAOCliente dao;
		
		public int id_cliente;
		public int id_vendedor;
		private boolean editar = false;
		
		public static final String ARG_ITEM_ID = "cliente_add";

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			activity = getActivity();
	        dao = new DAOCliente(getActivity());
		    setHasOptionsMenu(true);
		}
		
		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			inflater.inflate(R.menu.form_cliente, menu);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_add_cliente, container,
					false);
			Bundle bundle = this.getArguments();
			this.editar = bundle.getBoolean("editar", false);
			this.id_cliente = bundle.getInt("id_cliente", 0);
			this.id_vendedor = bundle.getInt("id_vendedor", 0);
	        
			findViewsById(rootView);

			//setListeners();
			if(editar)
	        {
	        	Cliente cliente = dao.getCliente(id_cliente);
	       	    if(cliente != null)
	       	    {
	       	    	etxt_nombre.setText(cliente.nombre);
	    			etxt_direccion.setText(cliente.direccion);
	    			etxt_telefono.setText(cliente.telefono);
	       	    }
	        }
			
			etxt_nombre.addTextChangedListener(new TextWatcher() {
	            public void afterTextChanged(Editable s) {
	                Validation.hasText(etxt_nombre);
	            }
	            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	            public void onTextChanged(CharSequence s, int start, int before, int count){}
	        });
	        
			etxt_direccion.addTextChangedListener(new TextWatcher() {
	            public void afterTextChanged(Editable s) {
	                Validation.hasText(etxt_direccion);
	            }
	            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	            public void onTextChanged(CharSequence s, int start, int before, int count){}
	        });
	        
			etxt_telefono.addTextChangedListener(new TextWatcher() {
	            public void afterTextChanged(Editable s) {
	                Validation.hasText(etxt_telefono);
	            }
	            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	            public void onTextChanged(CharSequence s, int start, int before, int count){}
	        });

			return rootView;
		}
/*
		private void setListeners() {
			button_add.setOnClickListener(this);
			button_reset.setOnClickListener(this);
		}
*/
		protected void resetAllFields() {
			etxt_nombre.setText("");
			etxt_direccion.setText("");
			etxt_telefono.setText("");
		}

		private Cliente setCliente() {
			Cliente cliente = new Cliente();
			cliente.id = id_cliente;
			cliente.id_vendedor = id_vendedor;
			cliente.nombre = etxt_nombre.getText().toString();
			cliente.direccion = etxt_direccion.getText().toString();
			cliente.telefono = etxt_telefono.getText().toString();
			return cliente;
		}

		@Override
		public void onResume() {
			if(editar)
	        {
				getActivity().setTitle(R.string.update_cliente);
				getActivity().getActionBar().setTitle(R.string.update_cliente);
		    }
			else
			{
				getActivity().setTitle(R.string.add_cliente);
				getActivity().getActionBar().setTitle(R.string.add_cliente);
			}
			super.onResume();
		}

		@Override
		public void onSaveInstanceState(Bundle outState) {

	}
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			int id = item.getItemId();
			
			switch (id) {
		        case R.id.action_save:
		        	if ( checkValidation () )
				    	 confirmarRegistro();
		             else
		                 Toast.makeText(activity, "Debe corregir los errores para continuar", Toast.LENGTH_LONG).show();
				     return true;
		        case R.id.action_revert:
		        	((MainActivity)activity).goToClientes();
		        	return true;
			}
				
			return super.onOptionsItemSelected(item);
		}

		private void findViewsById(View rootView) {
			etxt_nombre = (EditText) rootView.findViewById(R.id.etxt_nombre);
			etxt_direccion = (EditText) rootView.findViewById(R.id.etxt_direccion);
			etxt_telefono = (EditText) rootView.findViewById(R.id.etxt_telefono);
		}
		
		/**
		 * Se pregunta al usuario si realmente desea salir de la aplicacion
		 * */
		private void confirmarRegistro()
		{
			AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this.activity);  
	        dialogo1.setTitle("Confirmacion");  
	        if(!editar){
	        	dialogo1.setMessage("Esta seguro de agregar este cliente?");
	        }
	        else
	        {
	        	dialogo1.setMessage("Esta seguro de modificar este cliente?");
	        }
	        dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {  
	            public void onClick(DialogInterface dialogo1, int id) {  
	            	validarCliente();
	            }  
	        });  
	        dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {  
	            public void onClick(DialogInterface dialogo1, int id) {  
	                return;
	            }  
	        });            
	        dialogo1.show();  
		}
		
		private boolean checkValidation() {
	        boolean ret = true;
	 
	        if (!Validation.hasText(etxt_nombre)) ret = false;
	        if (!Validation.hasText(etxt_direccion)) ret = false;
	        if (!Validation.hasText(etxt_telefono)) ret = false;
	 
	        return ret;
	    }
		
		/**
		 *Se realiza la validacion de los datos ingresados y se registra el nuevo usuario
		 **/
		private void validarCliente()
		{
			if(etxt_nombre.getText().toString().trim().equals(""))
			{
				Toast.makeText(activity, "Debe ingresar un nombre", Toast.LENGTH_LONG).show();
				etxt_nombre.requestFocus();
				return;
			}
			
			if(etxt_direccion.getText().toString().trim().equals(""))
			{
				Toast.makeText(activity, "Debe ingresar direccion", Toast.LENGTH_LONG).show();
				etxt_direccion.requestFocus();
				return;
			}
			
			if(etxt_telefono.getText().toString().trim().equals(""))
			{
				Toast.makeText(activity, "Debe ingresar telefono", Toast.LENGTH_LONG).show();
				etxt_telefono.requestFocus();
				return;
			}
			
			Cliente cliente = setCliente();
			if(!editar)
			{
				dao.save(cliente);
				Toast.makeText(activity, "Cliente agregado correctamente", Toast.LENGTH_LONG).show();
			}
			else
			{
				dao.update(cliente);
				Toast.makeText(activity, "Cliente modificado correctamente", Toast.LENGTH_LONG).show();
			}
			
			((MainActivity)activity).goToClientes();
		}
}
