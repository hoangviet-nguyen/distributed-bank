import type { Account } from "../Interfaces";
import type { Bank } from "../Interfaces";

export class ServletBank implements Bank {
    
    public createAccount(): string {
        throw new Error("Method not implemented.");
    }

    public closeAccount(): boolean {
        throw new Error("Method not implemented.");
    }

    public getAccountNumbers(): string {
        throw new Error("Method not implemented.");
    }

    public getAccount(): Account {
        throw new Error("Method not implemented.");
    }

    public transfer(): void {
        throw new Error("Method not implemented.");
    }

}