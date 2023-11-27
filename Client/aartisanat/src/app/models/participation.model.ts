import { Product } from "./product.model";
import { User } from "./user.model";

// participation.model.ts
export class Participation {
    id?: number;
    // Omitting buyer and product in the TypeScript model, assuming they will be populated separately
    // and their full details are not needed in this context
    // buyer?: User;
    // product?: Product;
    buyer?: User;  // If you only need the ID for referencing
    product?: Product;  // If you only need the ID for referencing
    amount?: number;
    createdAt?: Date;
    updatedAt?: Date;
  
    constructor(init?: Partial<Participation>) {
      if (init) {
        Object.assign(this, init);
      }
    }
  }
  