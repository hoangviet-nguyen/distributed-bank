import type { Account } from "../Interfaces";

class ServletAccount implements Account {
    constructor(private owner: string, private number: string) {}
    
    public isActive(): boolean {
        throw new Error("Method not implemented.");
    }

    public deposit(): void {
        throw new Error("Method not implemented.");
    }

    public withdraw(): void {
        throw new Error("Method not implemented.");
    }

    public getBalance(): void {
        throw new Error("Method not implemented.");
    }

    public getOwner(): string {
        return this.owner;
    }

    public getNumber(): string {
        return this.number;
    }
}