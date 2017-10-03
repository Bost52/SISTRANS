package vos;

public class Pago {

	private double montoTotal;
	
	private Long cuentaPSE; 
	
	public Pago(double montoTotal, Long cuenta) {
		super();
		this.montoTotal = montoTotal;
		this.cuentaPSE = cuenta;
	}

	public double getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(double montoTotal) {
		this.montoTotal = montoTotal;
	}

	public Long getCuenta() {
		return cuentaPSE;
	}

	public void setCuenta(Long cuenta) {
		this.cuentaPSE = cuenta;
	}
	
	
}
