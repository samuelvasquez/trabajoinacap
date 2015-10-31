package cl.inacap.samuelvasquez.unidad3.tarea1.datamodel;

public class Producto {
	// Propiedades
	public int id;
	public String nombre;
	public Boolean es_activo;
	
	// Constructor de las instancias de Producto
	public Producto(){	}
	
	// Sobreescribe metodo toString() de la clase Producto
	public String toString()
	{
		return String.valueOf(this.id) 
				+ ". " 
				+ this.nombre;
	}
}
