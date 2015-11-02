package cl.inacap.samuelvasquez.unidad3.tarea1.dataaccess;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import cl.inacap.samuelvasquez.unidad3.tarea1.datamodel.Usuario;

public class DAOUsuario {
	protected SQLiteDatabase database;
    private DBHelper dbHelper;
    private Context mContext;
	private String[] columnas = new String[]{"ID", "NOMBRE", "LOGIN", "CONTRASENA", "ES_ACTIVO"} ;
	    
	public DAOUsuario(Context context)
	{
		this.mContext = context;
        dbHelper = DBHelper.getHelper(mContext);
        open();
	}
	
	public void open() throws SQLException {
        if(dbHelper == null)
            dbHelper = DBHelper.getHelper(mContext);
        database = dbHelper.getWritableDatabase();
    }
	
	private Usuario cursorToUsuario(Cursor cursor) 
	{
		Usuario usuario = new Usuario();
		usuario.id = cursor.getInt(0);
		usuario.nombre = cursor.getString(1);
		usuario.login = cursor.getString(2);
		usuario.contrasena = cursor.getString(3);
		usuario.es_activo = (cursor.getInt(4) == 1);
		return usuario;
	}
	
	public List<Usuario> getAllUsuarios() 
	{
		Log.i("daousuario", "getAllUsuarios");
		
		List<Usuario> usuarios = new ArrayList<Usuario>();
		String where = "ES_ACTIVO = 1 "; // the condition for the row(s) you want returned.
		Cursor cursor = database.query("USUARIO", columnas, where, null, null, null, null);
		while (cursor.moveToNext()) {
			Usuario usuario = cursorToUsuario(cursor);
			usuarios.add(usuario);
		}
		// make sure to close the cursor
		cursor.close();
		Log.i("daousuario", "return " + String.valueOf(usuarios.size()));
		
		return usuarios;
	}
	
	// Realiza validacion de login y contrasena del usuario
	public boolean ValidarUsuario(String login, String contrasena)
	{
		// Se obtiene lista de usuarios
		List<Usuario> lista = getAllUsuarios();
		int totalUsuarios = lista.size();
		// Se recorre lista de usuarios buscando alguna en que coincida el login y contraseña
		for(int posicion = 0; posicion < totalUsuarios; posicion++)
		{
			// si se encuentra usuario se retorna un TRUE
			if(lista.get(posicion).login.equals(login)
					&& lista.get(posicion).contrasena.equals(contrasena))
			{
				return true;
			}
		}
		// si al recorer toda la lista, no se ha encontrado un usuario valido,
		// se retorna FALSE
		return false;
	}
	
	public Usuario GetUsuario(String login) {
		List<Usuario>  listaUsuarios = getAllUsuarios();
		 
		for (Usuario _usuario : listaUsuarios) {
           if(_usuario.login.equals(login)){
               return _usuario;
           }
		}
		return null;
	}
	
	public Usuario GetUsuario(int id) {
        Usuario usuario = null;
 
        String where = "ES_ACTIVO = 1 AND ID = ? "; // the condition for the row(s) you want returned.
		String[] whereArgs = new String[] { // The value of the column specified above for the rows to be included in the response
				String.valueOf(id)
		    };
		Cursor cursor = database.query("USUARIO", columnas, where, whereArgs, null, null, null);
		
        if (cursor.moveToNext()) {
        	usuario = cursorToUsuario(cursor);
	    }
        
        return usuario;
    }
}
