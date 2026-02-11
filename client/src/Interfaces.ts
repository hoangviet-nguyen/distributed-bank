export interface Account {
    isActive():     boolean;
    deposit():      void;
    withdraw():     void;
    getBalance():   void;
    getOwner():     string;
    getNumber():    string;
}

export interface Bank {
    createAccount():        string;
    closeAccount():         boolean;
    getAccountNumbers():    string;
    getAccount():           Account;
    transfer():             void;
}