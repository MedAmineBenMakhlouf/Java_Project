import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { loadStripe } from '@stripe/stripe-js';
import { Observable, interval, map, startWith, takeWhile, Subscription } from 'rxjs';
import { ParticipationsService } from 'src/app/services/participation.service';
import { ProductsService } from 'src/app/services/product.service';
import { UsersService } from 'src/app/services/user.service';
import { WalletsService } from 'src/app/services/wallet.service';

@Component({
  selector: 'app-participate',
  templateUrl: './participate.component.html',
  styleUrls: ['./participate.component.scss']
})
export class ParticipateComponent {
  amount: any;
  data: any;
  bid: any;
  currentDate: any;
  countdown?: Observable<string>;
  deleted: any;
withdrow: any;
  constructor(
    private participationService: ParticipationsService,
    private route: ActivatedRoute,
    private router: Router,
    private userService: UsersService,
    private productService: ProductsService,
    private walletService: WalletsService
  ) { }


  getCountdown(targetDate: Date): Observable<string> {
    return interval(1000).pipe(
      startWith(0),
      map(() => {
        const now = new Date();
        const diff = targetDate.getTime() - now.getTime();
        if (diff <= 0) {
          return '00:00:00';
        }
        return this.formatCountdown(diff);
      }),
      takeWhile(val => val !== '00:00:00', true)
    );
  }

  private formatCountdown(diff: number): string {
    const days = Math.floor(diff / (1000 * 60 * 60 * 24));
    const hours = Math.floor((diff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    const minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));
    const seconds = Math.floor((diff % (1000 * 60)) / 1000);
    return `${this.padZero(days)} day(s) ${this.padZero(hours)} hour(s) ${this.padZero(minutes)} minute(s) ${this.padZero(seconds)} second(s)`;
}

  private padZero(value: number): string {
    return value < 10 ? `0${value}` : `${value}`;
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      const id = params['id'];
      this.participationService.getParticipation(id).subscribe({
        next: (part) => {
          this.data = part;
          console.log(part);
          const targetDate = new Date(this.data.product.endTime);
          this.countdown = this.getCountdown(targetDate);
          console.log(this.countdown);
        },
        error: (error) => {
          console.error('There was an error!', error);
        }

      });
    });
  }

  saveBid(id: any) {
    console.log(this.bid);
    this.participationService.saveBid(this.bid, id).subscribe({
      next: (part) => {
        this.data = part;
        console.log(JSON.stringify(part));
        alert("Your bid has been submitted successfully");
        window.location.reload();
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
  editProduct(id: any) {
    this.router.navigateByUrl(`/product/${id}/edit`);
  }

  deleteProduct(id: any) {
    this.productService.deleteProduct(id).subscribe(
      {
        next: (response) => {
          this.deleted = response;
          alert("Your product has been deleted successfully");
          this.router.navigateByUrl("/sellerDashborad");
        },
        error: (error) => {
          console.log(error)
        }
      }
      )
      
  }
}
