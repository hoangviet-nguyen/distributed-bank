import type { Bank } from "./Interfaces";
import type { Account } from "./Interfaces";
import { ServletBank } from "./servlet/ServletBank";

let bank: Bank;

document.addEventListener("DOMContentLoaded", () => {
    console.log("Dom Ready")
    const selection = document.getElementById("selection") as HTMLSelectElement;

    const changeBank = (): void => {
        switch(selection.value) {
            case "http":
                bank = new ServletBank();
                break;
            default:
                bank = new ServletBank();
                break;
        }
    }
    
    selection.addEventListener("change", changeBank);
})
