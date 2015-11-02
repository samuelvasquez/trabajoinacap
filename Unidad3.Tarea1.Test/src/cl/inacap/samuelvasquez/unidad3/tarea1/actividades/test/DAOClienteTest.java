package cl.inacap.samuelvasquez.unidad3.tarea1.actividades.test;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;
import android.test.AndroidTestCase;
import android.test.IsolatedContext;
import android.test.RenamingDelegatingContext;
import android.test.mock.MockContentResolver;
import android.test.mock.MockContext;
import cl.inacap.samuelvasquez.unidad3.tarea1.dataaccess.DAOCliente;
import cl.inacap.samuelvasquez.unidad3.tarea1.datamodel.Cliente;

public class DAOClienteTest extends AndroidTestCase {
	 private static final String TEST_FILE_PREFIX = "test_";
	    private DAOCliente daoCliente;
	
	protected void setUp() throws Exception {
		super.setUp();

		  MockContentResolver resolver = new MockContentResolver();
          RenamingDelegatingContext targetContextWrapper = new RenamingDelegatingContext(
                  new MockContext(), // The context that most methods are delegated to
                  getContext(), // The context that file methods are delegated to
                  TEST_FILE_PREFIX);
          Context context = new IsolatedContext(resolver, targetContextWrapper);
          setContext(context);

          daoCliente = new DAOCliente(context);
	}
	
	protected void tearDown() throws Exception {
		 super.tearDown();
	}

	public void testUpdateOK() {
		// 1. Actualizar datos del cliente y luego consultar por el para revisar los datos
		Cliente cliente1 = new Cliente();
		cliente1.id = 2;
		cliente1.nombre = "nuevo cliente 2";
		cliente1.direccion = "direccion actualizada cliente 2";
		cliente1.telefono = "121212";
		daoCliente.update(cliente1);
		
		Cliente cliente2 = daoCliente.getCliente(2);
		if(cliente2 == null)
			fail("No se encuentra informacion de cliente");
		
		assertEquals(cliente1.nombre, cliente2.nombre);
	}
	
	public void testUpdateNoExiste() {
		// 2. Actualizar cliente no existente
		Cliente cliente3 = new Cliente();
		cliente3.id = 100;
		cliente3.nombre = "cliente 100";
		cliente3.direccion = "actualizada cliente 100";
		cliente3.telefono = "121212";
		try 
		{
			daoCliente.update(cliente3);
			fail("Se esperaba excepcion NullPointerException");
		} 
		catch(NullPointerException e) {}
	}
	
	public void testUpdateNombreVacio() {
		// 3. Actualizar nombre cliente pasando en blanci
				Cliente cliente4 = new Cliente();
				cliente4.id = 2;
				cliente4.nombre = "";
				cliente4.direccion = "actualizada cliente 2";
				cliente4.telefono = "121212";
				try 
				{
					daoCliente.update(cliente4);
					fail("Se esperaba excepcion NullPointerException");
				} 
				catch(NullPointerException e) {}
	}

	public void testSaveOK() {
		// 1. Ingresa datos del cliente y luego consultar por el para revisar los datos
				Cliente cliente1 = new Cliente();
				cliente1.id_vendedor = 1;
				cliente1.nombre = "nuevo cliente vendedor 1";
				cliente1.direccion = "direccion nuevo cliente 1";
				cliente1.telefono = "121212";
				long id_new = daoCliente.save(cliente1);
				
				Cliente cliente2 = daoCliente.getCliente((int)id_new);
				if(cliente2 == null)
					fail("No se encuentra informacion de cliente");
				
				assertEquals(cliente1.nombre, cliente2.nombre);
	}
	
	public void testSaveVendedorNoExiste() {
				Cliente cliente3 = new Cliente();
				cliente3.id_vendedor = 0;
				cliente3.nombre = "cliente 100";
				cliente3.direccion = "actualizada cliente 100";
				cliente3.telefono = "121212";
				try 
				{
					daoCliente.save(cliente3);
					fail("Se esperaba excepcion NullPointerException");
				} 
				catch(NullPointerException e) {}
	}
	
	public void testSaveNombreVacio() {
		Cliente cliente4 = new Cliente();
		cliente4.id_vendedor = 1;
		cliente4.nombre = "";
		cliente4.direccion = "actualizada cliente 2";
		cliente4.telefono = "121212";
		try 
		{
			daoCliente.save(cliente4);
			fail("Se esperaba excepcion NullPointerException");
		} 
		catch(NullPointerException e) {}

	}
	
	public void testDeleteOK() {
		Cliente cliente1 = new Cliente();
		cliente1.id = 1;
		daoCliente.delete(cliente1);
		
		Cliente cliente2 = daoCliente.getCliente(1);
		assertNull("No se elimino el cliente", cliente2);
	}

	public void testDeleteNoExiste() {
		Cliente cliente3 = new Cliente();
		cliente3.id = 100;
		try 
		{
			daoCliente.delete(cliente3);
			fail("Se esperaba excepcion NullPointerException");
		} 
		catch(NullPointerException e) {}
	}

	
	public void testGetClientesByIdVendedorOK() {
		ArrayList<Cliente> lista = daoCliente.getClientesByIdVendedor(1);
		if(lista.size() == 0)
			fail("No se encontraron clientes de vendedor");
		
		Iterator<Cliente> iterator = lista.iterator();
		while (iterator.hasNext()) {
			Cliente cliente = iterator.next();
			assertEquals(1, cliente.id_vendedor);
		}
		
	}

	public void testGetClientesByIdVendedorNoExiste() {
		try 
		{
			daoCliente.getClientesByIdVendedor(0);
			fail("Se esperaba excepcion NullPointerException");
		} 
		catch(NullPointerException e) {}
	}

	
	public void testGetClienteOK() {
		Cliente cliente = daoCliente.getCliente(3);
		assertEquals(cliente.id, 3);
	}
	
	public void testGetClienteNoExiste() {
			Cliente cliente = daoCliente.getCliente(0);
			assertNull(cliente);
	}

}
