package App.service;

import App.entity.Account;
import App.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account findOrCreate(Account account) {
        return this.accountRepository.save(account);
    }

    public Optional<Account> findById(UUID id) {
        return this.accountRepository.findById(id);
    }
}
