package com.example.OnliDealz.Black;

public class ProfitMargin {
    String timer = null;
    String summedOrder = null;
    String summedSupply = null;
    String profit = null;

    public ProfitMargin(String timer, String summedOrder, String summedSupply, String profit) {
        this.timer = timer;
        this.summedOrder = summedOrder;
        this.summedSupply = summedSupply;
        this.profit = profit;
    }

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public String getSummedOrder() {
        return summedOrder;
    }

    public void setSummedOrder(String summedOrder) {
        this.summedOrder = summedOrder;
    }

    public String getSummedSupply() {
        return summedSupply;
    }

    public void setSummedSupply(String summedSupply) {
        this.summedSupply = summedSupply;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }
}

