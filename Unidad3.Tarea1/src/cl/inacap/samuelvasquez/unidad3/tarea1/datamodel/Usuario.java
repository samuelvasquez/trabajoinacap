package cl.inacap.samuelvasquez.unidad3.tarea1.datamodel;

public class Usuario 
{
	// Propiedades
	public int id;
	public String nombre;
	public String login;
	public String contrasena;
	public Boolean es_activo;
	
	// Constructor de las instancias de Usuario
	public Usuario()
	{
	}
	
	// Sobreescribe metodo toString() de la clase Usuario
	public String toString()
	{
		return String.valueOf(this.id) 
				+ ": " 
				+ this.nombre 
				+ " (" 
				+ this.login 
				+ ")";
	}
}
