package org.example.DAO;

import org.example.model.Books;

import java.util.List;

public class BookDAO implements IBookIDAO{
    @Override
    public Books get(Long book){
        return null;
    }
    @Override
    public void save(Books books){

    }

    @Override
    public void update(Books props){

    }

    @Override
    public void delete(Long book){
        return;
    }

    @Override
    public List<Books> getAll(){
        return null;
    }
}
