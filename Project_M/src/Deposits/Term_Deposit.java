package Deposits;

public class Term_Deposit extends Deposit {
	private int term;
	private float initial_amount;

	public Term_Deposit(String bank, float interest_rate, int term, boolean with_capitalization, int noipify){
		this.bank = bank;
		this.interest_rate = interest_rate;
		this.term = term;
		this.with_capitalization = with_capitalization;
		this.noipify = noipify;
		this.initial_amount = 0;
		this.money = 0; 
	}
	

	@Override
	public float toWithdraw() {
		if(term == 0) {
			float rtrn_money = money;
			money = 0;
			return rtrn_money;
		}else {
			money = 0;
			float rtrn_initial_amount = initial_amount;
			initial_amount = 0;
			return rtrn_initial_amount;
		}
	}

	@Override
	public void toDeposite(int money) {
		if(this.initial_amount == 0) {
			this.initial_amount = money;
			this.money = money;
		}
	}

	@Override
	public void toWait(int time) {
		if(term < time)
			time = term;
		
		if(with_capitalization) {
			money = (float) (money * Math.pow((1 + interest_rate / noipify), noipify * time));
		}else {
			money = money + money * interest_rate * time / 12;
		}
		this.term -= time;
	}


	@Override
	public boolean Deposited() {
		if(initial_amount != 0)
			return true;
		return false;
	}

}
