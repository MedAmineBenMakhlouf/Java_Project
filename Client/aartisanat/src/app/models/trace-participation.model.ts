// trace-participation.model.ts
import { User } from './user.model'; // Assuming you have a User model
import { Product } from './product.model'; // Assuming you have a Product model

export class TraceParticipation {
  id?: number;
  buyer?: User;
  product?: Product;
  amount?: number;
  createdAt?: Date;
  updatedAt?: Date;

  constructor(init?: Partial<TraceParticipation>) {
    Object.assign(this, init);
  }
}
