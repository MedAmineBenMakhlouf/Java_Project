// wallet.model.ts
export class Wallet {
    id?: number;
    balance?: number;
    createdAt?: Date;
    updatedAt?: Date;
    userId?: number; // Assuming you want to keep a reference to the User ID
  
    // Optional: If you want to keep the full User object
    // user?: User; 
  
    constructor(init?: Partial<Wallet>) {
      Object.assign(this, init);
    }
  }