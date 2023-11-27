import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { loadStripe } from '@stripe/stripe-js';
import { Subscription, interval, startWith } from 'rxjs';
import { Product } from 'src/app/models/product.model';
import { ProductsService } from 'src/app/services/product.service';
import { UsersService } from 'src/app/services/user.service';
import { WalletsService } from 'src/app/services/wallet.service';

@Component({
  selector: 'app-seller-dashboard',
  templateUrl: './seller-dashboard.component.html',
  styleUrls: ['./seller-dashboard.component.scss']
})
export class SellerDashboardComponent {
  data: any = {};
  amount: any;
  isOn = false;
  product:any;

  currentDate: Date = new Date();
  private toggleSubscription?: Subscription;
  response: any;
withdrow: any;
  constructor(private userService: UsersService,
    private walletService: WalletsService,
    private productService: ProductsService,
    private router: Router) { }


  ngOnInit() {
    const toggleTimer = interval(900);

    this.toggleSubscription = toggleTimer.subscribe(() => {
      this.isOn = !this.isOn; // Toggle the state
    
    });



    this.userService.sellerDashboard().subscribe({
      next: (data) => {
        this.data = data;
        console.log(JSON.stringify(this.data));
       
      },
      error: (error) => {
        console.error('There was an error!', error);
      }
    });

    interval(1000)
      .pipe(startWith(0)) 
      .subscribe(() => 
      {
        this.currentDate = new Date(); 
        console.log(this.data.participations==null ? "no participations" : "fama participation");
        if(this.data.participations!=null)
        {
        this.data.products.forEach((product: any) => {
          if(product.typeProduct==true)
          {
            let sysDate = this.currentDate.toLocaleDateString('en-US');
            let productTime = new Date(product.endTime); 
            let formattedDate = productTime.toLocaleDateString('en-US');
            // formattedDate<= sysDate ? console.log("prod date > sysdate") : console.log("prod date < sysdate")
            if(formattedDate<=sysDate)
            {
              
              console.log(JSON.stringify(product.id));
              this.productService.sellProduct(product.id).subscribe(
                {
                  next: (response) => {
                    this.response = response;
                    
                  },
                  error: (error) => {
                    console.error('There was an error!', error);
                  }
                }
              );
            }
          }
        });
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
dashboardUser() {
  this.router.navigateByUrl('/dashboard');
}
dashboardSeller() {
  this.router.navigateByUrl('/sellerDashborad');
}
logout() {
  this.userService.logoutUser();
  this.router.navigateByUrl('/register');
  sessionStorage.removeItem("user_id");
}
addProduct() {
  this.router.navigateByUrl('/addProduct');
}

showMyProduct(id:any)
{
  this.router.navigateByUrl(`/product/${id}/show`);
}


editProfle()
{
  this.router.navigateByUrl(`/user/edit`);
}
AddProduct()
{
  this.router.navigateByUrl(`/product/add`);
}

withdraw() {
  console.log(this.amount);
  this.walletService.withdraw(this.amount).subscribe({
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
deleteProduct(id:any)
{
  this.productService.deleteProduct(id).subscribe({
    next:(res)=>{
      console.log(res);
      alert("Deleted Successfully!");
    },
    error:(error)=>{
      console.log(error);
    }
  })
}
}
