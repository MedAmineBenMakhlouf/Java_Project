// product.model.ts
export class Product {
    id?: number;
    name?: string;
    description?: string;
    price?: number;
    duration?: number;
    status?: boolean;
    typeProduct?: boolean; // You might want to create an enum or separate model for this
    startTime?: Date;
    endTime?: Date;
    category?: string; // You might want to create an enum or separate model for this
    // In a real Angular application, the user might be a separate model object
    // and would be fetched with a separate service method, so it might be omitted here.
    // Same for files, participations, traceParticipations, etc.
    // If needed, they would be arrays of their respective model types.
    // Example:
    // user?: User;
    // files?: File[];
    // participations?: Participation[];
    // traceParticipations?: TraceParticipation[];
  
    // createdAt and updatedAt are typically managed by the backend,
    // and would not be included in the model used for POST/PUT requests,
    // though they may be present in models used to display data fetched from the backend.
    createdAt?: Date;
    updatedAt?: Date;
    user_id?:number;
  
    constructor(init?: Partial<Product>) {
      if (init) {
        Object.assign(this, init);
      }
    }
  }
  