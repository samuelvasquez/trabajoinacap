package cl.inacap.samuelvasquez.unidad3.tarea1.datamodel;


public class Cliente {
	// Propiedades
	public int id;
	public int id_vendedor;
	public String nombre;
	public String direccion;
	public String telefono;
	public Boolean es_activo;
	
	// Constructor de las instancias de Cliente
	public Cliente(){}
		
	// Sobreescribe metodo toString() de la clase Cliente
	public String toString()
	{
		return String.valueOf(this.id) 
				+ ". " 
				+ this.nombre;
	}
}
