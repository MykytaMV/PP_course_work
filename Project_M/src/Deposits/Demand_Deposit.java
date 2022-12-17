package Deposits;

public class Demand_Deposit extends Deposit {
	
	public Demand_Deposit(String bank, float interest_rate, boolean with_capitalization, int noipify){
		this.bank = bank;
		this.interest_rate = interest_rate;
		this.with_capitalization = with_capitalization;
		this.noipify = noipify;
		this.money = 0; 
	}
	

	@Override
	public float toWithdraw() {
		float return_money = money;
		money = 0;
		return return_money;
	}

	@Override
	public void toDeposite(int money) {
		this.money += money; 
		
	}

	@Override
	public void toWait(int time) {
		if(with_capitalization) {
			money = (float) (money * Math.pow((1 + interest_rate / noipify), noipify * time));
		}else {
			money = money + money * interest_rate * time / 12;
		}
		
	}


	@Override
	public boolean Deposited() {
		return false;
	}

}
