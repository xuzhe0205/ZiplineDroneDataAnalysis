package com.bgi.demo.service;

import com.bgi.demo.model.Transaction;
import com.bgi.demo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction saveTransaction(final Transaction transaction){
        return transactionRepository.save(transaction);
    }

    @Override
    public Long numberOfTransactions(){
        return transactionRepository.count();
    }

    @Override
    public List<Transaction> findAllTransactions(){
        return transactionRepository.findAll();
    }
}
