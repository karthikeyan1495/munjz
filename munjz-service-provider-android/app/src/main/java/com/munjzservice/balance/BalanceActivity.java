package com.munjzservice.balance;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.munjzservice.R;
import com.munjzservice.balance.model.Balance;
import com.munjzservice.balance.model.BalanceResponse;
import com.munjzservice.balance.viewmodel.BalanceVM;
import com.munjzservice.databinding.ActivityBalanceBinding;
import com.munjzservice.login.LoginActivity;
import com.munjzservice.servicerequest.ViewServiceRequestActivity;
import com.munjzservice.sharedpreferences.AppSession;
import com.munjzservice.tab.active.viewmodel.ActiveListVM;

import java.util.Observable;
import java.util.Observer;

public class BalanceActivity extends AppCompatActivity implements Observer {

    ActivityBalanceBinding binding;
    BalanceVM balanceVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLoginStatus();
    }

    private void checkLoginStatus(){
        if (AppSession.getInstance(this).isLogin()) {
            bindView();
            setUpObserver(balanceVM);
        }else{
            startActivity(new Intent(BalanceActivity.this, LoginActivity.class));
            finish();
        }
    }

    public void setUpObserver(Observable observable) {
        observable.addObserver(this);
    }
    private void bindView(){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_balance);
        balanceVM=new BalanceVM(this);
        binding.setBalanceVM(balanceVM);
        binding.setBalance(new Balance());
    }
    @Override
    public void update(Observable observable, Object object) {
        if(observable instanceof BalanceVM) {
            BalanceResponse balanceResponse=(BalanceResponse)object;
            binding.setBalance(balanceResponse.getBalance_details());
        }
    }
}
