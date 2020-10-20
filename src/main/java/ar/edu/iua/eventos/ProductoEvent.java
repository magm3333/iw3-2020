package ar.edu.iua.eventos;

import org.springframework.context.ApplicationEvent;

public class ProductoEvent extends ApplicationEvent {

	private static final long serialVersionUID = 7482819204842463L;

	public enum Tipo {
		SUBE_PRECIO,
		VENCIMIENTO_PROXIMO
	}
	
	private Tipo tipo;
	
	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public ProductoEvent(Object source, Tipo tipo) {
		super(source);
		this.tipo=tipo;
	}

}
