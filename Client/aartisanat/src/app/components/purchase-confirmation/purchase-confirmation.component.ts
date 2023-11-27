import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductsService } from 'src/app/services/product.service';
import { UsersService } from 'src/app/services/user.service';

@Component({
  selector: 'app-purchase-confirmation',
  templateUrl: './purchase-confirmation.component.html',
  styleUrls: ['./purchase-confirmation.component.scss']
})
export class PurchaseConfirmationComponent {
  data: any;
  constructor(
    private userService: UsersService,
    private productService: ProductsService,
    private http: HttpClient,
    private router: Router,
    private route: ActivatedRoute
) {
}

ngOnInit()
{
  this.route.params.subscribe(params => {
    const id = params['id'];
    this.productService.getProduct(id).subscribe({

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

  dashboardUser() {
    this.router.navigateByUrl('/dashboard');
}
dashboardSeller() {
    this.router.navigateByUrl('/sellerDashborad');
}
logout() {
    this.userService.logoutUser();
    sessionStorage.removeItem("user_id");
    this.router.navigateByUrl('/login');
}
goBack()
{
  this.router.navigateByUrl('/sellerDashborad');
}
printPage() {
  window.print();
  this.router.navigateByUrl('/sellerDashborad');
}
}
