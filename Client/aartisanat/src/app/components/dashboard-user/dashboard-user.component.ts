import { ProductsService } from 'src/app/services/product.service';
import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UsersService } from 'src/app/services/user.service';
import { loadStripe } from '@stripe/stripe-js';
import { WalletsService } from 'src/app/services/wallet.service';

@Component({
  selector: 'app-dashboard-user',
  templateUrl: './dashboard-user.component.html',
  styleUrls: ['./dashboard-user.component.scss']
})
export class DashboardUserComponent {
  dashboardData: any;
  response: any;
  amount: any;
  wallet: any;
  accessories: string = "accessories";
  arts:string ="arts";
  woodworking: string = "woodworking";
  ceramics:string = "ceramics";
  textils: string = "textils";
withdrow: any;


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
        this.dashboardData = data;
        console.log(JSON.stringify(data));

        console.log(sessionStorage.getItem('user_id'));



      },
      error: (error) => {
        console.error('There was an error!', error);
      }
    });
  }
  showUser(id: any) {
    this.userService.getOneUser(id).subscribe({
      next: (data) => {

        // sessionStorage.setItem("user", JSON.stringify(data))
        this.router.navigateByUrl(`/user/${id}`);
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
  GoToAccessoires() {
    console.log()
    this.router.navigateByUrl(`/product/${this.accessories}/fixedPrices`);
  }
  GoToCeramics() {
    this.router.navigateByUrl(`/product/${this.ceramics}/fixedPrices`);
  }
  GoToTextils() {
    this.router.navigateByUrl(`/product/${this.textils}/fixedPrices`);
  }
  GoToArt() {
    this.router.navigateByUrl(`/product/${this.arts}/fixedPrices`);
  }
  GoToWoodWorking() {
    this.router.navigateByUrl(`/product/${this.woodworking}/fixedPrices`);
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
    this.router.navigateByUrl('/login');
  }

  editProfle()
{
  this.router.navigateByUrl(`/user/edit`);
}
}
