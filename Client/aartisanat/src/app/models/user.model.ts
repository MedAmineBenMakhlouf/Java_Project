// user.model.ts
import { Role } from './role.model'; // Assuming you have a Role model
import { Wallet } from './wallet.model'; // Assuming you have a Wallet model
import { Product } from './product.model'; // Assuming you have a Product model
import { SelledProduct } from './selled-product.model'; // Assuming you have a SelledProduct model
import { Participation } from './participation.model'; // Assuming you have a Participation model
import { TraceParticipation } from './trace-participation.model';
export class User {
  id?: number;
  username?: string;
  email?: string;
  phone?: string;
  address?: string;
  password?: string;
  passwordConfirmation?: string;
  createdAt?: Date;
  updatedAt?: Date;
  role?: string;
  wallet?: Wallet;
  products?: Product[];
  buyers?: SelledProduct[];
  sellers?: SelledProduct[];
  participations?: Participation[];
  traceParticipations?: TraceParticipation[]; // Replace `any` with the appropriate model
  file_path?: string;

  constructor(init?: Partial<User>) {
    Object.assign(this, init);
  }
}
