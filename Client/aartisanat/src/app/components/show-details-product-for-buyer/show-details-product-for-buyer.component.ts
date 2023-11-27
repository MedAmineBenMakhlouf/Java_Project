import { Component, ElementRef, QueryList, ViewChildren, Inject,ClassProvider, FactoryProvider, InjectionToken, PLATFORM_ID } from '@angular/core';
import { gsap } from 'gsap';
import { ActivatedRoute, Router } from '@angular/router';
import { UsersService } from 'src/app/services/user.service';
import { debounceTime, fromEvent, map, startWith } from 'rxjs';
import { NgbCarouselConfig, NgbCarouselModule } from '@ng-bootstrap/ng-bootstrap';
import { ProductsService } from 'src/app/services/product.service';
import { loadStripe } from '@stripe/stripe-js';
import { WalletsService } from 'src/app/services/wallet.service';

@Component({
  selector: 'app-show-details-product-for-buyer',
  templateUrl: './show-details-product-for-buyer.component.html',
  styleUrls: ['./show-details-product-for-buyer.component.scss']
})
export class ShowDetailsProductForBuyerComponent {

  rep: any;
  amount: any;
  dashboardData: any;
withdrow: any;


  constructor( private router: Router,
    private userService: UsersService,
    private productService: ProductsService,
    private walletService : WalletsService,
    config: NgbCarouselConfig,
    private route: ActivatedRoute
    ) {
      config.interval = 10000;
      config.wrap = false;
      config.keyboard = false;
      config.pauseOnHover = false;
    }

    // images = ['https://img.freepik.com/photos-gratuite/peinture-lac-montagne-montagne-arriere-plan_188544-9126.jpg?w=1380&t=st=1700035279~exp=1700035879~hmac=217c00418e67918b159a17cf8f28b60a6d2c458dda887301a91f99f5eb89cb60',
    //  'https://img.freepik.com/photos-gratuite/belle-vue-glaciale-antarctique-pendant-journee_181624-61545.jpg?t=st=1699978804~exp=1699979404~hmac=cb637430390eacf2bb35bfd09838bc811c739c532f7d8a4084cb1854c480d0df'];// Array of image URLs
    // currentIndex = 0;
  
    // scrollLeft() {
    //   if (this.currentIndex > 0) this.currentIndex--;
    // }
  
    // scrollRight() {
    //   if (this.currentIndex < this.images.length - 1) this.currentIndex++;
    // }

    ngOnInit() {
      this.userService.getDashboard().subscribe({
        next: (data) => {
          this.dashboardData = data;
          console.log(this.dashboardData);
  
          console.log(sessionStorage.getItem('user_id'));
  
  
  
        },
        error: (error) => {
          console.error('There was an error!', error);
        }
      });
      this.route.params.subscribe(params => {
        const id = params['id'];
        console.log(id) // Get ID from route parameters
        this.productService.getMyProduct(id).subscribe({
          next: (data) => {
            this.rep = data;
            console.log(this.rep);
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
    logout() {
      this.userService.logoutUser();
      sessionStorage.removeItem("user_id");
      this.router.navigateByUrl('/register');
    }
  
    editProfle() {
      this.router.navigateByUrl(`/user/edit`);
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

    participate(id: any) {
      this.router.navigateByUrl(`/product/${id}/participate`);
    }
}
