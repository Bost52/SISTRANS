package vos;

public class Pago {

	private double montoTotal;
	
	private PSE cuenta;
	
	public Pago(double montoTotal, PSE cuenta) {
		super();
		this.montoTotal = montoTotal;
		this.cuenta = cuenta;
	}

	public double getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(double montoTotal) {
		this.montoTotal = montoTotal;
	}

	public PSE getCuenta() {
		return cuenta;
	}

	public void setCuenta(PSE cuenta) {
		this.cuenta = cuenta;
	}
	
	
}
