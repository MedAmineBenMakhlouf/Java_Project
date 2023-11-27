import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { loadStripe } from '@stripe/stripe-js';
import { ProductsService } from 'src/app/services/product.service';
import { UsersService } from 'src/app/services/user.service';
import { WalletsService } from 'src/app/services/wallet.service';

@Component({
  selector: 'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrls: ['./side-bar.component.scss']
})
export class SideBarComponent {
  data: any;
  amount: any;

  constructor(private userService: UsersService,
    private router: Router,
    private productService: ProductsService,
    private walletService: WalletsService,
    private route: ActivatedRoute,
  ) {

  }

  ngOnInit() {
    this.userService.getDashboard().subscribe({
      next: (data) => {
        this.data = data;
        console.log(JSON.stringify(data));

        console.log(sessionStorage.getItem('user_id'));
      },
      error: (error) => {
        console.error('There was an error!', error);
      }
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
  editProfle()
  {
    this.router.navigateByUrl(`/user/edit`);
  }
}
