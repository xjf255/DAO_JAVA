package org.example.model;

import java.sql.Timestamp;

public class Lend {
    private Long cod = null;
    private int cod_book;
    private int cod_user;
    private Timestamp lend_date;
    private Timestamp return_date;

    public Long getCod() {
        return cod;
    }

    public void setCod(Long cod) {
        this.cod = cod;
    }

    public int getCod_book() {
        return cod_book;
    }

    public void setCod_book(int cod_book) {
        this.cod_book = cod_book;
    }

    public int getCod_user() {
        return cod_user;
    }

    public void setCod_user(int cod_user) {
        this.cod_user = cod_user;
    }

    public Timestamp getLend_date() {
        return lend_date;
    }

    public void setLend_date(Timestamp lend_date) {
        this.lend_date = lend_date;
    }

    public Timestamp getReturn_date() {
        return return_date;
    }

    public void setReturn_date(Timestamp return_date) {
        this.return_date = return_date;
    }

    @Override
    public String toString() {
        return "Lend{" +
                "cod=" + cod +
                ", cod_book=" + cod_book +
                ", cod_user=" + cod_user +
                ", lend_date=" + lend_date +
                ", return_date=" + return_date +
                '}';
    }
}

