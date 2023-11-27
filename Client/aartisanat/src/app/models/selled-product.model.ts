// selled-product.model.ts
import { User } from './user.model'; // Assuming you have a User model
import { Product } from './product.model'; // Assuming you have a Product model

export class SelledProduct {
  id?: number;
  createdAt?: Date;
  updatedAt?: Date;
  product?: Product;
  seller?: User;
  buyer?: User;

  constructor(init?: Partial<SelledProduct>) {
    if (init) {
      Object.assign(this, init);
    }
  }
}
