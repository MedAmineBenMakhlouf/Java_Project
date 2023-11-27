import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { loadStripe } from '@stripe/stripe-js';
import { ProductsService } from 'src/app/services/product.service';
import { UsersService } from 'src/app/services/user.service';
import { WalletsService } from 'src/app/services/wallet.service';
import { interval, Subscription } from 'rxjs';

@Component({
  selector: 'app-product-by-category-auction',
  templateUrl: './product-by-category-auction.component.html',
  styleUrls: ['./product-by-category-auction.component.scss']
})
export class ProductByCategoryAuctionComponent {
  amount:any;
  data: any;
  private toggleSubscription?: Subscription;
  isOn = false;
withdrow: any;
  constructor(
    private productService: ProductsService,
    private userService: UsersService,
    private walletService: WalletsService,
    private route: ActivatedRoute,
    private router: Router
  ) { }
  
  ngOnInit() {
    const toggleTimer = interval(900);

    // Subscribe to the observable
    this.toggleSubscription = toggleTimer.subscribe(() => {
      this.isOn = !this.isOn; // Toggle the state
    });
    this.route.params.subscribe(params => {
      const category = params['category'];
      this.productService.getByCategory(category).subscribe({

        next: (part) => {
          this.data = part;
          console.log(this.data);
        },
        error: (error) => {
          console.error('There was an error!', error);
        }

      });
    });
  }
  AddBalance() {
    console.log(this.amount);
    this.walletService.configWallet(this.amount).subscribe({
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
  showDetails(id:any)
  {
    this.router.navigateByUrl('/product/details/' + id);
  }
  participate(id: any) {
    this.router.navigateByUrl(`/product/${id}/participate`);
  }

  buy(id: any) {
    this.productService.buyProduct(id).subscribe({
      next: async (response) => {
        this.router.navigateByUrl(`/product/${id}/selled`);
      },
      error: (error) => {
        console.error('There was an error!', error);
      }
    });
  }

  editProfle()
{
  this.router.navigateByUrl(`/user/edit`);
}

auctions()
{
  this.route.params.subscribe(params => {
    const category = params['category'];

  this.router.navigateByUrl(`/product/${category}/auctions`);
  })
}
fixedPrice()
{
  this.route.params.subscribe(params => {
    const category = params['category'];

  this.router.navigateByUrl(`/product/${category}/fixedPrices`);
  })
}
}
