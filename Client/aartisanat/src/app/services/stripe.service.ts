// import { Injectable } from '@angular/core';
// import { Stripe, StripeCardElement, StripeElements } from '@stripe/stripe-js';
// import { loadStripe, StripeCardElementOptions, StripeElementsOptions } from '@stripe/stripe-js/pure';

// @Injectable({
//   providedIn: 'root'
// })
// export class StripeService {
//   private stripe: Stripe;
//   private elements: StripeElements;

//   constructor() {
//     loadStripe('your_publishable_key').then((stripe) => {
//       this.stripe = stripe;
//       this.elements = stripe.elements();
//     });
//   }

//   createCardElement(options?: StripeCardElementOptions): StripeCardElement {
//     return this.elements.create('card', options);
//   }

//   async redirectToCheckout(sessionId: string) {
//     const { error } = await this.stripe.redirectToCheckout({ sessionId });
//     if (error) {
//       // Handle any errors here
//     }
//   }

//   // ... other methods like confirmCardPayment, createPaymentMethod, etc.
// }
