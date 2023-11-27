// role.model.ts
import { User } from './user.model'; // Assuming you have a User model

export class Role {
  id?: number;
  name?: string;
  users?: User[]; // You may decide not to include this in the frontend model

  constructor(init?: Partial<Role>) {
    if (init) {
      Object.assign(this, init);
    }
  }
}
