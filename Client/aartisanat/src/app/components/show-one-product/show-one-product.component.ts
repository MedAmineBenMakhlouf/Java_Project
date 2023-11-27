import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { loadStripe } from '@stripe/stripe-js';
import { ProductsService } from 'src/app/services/product.service';
import { UsersService } from 'src/app/services/user.service';
import { WalletsService } from 'src/app/services/wallet.service';

@Component({
  selector: 'app-show-one-product',
  templateUrl: './show-one-product.component.html',
  styleUrls: ['./show-one-product.component.scss']
})
export class ShowOneProductComponent {
  resp: any;
  withdrow: any;

  editProfle() {
    throw new Error('Method not implemented.');
    }
    AddBalance() {
    throw new Error('Method not implemented.');
    }
      data: any;
    amount: any;
      constructor(
        private productService: ProductsService,
        private route: ActivatedRoute,
        private userService: UsersService,
        private router: Router,
        private walletService:WalletsService
      ) { }
    
      ngOnInit() {
    
        this.userService.getDashboard().subscribe({
          next: (data) => {
            this.resp = data;
            console.log(this.resp);
    
            console.log(sessionStorage.getItem('user_id'));
          },
          error: (error) => {
            console.error('There was an error!', error);
          }
        });
    
        this.route.params.subscribe(params => {
          const id = params['id']; // Get ID from route parameters
          this.productService.getMyProduct(id).subscribe({
            next: (data) => {
              this.data = data;
              console.log(this.data);
            },
            error: (error) => {
              console.error('There was an error!', error);
            }
          });
        });
      }
      dashboardUser() {
        this.router.navigateByUrl('/dashboard');
      }
      dashboardSeller() {
        this.router.navigateByUrl('/sellerDashborad');
      }
      logout()
      {
        this.userService.logoutUser();
        sessionStorage.removeItem("user_id");
        this.router.navigateByUrl('/register');
      }
      withdrawBalance() {
        console.log(this.withdrow);
        this.walletService.withdraw(this.withdrow).subscribe({
          next: async (response) => {
            // Load the Stripe.js library
            const stripe = await loadStripe('pk_test_51OAa7NDYAUChbq42qUbpfV0sCVxEL7NCccBnfuZRUyK5qG8jupanv6IHxa3erOvExCXALbddf2zDn4C10QZTEXh300mWuvmhUK');
            const sessionId = response.sessionId;
            if (stripe) {
              // Redirect to Stripe Checkout
              stripe.redirectToCheckout({ sessionId });
            } else {
              console.error('Stripe.js failed to load');
            }
          },
          error: (error) => {
            console.error('There was an error!', error);
          }
      
        });
      }
}
