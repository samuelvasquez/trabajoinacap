package cl.inacap.samuelvasquez.unidad3.tarea1.dataaccess;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import cl.inacap.samuelvasquez.unidad3.tarea1.datamodel.Cliente;
import cl.inacap.samuelvasquez.unidad3.tarea1.datamodel.Usuario;

public class DAOCliente {
	protected SQLiteDatabase database;
    private DBHelper dbHelper;
    private Context mContext;
	    
    private String tabla = "CLIENTE";
	private String[] columnas = new String[]{"ID", "ID_VENDEDOR", "NOMBRE", "DIRECCION", "TELEFONO", "ES_ACTIVO"} ;
	
	private DAOUsuario daoUsuario;
	
	public DAOCliente(Context context)
	{
		this.mContext = context;
		daoUsuario = new DAOUsuario(context);
        dbHelper = DBHelper.getHelper(mContext);
        open();
	}
	
	public void open() throws SQLException {
        if(dbHelper == null)
            dbHelper = DBHelper.getHelper(mContext);
        database = dbHelper.getWritableDatabase();
    }
	
	public long update(Cliente cliente) {
		Log.d("DAOCliente", "update " 
				+ " id: " + String.valueOf(cliente.id)
				+ " nombre: " + cliente.nombre
				+ " direccion: " + cliente.direccion
				+ " telefono: " + cliente.telefono);

		// Valido que el cliente exista
		Cliente _cliente = getCliente(cliente.id);
		if(_cliente == null)
			throw new NullPointerException("No se encuentra informacion de cliente");
		
		// Validacion de campos obligatorios
		if(cliente.nombre.trim() == "")
		{
			throw new NullPointerException("Debe ingresar nombre");
		}
		if(cliente.direccion.trim() == "")
		{
			throw new NullPointerException("Debe ingresar direccion");
		}
		if(cliente.telefono.trim() == "")
		{
			throw new NullPointerException("Debe ingresar telefono");
		}
				
        ContentValues values = new ContentValues();
        values.put("NOMBRE", cliente.nombre);
        values.put("DIRECCION", cliente.direccion);
        values.put("TELEFONO",cliente.telefono);
     
        long result = database.update(tabla, values,
                "ID = ? ",
                new String[] { String.valueOf(cliente.id) });
        Log.d("Update Result:", "=" + result);
        return result;
    }
	
	public long save(Cliente cliente) {
		// Validacion existe vendedor
		Usuario _usuario =  daoUsuario.GetUsuario(cliente.id_vendedor);
				if(_usuario == null)
					throw new NullPointerException("No se encuentra informacion de vendedor");
				
		// Validacion de campos obligatorios
				if(cliente.nombre.trim() == "")
				{
					throw new NullPointerException("Debe ingresar nombre");
				}
				if(cliente.direccion.trim() == "")
				{
					throw new NullPointerException("Debe ingresar direccion");
				}
				if(cliente.telefono.trim() == "")
				{
					throw new NullPointerException("Debe ingresar telefono");
				}
				
        ContentValues values = new ContentValues();
        values.put("ID_VENDEDOR", cliente.id_vendedor);
        values.put("NOMBRE", cliente.nombre);
        values.put("DIRECCION", cliente.direccion);
        values.put("TELEFONO",cliente.telefono);
        return database.insert(tabla, null, values);
    }
	
	public long delete(Cliente cliente) {
		// Valido que el cliente exista
		Cliente _cliente = getCliente(cliente.id);
		if(_cliente == null)
			throw new NullPointerException("No se encuentra informacion de cliente");
			
		ContentValues values = new ContentValues();
        values.put("ES_ACTIVO", "0");
     
        long result = database.update(tabla, values,
        		"ID = ? ",
                new String[] { String.valueOf(cliente.id) });
        Log.d("Delete Result:", "=" + result);
        return result;
    }
	
	private Cliente cursorToCliente(Cursor cursor) 
	{
		Cliente cliente = new Cliente();
		cliente.id = cursor.getInt(0);
		cliente.id_vendedor = cursor.getInt(1);
		cliente.nombre = cursor.getString(2);
		cliente.direccion = cursor.getString(3);
		cliente.telefono = cursor.getString(4);
		cliente.es_activo = (cursor.getInt(5) == 1);
		return cliente;
	}
	
	public ArrayList<Cliente> getClientesByIdVendedor(int id_vendedor) 
	{
		Log.i("daocliente", "getClientesByIdVendedor " + String.valueOf(id_vendedor));

		// Validacion existe vendedor
		Usuario _usuario =  daoUsuario.GetUsuario(id_vendedor);
				if(_usuario == null)
					throw new NullPointerException("No se encuentra informacion de vendedor");
	
		  
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		String where = "ES_ACTIVO = 1 AND ID_VENDEDOR=?"; // the condition for the row(s) you want returned.
		String[] whereArgs = new String[] { // The value of the column specified above for the rows to be included in the response
				String.valueOf(id_vendedor)
		    };
		Cursor cursor = database.query(tabla, columnas, where, whereArgs, null, null, null);
		while (cursor.moveToNext()) {
			Cliente cliente = cursorToCliente(cursor);
			clientes.add(cliente);
		}
		// make sure to close the cursor
		cursor.close();
		
		Log.i("daocliente", "return " + String.valueOf(clientes.size()));
		
		return clientes;
	}
	
	public Cliente getCliente(int id) {
        Cliente cliente = null;
 
        String where = "ES_ACTIVO = 1 AND ID = ? "; // the condition for the row(s) you want returned.
		String[] whereArgs = new String[] { // The value of the column specified above for the rows to be included in the response
				String.valueOf(id)
		    };
		Cursor cursor = database.query(tabla, columnas, where, whereArgs, null, null, null);
		
        if (cursor.moveToNext()) {
        	cliente = cursorToCliente(cursor);
	    }
        
        return cliente;
    }
}
