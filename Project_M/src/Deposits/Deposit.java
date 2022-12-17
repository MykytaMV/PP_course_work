package Deposits;

public abstract class Deposit {
	protected String bank;
	protected float interest_rate;
	protected float money;
	protected int noipify;
	protected boolean with_capitalization;
	
	public  abstract float toWithdraw();
	
	public  abstract void toDeposite(int money);
	
	public  abstract void toWait(int time);
	
	public  abstract boolean Deposited();
	
	public double viewDepositAccount() {
		return money;
	}
	
	public String getBank() {
		return bank;
	}
	
	public int numberOfInterestPeriodsInFullYear() {
		return noipify;
	}
	
	public float getInterestRate() {
		return interest_rate;
	}
	
	public boolean withCapitalization() {
		return with_capitalization;
	}
	

}
