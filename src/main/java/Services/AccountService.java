package Services;

import Domain.Account;
import Domain.UserType;
import Repo.AccountRepoInterface;

public class AccountService {

    private AccountRepoInterface accountRepo;

    public AccountService(AccountRepoInterface accountRepo) {
        this.accountRepo = accountRepo;
    }

    public void signUp(String username, String password, UserType userType){
        if(findAccount(username)==null)
            accountRepo.add(new Account(username, password, userType));
        else{
            System.out.println("Username utilizat!");
        }

    }

    public Account logIn(String username, String password) throws BadCredentialsException {
        Account account = findAccount(username);
        if(account!=null && account.getPassword().equals(password))
            return account;
        else{
            System.out.println("Parola gresita!");
            throw new BadCredentialsException("Username sau parola gresite!");
        }

    }

    public Account findAccount(String username){
        return accountRepo.getOne(username);
    }
}
